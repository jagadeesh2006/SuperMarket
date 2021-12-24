package Controller.AdminControl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controller.ErrorCodes;
import Models.Bill;
import Services.BillServices;

@WebServlet("/getBills")
public class GetBills extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		PrintWriter out = response.getWriter();
		if(request.getParameter("fromDate")!=null&&request.getParameter("toDate")!=null) { 
	        session.setAttribute("fromDate",request.getParameter("fromDate")); 
	        session.setAttribute("toDate",request.getParameter("toDate")); 
		}
		String fromDate=(String) (session.getAttribute("fromDate"));
		String toDate=(String) (session.getAttribute("toDate")); 
		Date sqlFromDate = Date.valueOf(fromDate);
		Date sqlToDate = Date.valueOf(toDate); 
		int page=0;
		if(request.getParameter("page")!=null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		try {
			BillServices billservices = new BillServices();
			List<Bill> bills = 	billservices.getBillsByPage(sqlFromDate,sqlToDate,page);			  
			request.setAttribute("bills", bills);
			request.setAttribute("page", page);
			RequestDispatcher rd = request.getRequestDispatcher("displayAllBills.jsp");
			rd.forward(request, response);
		}catch(SQLException e) {
			out.print(ErrorCodes.DATABASE_ERROR.getDescription());
			response.setStatus(ErrorCodes.DATABASE_ERROR.getCode());
			return;
		}finally {
			
		}
		
	}

}
