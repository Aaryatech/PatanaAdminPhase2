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
<title>Bill-wise Report Pdf</title>
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

<div align="center"> <h5> Franchise-wise sales Report <br> Dates : ${fromDate} ,${toDate}</h5></div>
	<table  align="center" border="1" cellspacing="0" cellpadding="1" 
		id="table_grid" class="table table-bordered">
		<thead>
			<tr class="bgpink">
				<th>Sr. No.</th>
				<th>Party Name</th>
				<th>Sales</th>
				<th>GVN</th>
				<th>Net Value</th>
				<th>GRN</th>
				<th>Net Value</th>
				<th>In Lakh</th>
				<th>Return%</th>
			</tr>
		</thead>
		<tbody>
				<c:set var="netValue1" value="${0}"/>
				<c:set var="netValue2" value="${0}"/>
				<c:set var="inLac" value="${0}"/>
				<c:set var="retPer" value="${0}"/>
				<c:set var="calretPer" value="${0}"/>
				
				<c:set var="ttlSales" value="${0}"/>
				<c:set var="ttlNetValue1" value="${0}"/>
				<c:set var="ttlNetValue2" value="${0}"/>
				<c:set var="ttlInLac" value="${0}"/>
				
				<c:set var="calFinlRate" value="${0}"/>
				<c:set var="ttlFinlRate" value="${0}"/>
				
			<c:forEach items="${report}" var="report" varStatus="count">
			
			<c:set var="ttlRetPer" value="${0}"/>
				<c:set var="calretPer" value="${0}"/>
				
				<c:set var="netValue1" value="${report.saleValue - report.gvnValue}"/>
				<c:set var="netValue2" value="${netValue1 - report.grnValue}"/>
				<c:set var="inLac" value="${netValue2/100000}"/>
				
				<c:if test="${report.grnValue>0}">
					<c:set var="calretPer" value="${report.saleValue/100}"/>
					<c:set var="retPer" value="${report.grnValue/calretPer}"/>
				</c:if>
				<tr>
					<td width="0" ><c:out value="${count.index+1}" /></td>
					<td><c:out value="${report.frName} ${report.frCode}" /></td>
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2" value="${report.saleValue}" /></td>
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2" value="${report.gvnValue}" /></td>
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2" value="${netValue1}" /></td>								
						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2" value="${report.grnValue}" /></td>
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${netValue2}" /></td>
								
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${inLac}" /></td>
								
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${retPer}" /></td>
					
					<c:set var="ttlSales" value="${ttlSales+report.saleValue}"/>
					<c:set var="ttlGvn" value="${ttlGvn+report.gvnValue}"/>
					<c:set var="ttlGrn" value="${ttlGrn+report.grnValue}"/>
					<c:set var="ttlNetValue1" value="${ttlNetValue1+netValue1}"/>
					<c:set var="ttlNetValue2" value="${ttlNetValue2+netValue2}"/>
					<c:set var="ttlInLac" value="${ttlInLac+inLac}"/>
					<c:set var="ttlRetPer" value="${ttlSales/100}"/>
					<c:set var="ttlFinlRate" value="${ttlGrn/ttlRetPer}"/>
					
					<%-- <c:set var="taxAmount" value="${taxAmount + report.taxableAmt}" />
					
					<c:set var="grandTotal"
						value="${grandTotal+total}" />
					 --%>
					
					
				</tr>

			</c:forEach>
				 <tr>
				
					<td width="100"colspan='2' align="left"><b>Total</b></td>
					<td width="100"align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${ttlSales}" /></b></td>
					<td width="100" align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${ttlGvn}" /></b></td>
					<td width="100"align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${ttlNetValue1}" /></b></td>
					<td width="100" align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${ttlGrn}" /></b></td>
					<td width="100" align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${ttlNetValue2}" /></b></td>
					<td width="100" align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${ttlInLac}" /></b></td>
					<td width="100" align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${ttlFinlRate}" /></b></td>
				</tr>
		</tbody>
	</table>
	<br>
<div class="footer_btm" style="display: inline-block; width: 100%; text-align: center; position: absolute; bottom:0; margin: 20px 0 0 0;">
	<img alt="" src="${pageContext.request.contextPath}/resources/img/mongi.png" height="20px;" style="float: left; vertical-align: middle;">
	<span style="display: inline-block; float:left; text-align: center; width: 70%; vertical-align: middle; font-size: 12px;  ">******</span>
	<img alt="" src="${pageContext.request.contextPath}/resources/img/powerd_logo.png" height="10px;" style="float: right; vertical-align: top;">
	</div>
	<!-- END Main Content -->

</body>
</html>