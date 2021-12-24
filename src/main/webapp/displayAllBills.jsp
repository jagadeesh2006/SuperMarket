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
<a  href="getBills?page=${page +1}">next</a>
<a  href="getBills?page=${page -1}">previous</a>
<c:forEach items="${bills}" var="bill">
	<table id="customers" class="customers">
  <tr>
    <td>Bill No</td>
    <td>${bill.getBillNum() }</td>
  </tr>
  <tr>
    <td>Customer Id</td>
    <td>${bill.getCustomerId() }</td>
  </tr>
  <tr>
    <td>Representative Id</td>
    <td>${bill.getRepId() }</td>
  </tr>
  <tr>
    <td>Bill Date</td>
    <td>${bill.getDate() }</td>
  </tr>
  </table>
  <table class="customers">
  <tr>
  	<th>Product Id</th>
  	<th>Product Name</th>
  	<th>Product Quantity</th>
  	<th>Product	Unit price</th>
  </tr>
  	
  	
  <c:forEach items="${bill.getProducts() }" var="product">
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
    <td>${bill.getTotalAmount() }</td>
  </tr>
  <tr>
    <td>Discount</td>
    <td>${bill.getDiscount() }</td>
  </tr>
  <tr>
    <td>Net Amount</td>
    <td>${bill.getNetAmount() }</td>
  </tr>
  <tr>
    <td>Mode Of Transaction</td>
    <td>${bill.getModeOfTransaction() }</td>
  </tr>
  </table>
  <br>
  <h1>-----------------------------------------------------------------------------</h1>
  <br>
</c:forEach>

</body>
</html>