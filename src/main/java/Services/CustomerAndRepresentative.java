package Services;
import DatabaseHandler.*;
import Models.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerAndRepresentative {
	private static Connection getDbConnection() {
		return DatabaseConnection.getConnection();		
	}
	
	private boolean isCustomerExists(long phoneNumber) throws SQLException {
		PreparedStatement pstm = null;
		try(Connection con = getDbConnection()){
			String query = "SELECT PHONE_NUMBER FROM CUSTOMERS WHERE PHONE_NUMBER=?";
			pstm=con.prepareStatement(query);
			pstm.setLong(1, phoneNumber);
			ResultSet rs = pstm.executeQuery();	
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstm!=null) {
				pstm.close();
			}			
		}
		return false;
	}
	
	private boolean isRepresentativeExists(long phoneNumber) throws SQLException {
		PreparedStatement pstm = null;
		try(Connection con = getDbConnection()){
			String query = "SELECT PHONE_NUMBER FROM REPRESENTATIVE WHERE PHONE_NUMBER=?";
			pstm=con.prepareStatement(query);
			pstm.setLong(1, phoneNumber);
			ResultSet rs = pstm.executeQuery();	
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstm!=null) {
				pstm.close();
			}			
		}
		return false;
	}
	
	public List<Customer> getCustomers(){
		List<Customer> customers = new ArrayList<Customer>();
		try(Connection con = getDbConnection();Statement stm = con.createStatement()){
			String query = "SELECT * FROM CUSTOMERS";
			ResultSet rs = stm.executeQuery(query);
			while(rs.next()) {
				Customer customer = new Customer(rs.getInt(1),rs.getString(2),rs.getLong(3));
				customers.add(customer);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return customers;
	}
	
	public List<Representative> getRepresentatives(){
		List<Representative> representatives = new ArrayList<Representative>();
		try(Connection con = getDbConnection();Statement stm = con.createStatement()){
			String query = "SELECT * FROM REPRESENTATIVE";
			ResultSet rs = stm.executeQuery(query);
			while(rs.next()) {
				LocalDate DOJ = rs.getDate(5).toLocalDate();
				Representative representative = new Representative(rs.getInt(1),rs.getString(2),rs.getLong(3),rs.getString(4),DOJ,rs.getInt(6));
				representatives.add(representative);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return representatives;
	}
	
	
	public void addCustomer(String name,long phoneNumber) throws SQLException {
		if(!isCustomerExists(phoneNumber)) {
			PreparedStatement pstm = null;
			try(Connection con = getDbConnection()){
				String query = "INSERT INTO CUSTOMERS(CUSTOMER_NAME,PHONE_NUMBER) VALUES(?,?);";
				pstm=con.prepareStatement(query);
				pstm.setString(1, name);
				pstm.setLong(2, phoneNumber);
				pstm.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				if(pstm!=null) {
					pstm.close();
				}			
			}
		}

	}
	
	public boolean addRepresentative(String name,long phoneNumber,String designation,LocalDate DOJ,int salary) throws SQLException {
		if(!isRepresentativeExists(phoneNumber)) {
			PreparedStatement pstm = null;
			try(Connection con = getDbConnection()){
				String query = "INSERT INTO CUSTOMERS(REP_NAME,PHONE_NUMBER,DESIGNATION,DOJ,SALARY) VALUES(?,?,?,?,?);";
				pstm=con.prepareStatement(query);
				pstm.setString(1, name);
				pstm.setLong(2, phoneNumber);
				pstm.setString(3, designation);
				pstm.setDate(4, Date.valueOf(DOJ));
				pstm.setInt(5,salary);
				pstm.executeQuery();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				if(pstm!=null) {
					pstm.close();
				}
				
			}
		}
		return false;
	}	
}
