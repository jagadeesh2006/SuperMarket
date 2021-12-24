package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import Models.Bill;
import Models.Representative;
import Models.Stock;
import Services.BillServices;
import Services.CustomerAndRepresentative;
import Services.StockService;

@WebServlet("/bill")
public class GenerateBillApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StockService stockService = new StockService();
		List<Stock> stocks = stockService.getStocks();
		request.setAttribute("stocks", stocks);
		CustomerAndRepresentative  representative = new CustomerAndRepresentative();
		List<Representative> representatives = new ArrayList<Representative>();
		representatives = representative.getRepresentatives();
		request.setAttribute("representatives", representatives);
		System.out.println(representatives);
		RequestDispatcher rd = request.getRequestDispatcher("displayBillForm.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  PrintWriter out = response.getWriter();
		  BillServices bill = new BillServices();
		  long customerNum = Long.parseLong(request.getParameter("customerNum")); 
		  int repId =  Integer.parseInt(request.getParameter("repId"));
		  if(request.getParameterValues("product")==null && request.getParameterValues("quantity")==null) {
			  out.print(ErrorCodes.NO_PRODUCTS_SELECTED.getDescription());
			  response.setStatus(ErrorCodes.NO_PRODUCTS_SELECTED.getCode());
			  return;
		  }
		  String[] products = request.getParameterValues("product");
		  String[] quantitys =  request.getParameterValues("quantity");
		  StockService stockService = new StockService();
		  List<Stock> stocks = stockService.getStocks();
		  for(int i=0;i<products.length;i++) {
			  for(Stock stock: stocks) {
				  if(Integer.parseInt(products[i])==stock.getProductId()) {
					  if(Integer.parseInt(quantitys[i])>stock.getquantity()) {						  
						  out.print(ErrorCodes.MAXIMUM_LIMIT_EXCEEDED.getDescription()+" "+stock.getProductName()+" = "+stock.getquantity());
						  response.setStatus(ErrorCodes.MAXIMUM_LIMIT_EXCEEDED.getCode());
						  return;
					  }
					  break;
				  }
			  }
		  }
		  
		  int discount =  Integer.parseInt(request.getParameter("discount"));
		  if(discount>100) {
			  out.print(ErrorCodes.MAXIMUM_LIMIT_EXCEEDED.getDescription()+" DISCOUNT NOT MORE THAN 100%");
			  response.setStatus(ErrorCodes.MAXIMUM_LIMIT_EXCEEDED.getCode());
			  return;
		  }
		  String modeOfTransaction = request.getParameter("modeOfTransaction");
		  try {
			  Connection con = BillServices.getDbConnection();
			  String query = "SELECT CUSTOMER_ID FROM CUSTOMERS WHERE PHONE_NUMBER="+customerNum;			  
			  Statement stm = con.createStatement();
			  ResultSet cusRs = stm.executeQuery(query);
			  if(cusRs.next()) {
				  Bill currentBill = bill.generateBill(customerNum,repId,products,quantitys,discount,modeOfTransaction);
				  request.setAttribute("currentBill", currentBill);
				  RequestDispatcher rd = request.getRequestDispatcher("displayBillCurrentBill.jsp");
				  rd.forward(request, response);
			  }
			  else{				  
				  HttpSession session=request.getSession();   
				  session.setAttribute("customerNum", customerNum);
				  session.setAttribute("repId", repId);
				  session.setAttribute("products", products);
				  session.setAttribute("quantitys", quantitys);
				  session.setAttribute("discount", discount);
				  session.setAttribute("modeOfTransaction", modeOfTransaction);
				  RequestDispatcher rd = request.getRequestDispatcher("displayCustomerForm.jsp");
				  rd.forward(request, response);
			  }
		} catch (SQLException e) {
			out.print(ErrorCodes.DATABASE_ERROR.getDescription());
			response.setStatus(ErrorCodes.DATABASE_ERROR.getCode());
			return;
		}		  
		
	}

}
