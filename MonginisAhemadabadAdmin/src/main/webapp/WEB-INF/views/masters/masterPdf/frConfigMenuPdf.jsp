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
<title>Franchise Configured Menu List PDF</title>
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
<div align="center"> <h5> Franchise Configured Menu List</h5></div>
	<table  align="center" border="1" cellspacing="0" cellpadding="1" 
		id="table_grid" class="table table-bordered">
		<thead>
			<tr class="bgpink">
			<th width="10%">Sr. No.</th>
				<c:forEach items="${menuIds}" var="menuIds" varStatus="count">
				
				<c:if test="${menuIds==1}">
						<th>Franchise</th>
					</c:if>

					<c:if test="${menuIds==2}">
						<th>Menu</th>
					</c:if>

					<c:if test="${menuIds==3}">
						<th>Category</th>
					</c:if>

					<c:if test="${menuIds==4}">
						<th>Type</th>
					</c:if>
					
					<c:if test="${menuIds==5}">
						<th>From Time</th>
					</c:if>
					
					<c:if test="${menuIds==6}">
						<th>To Time</th>
					</c:if>

					<c:if test="${menuIds==7}">
						<th>Profit%</th>	
					</c:if>
					
					<c:if test="${menuIds==8}">
						<th>GRN%</th>	
					</c:if>
					
					<c:if test="${menuIds==9}">
						<th>Discount%</th>	
					</c:if>

				</c:forEach>
			</tr>
		</thead>
		<tbody>
		
			<c:forEach items="${menuList}" var="menuList" varStatus="count">
				<tr>	
					<td>${count.index+1}</td>
					<c:forEach items="${menuIds}" var="menuIds">
					
					<c:if test="${menuIds==1}">
						<td style="text-align: left;">${menuList.frName}</td>
					</c:if>
										
					<c:if test="${menuIds==2}">
						<td style="text-align: left;">${menuList.menuTitle}</td>
					</c:if>
					
					<c:if test="${menuIds==3}">						
						<td style="text-align: left;">${menuList.catName}</td>
					</c:if>

						<c:if test="${menuIds==4}">
							<td style="text-align: left;">${menuList.type==0 ? 'Regular' : 
														menuList.type==1 ? 'Same Day Regular' : 
														menuList.type==2 ? 'Regular with limit' : 
														menuList.type==3 ? 'Regular cake As SP Order' : 'Delivery And Production Date'}</td>
						</c:if>
						
						<c:if test="${menuIds==5}">
							<td style="text-align: left;">${menuList.fromTime}</td>
						</c:if>
						
						
						<c:if test="${menuIds==6}">
							<td style="text-align: left;">${menuList.toTime}</td>
						</c:if>

						<c:if test="${menuIds==7}">
							<td style="text-align: left;">${menuList.grnPer}</td>
						</c:if>

						<c:if test="${menuIds==8}">
							<td style="text-align: left;">${menuList.profitPer}</td>
						</c:if>

						<c:if test="${menuIds==9}">
							<td style="text-align: left;">${menuList.discPer}</td>
						</c:if>
					</c:forEach>
				</tr>
			</c:forEach>				
		</tbody>
	</table>
	

	<!-- END Main Content -->

</body>
</html>