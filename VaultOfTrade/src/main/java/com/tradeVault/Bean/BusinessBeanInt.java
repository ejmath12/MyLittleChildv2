package main.java.com.tradeVault.Bean;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import main.java.com.tradeVault.models.BeanResult;
import main.java.com.tradeVault.models.Order;
import main.java.com.tradeVault.models.Product;

@Service
public interface BusinessBeanInt {
	public BeanResult processNewCompany(HttpServletRequest req);
	public BeanResult processSignIn(HttpServletRequest req);
	public BeanResult processNewProduct(HttpServletRequest request, HttpSession httpSession);
	public int getAlertsCount(String parameter, String string);
	public List<Product> viewAllProducts();
	public List<String> retrieveLogistics();
	public BeanResult saveOrder(HttpServletRequest request, HttpSession httpSession);
	public List<Order> viewOrders(HttpSession httpSession);
	public BeanResult editOrderBL(HttpServletRequest request);
}
