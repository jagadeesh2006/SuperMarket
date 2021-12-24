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

@WebServlet("/getRepByAmount")
public class GetRepByAmount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("get method not allowed").append(request.getContextPath());
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
			  String query = "select REPRESENTATIVE.REP_NAME,SUM(NET_AMOUNT) as sum_amt FROM BILL INNER JOIN REPRESENTATIVE ON REPRESENTATIVE.REP_ID=BILL.REP_ID WHERE BILL_DATE>='"+ sqlFromDate +"' AND BILL_DATE <='"+ sqlToDate +"' GROUP BY BILL.REP_ID ORDER BY(sum_amt) DESC LIMIT 1;";			  
			  stm = con.createStatement();
			  ResultSet billCountRs = stm.executeQuery(query);
			  if(billCountRs.next()) {
				  String name = billCountRs.getString(1);
				  int amount = billCountRs.getInt(2);
				  request.setAttribute("name", name);
				  request.setAttribute("amount", amount);
				  RequestDispatcher rd = request.getRequestDispatcher("displayByAmount.jsp");
				  rd.forward(request, response);
			  }else {
				  System.out.println("FALSE");
			  }
			  
		}catch(SQLException e) {
			out.print(ErrorCodes.DATABASE_ERROR.getDescription());
			response.setStatus(ErrorCodes.DATABASE_ERROR.getCode());
			return;
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
