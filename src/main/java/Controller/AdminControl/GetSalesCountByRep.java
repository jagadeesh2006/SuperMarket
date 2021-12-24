package Controller.AdminControl;

import java.io.IOException;
import java.io.PrintWriter;
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
import Models.RepCountByRep;
import Services.BillServices;
@WebServlet("/getSalesCountByRep")
public class GetSalesCountByRep extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(); 
		PrintWriter out = response.getWriter();
		if(request.getParameter("month")!=null) { 
	        session.setAttribute("month",request.getParameter("month"));  
		}
		int month = Integer.parseInt(session.getAttribute("month").toString());
		System.out.println(month);
		int page=0;
		if(request.getParameter("page")!=null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		try {
			BillServices billservices = new BillServices();
			List<RepCountByRep> repCountByReps = 	billservices.getRepCountByRep(month,page);			  
			request.setAttribute("repCountByReps", repCountByReps);
			request.setAttribute("page", page);
			RequestDispatcher rd = request.getRequestDispatcher("displaySalesCountByRep.jsp");
			rd.forward(request, response);
		}catch(SQLException e) {
			out.print(ErrorCodes.DATABASE_ERROR.getDescription());
			response.setStatus(ErrorCodes.DATABASE_ERROR.getCode());
			return;
		}
	}

}
