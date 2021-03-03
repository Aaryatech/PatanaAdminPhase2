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
<title>Product List PDF</title>
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
<div align="center"> <h5> Product List</h5></div>
	<table  align="center" border="1" cellspacing="0" cellpadding="1" 
		id="table_grid" class="table table-bordered">
		<thead>
			<tr class="bgpink">
			<th width="10%">Sr. No.</th>
				<c:forEach items="${porductIds}" var="porductIds" varStatus="count">

					<c:if test="${porductIds==1}">
						<th>Item Name</th>
					</c:if>

					<c:if test="${porductIds==2}">
						<th>UOM</th>
					</c:if>

					<c:if test="${porductIds==3}">
						<th>Sub Category</th>
					</c:if>

					<c:if test="${porductIds==4}">
						<th>MRP1</th>	
					</c:if>

					<c:if test="${porductIds==5}">
						<th>MRP2</th>
					</c:if>

					<c:if test="${porductIds==6}">
						<th>MRP3</th>
					</c:if>

					<c:if test="${porductIds==7}">
						<th>GST%</th>
					</c:if>
					
					<c:if test="${porductIds==8}">
						<th>HSN Code</th>
					</c:if>
					
					<c:if test="${porductIds==9}">
						<th>Shelf Life</th>
					</c:if>
					
					<c:if test="${porductIds==10}">
						<th>Sort No.</th>
					</c:if>
					
					<c:if test="${porductIds==11}">
						<th>Is Active</th>
					</c:if>

						
				</c:forEach>
			</tr>
		</thead>
		<tbody>
		<c:set value="" var="abcType"/>
			<c:forEach items="${printProductList}" var="printProductList" varStatus="count">
				<tr>				
					<td>${count.index+1}</td>
					<c:forEach items="${porductIds}" var="porductIds">										
					<c:if test="${porductIds==1}">
						<td style="text-align: left;">${printProductList.itemName}</td>
					</c:if>
					
					<c:if test="${porductIds==2}">						
						<td style="text-align: left;">${printProductList.uom}</td>
					</c:if>
					
					<c:if test="${porductIds==3}">						
					<td style="text-align: left;">${printProductList.subCatName}</td>
					</c:if>
					
					<c:if test="${porductIds==4}">						
						<td style="text-align: right;">${printProductList.itemMrp1}</td>
					</c:if>
					
					<c:if test="${porductIds==5}">						
						<td style="text-align: right;">${printProductList.itemMrp2}</td>
					</c:if>
					
					<c:if test="${porductIds==6}">						
						<td style="text-align: right;">${printProductList.itemMrp3}</td>
					</c:if>
					
					<c:if test="${porductIds==7}">						
						<td style="text-align: right;">${printProductList.itemTax3}</td>
					</c:if>			
					
					<c:if test="${porductIds==8}">						
						<td style="text-align: center;">${printProductList.itemHsncd}</td>
					</c:if>			
					
					<c:if test="${porductIds==9}">						
						<td style="text-align: right;">${printProductList.itemShelfLife}</td>
					</c:if>			
					
					<c:if test="${porductIds==10}">						
						<td style="text-align: right;">${printProductList.itemSortId}</td>
					</c:if>		
					
					<c:if test="${porductIds==11}">						
						<td style="text-align: left;">${printProductList.itemIsUsed==1 ? 'Active' : 
						printProductList.itemIsUsed==4 ? 'Inactive' : 
						printProductList.itemIsUsed==11 ? 'Sunday Active' : 
						printProductList.itemIsUsed==12 ? 'Monday Active' : 
						printProductList.itemIsUsed==13 ? 'Tuesday Active' : 
						printProductList.itemIsUsed==14 ? 'Wednesday Active' : 
						printProductList.itemIsUsed==15 ? 'Thursday Active' : 
						printProductList.itemIsUsed==16 ? 'Friday Active' : 'Saturday Active'}</td>
					</c:if>									
				</c:forEach>
				</tr>
			</c:forEach>				
		</tbody>
	</table>
	

	<!-- END Main Content -->

</body>
</html>