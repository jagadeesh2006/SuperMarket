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
@WebServlet("/GetRepByBill")
public class GetRepByBill extends HttpServlet {
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
			  String query = "SELECT REPRESENTATIVE.REP_NAME,COUNT(BILL.REP_ID) FROM REPRESENTATIVE INNER JOIN BILL ON REPRESENTATIVE.REP_ID=BILL.REP_ID WHERE BILL.BILL_DATE>='"+ sqlFromDate +"' AND BILL.BILL_DATE <='"+ sqlToDate +"' GROUP BY BILL.REP_ID ORDER BY COUNT(BILL.REP_ID) DESC LIMIT 1";			  
			  stm = con.createStatement();
			  ResultSet billCountRs = stm.executeQuery(query);
			  if(billCountRs.next()) {
				  String name =billCountRs.getString(1);
				  int count = billCountRs.getInt(2);
				  request.setAttribute("name", name);
				  request.setAttribute("count", count);
				  RequestDispatcher rd = request.getRequestDispatcher("dispalyByBillCount.jsp");
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
