package main.java.com.tradeVault.Bean;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import main.java.com.tradeVault.Dao.DaoObj;
import main.java.com.tradeVault.Utils.TradeVaultUtil;
import main.java.com.tradeVault.models.BeanResult;
import main.java.com.tradeVault.models.Company;
import main.java.com.tradeVault.models.CompanyTypeEnum;
import main.java.com.tradeVault.models.Order;
import main.java.com.tradeVault.models.Product;
import main.java.com.tradeVault.models.StatusEnum;


@Service(value="busns")
public class BusinessBeanImpl implements BusinessBeanInt {

	@Autowired 
	@Qualifier("dataBaseTyp")
	private DaoObj daoObj;
	
	public List<String> retrieveLogistics(){
		return daoObj.retrieveLogistics();
	}


	@Override
	public BeanResult processNewCompany(HttpServletRequest req) {
		Company newComp = TradeVaultUtil.convertReqToCompany(req);
		return daoObj.saveCompany(newComp);	
	}
	@Override
	public int getAlertsCount(String parameter,String type) {
		return daoObj.getAlertCount(parameter,type);
	}

	@Override
	public BeanResult processSignIn(HttpServletRequest req) {
		Company comRetrieved = TradeVaultUtil.getCompanyKeys(req);
		daoObj.retrieveCompany(comRetrieved);
		if(comRetrieved.getType()!=null) {
			if(comRetrieved.getType().equals(CompanyTypeEnum.MANUFACTURER)) {
				return new BeanResult(StatusEnum.SUCCESS,"MANUFACTURER");
			}
			if(comRetrieved.getType().equals(CompanyTypeEnum.CLIENT)) {
				return new BeanResult(StatusEnum.SUCCESS,"CLIENT");
			}
			if(comRetrieved.getType().equals(CompanyTypeEnum.LOGISTICS)) {
				return new BeanResult(StatusEnum.SUCCESS,"LOGISTICS");
			}
		}
		return new BeanResult(StatusEnum.FAILED,"<script>window.onload = function() {alert('User or password incorrect');}</script>");
	}

	
	@Override
	public BeanResult processNewProduct(HttpServletRequest request, HttpSession httpSession) {
		Product newProduct = TradeVaultUtil.convertReqToProduct(request);
		newProduct.setCompanyName(httpSession.getAttribute("user").toString());
		return daoObj.saveProduct(newProduct);
	}
	@Override
	public List<Product> viewAllProducts() {
		return daoObj.retrieveAllProducts();
	}


	@Override
	public BeanResult saveOrder(HttpServletRequest request,HttpSession httpSession) {
		List <Order> order = TradeVaultUtil.convertReqToOrder(request,httpSession);
		BeanResult br=daoObj.saveOrder(order);
		return br;
	}


	@Override
	public List<Order> viewOrders(HttpSession httpSession) {
		String companyName = httpSession.getAttribute("user").toString();
		String companyType = httpSession.getAttribute("companyType").toString();
		return daoObj.viewOrders(companyName,companyType);
	}


	@Override
	public BeanResult editOrderBL(HttpServletRequest request) {
		String[] chosenProducts = request.getParameterValues("chosen_order");
		String logistics = request.getParameter("chosen_logistics");
		BeanResult br=daoObj.editOrder(chosenProducts,logistics);
		return br;

	}

}
