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
<title>Credit Note-wise tax slab-wise Report Pdf</title>
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

<div align="center"> <h5>Credit Note-wise tax slab-wise Report <br> Dates : ${fromDate} ,${toDate} <br>
	By : ${creditNoteType==1 ? 'GRN' : creditNoteType==0 ? 'GVN' : 'All'}</h5></div>
	<table  align="center" border="1" cellspacing="0" cellpadding="1" 
		id="table_grid" class="table table-bordered">
		<thead>
			<tr class="bgpink">
				<th>Sr. No.</th>
				<th>CRN No.</th>
				<th>CRN Date</th>
				<th>Invoice No.</th>
				<th>Invoice Date</th>
				<th>Party Name</th>
				<th>GST No</th>
				<th>Tax Rate</th>
				<th>Bill Qty.</th>
				<th>Taxable Amt</th>
				<th>CGST Amt</th>
				<th>CGST Amt</th>
				<th>Bill Amt</th>
			</tr>
		</thead>
		<tbody>
				
				<c:set var="ttlBill" value="${0}"/>
				<c:set var="ttlTaxable" value="${0}"/>
				<c:set var="ttlSgst" value="${0}"/>
				<c:set var="ttlCgst" value="${0}"/>	
				
			<c:forEach items="${report}" var="report" varStatus="count">
			
				<c:set var="ttlBill" value="${ttlBill+report.crnAmt}"/>
				<c:set var="ttlTaxable" value="${ttlTaxable+report.crnTaxable}"/>
				<c:set var="ttlSgst" value="${ttlSgst+report.sgstAmt}"/>
				<c:set var="ttlCgst" value="${ttlCgst+report.cgstAmt}"/>	
				
				
				
				
				<tr>
					<td width="0" ><c:out value="${count.index+1}" /></td>
					<td><c:out value="${report.crndId}"/></td>
					<td><c:out value="${report.crnDate}"/></td>
					<td><c:out value="${report.invoiceNo}"/></td>
					<td><c:out value="${report.billDate}"/></td>					
					<td><c:out value="${report.frName} ${report.frCode}" /></td>
					<td><c:out value="${report.frGstNo}"/></td>	
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2" value="${report.cgstPer+report.sgstPer}" /></td>
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2" value="${report.crnQty}" /></td>
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2" value="${report.crnTaxable}" /></td>								
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2" value="${report.cgstAmt}" /></td>
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${report.sgstAmt}" /></td>
								
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${report.crnAmt}" /></td>								
				</tr>

			</c:forEach>
				 <tr>
					<td></td>		
					<td width="100"colspan='8' align="left"><b>Total</b></td>					
					<td width="10" align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${ttlTaxable}" /></b></td>
					<td width="10" align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${ttlCgst}" /></b></td>
					<td width="10" align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${ttlSgst}" /></b></td>
					<td width="10" align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2"  minFractionDigits="2"  value="${ttlBill}" /></b></td>
				</tr>
		</tbody>
	</table>
	<br><br>
<div class="footer_btm" style="display: inline-block; width: 100%; text-align: center; position: absolute; bottom:0; margin: 20px 0 0 0;">
	<img alt="" src="${pageContext.request.contextPath}/resources/img/mongi.png" height="20px;" style="float: left; vertical-align: middle;">
	<span style="display: inline-block; float:left; text-align: center; width: 70%; vertical-align: middle; font-size: 12px;  ">******</span>
	<img alt="" src="${pageContext.request.contextPath}/resources/img/powerd_logo.png" height="10px;" style="float: right; vertical-align: top;">
	</div>
	<!-- END Main Content -->

</body>
</html>