package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Models.Customer;
import Models.Representative;
import Models.Stock;
import Services.CustomerAndRepresentative;
import Services.StockService;

@WebServlet("/customer")
public class CustomerApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerAndRepresentative  customer = new CustomerAndRepresentative();
		List<Customer> customers = new ArrayList<Customer>();
		customers = customer.getCustomers();
		request.setAttribute("customers", customers);
		RequestDispatcher rd = request.getRequestDispatcher("displayCustomers.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerAndRepresentative Customer = new CustomerAndRepresentative();
		String name=request.getParameter("customerName");
		long phoneNumber = Long.parseLong(request.getParameter("phoneNum"));
		try {
				  Customer.addCustomer(name, phoneNumber);
				  request.setAttribute("phoneNumber", phoneNumber);
				  StockService stockService = new StockService();
				  List<Stock> stocks = stockService.getStocks();
				  request.setAttribute("stocks", stocks);
				  CustomerAndRepresentative  representative = new CustomerAndRepresentative();
				  List<Representative> representatives = new ArrayList<Representative>();
				  representatives = representative.getRepresentatives();
				  request.setAttribute("representatives", representatives);
				  RequestDispatcher rd = request.getRequestDispatcher("displayBillForm.jsp");
				  rd.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
