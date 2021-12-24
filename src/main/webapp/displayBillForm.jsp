<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Enter Bill Details</title>
<script>
function add() { 
    var select = document.createElement("select");
    var id = document.getElementsByName("product").length;
    select.setAttribute("name", "product");
    select.setAttribute("id", "product_"+id);
    var option = document.createElement("option");
	option.value = 0;
    option.text = "select products";
	select.appendChild(option);
    select.onchange=myFunction;
    	<c:forEach items="${stocks }" var="stock">
    		var option = document.createElement("option");
    		option.value = ${stock.getProductId() };
    		var price = ${stock.getprice() };
    	    option.text = "${stock.getProductName()} "+" - $" + price;
    		select.appendChild(option);
		</c:forEach>
	var element = document.createElement("input");
	element.setAttribute("type", "number");
	element.setAttribute("name", "quantity");
	element.setAttribute("id", "quantity_"+id);
	element.setAttribute("min", "0");
	element.setAttribute("max", "0");
	element.setAttribute("value", "0");
	var spandroupdown = document.getElementById("spandroupdown");
	spandroupdown .appendChild(select);
	spandroupdown .appendChild(element);
 }
 
 function myFunction(event){
	 var id=(event.target.id).split('_')[1];
	 console.log(id);
	 var value = document.getElementById("product_"+id).value;	 	
	  <c:forEach items="${stocks }" var="stock">
	  	if(${stock.getProductId() }==value){
	  		console.log(${stock.getquantity()});
	  		document.getElementById("quantity_"+id).max=${stock.getquantity()} ;
	  	}	
	 </c:forEach>
	 
 }

</script>
<style type="text/css">
</style>
</head>
<body>
	<form action="/SuperMarket/bill" method="post">
			<table style="with: 50%">
				<tr>
					<td>Customer Number</td>
					<td><input class="removeArrow" type="tel" name="customerNum"  value="${phoneNumber }" readonly/></td>
				</tr>
				<tr>
					<td>Rep ID</td>
						<td>
							<select name="repId" id="repId">
								<c:forEach items="${representatives }" var="representative">
									<option value="${representative.getRepId() }">${representative.getRepId() }  -  ${representative.getName() }</option>
    							</c:forEach>
    						</select>
    					</td>
				</tr>
				<tr>
					<td>Products</td>
										
					<td><button type="button" id="selectProducts" onclick="add()">Select Products</button></td>
					<td>
						<span id="spandroupdown">
						</span>
					</td>
				</tr>
				<tr>
					<td>Discount</td>
					<td><input class="removeArrow" type="number" name="discount" max="100" value="0"/></td>
				</tr>
				<tr>
					<td>Mode Of Transaction</td>
					
					<td>
					<select name="modeOfTransaction">
  						<option value="CASH">CASH</option>
  						<option value="CREDIT_CARD">CREDIT CARD</option>
  						<option value="UPI">UPI</option>
  						<option value="DEBIT_CRAD">DEBIT CRAD</option>
					</select>
					</td>
				</tr>
			</table>
		<input type="submit" value="Submit" />
	</form>

</body>
</html>