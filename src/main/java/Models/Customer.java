package Models;

public class Customer {
	private int customerId;
	private String name;
	private long phoneNum;
	public Customer(int customerId, String name, long phoneNumber) {
		this.customerId = customerId;
		this.name = name;
		this.phoneNum = phoneNumber;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(long phoneNum) {
		this.phoneNum = phoneNum;
	}

}
