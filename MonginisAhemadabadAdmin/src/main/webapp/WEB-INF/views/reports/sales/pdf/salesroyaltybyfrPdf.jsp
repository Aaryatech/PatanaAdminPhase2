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
<title>Franchasee-wise Royalty Report  PDF</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->


<style type="text/css">
 table {
	border-collapse: collapse;
	font-size: 10;
	width:100%;
    page-break-inside: auto !important 

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


h5{
	margin-bottom: 6px;
}
h6  {
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
<p align="center">${FACTORYNAME} <br> ${FACTORYADDRESS}</p>
<div align="center"> <h5>Franchasee-wise royalty Report <br> Dates : ${fromDate} ,${toDate}</h5></div>
	<table  align="center" border="1" cellspacing="0" cellpadding="1" 
		id="table_grid" class="table table-bordered">
	
		<thead>
			<tr class="bgpink">
				<th height="25">Sr.No.</th>
				<th>Franchisee Name</th>
				<th>City</th>
				<th>Sale Value</th>
				<th>GRN Value</th>
				<th>GVN Value</th>
				<th>%</th>
				<th>Net Value</th>
				<th>Amount</th>
		<!-- 		<th>Royalty Amt</th> -->
			</tr>
		</thead>
		<tbody>
		
			<c:set var="grandNetValue" value="${0 }" />
						<c:set var="granBillTaxableValue" value="${0 }" />
			
			<c:set var="grandGrnValue" value="${0 }" />
			<c:set var="grandGvnValue" value="${0 }" />
			<c:set var="FinalNetValue" value="${0 }" />
				<c:set var="taxPer" value="${0}" />
				
								<c:set var="rAmtSum" value="${0}" />
				
			<c:forEach items="${report}" var="report" varStatus="count">
				<tr>
					<td><c:out value="${count.index+1}" /></td>
					<td><c:out value="${report.frName} ${report.frCode}" /></td>
					<td><c:out value="${report.frCity}" /></td>
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2" value="${report.tBillTaxableAmt}" /></td>
								
								
														<c:set var="granBillTaxableValue" value="${granBillTaxableValue+report.tBillTaxableAmt}" />
								
					
					<td align="right">
					<fmt:formatNumber type="number"
								maxFractionDigits="2" value="${report.tGrnTaxableAmt}" /></td>
					<td align="right">
					<fmt:formatNumber type="number"
								maxFractionDigits="2" value="${report.tGvnTaxableAmt}" /></td>
					<td align="right"><c:out value="${royPer}" /></td>
					
					<c:set var="netValue" value="${report.tBillTaxableAmt -(report.tGrnTaxableAmt + report.tGvnTaxableAmt)}" />
					
					<c:set var="grandNetValue"
						value="${grandNetValue + netValue}" />
						
					<c:set var="grandGrnValue" value="${grandGrnValue + report.tGrnTaxableAmt}" />
					
										<c:set var="grandGvnValue" value="${grandGvnValue + report.tGvnTaxableAmt}" />
				
					<td align="right">
					<fmt:formatNumber type="number"
								maxFractionDigits="2" value="${netValue}" /></td>
								
					<c:set var="rAmt"
						value="${netValue*royPer/100}" />
							<c:set var="taxPer"
						value="${taxPer + 3}" />
							<c:set var="FinalNetValue"
						value="${FinalNetValue + netValue}" />
						
					<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2" value="${rAmt}" /></td>
								
																<c:set var="rAmtSum" value="${rAmtSum+rAmt}" />
								
				</tr>

			</c:forEach>
				<tr>
					<td colspan='3'><b>Total</b></td>
					<td align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2" value="${granBillTaxableValue}" /></b></td>
					<td align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2" value="${grandGrnValue}" /></b></td>
					<td align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2" value="${grandGvnValue}" /></b></td>
								<td></td>
				 <%--  <td  width="100" align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2" value="${taxPer}" /></b></td> --%>
					<td  align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2" value="${FinalNetValue}" /></b></td>
								
								
								<td align="right"><b><fmt:formatNumber type="number"
								maxFractionDigits="2" value="${rAmtSum}" /></b></td>
								
								
								
								
								
					
					
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