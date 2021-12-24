package Controller.AdminModel;

import java.time.LocalDate;

public class SalesCount {
	private LocalDate date;
	private int BillCount;
	public SalesCount(LocalDate date, int billCount) {
		super();
		this.date = date;
		BillCount = billCount;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getBillCount() {
		return BillCount;
	}
	public void setBillCount(int billCount) {
		BillCount = billCount;
	}
}
