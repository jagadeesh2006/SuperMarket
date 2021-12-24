package Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bill {
	private int billNum;
	private int customerId;
	private int repId;
	private LocalDate date;
	private int totalAmount;
	private int discount;
	private int netAmount;
	private String modeOfTransaction;
	private List<Stock> products = new ArrayList<Stock>();
	public Bill(int billNum, int customerId, int repId, LocalDate date, int totalAmount, int discount, int netAmount,
			String modeOfTransaction,List<Stock> products) {
		super();
		this.billNum = billNum;
		this.customerId = customerId;
		this.repId = repId;
		this.date = date;
		this.totalAmount = totalAmount;
		this.discount = discount;
		this.netAmount = netAmount;
		this.modeOfTransaction = modeOfTransaction;
		this.products.addAll(products);
	}
	public int getBillNum() {
		return billNum;
	}
	public void setBillNum(int billNum) {
		this.billNum = billNum;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getRepId() {
		return repId;
	}
	public void setRepId(int repId) {
		this.repId = repId;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public int getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(int netAmount) {
		this.netAmount = netAmount;
	}
	public String getModeOfTransaction() {
		return modeOfTransaction;
	}
	public void setModeOfTransaction(String modeOfTransaction) {
		this.modeOfTransaction = modeOfTransaction;
	}
	public List<Stock> getProducts() {
		return products;
	}
	public void setProducts(List<Stock> products) {
		this.products = products;
	}
	
}
