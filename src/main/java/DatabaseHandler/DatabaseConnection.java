package DatabaseHandler;

import java.sql.*;

public class DatabaseConnection {
	  
    public static Connection getConnection()
    {
    	synchronized(DatabaseConnection.class) {
    		try {
				Class.forName(DatabaseConstants.MYSQL_DRIVER); 
				String url = "jdbc:mysql://localhost:3306/"+DatabaseConstants.SCHEMA+"?useSSL=false";
				return DriverManager.getConnection(url,DatabaseConstants.MYSQL_USER_NAME,DatabaseConstants.MYSQL_PASSWORD);
			} 
			catch (SQLException | ClassNotFoundException e) {
				System.out.println("SQL Exception! " + e);
			}
		}
    	System.out.println("not connected!!!!!!11");
    	return null;
    }

}
