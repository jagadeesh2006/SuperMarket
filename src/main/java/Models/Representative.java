package Models;

import java.time.LocalDate;

public class Representative {
	private int repId;
	private String name;
	private long phoneNum;
	private String designation;
	private LocalDate DOJ;
	private int salary;
	
	public Representative(int repId, String name, long phoneNum, String designation, LocalDate dOJ, int salary) {
		super();
		this.repId = repId;
		this.name = name;
		this.phoneNum = phoneNum;
		this.designation = designation;
		DOJ = dOJ;
		this.salary = salary;
	}
	
	public int getRepId() {
		return repId;
	}
	public void setRepId(int repId) {
		this.repId = repId;
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
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public LocalDate getDOJ() {
		return DOJ;
	}
	public void setDOJ(LocalDate dOJ) {
		DOJ = dOJ;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
}
