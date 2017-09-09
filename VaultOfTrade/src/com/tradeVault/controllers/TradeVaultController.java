package com.tradeVault.controllers;


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

import com.tradeVault.Bean.BusinessBeanInt;
import com.tradeVault.Utils.TradeVaultUtil;
import com.tradeVault.models.BeanResult;
import com.tradeVault.models.Order;
import com.tradeVault.models.StatusEnum;
import com.tradeVault.models.Product;



@Controller
public class TradeVaultController {
	@Autowired 
	@Qualifier("businessLogTyp")
	private BusinessBeanInt businessBean;

	@RequestMapping(value="/")  
	public ModelAndView tradeHome() {  
		return new ModelAndView("tradeVault"); 
	}

	private static List<String> usersOnline = new ArrayList<String>();

	@Autowired 
	private HttpSession httpSession;

	@RequestMapping(value="/signIn")  
	public ModelAndView signIn(HttpServletRequest request,HttpServletResponse response) {
		String companyName = request.getParameter("name");
		if(usersOnline.contains(companyName)) {
			return new ModelAndView("tradeVault","generated","<script>window.onload = function() {alert('Another session active for same user. Please try later');}</script>");
		}
		BeanResult busR = businessBean.processSignIn(request);
		if(busR.getStatus().equals(StatusEnum.FAILED))
			return new ModelAndView("tradeVault","generated",busR.getDescription());
		else {
			int alertCount = businessBean.getAlertsCount(request.getParameter("name"),busR.getDescription());
			Map<String, String> model = new HashMap<String, String>();
			model.put("generated",companyName );
			model.put("alert", String.valueOf(alertCount));
			//usersOnline.add(companyName);
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
			tradeHome();
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
			tradeHome();
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
			tradeHome();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		List<Order> orders= businessBean.viewOrders(httpSession);
		for(Order order:orders) {
			System.out.println(order.getProductName());
		}
		if(orders.size()==0) {
			model.put("generated", "Shhh! No active orders");
		} else {
			String companyType = httpSession.getAttribute("companyType").toString();
			List<Order> updateOrders = new ArrayList<Order>();
			List<Order> normalOrders = new ArrayList<Order>();
			List<Order> attnOrders = TradeVaultUtil.extractOrders(orders,updateOrders, normalOrders, companyType);
			model.put("type",companyType);
			if(attnOrders.size()>0) {
				model.put("orders", attnOrders);
				model.put("edit", "<a href=\"\" onclick=\"editStuff();return false;\"><h4>Update Status</h4></a>");
			}
			if(updateOrders.size()>0) {
				model.put("upOrders", updateOrders);
			}
			if(normalOrders.size()>0) {
				model.put("norOrders", normalOrders);
			}
		}
		return new ModelAndView("ordersView","model",model);  		
	}


	@RequestMapping(value="/editOrder") 
	public ModelAndView editOrder(HttpServletRequest request) {
		if(httpSession.getAttribute("user") == null) {
			tradeHome();
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
			tradeHome();
		}
		String companyType = httpSession.getAttribute("companyType").toString();
		if(!companyType.equals("CLIENT")) {
		    logout();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		List<Product> prodList = businessBean.viewAllProducts();
		for(Product prod:prodList) {
			System.out.println(prod.getProductName());
		}
		if(prodList.size()==0) {
			model.put("generated", "Manufacturers are lazy! No products available");
		} else {
			model.put("logAlert","Select Logistics Provider:");
			model.put("generated", "Available Products");
			model.put("products", prodList);
			model.put("buy", "Buy Now");
			model.put("logistics", businessBean.retrieveLogistics());
		}
		return new ModelAndView("prodView","model",model);  		
	}


	@RequestMapping(value="/signUp")  
	public ModelAndView signUp(HttpServletRequest request,HttpServletResponse response) {  
		BeanResult busR = businessBean.processNewCompany(request);
		return new ModelAndView("tradeVault","generated",busR.getDescription());  
	}
	@RequestMapping(value="/logOut")  
	public ModelAndView logout() {
		httpSession.invalidate();
		return tradeHome();
	}
}  

