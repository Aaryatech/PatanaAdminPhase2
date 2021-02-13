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
<title>Current Stock PDF</title>
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
		<h5>Current Stock Pdf</h5>
	</div>
	
	<table align="center" border="1" cellspacing="0" cellpadding="1"
		id="table_grid" class="table table-bordered">
		<thead>
			<tr class="bgpink">
				<th>Sr.No.</th>
				<th>Item Id</th>
				<th>Item Name</th>
				
				<c:forEach items="${frIdsList}" var="frIds"> 
					 <c:forEach items="${frList}" var="frList">
						<c:if test="${frIds == frList.frId}">
							<th>${frList.frName}</th>
						</c:if>
					 </c:forEach>			
				 </c:forEach>
			</tr>
			
			
		</thead>
		<tbody>
			
			 <c:forEach items="${currStkItem}" var="stockItm" varStatus="count">			 
				<tr>
					<td width="60" align="center"><c:out value="${count.index+1}" /></td>
					<c:set value="0" var="flag"/>
					<td width="120" align="center">${stockItm}</td>
					
					<c:forEach items="${curStkFrList}" var="stockFr">
					 <c:forEach items="${frIdsList}" var="frIds"> 
						<c:if test="${stockFr.frId==frIds}"> 
							
							<c:set value="${stockFr.currentStockDetailList}" var="curStockResp"/>
							<c:forEach items="${curStockResp}" var="stock">
								<c:set value="${stock.currentRegStock }" var="regCurrentStock"/> 
								
								<c:if test="${stock.id==stockItm}"> 
									<c:if test="${flag==0}">
											<td width="120" style="padding: 3px;"><c:out
													value="${stock.itemName}" /></td>
													
										<c:set value="1" var="flag"/>
									</c:if>
									
									<c:choose>
										<c:when test="${regCurrentStock<0}">
											<td width="100" align="right" style="padding: 3px;"><fmt:formatNumber
													type="number" maxFractionDigits="2" minFractionDigits="2"
													value="0" /></td>
										</c:when>
										<c:otherwise>
											<td width="100" align="right" style="padding: 3px;"><fmt:formatNumber
													type="number" maxFractionDigits="2" minFractionDigits="2"
													value="${regCurrentStock }" /></td>
										</c:otherwise>
									</c:choose>
								</c:if>
							
							</c:forEach>
							
						</c:if>
					</c:forEach>			
					</c:forEach>
					

					<%-- <td width="120" style="padding: 3px;"><c:out
							value="${report.frName}" /></td>
					<td width="100" align="right" style="padding: 3px;"><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${report.reqQty}" /></td>
					 --%>

				</tr>
			</c:forEach> 
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