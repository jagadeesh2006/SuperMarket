<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bill</title>
<style>
.customers {
  font-family: Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

.customers td, .customers th {
  border: 1px solid #ddd;
  padding: 8px;
}

.customers tr:nth-child(even){background-color: #f2f2f2;}

.customers tr:hover {background-color: #ddd;}
.customers th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #04AA6D;
  color: white;

}
</style>
</head>
<body>
	<table id="customers" class="customers">
  <tr>
    <td>Bill No</td>
    <td>${currentBill.getBillNum() }</td>
  </tr>
  <tr>
    <td>Customer Id</td>
    <td>${currentBill.getCustomerId() }</td>
  </tr>
  <tr>
    <td>Representative Id</td>
    <td>${currentBill.getRepId() }</td>
  </tr>
  <tr>
    <td>Bill Date</td>
    <td>${currentBill.getDate() }</td>
  </tr>
  </table>
  <table class="customers">
  <tr>
  	<th>Product Id</th>
  	<th>Product Name</th>
  	<th>Product Quantity</th>
  	<th>Product	Unit price</th>
  </tr>
  	
  	
  <c:forEach items="${currentBill.getProducts() }" var="product">
  <tr>
  	<td>${product.getProductId() }</td>
  	<td>${product.getProductName() }</td>
  	<td>${product.getquantity() }</td>
  	<td>${product.getprice() }</td>
  </tr>
  	
  
  </c:forEach>
  </table>
  <table class="customers">
  <tr>
    <td>Total Amount</td>
    <td>${currentBill.getTotalAmount() }</td>
  </tr>
  <tr>
    <td>Discount</td>
    <td>${currentBill.getDiscount() }</td>
  </tr>
  <tr>
    <td>Net Amount</td>
    <td>${currentBill.getNetAmount() }</td>
  </tr>
  <tr>
    <td>Mode Of Transaction</td>
    <td>${currentBill.getModeOfTransaction() }</td>
  </tr>
  </table>

</body>
</html>