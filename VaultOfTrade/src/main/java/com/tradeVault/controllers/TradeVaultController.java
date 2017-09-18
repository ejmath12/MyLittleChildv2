package main.java.com.tradeVault.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import main.java.com.tradeVault.Bean.BusinessBeanInt;
import main.java.com.tradeVault.Utils.TradeVaultUtil;
import main.java.com.tradeVault.models.BeanResult;
import main.java.com.tradeVault.models.Order;
import main.java.com.tradeVault.models.Product;
import main.java.com.tradeVault.models.StatusEnum;



@Controller
public class TradeVaultController {
	@Autowired 
	@Qualifier("businessLogTyp")
	private BusinessBeanInt businessBean;

	@RequestMapping(value="/")  
	public ModelAndView tradeHome() {  
		return new ModelAndView("tradeVault"); 
	}


	@Autowired 
	private HttpSession httpSession;

	@RequestMapping(value="/signIn")  
	public ModelAndView signIn(HttpServletRequest request,HttpServletResponse response) {
		//checks if the any session is active
		if(request == null || request.getParameter("name") == null) {
			return tradeHome();
		}
		String companyName = request.getParameter("name");
		BeanResult busR = businessBean.processSignIn(request);
		if(busR.getStatus().equals(StatusEnum.FAILED))
			return new ModelAndView("tradeVault","generated",busR.getDescription());
		else {
			int alertCount = businessBean.getAlertsCount(request.getParameter("name"),busR.getDescription());
			Map<String, String> model = new HashMap<String, String>();
			model.put("generated",companyName );
			model.put("alert", String.valueOf(alertCount));
			httpSession.setAttribute("user",companyName);
			httpSession.setAttribute("alertCount",alertCount);
			httpSession.setAttribute("companyType",busR.getDescription());
			if(busR.getDescription().equals("MANUFACTURER"))
				return new ModelAndView("manuView","model", model);
			else if(busR.getDescription().equals("CLIENT"))
				return new ModelAndView("clientView","model", model);
			else 
				return new ModelAndView("logView","model", model);				
		}
	}

	@RequestMapping(value="/newProduct") 
	public ModelAndView newProduct(HttpServletRequest request,HttpServletResponse response) {
		if(httpSession.getAttribute("user") == null) {
			return tradeHome();
		}
		String companyType = httpSession.getAttribute("companyType").toString();
		if(!companyType.equals("MANUFACTURER")) {
			logout();
		}
		BeanResult busR = businessBean.processNewProduct(request,httpSession);
		Map<String, String> model = new HashMap<String, String>();
		model.put("generated",httpSession.getAttribute("user").toString());
		model.put("alert", httpSession.getAttribute("alertCount").toString());
		model.put("popUp",busR.getDescription());
		return new ModelAndView("manuView","model",model);  
	}

	@RequestMapping(value="/buyNow") 
	public ModelAndView saveOrder(HttpServletRequest request,HttpServletResponse response) {
		if(httpSession.getAttribute("user") == null) {
			return tradeHome();
		}
		String companyType = httpSession.getAttribute("companyType").toString();
		if(!companyType.equals("CLIENT")) {
			logout();
		}
		BeanResult busR= businessBean.saveOrder(request,httpSession);
		Map<String, String> model = new HashMap<String, String>();
		model.put("generated",httpSession.getAttribute("user").toString());
		model.put("alert",httpSession.getAttribute("alertCount").toString());
		model.put("popUp", busR.getDescription());
		return new ModelAndView("clientView","model", model);
	}


	@RequestMapping(value="/orderView")
	public ModelAndView viewOrders() {
		if(httpSession.getAttribute("user") == null) {
			return tradeHome();
		}
		httpSession.setAttribute("alertCount",0);
		Map<String, Object> model = new HashMap<String, Object>();
		List<Order> orders= businessBean.viewOrders(httpSession);
		String companyType = httpSession.getAttribute("companyType").toString();
		List<Order> updateOrders = new ArrayList<Order>();
		List<Order> normalOrders = new ArrayList<Order>();
		List<Order> attnOrders = TradeVaultUtil.extractOrders(orders,updateOrders, normalOrders, companyType);
		if(attnOrders.size()>0) {
			model.put("orders", attnOrders);
			if(companyType.equals("MANUFACTURER")) {
				model.put("logAlert","Select Logistics Provider:");
				model.put("logistics", businessBean.retrieveLogistics());
				model.put("edit", "<a href=\"\" onclick=\"editStuff();return false;\"><h4>Update Status</h4></a>");
			} else
				model.put("edit", "<a href=\"\" onclick=\"editStuffLog();return false;\"><h4>Update Status</h4></a>");

		}
		if(updateOrders.size()>0) {
			model.put("upOrders", updateOrders);
		}
		if(normalOrders.size()>0) {
			model.put("norOrders", normalOrders);
		}
		return new ModelAndView("ordersView","model",model);  		
	}


	@RequestMapping(value="/editOrder") 
	public ModelAndView editOrder(HttpServletRequest request) {
		if(httpSession.getAttribute("user") == null) {
			return tradeHome();
		}
		BeanResult busR = businessBean.editOrderBL(request);
		Map<String, String> model = new HashMap<String, String>();
		model.put("generated",httpSession.getAttribute("user").toString());
		model.put("alert", httpSession.getAttribute("alertCount").toString());
		model.put("popUp",busR.getDescription());
		String companyType = httpSession.getAttribute("companyType").toString();
		if(companyType.equals("MANUFACTURER"))
			return new ModelAndView("manuView","model", model);
		else if(companyType.equals("CLIENT"))
			return new ModelAndView("clientView","model", model);
		else 
			return new ModelAndView("logView","model", model);				
	}


	@RequestMapping(value="/viewProduct")
	public ModelAndView viewProducts() {
		if(httpSession.getAttribute("user") == null) {
			return tradeHome();
		}
		String companyType = httpSession.getAttribute("companyType").toString();
		if(!companyType.equals("CLIENT")) {
			logout();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		List<Product> prodList = businessBean.viewAllProducts();
		if(prodList.size()==0) {
			model.put("generated", "Manufacturers are lazy! No products available");
		} else {
			model.put("generated", "Available Products");
			model.put("products", prodList);
			model.put("buy", "Buy Now");
		}
		return new ModelAndView("prodView","model",model);  		
	}


	@RequestMapping(value="/signUp")  
	public ModelAndView signUp(HttpServletRequest request,HttpServletResponse response) {  
		if(request == null || request.getParameter("companyName") == null) {
			return tradeHome();
		}
		BeanResult busR = businessBean.processNewCompany(request);
		return new ModelAndView("tradeVault","generated",busR.getDescription());  
	}
	@RequestMapping(value="/logOut")  
	public ModelAndView logout() {
		httpSession.invalidate();
		return tradeHome();
	}
}  

