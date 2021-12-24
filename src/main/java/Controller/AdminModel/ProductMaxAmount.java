package Controller.AdminModel;

public class ProductMaxAmount {
	private String productName;
	private int amount;
	public ProductMaxAmount(String productName, int amount) {
		super();
		this.productName = productName;
		this.amount = amount;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
