<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sales Count</title>
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

th {
  background-color: #04AA6D;
  color: white;
}
</style>
</head>
<body>
	<table>
  			<tr>
    			<th>Date</th>
    			<th>Count</th>
  			</tr>
			<c:forEach items="${salesCounts }" var="salesCount">
				<tr>
    				<td>${salesCount.getDate()}</td>
    				<td>${salesCount.getBillCount()}</td>
  				</tr>
		</c:forEach>
	</table>
</body>
</html>