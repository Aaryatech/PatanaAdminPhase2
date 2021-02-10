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
<title>GRN GVN Item Wise Report</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->


 <style type="text/css">
 table {
	border-collapse: collapse;
	font-size: 10;
	width:100%;
	page-break-inside: auto !important; 
	
} 
p  {
    color: black;
    font-family: arial;
    font-size: 80%;
	margin-top: 0;
	margin-bottom: 6px;
	padding: 0;
	font-weight: bold;
}

h6  {
    color: black;
    font-family: arial;
    font-size: 80%;
}
h5{
	margin-bottom: 6px;
}

th {
	background-color: #EA3291;
	color: white;
	
}
</style>

</head>
<body onload="myFunction()">
<p align="center">${FACTORYNAME} <br> ${FACTORYADDRESS}</p>

<div align="center"> <h5> GRN GVN Item-wise Report <br> Dates : ${fromDate} ,${toDate} &nbsp; By : ${isGrn==1 ? 'GVN' : isGrn==1 ? 'GRN' : 'Cust Complaint'}</h5></div>
	<table  align="center" border="1" cellspacing="0" cellpadding="1" 
		id="table_grid" class="table table-bordered">
		<thead>
			<tr class="bgpink">
				<th>Sr.No.</th>
				<th>Type</th>
				<th>Item Name</th>
				<th>Req Qty</th>
				<th>Req Value</th>
				<th>Apr Qty</th>
				<th>Apr Value</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="ttlReqQty" value="${0}" />
			<c:set var="ttlReqAmt" value="${0 }" />
			<c:set var="ttlAprvQty" value="${0 }" />
			<c:set var="ttlAprvAmt" value="${0 }" />
			<c:forEach items="${report}" var="report" varStatus="count">
				<tr>
					<td width="0" ><c:out value="${count.index+1}" /></td>
					<td><c:out value="${report.isGrn==1 ? 'GVN' : report.isGrn==1 ? 'GRN' : 'Cust Complaint'}" /></td>
					<td style="text-align: center;"><c:out value="${report.itemName}" /></td>
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${report.reqQty}" /></td>
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${report.totalAmt}" /></td>			
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${report.aprQty}" /></td>
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${report.aprGrandTotal}" /></td>
				
					
					<c:set var="ttlReqQty" value="${ttlReqQty+report.reqQty}" />
					<c:set var="ttlReqAmt" value="${ttlReqAmt+report.totalAmt}" />
					<c:set var="ttlAprvQty" value="${ttlAprvQty+report.aprQty}" />
					<c:set var="ttlAprvAmt" value="${ttlAprvAmt+report.aprGrandTotal}" />
				</tr>

			</c:forEach>
				<tr>
				
					<td width="100"colspan='3' align="left"><b>Total</b></td>
					<td width="100"align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${ttlReqQty}" /></b></td>
					<td width="100" align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${ttlReqAmt}" /></b></td>
					<td width="100"align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${ttlAprvQty}" /></b></td>
				
					<td width="100" align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${ttlAprvAmt}" /></b></td>
					<!--  <td><b>Total</b></td> -->
				</tr>
		</tbody>
	</table>
	<div class="footer_btm" style="display: inline-block; width: 100%; text-align: center; position: absolute; bottom:0; margin: 20px 0 0 0;">
	<img alt="" src="${pageContext.request.contextPath}/resources/img/mongi.png" height="20px;" style="float: left; vertical-align: middle;">
	<span style="display: inline-block; float:left; text-align: center; width: 70%; vertical-align: middle; font-size: 12px;  ">******</span>
	<img alt="" src="${pageContext.request.contextPath}/resources/img/powerd_logo.png" height="10px;" style="float: right; vertical-align: top;">
	</div>

	<!-- END Main Content -->

</body>
</html>