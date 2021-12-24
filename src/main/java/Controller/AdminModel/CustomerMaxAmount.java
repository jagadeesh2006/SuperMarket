package Controller.AdminModel;

public class CustomerMaxAmount {
	private String customerName;
	private int amount;
	public CustomerMaxAmount(String customerName, int amount) {
		super();
		this.customerName = customerName;
		this.amount = amount;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
