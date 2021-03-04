<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<style>
	table{
  width:100%;
  table-layout: fixed;
  border:1px solid #ddd;
}
/* Model Css */
body {font-family: Arial, Helvetica, sans-serif;}

/* The Modal (background) */
.modal {
  display: none; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  padding-top: 100px; /* Location of the box */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
  background-color: #fefefe;
  margin: auto;
  padding: 10px;
  border: 1px solid #888;
  width: 16%;
}

/* The Close Button */
.close {
  color: #aaaaaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}
		
	.modal-content{
	    margin-top: 5%;
	    margin-left: 35%;
	    width: 55%;
	    height: 50%;
	}
	</style>

	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<body>
	<c:url value="/showSpCakeImage" var="showSpCakeImage"></c:url>
	<c:url value="/getSpCakePrintIds" var="getSpCakePrintIds"/>
	
	<jsp:include page="/WEB-INF/views/include/logout.jsp"></jsp:include> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tableSearch.css">


	<div class="container" id="main-container">

		<!-- BEGIN Sidebar -->
		<div id="sidebar" class="navbar-collapse collapse">

			<jsp:include page="/WEB-INF/views/include/navigation.jsp"></jsp:include>

			<div id="sidebar-collapse" class="visible-lg">
				<i class="fa fa-angle-double-left"></i>
			</div>
			<!-- END Sidebar Collapse Button -->
		</div>
		<!-- END Sidebar -->


		<!-- BEGIN Content -->
		<div id="main-content">
			<!-- BEGIN Page Title -->
			<!-- <div class="page-title">
				<div>
					<h1>
						<i class="fa fa-file-o"></i>Special Cake
					</h1>
				</div>
			</div> -->
			<!-- END Page Title -->
			
			
			
			<c:set var="isEdit" value="0">
					</c:set>
					<c:set var="isView" value="0">
					</c:set>
					<c:set var="isDelete" value="0">
					</c:set>

					<c:forEach items="${sessionScope.newModuleList}" var="modules">
						<c:forEach items="${modules.subModuleJsonList}" var="subModule">

							<c:choose>
								<c:when test="${subModule.subModuleMapping eq 'showSpecialCake'}">

									<c:choose>
										<c:when test="${subModule.editReject=='visible'}">
											<c:set var="isEdit" value="1">
											</c:set>
										</c:when>
										<c:otherwise>
											<c:set var="isEdit" value="0">
											</c:set>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${subModule.view=='visible'}">
											<c:set var="isView" value="1">
											</c:set>
										</c:when>
										<c:otherwise>
											<c:set var="isView" value="0">
											</c:set>
										</c:otherwise>
									</c:choose>


									<c:choose>
										<c:when test="${subModule.deleteRejectApprove=='visible'}">
											<c:set var="isDelete" value="1">
											</c:set>
										</c:when>
										<c:otherwise>
											<c:set var="isDelete" value="0">
											</c:set>
										</c:otherwise>
									</c:choose>
								</c:when>
							</c:choose>
						</c:forEach>
					</c:forEach>
			<!-- BEGIN Main Content -->
			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="col-md-12">
							<div class="box">
                           	<div class="row" >
									<div class="col-md-12">

										<div class="box" >
											<div class="box-title">
												<h3>
													<i class="fa fa-table"></i> Special Cake List
												</h3>
												<div class="box-tool">
													<a data-action="collapse" href="#"><i
														class="fa fa-chevron-up"></i></a>
													<!--<a data-action="close" href="#"><i class="fa fa-times"></i></a>-->
												</div>
											</div>
											`
											
						<div class="box-content">
						<%-- <form action="${pageContext.request.contextPath}/uploadSpByFile" class="form-horizontal"
							method="post" enctype="multipart/form-data">
							<div class="form-group">
						<div class="col-sm-9 col-sm-offset-3 col-lg-2 col-lg-offset-5">	<input type="button" id="expExcel1" class="btn btn-primary" value="Excel Import Format" onclick="exportToExcel1();">
						</div>		<label class="col-sm-1 col-lg-1 control-label">Select
									File</label>
								<div class="col-sm-3 col-lg-2 controls">
									<input type="file"  name="file" required/>
								</div>&nbsp;&nbsp;&nbsp;
								<div class="col-sm-2 col-lg-1">
									<input type="submit" class="btn btn-primary" value="Save">
								</div>
								</div>
							
							</form> --%>
<div class="col-md-9" ></div> 
					<label for="search" class="col-md-3" id="search">
    <i class="fa fa-search" style="font-size:20px"></i>
									<input type="text"  id="myInput" onkeyup="myFunction()" style="border-radius: 25px;" placeholder="Search by code.." title="Type in a name">
										</label>  

							<div class="clearfix"></div>
							
								<div id="table-scroll" class="table-scroll">
							 
									<div id="faux-table" class="faux-table" aria="hidden" >
									<table id="table2"class="table table-advance">
											<thead>
												<tr class="bgpink">
															<th  style="width: 38px" align="left">Select</th>
												
														<th style="width: 38px" align="left">No</th>
																<!-- <th width="104" style="text-align: center;">Image</th> -->
																<th width="126" style="text-align: center;">Code</th>
																<th width="120" style="text-align: center;">Name</th>
																<th width="96" style="text-align: center;">Type</th>
																<th width="96" style="text-align: center;">Min Weight</th>
																<th width="96" style="text-align: center;">Max Weight</th>
																<th width="80" style="text-align: center;">Rate</th>																
																<th width="80" style="text-align: center;">MRP</th>
																<th width="80" style="text-align: center;">Add On Rate</th>
																<th width="90" style="text-align: center;">Action</th>
												</tr>
												</thead>
												</table>
									
									</div>
									<div class="table-wrap"  >
									
										<table id="table1" class="table table-advance">
											<thead>
												<tr class="bgpink">
											<th  style="width:38px" align="left">Select</th>
												
													<th  style="width: 38px" align="left">No</th>
																<!-- <th width="104" style="text-align: center;">Image</th> -->
																<th width="126" style="text-align: center;">Code</th>
																<th width="120" style="text-align: center;">Name</th>
																<th width="96" style="text-align: center;">Type</th>
																<th width="96" style="text-align: center;">Weight</th>
																<th width="96" style="text-align: center;">Weight</th>
																<th width="80" style="text-align: center;">Rate</th>
																<th width="80" style="text-align: center;">MRP</th>
																<th width="80" style="text-align: center;">Add On Rate</th>
																<th width="90" style="text-align: center;">Action</th>
												</tr>
												</thead>
												<tbody>
					<c:forEach items="${specialCakeList}" var="specialCake" varStatus="count">


																<tr>
																<td><input type="checkbox" class="chk" name="select_to_print" id="${specialCake.spId}"	value="${specialCake.spId}"/></td>
																
																	<td><c:out value="${count.index+1}"/></td>
																	<%-- <td style="text-align: center;">
																			<img src="${url}${specialCake.spImage}" width="70" height="70" 	
																			 onerror="this.src='resources/img/No_Image_Available.jpg';"/>  
																	</td> --%>
																	<td style="text-align: center;"><c:out
																			value="${specialCake.spCode}  "></c:out></td>
																	<td style="text-align: left;"><c:out
																			value="${specialCake.spName}  "></c:out></td>
																	<c:choose>
																			<c:when test="${specialCake.spType==1}">
																			<td style="text-align: left; padding-left: 3%;"><c:out value="Chocolate"></c:out></td>
																				
																			</c:when>
																			<c:when test="${specialCake.spType==2}">
																			<td style="text-align: left; padding-left: 3%;"><c:out value="FC"></c:out></td>
																				
																			</c:when>
																			
																			
																			<c:otherwise>
																				<td style="text-align: left; padding-left: 3%;"><c:out value="ALL"></c:out></td>
																				
																			</c:otherwise>
																		</c:choose>
																			<td style="text-align: right; padding-right: 3%;"><c:out
																			value="${specialCake.spMinwt}  "></c:out></td>
																			<td  style="text-align: right; padding-right: 3%;"><c:out
																			value="${specialCake.spMaxwt}  "></c:out></td>
																			<td  style="text-align: right; padding-right: 1%;"><c:out
																			value="${specialCake.spRate1}  "></c:out></td>
																			<td  style="text-align: right; padding-right: 1%;"><c:out
																			value="${specialCake.mrpRate1}  "></c:out></td>
																			
																			
																		<td style="text-align: left;"><c:out
																			value="${specialCake.isAddonRateAppli == 1 ? 'YES' : 'NO'}  "></c:out></td>
																		
																		<c:choose>
																	<c:when test="${isEdit==1 and isDelete==1}">
																		<td  style="text-align: center;"><a
																		href="updateSpCake/${specialCake.spId}"data-toggle="tooltip" title="Edit Special Cake">	<span
																			class="glyphicon glyphicon-edit"></span></a>
																			<a href="viewSpCakeDetailed/${specialCake.spId}" class="action_btn" data-toggle="tooltip" title="Sp Ingredients Details">
					                                                  <i class="fa fa-list"></i></a>
                                                                         <a href="deleteSpecialCake/${specialCake.spId}"
																		onclick="return confirm('Are you sure want to delete this record');" data-toggle="tooltip" title="Delete"><span
																			class="glyphicon glyphicon-remove"></span></a>
																			
																	<a href="javascript:void(0);" data-toggle="tooltip" title="Image" onclick="openImg(${specialCake.spId})">
																		<span class="glyphicon glyphicon-picture"></span>
																	</a>		
																	</td>
														
																	</c:when>

																	<c:when test="${isEdit==1 and isDelete==0}">
																		<td  style="text-align: center;"><a
																		href="updateSpCake/${specialCake.spId}"data-toggle="tooltip" title="Edit Special Cake"><span
																			class="glyphicon glyphicon-edit"></span></a>
																			<a href="viewSpCakeDetailed/${specialCake.spId}" class="action_btn"data-toggle="tooltip" title="Sp Ingredients Details" >
					                                                  <i class="fa fa-list"></i></a>
                                                                         <a href="deleteSpecialCake/${specialCake.spId}"  class="disableClick"
																		onClick="return confirm('Are you sure want to delete this record');"><span
																			class="glyphicon glyphicon-remove"></span></a>
																	
																		<a href="javascript:void(0);" data-toggle="tooltip" title="Image" onclick="openImg(${specialCake.spId})">
																		<span class="glyphicon glyphicon-picture"></span>
																	</a>
																	</td>
																	</c:when>

																	<c:when test="${isEdit==0 and isDelete==1}">
																		<td  style="text-align: center;"><a
																		href="updateSpCake/${specialCake.spId}"  class="disableClick"><span
																			class="glyphicon glyphicon-edit"></span></a>
																			<a href="viewSpCakeDetailed/${specialCake.spId}" class="action_btn" >
					                                                  	<i class="fa fa-list"></i></a>
                                                                         <a href="deleteSpecialCake/${specialCake.spId}"
																		onClick="return confirm('Are you sure want to delete this record');"><span
																			class="glyphicon glyphicon-remove"data-toggle="tooltip" title="Delete"></span></a></td>
																	
																	<a href="javascript:void(0);" data-toggle="tooltip" title="Image" onclick="openImg(${specialCake.spId})">
																		<span class="glyphicon glyphicon-picture"></span>
																	</a>
																	</c:when>

																	<c:otherwise>

																	<td  style="text-align: center;"><a
																		href="updateSpCake/${specialCake.spId}"  class="disableClick"><span
																			class="glyphicon glyphicon-edit"></span></a>
																			<a href="viewSpCakeDetailed/${specialCake.spId}" class="action_btn"data-toggle="tooltip" title="Sp Ingredients Details" >
					                                                  	<i class="fa fa-list"></i></a>
                                                                         <a href="deleteSpecialCake/${specialCake.spId}"  class="disableClick"
																		onClick="return confirm('Are you sure want to delete this record!');"><span
																			class="glyphicon glyphicon-remove"></span></a>
																	<a href="javascript:void(0);" data-toggle="tooltip" title="Image" onclick="openImg(${specialCake.spId})">
																		<span class="glyphicon glyphicon-picture"></span>
																	</a>		
																	</td>	
																	</c:otherwise>
																</c:choose>
																			
																			
																			
																<%-- 			
																	<td align="left"><a
																		href="updateSpCake/${specialCake.spId}"><span
																			class="glyphicon glyphicon-edit"></span></a>
																			<a href="viewSpCakeDetailed/${specialCake.spId}" class="action_btn" >
					                                                  	<abbr title="Detailed"><i class="fa fa-list"></i></abbr></a>
                                                                         <a href="deleteSpecialCake/${specialCake.spId}"
																		onClick="return confirm('Are you sure want to delete this record');"><span
																			class="glyphicon glyphicon-remove"></span></a></td> --%>
																</tr>
															</c:forEach>


							</tbody>

						</table>
					</div>
				</div>



												<div class="form-group" id="range"
													style="background-color: white;">

													<input type="button" id="expExcel" class="btn btn-primary"
														value="Export To Excel" onclick="exportToExcel();">
													<input type="button" margin-right: 5px;" id="btn_delete"
														class="btn btn-primary" onclick="deleteBySpId()"
														value="Delete" /> <input type="button"
														margin-right: 5px;" id="btn_exl_pdf"
														class="btn btn-primary" onclick="getHeaders()"
														value="Excel / Pdf" />
												</div>

												<%-- <div class="box-content">

												<div class="clearfix"></div>
												<div class="table-responsive" style="border: 0">
													<table width="100%" class="table table-advance" id="table1">
														<thead>
															<tr>
																<th width="17" style="width: 18px">#</th>
																<th width="364" align="left">Image</th>
																<th width="282" align="left">Code</th>
																<th width="218" align="left">Name</th>
																<th width="106" align="left">Type</th>
																<th width="206" align="left">Min Weight</th>
																<th width="206" align="left">Max Weight</th>
																<th width="206" align="left">Book Before</th>
																<th width="66" align="left">Action</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${specialCakeList}" var="specialCake" varStatus="count">


																<tr>
																	<td><c:out value="${count.index+1}"/></td>
																	<td align="left">
																			 <img src="${url}${specialCake.spImage}" width="120" height="100" 	
																			 onerror="this.src='resources/img/No_Image_Available.jpg';"/> 
																	</td>
																	<td align="left"><c:out
																			value="${specialCake.spCode}  "></c:out></td>
																	<td align="left"><c:out
																			value="${specialCake.spName}  "></c:out></td>
																	<c:choose>
																			<c:when test="${specialCake.spType==1}">
																			<td align="left"><c:out value="Chocolate"></c:out></td>
																				
																			</c:when>
																			<c:when test="${specialCake.spType==2}">
																			<td align="left"><c:out value="FC"></c:out></td>
																				
																			</c:when>
																			
																			
																			<c:otherwise>
																				<td align="left"><c:out value="ALL"></c:out></td>
																				
																			</c:otherwise>
																		</c:choose>
																			<td align="left"><c:out
																			value="${specialCake.spMinwt}  "></c:out></td>
																				<td align="left"><c:out
																			value="${specialCake.spMaxwt}  "></c:out></td>
																				<td align="left"><c:out
																			value="${specialCake.spBookb4}  "></c:out></td>
																			
																	<td align="left"><a
																		href="updateSpCake/${specialCake.spId}"><span
																			class="glyphicon glyphicon-edit"></span></a>
																			<a href="viewSpCakeDetailed/${specialCake.spId}" class="action_btn" >
					                                                  	<abbr title="Detailed"><i class="fa fa-list"></i></abbr></a>
                                                                         <a href="deleteSpecialCake/${specialCake.spId}"
																		onClick="return confirm('Are you sure want to delete this record');"><span
																			class="glyphicon glyphicon-remove"></span></a></td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
												<!-- <div class="form-group">
													<div
														class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2">
														
														
													</div>
												</div> -->
												</form>
											</div> --%>
										</div>
									</div>
								</div>
</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- END Main Content -->
			<jsp:include page="/WEB-INF/views/include/copyrightyear.jsp"></jsp:include>

			<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
				class="fa fa-chevron-up"></i></a>
		</div>
		<!-- END Content -->
	</div>
	<!-- END Container -->
	
	<table width="100%" class="table table-advance" id="printtable2" style="display: none;">
		<thead style="background-color: #f3b5db;" >
			<tr>
				<th>Name</th>
				<th>UOM</th>
				<th>GST%</th>
				<th>HSN Code</th>
				<th>Cake Type</th>
				<th>Cake Shape</th>
				<th>Flavour</th>
				<th>Event</th>
				<th>Book Before</th>
				<th>MAX Range</th>
				<th>MIN Range</th>
				<th>Increamented By</th>
				<th>MRP1</th>
				<th>MRP2</th>
				<th>MRP3</th>
				<th>Customer Choice</th>
				<th>Addon Appli</th>
				<th>Status</th>				
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>

	<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content" id="modal_theme_primary">
    <span class="close">&times;</span>
    <div class="box">
									<div class="box-title">
										<h3>
											<i class="fa fa-table"></i> Select Columns
										</h3>										
									</div>

				<div class="box-content">
					<div class="clearfix"></div>
					<div class="table-responsive" style="border: 0">
						<table width="100%" class="table table-advance" id="modelTable">
							<thead style="background-color: #f3b5db;">
								<tr>
									<th width="15"><input type="checkbox" name="selAll"
										id="selAllChk" />
									</th>
									<th>Headers</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<span class="validation-invalid-label" id="error_modelchks"
										style="display: none;">Select Check Box.</span>
					</div>
				</div>
				<div class="form-group" style="background-color: white;">
									&nbsp;	&nbsp;	&nbsp;	&nbsp;
										<input type="button" margin-right: 5px;"
											class="btn btn-primary" id="expExcel" onclick="getIdsReport(1)" 
											value="Excel" />
									&nbsp;	&nbsp;	&nbsp;	&nbsp;
										<input type="button" margin-right: 5px;"
											class="btn btn-primary" onclick="getIdsReport(2)" 
											value="Pdf" />
									</div>
									</div>
								
  </div>

</div>

	<!--basic scripts-->
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="${pageContext.request.contextPath}/resources/assets/jquery/jquery-2.0.3.min.js"><\/script>')
	</script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/jquery-cookie/jquery.cookie.js"></script>

	<!--page specific plugin scripts-->
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.resize.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.pie.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.stack.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.crosshair.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.tooltip.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/sparkline/jquery.sparkline.min.js"></script>


	<!--page specific plugin scripts-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/additional-methods.min.js"></script>

	<!--flaty scripts-->
	<script src="${pageContext.request.contextPath}/resources/js/flaty.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/flaty-demo-codes.js"></script>
	<!--page specific plugin scripts-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/chosen-bootstrap/chosen.jquery.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/clockface/js/clockface.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/date.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/daterangepicker.js"></script>
</body>
<script>
function myFunction() {
  var input, filter, table, tr, td,td1, i;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("table1");
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[3];
    td1 = tr[i].getElementsByTagName("td")[4];

    if (td) {
      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      }else if (td1.innerHTML.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      }  else {
        tr[i].style.display = "none";
      }
    }       
  }
}
</script>

<script>
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();   
});
</script>
<script type="text/javascript">
function deleteBySpId()
{

var checkedVals = $('.chk:checkbox:checked').map(function() {
    return this.value;
}).get();
checkedVals=checkedVals.join(",");

if(checkedVals=="")
	{
	alert("Please Select Special Cake")
	}
else
	{
	 if (confirm("Are you sure want to delete this record!")) {
		 window.location.href='${pageContext.request.contextPath}/deleteSpecialCake/'+checkedVals;
		  } 
	

	}

}
</script>
<script type="text/javascript">
function exportToExcel()
{
	window.open("${pageContext.request.contextPath}/exportToExcel");
			document.getElementById("expExcel").disabled=true;
}

function exportToExcel1()
{
	window.open("${pageContext.request.contextPath}/exportToExcelDummy");
			document.getElementById("expExcel1").disabled=true;
}
</script>
<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <span class="close">&times;</span>
     <img src="" width="170" height="170" id="spImg"
		onerror="this.src='resources/img/No_Image_Available.jpg';"/>
  </div>

</div>

<script>
//Get the modal
var modal = document.getElementById("myModal");
function openImg(spId){
	var image = new Image();
	$.getJSON('${showSpCakeImage}', {
					spId : spId,
					ajax : 'true',
				},  function(data) {
					document.getElementById("spImg").src = data.message;
					modal.style.display = "block";
				});
  
}

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}
</script>
<script>
				function getHeaders(){
					
					openModel();
					$('#modelTable td').remove();
				var thArray = [];
	
				$('#printtable2 > thead > tr > th').each(function(){
				    thArray.push($(this).text())
				})
				
					
				var seq = 0;
					for (var i = 0; i < thArray.length; i++) {
						seq=i+1;					
						var tr1 = $('<tr></tr>');
						tr1.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html('<input type="checkbox" class="chkcls" name="chkcls'
								+ seq
								+ '" id="catCheck'
								+ seq
								+ '" value="'
								+ seq
								+ '">') );
						tr1.append($('<td style="padding: 7px; line-height:0; border-top:0px;"></td>').html(innerHTML=thArray[i]));
						$('#modelTable tbody').append(tr1);
					}
				}
				
				$(document).ready(

						function() {

							$("#selAllChk").click(
									function() {
										$('#modelTable tbody input[type="checkbox"]')
												.prop('checked', this.checked);

									});
						});
				
				  function getIdsReport(val) {
					  var isError = false;
						var checked = $("#modal_theme_primary input:checked").length > 0;
					
						if (!checked) {
							$("#error_modelchks").show()
							isError = true;
						} else {
							$("#error_modelchks").hide()
							isError = false;
						}

						if(!isError){
					  var elemntIds = [];										
								
								$(".chkcls:checkbox:checked").each(function() {
									elemntIds.push($(this).val());
								}); 
												
						$
						.getJSON(
								'${getSpCakePrintIds}',
								{
									elemntIds : JSON.stringify(elemntIds),
									val : val,
									ajax : 'true'
								},
								function(data) {
									if(data!=null){
										//$("#modal_theme_primary").modal('hide');
										if(val==1){
											window.open("${pageContext.request.contextPath}/exportToExcelNew");
											//document.getElementById("expExcel").disabled = true;
										}else{			
											 window.open('${pageContext.request.contextPath}/pdfForReport?url=pdf/getSpCakeListPdf/'+elemntIds.join());
											 $('#selAllChk').prop('checked', false);
										}
									}
								});
						}
					}		
				</script>
<script>
//Get the modal
var modal = document.getElementById("myModal");
function openModel(){
	modal.style.display = "block";
}

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}
</script>
</html>