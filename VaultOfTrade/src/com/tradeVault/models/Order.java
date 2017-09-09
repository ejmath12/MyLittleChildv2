package com.tradeVault.models;

public class Order {
 private String manName;
 private String clientName;
 private String productName;
 private String progressState;
 private String updateSeen;
 private String logistics;
 private int orderId;

 
public String getManName() {
	return manName;
}
public void setManName(String manName) {
	this.manName = manName;
}
public String getClientName() {
	return clientName;
}
public void setClientName(String clientName) {
	this.clientName = clientName;
}
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}
public String getProgressState() {
	return progressState;
}
public void setProgressState(String progressState) {
	this.progressState = progressState;
}
public String getUpdateSeen() {
	return updateSeen;
}
public void setUpdateSeen(String updateSeen) {
	this.updateSeen = updateSeen;
}
public String getLogistics() {
	return logistics;
}
public void setLogistics(String logistics) {
	this.logistics = logistics;
}
public void setOrderId(int orderId) {
	this.orderId = orderId;	
}
public int getOrderId() {
	return orderId;
}
 
 
}
