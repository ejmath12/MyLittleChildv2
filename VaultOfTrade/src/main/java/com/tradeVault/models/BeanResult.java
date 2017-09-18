package main.java.com.tradeVault.models;

public class BeanResult {
	private StatusEnum status;
	private String description;
	public BeanResult() {}

	public BeanResult(StatusEnum status,String description) {
		this.status = status;
		this.description = description;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String exception) {
		this.description = exception;
	}
}
