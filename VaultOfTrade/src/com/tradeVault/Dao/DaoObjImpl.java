package com.tradeVault.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.tradeVault.Utils.TradeVaultUtil;
import com.tradeVault.models.BeanResult;
import com.tradeVault.models.Company;
import com.tradeVault.models.CompanyTypeEnum;
import com.tradeVault.models.Order;
import com.tradeVault.models.Product;
import com.tradeVault.models.StatusEnum;

@Repository("mySql")
public class DaoObjImpl implements DaoObj {

	@Autowired
	@Qualifier("mySqlDS")
	private DataSource dataSource;

	@Override
	public BeanResult saveCompany(Company company) {
		String query = "insert into company (Company_Name, Password, Email_Id, Industry) values (?,?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, company.getCompanyName());
			ps.setString(2, company.getPassWord());
			ps.setString(3, company.getEmailId());
			ps.setString(4, company.getType().name());
			int out = ps.executeUpdate();
			if(out !=0){
				return new BeanResult(StatusEnum.SUCCESS, "<script>window.onload = function() {alert('Company Successfully registered. Please SignIn!');}</script>");
			} else 
				return new BeanResult(StatusEnum.FAILED, "<script>window.onload = function() {alert('Company already exists! Please use a different name');}</script>");
		}catch(MySQLIntegrityConstraintViolationException ex) {
			return new BeanResult(StatusEnum.FAILED, "<script>window.onload = function() {alert('Company already exists! Please use a different name');}</script>");
		}catch(SQLException e){
			return new BeanResult(StatusEnum.FAILED, "<script>window.onload = function() {alert('Company Save Failed with error "+e.getErrorCode()+"!');}</script>");	
		} finally{
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void retrieveCompany(Company company) {
		String query = "select Industry from company where Company_Name = ? and Password = ? ";
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, company.getCompanyName());
			ps.setString(2, company.getPassWord());
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				company.setType(CompanyTypeEnum.valueOf(rs.getString("Industry")));
				System.out.println("Company retrieved");
			}else System.out.println("No company found");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public List<String> retrieveLogistics() {
		String query = "select Company_Name from company where Industry='LOGISTICS' ";
		Connection con = null;
		PreparedStatement ps = null;
		List <String> logList = new ArrayList<String>();
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				logList.add(rs.getString("Company_Name"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				ps.close();
				con.close();
				return logList;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return logList;

	}

	@Override
	public int getAlertCount(String compName,String type) {
		StringBuilder query = new StringBuilder("select count(*) as total_count from orders where ");
		switch(type) {
		case "MANUFACTURER": query.append("Manufacture_Id = ? and Update_Seen not like '%M%'");
		break;
		case "CLIENT": query.append("Client_Id = ? and Update_Seen not like '%C%'");
		break;
		case "LOGISTICS": query.append("logistics = ? and Update_Seen not like '%L%'");
		break;
		}
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query.toString());
			ps.setString(1, compName);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt("total_count");
			}else return 0;
		}catch(SQLException e){
			e.printStackTrace();
			return 0;
		}finally{
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public BeanResult saveProduct(Product product) {
		String query = "insert into product (Product_Name, Company_Name, Features, Price, Availability, discount) values (?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, product.getProductName());
			ps.setString(2, product.getCompanyName());
			ps.setString(3, product.getFeatures());
			ps.setDouble(4, product.getPrice());
			ps.setInt(5, product.getAvailability());
			ps.setInt(6, product.getDiscount());
			int out = ps.executeUpdate();
			if(out !=0){
				return new BeanResult(StatusEnum.SUCCESS, "<script>window.onload = function() {alert('Product Successfully Added!');}</script>");
			} else 
				return new BeanResult(StatusEnum.FAILED, "<script>window.onload = function() {alert('Product already exists! Please check');}</script>");
		}catch(MySQLIntegrityConstraintViolationException ex) {
			return new BeanResult(StatusEnum.FAILED, "<script>window.onload = function() {alert('Product already exists! Please check');}</script>");
		}catch(SQLException e){
			return new BeanResult(StatusEnum.FAILED, "<script>window.onload = function() {alert('Product Save Failed with error "+e.getErrorCode()+"!');}</script>");	
		} finally{
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean retrieveProduct() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Product> retrieveAllProducts() {
		List<Product> prodList = new ArrayList<Product>();
		String query = "select Product_Name,Company_Name,Features,Price,Availability,discount from product";
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				prodList.add(TradeVaultUtil.convertResultToProduct(rs));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				ps.close();
				con.close();
				return prodList;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return prodList;
	}


	@Override
	public BeanResult saveOrder(List<Order> orders) {
		Connection con = null;
		PreparedStatement ps = null;
		String query = "insert into orders (Manufacture_Id, Client_Id, Product_Name, Progress_State, Update_Seen, logistics) values (?,?,?,?,?,?)";
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			for(Order order : orders) {
				System.out.println(order.getManName());
				ps.setString(1, order.getManName());
				ps.setString(2, order.getClientName());
				ps.setString(3, order.getProductName());
				ps.setString(4, order.getProgressState());
				ps.setString(5, order.getUpdateSeen());
				ps.setString(6, order.getLogistics());
				ps.addBatch();
			}
			int out[] = ps.executeBatch();
			for(int output : out) {
				if(output==0)
					return new BeanResult(StatusEnum.FAILED, "<script>window.onload = function() {alert('Couldnt Save Order! Please check');}</script>");
			}
		}catch(MySQLIntegrityConstraintViolationException ex) {
			return new BeanResult(StatusEnum.FAILED, "<script>window.onload = function() {alert('Product already exists! Please check');}</script>");
		}catch(SQLException e){
			return new BeanResult(StatusEnum.FAILED, "<script>window.onload = function() {alert('Product Save Failed with error "+e.getErrorCode()+"!');}</script>");	
		} finally{
			try {
				ps.close();
				con.close();
				return new BeanResult(StatusEnum.SUCCESS, "<script>window.onload = function() {alert('Order Successfully Saved!');}</script>");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		return new BeanResult(StatusEnum.SUCCESS, "<script>window.onload = function() {alert('Order Successfully Saved!');}</script>");
	}



	@Override
	public List<Order> viewOrders(String companyName, String companyType) {
		List<Order> orders = new ArrayList<Order>();
		StringBuilder checkSeen =new StringBuilder("update orders set ");
		StringBuilder query = new StringBuilder("select Manufacture_Id, Client_Id, Product_Name, Progress_State,logistics,Update_Seen,Order_Id from orders where ");
		switch(companyType) {
		case "MANUFACTURER": query.append("Manufacture_Id = ?");
		checkSeen.append("Update_Seen=CONCAT(Update_Seen, 'M') where Manufacture_Id=? and Update_Seen not like '%M%'");
		break;
		case "CLIENT": query.append("Client_Id = ?");
		checkSeen.append("Update_Seen=CONCAT(Update_Seen, 'C') where Client_Id=? and Update_Seen not like '%C%'");
		break;
		case "LOGISTICS": query.append("logistics = ?");
		checkSeen.append("Update_Seen=CONCAT(Update_Seen, 'L') where logistics=? and Update_Seen not like '%L%'");
		break;
		}
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;

		try{
			con = dataSource.getConnection();
			ps = con.prepareStatement(query.toString());
			ps1 = con.prepareStatement(checkSeen.toString());
			ps.setString(1, companyName);
			ps1.setString(1, companyName);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				orders.add(TradeVaultUtil.convertResultToOrders(rs));
			}
			ps1.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				ps1.close();
				ps.close();
				con.close();
				return orders;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return orders;

	}

	@Override
	public BeanResult editOrder(String[] chosenProducts) {
		Connection con = null;
		List<Integer> orderId = new ArrayList<Integer>();
		PreparedStatement ps = null;
		String query = "update orders set Progress_State =?,Update_Seen =? where Order_Id=?";
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			for(String s: chosenProducts) {
				System.out.println(s.split("#")[1]);
				switch(s.split("#")[1]) {
				case "PLACED": ps.setString(1, "DISPATCHED");
				ps.setString(2, "M");
				orderId.add(Integer.valueOf(s.split("#")[0]));
				System.out.println(Integer.valueOf(s.split("#")[0]));
				break;
				case "DISPATCHED": ps.setString(1, "TRANSIT");
				ps.setString(2, "L");
				break;
				case "TRANSIT": ps.setString(1, "DELIVERED");
				ps.setString(2, "L");
				break;		
				}
				ps.setInt(3, Integer.valueOf(s.split("#")[0]));
				ps.addBatch();
			}
			int out[] = ps.executeBatch();
			for(int output : out) {
				if(output==0)
					return new BeanResult(StatusEnum.FAILED, "<script>window.onload = function() {alert('Couldnt Save Order! Please check');}</script>");
			}
		}catch(MySQLIntegrityConstraintViolationException ex) {
			return new BeanResult(StatusEnum.FAILED, "<script>window.onload = function() {alert('Product already exists! Please check');}</script>");
		}catch(SQLException e){
			return new BeanResult(StatusEnum.FAILED, "<script>window.onload = function() {alert('Product Save Failed with error "+e.getErrorCode()+"!');}</script>");	
		} finally{
			try {
				ps.close();
				con.close();
				if(orderId.size()>0)
					updateProductCount(orderId);
				return new BeanResult(StatusEnum.SUCCESS, "<script>window.onload = function() {alert('Order Successfully Updated!');}</script>");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		return new BeanResult(StatusEnum.SUCCESS, "<script>window.onload = function() {alert('Order Successfully Updated!');}</script>");
	}

	private void updateProductCount(List<Integer> orderId) {
		Connection con = null;
		PreparedStatement ps = null;
		String query = "update product set Availability=Availability-1 where (Product_Name,Company_Name) in (select Product_name,Manufacture_Id from orders where Order_Id = ?) and Availability>0;";
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			for(Integer id :orderId) {
				ps.setInt(1, id);
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}


}
