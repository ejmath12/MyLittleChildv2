package main.java.com.tradeVault.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import main.java.com.tradeVault.models.Company;
import main.java.com.tradeVault.models.CompanyTypeEnum;
import main.java.com.tradeVault.models.Order;
import main.java.com.tradeVault.models.Product;

public class TradeVaultUtil {

	public static Company convertReqToCompany(HttpServletRequest req) {
		Company company = new Company();
		company.setCompanyName(req.getParameter("companyName"));
		company.setEmailId(req.getParameter("companyEmail"));
		company.setPassWord(generateHash(req.getParameter("passwordNew")));
		switch(req.getParameter("companyType")) {
		case "Manufacturer": company.setType(CompanyTypeEnum.MANUFACTURER);
		break;
		case "Client":company.setType(CompanyTypeEnum.CLIENT);
		break;
		case "Logistics":company.setType(CompanyTypeEnum.LOGISTICS);
		break;
		}
		return company;
	}

	public static Company getCompanyKeys(HttpServletRequest req) {
		Company company = new Company();
		company.setCompanyName(req.getParameter("name"));
		company.setPassWord(generateHash(req.getParameter("signinpass")));
		return company;
	}


	private static String generateHash(String input) {
		StringBuilder hash = new StringBuilder();

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] hashedBytes = sha.digest(input.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f' };
			for (int idx = 0; idx < hashedBytes.length; ++idx) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (NoSuchAlgorithmException e) {
			// handle error here.
		}

		return hash.toString();
	}

	public static Product convertReqToProduct(HttpServletRequest request) {
		Product product = new Product();
		product.setProductName(request.getParameter("prodName"));
		product.setFeatures(request.getParameter("Features"));
		product.setPrice(Double.valueOf(request.getParameter("Price")));
		product.setAvailability(Integer.valueOf(request.getParameter("Availability")));
		if(request.getParameter("discount") == null || request.getParameter("discount") == "")
			product.setDiscount(0);
		else
			product.setDiscount(Integer.valueOf(request.getParameter("discount")));
		return product; 
	}

	public static Product convertResultToProduct(ResultSet rs) {
		Product product = new Product();
		try {
			product.setProductName(rs.getString("Product_Name"));
			product.setCompanyName(rs.getString("Company_Name"));
			product.setFeatures(rs.getString("Features"));
			product.setPrice(rs.getDouble("Price"));
			product.setAvailability(rs.getInt("Availability"));
			product.setDiscount(rs.getInt("discount"));
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return product;

	}

	public static List<Order> convertReqToOrder(HttpServletRequest request, HttpSession httpSession) {
		String[] chosenProducts = request.getParameterValues("chosen_product");
		List<Order> orders = new ArrayList<Order>();
		for(String product : chosenProducts) {
			Order order = new Order();
			order.setManName(product.split("#")[1]);
			order.setProductName(product.split("#")[0]);
			order.setClientName(httpSession.getAttribute("user").toString());
			order.setProgressState("PLACED");
			order.setUpdateSeen("C");
			orders.add(order);
		}
		return orders;
	}

	public static Order convertResultToOrders(ResultSet rs) {
		Order order = new Order();
		try {
			order.setClientName(rs.getString("Client_Id"));
			order.setLogistics(rs.getString("logistics"));
			order.setManName(rs.getString("Manufacture_Id"));
			order.setProductName(rs.getString("Product_Name"));
			order.setProgressState(rs.getString("Progress_State"));
			order.setUpdateSeen(rs.getString("Update_Seen"));
			order.setOrderId(rs.getInt("Order_Id"));
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return order;
	}

	public static List<Order> extractOrders(List<Order> orders,List<Order> updateOrders, List<Order> normalOrders,String companyType) {
		List<Order> attentionOrder = new ArrayList<Order>();
		for(Order order: orders) {
			String progressState = order.getProgressState();
			switch(companyType) {
			case "MANUFACTURER": if(progressState.equals("PLACED")) {
				attentionOrder.add(order);
			} else {
				if(!order.getUpdateSeen().contains("M")){
					updateOrders.add(order);
				} else {
					normalOrders.add(order);
				}
			}
			break;
			case "CLIENT":if(!order.getUpdateSeen().contains("C")){
				updateOrders.add(order);
			} else {
				normalOrders.add(order);
			}
			break;
			case "LOGISTICS": if(progressState.equals("DISPATCHED") || progressState.equals("TRANSIT")) {
				attentionOrder.add(order);
			} else {
				if(!order.getUpdateSeen().contains("L")){
					updateOrders.add(order);
				} else {
					normalOrders.add(order);
				}
			}
			break;
			}
		}
		return attentionOrder;
	}
}

