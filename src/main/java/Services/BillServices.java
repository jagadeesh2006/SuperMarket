package Services;
import DatabaseHandler.*;
import Models.Bill;
import Models.RepCountByRep;
import Models.Stock;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class BillServices {
	public static Connection getDbConnection() {
		return DatabaseConnection.getConnection();		
	}
	public Bill generateBill(long customerNumber,int rep_id,String products[],String quantitys[],int discount,String modeOfTransaction) throws SQLException {
		
		PreparedStatement pstm = null;
		Bill bill=null;
		try(Connection con = getDbConnection()){
			String query = "SELECT CUSTOMER_ID FROM CUSTOMERS WHERE PHONE_NUMBER="+customerNumber;
			Statement stm = con.createStatement();
			ResultSet cusRs = stm.executeQuery(query);
			int customerId=0;
			if(cusRs.next()) {
				customerId = cusRs.getInt(1);
			}
			stm.close();
			LocalDate date  = LocalDate.now();
			Date sqldate = Date.valueOf(date);
			query = "INSERT INTO BILL(CUSTOMER_ID,REP_ID,BILL_DATE,DISCOUNT,MODE_OF_TRANSACTION) VALUES(?,?,?,?,?);";
			pstm=con.prepareStatement(query);
			pstm.setInt(1, customerId);
			pstm.setInt(2, rep_id);
			pstm.setDate(3, sqldate);
			pstm.setInt(4, discount);
			pstm.setString(5, modeOfTransaction);			
			pstm.executeUpdate();
			pstm.close();
			query = "SELECT BILL_NO FROM BILL ORDER BY BILL_NO DESC LIMIT 1";
			stm = con.createStatement();
			ResultSet billRs = stm.executeQuery(query);
			int  billId=0;
			if(billRs.next()) {
				billId = billRs.getInt(1);
			}			
			for(int i=0;i<products.length;i++) {
				int productId=Integer.parseInt(products[i]);
				int quantity =Integer.parseInt(quantitys[i]);
				query = "SELECT UNIT_PRICE FROM STOCKS WHERE PRODUCT_ID = "+productId+";";
				stm = con.createStatement();
				ResultSet soldRs = stm.executeQuery(query);
				int amount=0;
				if(soldRs.next()) {
					amount=Integer.parseInt(quantitys[i])*soldRs.getInt(1);
				}		
				
				query ="INSERT INTO SOLD_PRODUCTS(BILL_NO,PRODUCT_ID,QUANTITY,AMOUNT)VALUES(?,?,?,?);";
				pstm=con.prepareStatement(query);
				pstm.setInt(1, billId);
				pstm.setInt(2, productId);
				pstm.setInt(3, quantity);
				pstm.setInt(4, amount);
				pstm.executeUpdate();
				pstm.close();
			}
			query ="SELECT SOLD_PRODUCTS.PRODUCT_ID,STOCKS.PRODUCT_NAME,SOLD_PRODUCTS.QUANTITY,STOCKS.UNIT_PRICE FROM SOLD_PRODUCTS INNER JOIN STOCKS ON SOLD_PRODUCTS.PRODUCT_ID=STOCKS.PRODUCT_ID WHERE SOLD_PRODUCTS.BILL_NO="+billId;
			stm = con.createStatement();
			ResultSet getStockRs = stm.executeQuery(query);
			List<Stock> stocks = new ArrayList<Stock>();			
			while(getStockRs.next()) {
				Stock stock = new Stock(getStockRs.getInt(1), getStockRs.getString(2), getStockRs.getInt(3), getStockRs.getInt(4));
				stocks.add(stock);
			}
			query ="SELECT BILL_NO,CUSTOMER_ID,REP_ID,BILL_DATE,TOTAL_AMOUNT,DISCOUNT,NET_AMOUNT,MODE_OF_TRANSACTION FROM BILL WHERE BILL_NO="+billId+";";
			ResultSet getBillRs = stm.executeQuery(query);
			if(getBillRs.next()) {
				LocalDate DATE = getBillRs.getDate(4).toLocalDate();
				bill = new Bill(getBillRs.getInt(1),getBillRs.getInt(2),getBillRs.getInt(3),DATE,getBillRs.getInt(5),getBillRs.getInt(6),getBillRs.getInt(7),getBillRs.getString(8),stocks);
			}
			
			return bill;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstm!=null) {
				pstm.close();				
			}
			
		}
		return bill;
	}
	public boolean incrementSalary() throws SQLException {
		Connection con=null;
		Statement stm=null;
		PreparedStatement pstm=null;
		try {
			con=getDbConnection();
			String query="SELECT REP_ID,SALARY FROM REPRESENTATIVE WHERE (DATEDIFF(NOW(),DOJ)/365)<=5 AND (DATEDIFF(NOW(),DOJ)/365)>=2 ";
			stm = con.createStatement();
			ResultSet RepBtTwoAndFive = stm.executeQuery(query);
			while(RepBtTwoAndFive.next()) {
				float incrementAmount = (float) ((0.1)*RepBtTwoAndFive.getInt(2));
				int salary=(int) (RepBtTwoAndFive.getInt(2)+incrementAmount);
				query="UPDATE REPRESENTATIVE SET SALARY = ? where REP_ID=?";
				pstm = con.prepareStatement(query);
				pstm.setInt(1, salary);
				pstm.setInt(2, RepBtTwoAndFive.getInt(1));
				pstm.executeUpdate();
			}
			stm.close();
			query="SELECT REP_ID,SALARY FROM REPRESENTATIVE WHERE (DATEDIFF(NOW(),DOJ)/365)>5";
			stm = con.createStatement();
			ResultSet RepGreaterThanFive = stm.executeQuery(query);
			while(RepGreaterThanFive.next()) {
				float incrementAmount = (float) ((0.08)*RepGreaterThanFive.getInt(2));
				int salary=(int) (RepGreaterThanFive.getInt(2)+incrementAmount);
				query="UPDATE REPRESENTATIVE SET SALARY = ? WHERE REP_ID=?";
				pstm = con.prepareStatement(query);
				pstm.setInt(1, salary);
				pstm.setInt(2, RepGreaterThanFive.getInt(1));
				pstm.executeUpdate();
			}
			query="SELECT BILL.REP_ID,SUM(NET_AMOUNT),REPRESENTATIVE.SALARY FROM BILL INNER JOIN REPRESENTATIVE ON REPRESENTATIVE.REP_ID=BILL.REP_ID WHERE YEAR(NOW())=YEAR(BILL_DATE) GROUP BY BILL.REP_ID";
			stm = con.createStatement();
			ResultSet greaterSalesRs = stm.executeQuery(query);
			while(greaterSalesRs.next()) {
				if(greaterSalesRs.getInt(2)>10000) {
					float incrementAmount = (float) ((0.03)*greaterSalesRs.getInt(3));
					int salary=(int) (greaterSalesRs.getInt(3)+incrementAmount);
					query="UPDATE REPRESENTATIVE SET SALARY = ? WHERE REP_ID=?";
					pstm = con.prepareStatement(query);
					pstm.setInt(1, salary);
					pstm.setInt(2, greaterSalesRs.getInt(1));
					pstm.executeUpdate();
				}
			}
		}catch(SQLException e) {			
			e.printStackTrace();
			return false;
		}finally {
			con.close();
			pstm.close();
		}
		
		return true;
	}
	
	public List<Bill> getBillsByPage(Date fromDate,Date toDate,int page) throws SQLException{
		List<Bill> bills = new ArrayList<Bill>();
		Connection con=null;
		PreparedStatement pstm=null;
		try {
			con=getDbConnection();
			String query="SELECT BILL_NO,CUSTOMER_ID,REP_ID,BILL_DATE,TOTAL_AMOUNT,DISCOUNT,NET_AMOUNT,MODE_OF_TRANSACTION FROM BILL WHERE BILL_DATE>=? AND BILL_DATE<=? ORDER BY BILL_NO DESC LIMIT ?,10";
			pstm =con.prepareStatement(query);
			pstm.setDate(1, fromDate);
			pstm.setDate(2, toDate);
			pstm.setInt(3, page*10);
			ResultSet billResult=pstm.executeQuery();
			while(billResult.next()) {
				List<Stock> stocks = new ArrayList<Stock>();
				query ="SELECT SOLD_PRODUCTS.PRODUCT_ID,STOCKS.PRODUCT_NAME,SOLD_PRODUCTS.QUANTITY,STOCKS.UNIT_PRICE FROM SOLD_PRODUCTS INNER JOIN STOCKS ON SOLD_PRODUCTS.PRODUCT_ID=STOCKS.PRODUCT_ID WHERE SOLD_PRODUCTS.BILL_NO="+billResult.getInt(1);
				Statement stm = con.createStatement();
				ResultSet getStockRs = stm.executeQuery(query);
				while(getStockRs.next()) {
					Stock stock = new Stock(getStockRs.getInt(1), getStockRs.getString(2), getStockRs.getInt(3), getStockRs.getInt(4));
					stocks.add(stock);
				}
				LocalDate Date = billResult.getDate(4).toLocalDate();
				Bill bill = new Bill(billResult.getInt(1),billResult.getInt(2),billResult.getInt(3),Date,billResult.getInt(5),billResult.getInt(6),billResult.getInt(7),billResult.getString(8),stocks);
				bills.add(bill);
			}			
			return bills;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}finally {
			pstm.close();
			con.close();
		}
		return bills;

	}
	 public List<RepCountByRep> getRepCountByRep(int month,int page) throws SQLException{
		 	Connection con=null;
			PreparedStatement pstm=null;
			List<RepCountByRep> repCountByReps = new ArrayList<RepCountByRep>();
			try {
				con=getDbConnection();
				String query="SELECT BILL_DATE ,REP_ID,COUNT(BILL_NO) as COUNT FROM BILL WHERE MONTH(BILL_DATE)=? GROUP BY BILL_DATE,REP_ID ORDER BY BILL_DATE DESC,COUNT DESC LIMIT ?,5;";
				pstm = con.prepareStatement(query);
				pstm.setInt(1, month);
				pstm.setInt(2, page*5);
				ResultSet getRepCountByRepRs = pstm.executeQuery();
				while(getRepCountByRepRs.next()) {
					LocalDate date = getRepCountByRepRs.getDate(1).toLocalDate();
					RepCountByRep repCountByRep = new RepCountByRep(date,getRepCountByRepRs.getInt(2),getRepCountByRepRs.getInt(3));
					repCountByReps.add(repCountByRep);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				con.close();
				pstm.close();
			}
			return repCountByReps;
	 }
	
}
