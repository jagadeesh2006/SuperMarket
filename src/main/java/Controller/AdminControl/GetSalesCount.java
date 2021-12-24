package Controller.AdminControl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.ErrorCodes;
import Controller.AdminModel.SalesCount;
import Services.BillServices;


@WebServlet("/getSalesCount")
public class GetSalesCount extends HttpServlet {
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
			  String query = "SELECT BILL_DATE, COUNT(BILL_NO) AS NO_OF_BILLS FROM BILL WHERE BILL_DATE >= '"+sqlFromDate+"' AND BILL_DATE <= '"+sqlToDate+"' GROUP BY(BILL_DATE);";			  
			  stm = con.createStatement();
			  ResultSet billCountRs = stm.executeQuery(query);
			  List<SalesCount> salesCounts = new ArrayList<SalesCount>();
			  while(billCountRs.next()) {
				  LocalDate date = billCountRs.getDate(1).toLocalDate();
				  SalesCount salesCount = new SalesCount(date,billCountRs.getInt(2));
				  salesCounts.add(salesCount);
			  }			  
			  request.setAttribute("salesCounts", salesCounts);
			  RequestDispatcher rd = request.getRequestDispatcher("displaySalesCount.jsp");
			  rd.forward(request, response);
		}catch(SQLException e) {
			out.print(ErrorCodes.DATABASE_ERROR.getDescription());
			response.setStatus(ErrorCodes.DATABASE_ERROR.getCode());
			return;
		}
	}

}
