package com.tradeVault.Dao;

import java.util.List;


import org.springframework.stereotype.Repository;

import com.tradeVault.models.BeanResult;
import com.tradeVault.models.Company;
import com.tradeVault.models.Order;
import com.tradeVault.models.Product;

@Repository
public interface DaoObj {
	public BeanResult saveCompany(Company newComp);
	public void retrieveCompany(Company company);
	public BeanResult saveProduct(Product newProduct);
	public boolean retrieveProduct();
	public List<Product> retrieveAllProducts();
	public int getAlertCount(String parameter,String type);
	public List<String> retrieveLogistics();
	public BeanResult saveOrder(List<Order> order);
	public List<Order> viewOrders(String companyName, String companyType);
	public BeanResult editOrder(String[] chosenProducts);	
}
