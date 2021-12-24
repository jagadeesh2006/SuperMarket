package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Models.Representative;
import Models.Stock;
import Services.BillServices;
import Services.CustomerAndRepresentative;
import Services.StockService;


@WebServlet("/customerlogin")
public class CustomerLoginApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("displayCustomerLogin.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
		Statement stm = null;
		try {
			  long customerNum = Long.parseLong(request.getParameter("customerNum")); 
			  con = BillServices.getDbConnection();
			  String query = "SELECT CUSTOMER_ID FROM CUSTOMERS WHERE PHONE_NUMBER="+customerNum;			  
			  stm = con.createStatement();
			  ResultSet cusRs = stm.executeQuery(query);
			  if(cusRs.next()) {
				  StockService stockService = new StockService();
				  List<Stock> stocks = stockService.getStocks();
				  request.setAttribute("stocks", stocks);
				  request.setAttribute("phoneNumber", customerNum);
				  CustomerAndRepresentative  representative = new CustomerAndRepresentative();
				  List<Representative> representatives = new ArrayList<Representative>();
				  representatives = representative.getRepresentatives();
				  request.setAttribute("representatives", representatives);
				  RequestDispatcher rd = request.getRequestDispatcher("displayBillForm.jsp");
				  rd.forward(request, response);
			  }
			  else {
				  request.setAttribute("customerNum", customerNum);
				  RequestDispatcher rd = request.getRequestDispatcher("displayCustomerForm.jsp");
				  rd.forward(request, response);
			}
		}catch(IOException e) {
			System.out.println(e);
		}catch(ServletException e) {
			System.out.println(e);
		}catch(SQLException e) {
			System.out.println(e);
			
		}finally {
			if(con!=null) {
				try {
					con.close();
					stm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}			
		}
	}

}
