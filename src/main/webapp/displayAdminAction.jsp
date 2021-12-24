<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
table {
  border-collapse: collapse;
  width: 100%;
}

th, td {
  text-align: left;
  padding: 8px;
}

tr:nth-child(even){background-color: #f2f2f2}
</style>
</head>
<body>
<form action="/SuperMarket/getSalesCount" method="post">
<table>
	<tr>
		<td>Get sales Count</td>
		<td>
			<input type="date" name="fromDate"></input>
		</td>
		<td>
			<input type="date" name="toDate"></input>
		</td>
		<td>
			<input type="submit" value="Get">
		</td>
	</tr>
</table>
</form>
<form action="/SuperMarket/getTopCustomerByAmount" method="post">
<table>
	<tr>
		<td>Get Top Customer by Amount</td>
		<td>
			<input type="date" name="fromDate"></input>
		</td>
		<td>
			<input type="date" name="toDate"></input>
		</td>
		<td>
			<input type="submit" value="Get">
		</td>
	</tr>
</table>
</form>
<form action="/SuperMarket/GetTopCustomerByBills" method="post">
<table>
	<tr>
		<td>Get Top Customer by bills</td>
		<td>
			<input type="date" name="fromDate"></input>
		</td>
		<td>
			<input type="date" name="toDate"></input>
		</td>
		<td>
			<input type="submit" value="Get">
		</td>
	</tr>
</table>
</form>
<form action="/SuperMarket/getTopProductByAmount" method="post">
<table>
	<tr>
		<td>Get Top product by amount</td>
		<td>
			<input type="date" name="fromDate"></input>
		</td>
		<td>
			<input type="date" name="toDate"></input>
		</td>
		<td>
			<input type="submit" value="Get">
		</td>
	</tr>
</table>
</form>
<form action="/SuperMarket/getTopProductByBill" method="post" >
<table>
	<tr>
		<td>Get Top product by bills</td>
		<td>
			<input type="date" name="fromDate"></input>
		</td>
		<td>
			<input type="date" name="toDate"></input>
		</td>
		<td>
			<input type="submit" value="Get">
		</td>
	</tr>
</table>
</form>
<form action="/SuperMarket/getRepByAmount" method="post">
<table>
	<tr>
		<td>Get Top Representative by Amount</td>
		<td>
			<input type="date" name="fromDate"></input>
		</td>
		<td>
			<input type="date" name="toDate"></input>
		</td>
		<td>
			<input type="submit" value="Get">
		</td>
	</tr>
</table>
</form>
<form action="/SuperMarket/GetRepByBill" method="post">
<table>
	<tr>
		<td>Get Top Representative by bills</td>
		<td>
			<input type="date" name="fromDate"></input>
		</td>
		<td>
			<input type="date" name="toDate"></input>
		</td>
		<td>
			<input type="submit" value="Get">
		</td>
	</tr>
</table>
</form>
<form action="/SuperMarket/getBills" method="post">
<table>
	<tr>
		<td>Get Bills</td>
		<td>
			<input type="date" name="fromDate"></input>
		</td>
		<td>
			<input type="date" name="toDate"></input>
		</td>
		<td>
			<input type="submit" value="Get">
		</td>
	</tr>
</table>
</form>
<form action="/SuperMarket/getSalesCountByRep" method="post">
<table>
	<tr>
		<td>Get Bills</td>
		<td>
			<input type="number" name="month" min="1" max="12"></input>
		</td>
		<td>
			<input type="submit" value="Get">
		</td>
	</tr>
</table>
</form>
<form action="/SuperMarket/incrementSalary" method="post">
<table>
	<tr>
		<td>
			<input type="submit" value="INCREMENT">
		</td>
	</tr>
</table>
</form>
</body>
</html>