<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Order To Production Pdf</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->


<style type="text/css">
table {
	border-collapse: collapse;
	font-size: 10;
	width: 100%;
	page-break-inside: auto !important
}

p {
	color: black;
	font-family: arial;
	font-size: 60%;
	margin-top: 0;
	padding: 0;
}

h6 {
	color: black;
	font-family: arial;
	font-size: 80%;
}

th {
	background-color: #EA3291;
	color: white;
}
</style>
</head>
<body onload="myFunction()">
	<h3 align="center">${FACTORYNAME}</h3>
	<p align="center">${FACTORYADDRESS}</p>
	<div align="center">
		<h5>Order To Production Pdf <br> Production Date :
			&nbsp; ${productionDate}</h5> 
	</div>
	<table align="center" border="1" cellspacing="0" cellpadding="1"
		id="table_grid" class="table table-bordered">
		<thead>
			<tr class="bgpink">
				<th>Sr.No.</th>
				<th>Item Id</th>
				<th>Item Name</th>
				<th>Current Stock</th>
				<th>Order Qty.</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach items="${itemList}" var="itemList" varStatus="count">
				<tr>
					<c:set var="sr" value="${sr+1}" />
					<td width="20"><c:out value="${sr}" /></td>
					<td width="100"><c:out value="${itemList.itemId}" /></td>
					<td width="100"><c:out value="${itemList.itemName}" /></td>
					<td width="100" style="text-align: right;"><c:out value="${itemList.curOpeQty}" /></td>
					<td width="100" style="text-align: right;"><c:out value="${itemList.qty}" /></td>
				</tr>

			</c:forEach>
		</tbody>
	</table>


	<!-- END Main Content -->

</body>
</html>