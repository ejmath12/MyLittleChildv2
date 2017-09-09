package com.tradeVault.models;

public class Company {
	private String companyName;
	private String passWord;
	private String emailId;
	private CompanyTypeEnum type;
	
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public CompanyTypeEnum getType() {
		return type;
	}
	public void setType(CompanyTypeEnum type) {
		this.type = type;
	}
}
