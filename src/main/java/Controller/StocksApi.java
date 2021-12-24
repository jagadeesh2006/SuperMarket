package Controller;
import Models.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Services.StockService;


@WebServlet("/stocks")
public class StocksApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StockService stockService = new StockService();
		List<Stock> stocks = stockService.getStocks();
		request.setAttribute("stocks", stocks);
		RequestDispatcher rd = request.getRequestDispatcher("displayStocks.jsp");
		rd.forward(request, response);		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
