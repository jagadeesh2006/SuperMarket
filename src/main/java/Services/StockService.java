package Services;

import DatabaseHandler.*;
import Models.Stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockService {
	private static Connection getDbConnection() {
		return DatabaseConnection.getConnection();		
	}
	
	public List<Stock> getStocks(){
		List<Stock> stocks = new ArrayList<Stock>();
		try(Connection con = getDbConnection();Statement stm = con.createStatement()){
			String query = "SELECT * FROM STOCKS";
			ResultSet rs = stm.executeQuery(query);
			while(rs.next()) {
				Stock product = new Stock(rs.getInt(1),rs.getString(2), rs.getInt(3),rs.getInt(4));
				stocks.add(product);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return stocks;
	}
}
