package Controller.AdminModel;

public class CustomerMaxBill {
	private String CustomerName;
	private int count;
	public CustomerMaxBill(String customerName, int count) {
		super();
		CustomerName = customerName;
		this.count = count;
	}
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
