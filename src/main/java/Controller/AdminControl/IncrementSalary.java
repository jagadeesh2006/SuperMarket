package Controller.AdminControl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.ErrorCodes;
import Models.Representative;
import Services.BillServices;
import Services.CustomerAndRepresentative;

@WebServlet("/incrementSalary")
public class IncrementSalary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BillServices billServices = new BillServices();
		PrintWriter out = response.getWriter();
		try {
			boolean isIncremented = billServices.incrementSalary();
			if(isIncremented) {
				CustomerAndRepresentative  representative = new CustomerAndRepresentative();
				List<Representative> representatives = new ArrayList<Representative>();
				representatives = representative.getRepresentatives();
				request.setAttribute("representatives", representatives);
				RequestDispatcher rd = request.getRequestDispatcher("displayRepresentatives.jsp");
				rd.forward(request, response);
			}
		} catch (SQLException e) {
			out.print(ErrorCodes.DATABASE_ERROR.getDescription());
			response.setStatus(ErrorCodes.DATABASE_ERROR.getCode());
		}
	}

}
