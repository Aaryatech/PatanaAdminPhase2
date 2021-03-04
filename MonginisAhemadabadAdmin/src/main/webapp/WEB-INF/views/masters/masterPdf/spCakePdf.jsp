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
<title>Sp Cake List PDF</title>
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
    font-size: 60%;
	margin-top: 0;
	padding: 0;

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
<div align="center"> <h5> Sp Cake List</h5></div>
	<table  align="center" border="1" cellspacing="0" cellpadding="1" 
		id="table_grid" class="table table-bordered">
		<thead>
			<tr class="bgpink">
			<th width="10%">Sr. No.</th>
				<c:forEach items="${spCakeIds}" var="spCakeIds" varStatus="count">

					<c:if test="${spCakeIds==1}">
						<th>Name</th>
					</c:if>

					<c:if test="${spCakeIds==2}">
						<th>UOM</th>
					</c:if>

					<c:if test="${spCakeIds==3}">
						<th>GST</th>
					</c:if>

					<c:if test="${spCakeIds==4}">
						<th>HSN Code</th>	
					</c:if>

					<c:if test="${spCakeIds==5}">
						<th>Cake Type</th>
					</c:if>

					<c:if test="${spCakeIds==6}">
						<th>Cake Shape</th>
					</c:if>

					<c:if test="${spCakeIds==7}">
						<th>Flavour</th>
					</c:if>
					
					<c:if test="${spCakeIds==8}">
						<th>Event</th>
					</c:if>
					
					<c:if test="${spCakeIds==9}">
						<th>Book Before</th>
					</c:if>
					
					<c:if test="${spCakeIds==10}">
						<th>MAX Range</th>
					</c:if>
					
					<c:if test="${spCakeIds==11}">
						<th>MIN Range</th>
					</c:if>
					
					<c:if test="${spCakeIds==12}">
						<th>Increamented By</th>
					</c:if>
					
					<c:if test="${spCakeIds==13}">
						<th>MRP1</th>
					</c:if>
					
					<c:if test="${spCakeIds==14}">
						<th>MRP2</th>
					</c:if>
					
					<c:if test="${spCakeIds==15}">
						<th>MRP3</th>
					</c:if>
					
					<c:if test="${spCakeIds==16}">
						<th>Customer Choice</th>
					</c:if>
					
					<c:if test="${spCakeIds==17}">
						<th>Addon Appli</th>
					</c:if>
					
					<c:if test="${spCakeIds==18}">
						<th>Status</th>
					</c:if>

						
				</c:forEach>
			</tr>
		</thead>
		<tbody>
		
			<c:forEach items="${printSpCakeList}" var="printRouteList" varStatus="count">
				<tr>
				
					<td>${count.index+1}</td>
					<c:forEach items="${spCakeIds}" var="spCakeIds">
										
					<c:if test="${spCakeIds==1}">
						<td style="text-align: left;">${printRouteList.spName}</td>
					</c:if>
					
					<c:if test="${spCakeIds==2}">						
						<td style="text-align: left;">${printRouteList.uom}</td>
					</c:if>
					
					<c:if test="${spCakeIds==3}">						
					<td style="text-align: right;">${printRouteList.spTax3}</td>
					</c:if>
					
					<c:if test="${spCakeIds==4}">						
						<td style="text-align: left;">${printRouteList.spHsncd}</td>
					</c:if>
					
					<c:if test="${spCakeIds==5}">						
						<td style="text-align: left;">${printRouteList.typeName}</td>
					</c:if>
					
					<c:if test="${spCakeIds==6}">						
						<td style="text-align: left;">${printRouteList.shapeName}</td>
					</c:if>
					
					<c:if test="${spCakeIds==7}">						
						<td style="text-align: left;">${printRouteList.flavour}</td>
					</c:if>	
					
					<c:if test="${spCakeIds==8}">						
						<td style="text-align: left;">${printRouteList.eventName}</td>
					</c:if>	
					
					<c:if test="${spCakeIds==9}">						
						<td style="text-align: right;">${printRouteList.spBookB4}</td>
					</c:if>									
					
					<c:if test="${spCakeIds==10}">						
						<td style="text-align: right;">${printRouteList.spMaxwt}</td>
					</c:if>	
					
					<c:if test="${spCakeIds==11}">						
						<td style="text-align: right;">${printRouteList.spMinwt}</td>
					</c:if>	
					
					<c:if test="${spCakeIds==12}">						
						<td style="text-align: right;">${printRouteList.increamentedBy}</td>
					</c:if>	
					
					<c:if test="${spCakeIds==13}">						
						<td style="text-align: right;">${printRouteList.mrpRate1}</td>
					</c:if>	
					
					<c:if test="${spCakeIds==14}">						
						<td style="text-align: right;">${printRouteList.mrpRate2}</td>
					</c:if>	
					
					<c:if test="${spCakeIds==15}">						
						<td style="text-align: right;">${printRouteList.mrpRate3}</td>
					</c:if>	
					
					<c:if test="${spCakeIds==16}">						
						<td style="text-align: left;">${printRouteList.isCustChoiceCk == 0 ? 'Yes' : 'No'}</td>
					</c:if>	
					
					<c:if test="${spCakeIds==17}">						
						<td style="text-align: left;">${printRouteList.isAddonRateAppli == 0 ? 'Yes' : 'No'}</td>
					</c:if>	
					
					<c:if test="${spCakeIds==18}">						
						<td style="text-align: left;">${printRouteList.isUsed == 0 ? 'Yes' : 'No'}</td>
					</c:if>	
				</c:forEach>
				</tr>
			</c:forEach>				
		</tbody>
	</table>
	

	<!-- END Main Content -->

</body>
</html>