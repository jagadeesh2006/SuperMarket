package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Models.Representative;
import Services.CustomerAndRepresentative;

@WebServlet("/representative")
public class RepresentativeApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerAndRepresentative  representative = new CustomerAndRepresentative();
		List<Representative> representatives = new ArrayList<Representative>();
		representatives = representative.getRepresentatives();
		request.setAttribute("representatives", representatives);
		RequestDispatcher rd = request.getRequestDispatcher("displayRepresentatives.jsp");
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		CustomerAndRepresentative representative = new CustomerAndRepresentative();
		String name=request.getParameter("name");
		long phoneNumber = Long.parseLong(request.getParameter("phoneNumber"));
		String designation=request.getParameter("designation");
		LocalDate DOJ = LocalDate.parse(request.getParameter("DOJ"));
		int salary = Integer.parseInt(request.getParameter("salary"));
		try {
			if(representative.addRepresentative(name, phoneNumber,designation,DOJ,salary)) {
				System.out.println("rep added successfuly");
			}
			else {
				System.out.println("rep already exists");
			}
		} catch (SQLException e) {
			out.print(ErrorCodes.DATABASE_ERROR.getDescription());
			response.setStatus(ErrorCodes.DATABASE_ERROR.getCode());
			return;
		}
	}

}
