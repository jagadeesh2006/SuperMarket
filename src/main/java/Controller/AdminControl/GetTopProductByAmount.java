package Controller.AdminControl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.ErrorCodes;
import Services.BillServices;

@WebServlet("/getTopProductByAmount")
public class GetTopProductByAmount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
		Statement stm = null;
		PrintWriter out = response.getWriter();
		LocalDate fromDate = LocalDate.parse(request.getParameter("fromDate")) ;
		LocalDate toDate = LocalDate.parse(request.getParameter("toDate")) ;
		Date sqlFromDate = Date.valueOf(fromDate);
		Date sqlToDate = Date.valueOf(toDate);	
		try {
			  con = BillServices.getDbConnection();
			  String query = "SELECT STOCKS.PRODUCT_NAME,SUM(SOLD_PRODUCTS.AMOUNT) FROM SOLD_PRODUCTS INNER JOIN STOCKS ON SOLD_PRODUCTS.PRODUCT_ID=STOCKS.PRODUCT_ID INNER JOIN BILL ON BILL.BILL_NO=SOLD_PRODUCTS.BILL_NO WHERE\n"
			  		+ "BILL.BILL_DATE>='"+ sqlFromDate +"' AND BILL.BILL_DATE <='"+ sqlToDate +"' GROUP BY SOLD_PRODUCTS.PRODUCT_ID ORDER BY SUM(SOLD_PRODUCTS.AMOUNT) DESC LIMIT 1";			  
			  stm = con.createStatement();
			  ResultSet productRs = stm.executeQuery(query);
			  if(productRs.next()) {
				  String name = productRs.getString(1);
				  int amount = productRs.getInt(2);
				  request.setAttribute("name", name);
				  request.setAttribute("amount", amount);
				  RequestDispatcher rd = request.getRequestDispatcher("displayByAmount.jsp");
				  rd.forward(request, response);
			  }		  
		}catch(SQLException e) {
			out.print(ErrorCodes.DATABASE_ERROR.getDescription());
			response.setStatus(ErrorCodes.DATABASE_ERROR.getCode());
			return;
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				out.print(ErrorCodes.DATABASE_ERROR.getDescription());
				response.setStatus(ErrorCodes.DATABASE_ERROR.getCode());
				return;
			}
		}
	}

}
