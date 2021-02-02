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
<title>Grn Gvn Pending Item Report PDF</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

<style type="text/css">
table {
	border-collapse: collapse;
	font-size: 14;
	width: 100%;
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
	<p align="center">${FACTORYNAME} <br> ${FACTORYADDRESS}</p>

	<div align="center">
		<h5>GRN GVN Pending Item Report <br>
		Dates : ${fromDate}, ${toDate},&nbsp;&nbsp;  By : ${isGrn==1 ? 'GRN' : isGrn==0 ? 'GVN' : 'All'},&nbsp;
		&nbsp; CRN Generate : ${isCrn==1 ? 'Yes' : isGrn==0 ? 'No' : 'All'}</h5>
	</div>
	<table align="center" border="1" cellspacing="0" cellpadding="1"
		id="table_grid" class="table table-bordered">
		<thead>
			<tr class="bgpink">
				<th>Sr.No.</th>
				<th>GRN No.</th>
				<th>Invoice No.</th>
				<th>Item Name</th>
				<th>GRN %/th>
				<th>Req Qty.</th>
				<th>Req Value</th>
				<th>Apr Qty.</th>
				<th>Apr Amt</th>
				<th>GRN Generated</th>
				<th>Status</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="reqQtySum" value="${0}" />
			<c:set var="reqValSum" value="${0}" />
			<c:set var="aprQtySum" value="${0}" />
			<c:set var="aprValSum" value="${0}" />
			<c:forEach items="${report}" var="report" varStatus="count">
				<tr>
					<td width="60" align="center"><c:out value="${count.index+1}" /></td>					
					<td width="120" align="center"><c:out
							value="${report.grngvnSrno}~${report.grngvnDate}" /></td>

					<td width="120" style="padding: 3px;"><c:out
							value="${report.invoiceNo}~${report.billDate}" /></td>

					<td width="120" style="padding: 3px;"><c:out
							value="${report.itemName}" /></td>
					<td width="120" style="padding: 3px;"><c:out
							value="${report.grnPer}" /></td>
					<td width="100" align="right" style="padding: 3px;"><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${report.reqQty}" /></td>
					<td width="100" align="right" style="padding: 3px;"><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${report.reqAmt}" /></td>
					<td align="right" width="100" style="padding: 3px;"><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${report.aprQty}" /></td>
					<td align="right" width="100" style="padding: 3px;"><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${report.aprAmt}" /></td>
				
					<td width="120" align="center"><c:out
							value="${report.isCreditNote == 1 ? 'Yes' : 'No'}" /></td>
					 
							
							
					<td width="120" align="center"><c:out
							value="${report.grngvnStatus == 1 ? 'Pending' : report.grngvnStatus == 2 ? 'Approved By Gate' :
								report.grngvnStatus == 3 ? 'Reject By Gate' : report.grngvnStatus == 4 ? 'Approved By Store' :
								report.grngvnStatus == 5 ? 'Reject By Store' : report.grngvnStatus == 6 ? 'Approved By Acc' :
								report.grngvnStatus == 7 ? 'Reject By Acc' : ''}" /></td>		
							
								
					<c:set var="reqQtySum" value="${reqQtySum + report.reqQty}" />
					<c:set var="reqValSum" value="${reqValSum+report.reqAmt}" />

					<c:set var="aprQtySum" value="${aprQtySum + report.aprQty}" />
					<c:set var="aprValSum" value="${aprValSum+report.aprAmt}" />

				</tr>
			</c:forEach>
			<tr>
				<td></td>
				<td width="100" colspan='4' align="left"><b>Total</b></td>
				<td width="100" align="right" style="padding: 3px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${reqQtySum}" /></b></td>
				<td width="100" align="right" style="padding: 3px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${reqValSum}" /></b></td>
				<td width="100" align="right" style="padding: 3px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${aprQtySum}" /></b></td>

				<td width="100" align="right" style="padding: 3px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${aprValSum}" /></b></td>
				<td></td>
				<td></td>
				
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