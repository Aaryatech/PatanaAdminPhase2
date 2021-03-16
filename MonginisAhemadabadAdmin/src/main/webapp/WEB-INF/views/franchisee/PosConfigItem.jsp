<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body>

	<jsp:include page="/WEB-INF/views/include/logout.jsp"></jsp:include>

	<c:url var="getDateForAccGvnHeader" value="/getDateFoAccGvnHeader" />

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
			<div class="page-title">
				<div>
					<h1>
						<i class="fa fa-file-o"></i>
					</h1>

				</div>
			</div>
			<!-- END Page Title -->

			<!-- BEGIN Main Content -->
			<div class="row">
				<div class="col-md-12">
					<div class="box">
						<div class="box-title">
							<h3>
								<i class="fa fa-bars"></i>Pos Config Items
							</h3>
							<div class="box-tool">
								<a href="">Back to List</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>
							<!-- <div class="box-tool">
								<a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a> <a data-action="close" href="#"><i
									class="fa fa-times"></i></a>
							</div> -->
						</div>


						<div class="box-content">
							<form action="postPosConfigItem" method="post"
<%-- 								action="${pageContext.request.contextPath}/postPosConfigItem" method="post"--%>
								class="form-horizontal"  id="validation-form">

								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Config
										Item Names</label>
									<div class="col-sm-5 col-lg-3 controls">
										<input class="form-control " id="config_name"
											size="16" type="text" name="config_name" value="" />
									</div>
								
 
                               
								
								</div>

								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Item Ids</label>

									<div class="col-sm-5 col-lg-3 controls">

										<select data-placeholder="Item Ids"
											class="form-control chosen" multiple="multiple" tabindex="6"
											id="item_name" name="item_name[]" >
										<option value="">Select Item Ids</option>
										 <c:forEach items="${itemsList}" var="itemsList">
										            	  <option value="${itemsList.itemId}"><c:out value="${itemsList.itemName}"></c:out></option>
										</c:forEach> 
									     	</select>
									     	</div>
                                        	</div>
                           <div class="form-group" align="center">
						<input type="submit" id="pdf" class="btn btn-primary" value="Submit" >	 
						</div>         
                                   
                                   
                                   
                                   	<div class="col-md-12 table-responsive">
						<table class="table table-bordered table-striped fill-head "
							style="width: 100%" id="table_grid">
							<thead style="background-color: #f3b5db;">
													
														<tr >
															<th width="148" style="width: 18px" align="left">Sr</th>
															<th width="148" style="width: 18px" align="left">Config </th>
															<th width="198" style="text-align: center;">Item Name</th>
															<th width="198" style="text-align: center;">Action</th>
															</tr>
													</thead>
										<tbody>
                                     <c:forEach items="${stationList}" var="stationList" varStatus="srno" >
											<tr>
												<td>${srno.index+1}</td>
												<td>${stationList.configName}</td>
												<td>${stationList.extVar1}</td>
                                                <td style="text-align: center;">
<!--                                                 <input type="button" id="expExcel" class="btn btn-primary" 	 -->
<!--                                                 value="Delete" onclick="exportToExcel();"  -->
<!--  									            disabled="disabled"> -->
												

													<a href="deleteConfigItem/${stationList.id}"
													onClick="return confirm('Are you sure want to delete this record');"><span
														class="glyphicon glyphicon-remove"></span></a>
														
														
<%-- 													<c:choose> --%>
<%-- 													<c:when test="${isEdit==1 and isDelete==1}"> --%>
<%-- 																		<a href="updateItem/${itemsList.id}"><span --%>
<!-- 														class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp; -->
                                             
<%-- 													<a href="deleteConfigItem/${itemsList.id}" class="disableClick" --%>
<!-- 													onClick="return confirm('Are you sure want to delete this record');"><span -->
<!-- 														class="glyphicon glyphicon-remove"></span></a>< -->
														
<%-- 																	</c:when> --%>

<%-- 																	<c:when test="${isEdit==1 and isDelete==0}"> --%>
<%-- 																		<a href="updateItem/${itemsList.id}"><span --%>
<!-- 														class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp; -->
                                             
<%-- 													<a href="deleteConfigItem/${itemsList.id}" class="disableClick" --%>
<!-- 													onClick="return confirm('Are you sure want to delete this record');"><span -->
<!-- 														class="glyphicon glyphicon-remove"></span></a>< -->
														
<%-- 																	</c:when> --%>
                                                                        
<%-- 																	<c:when test="${isEdit==0 and isDelete==1}"> --%>
<%-- 																		<a href="updateItem/${itemsList.id}"><span --%>
<!-- 														class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp; -->
                                             
<%-- 													<a href="deleteConfigItem/${itemsList.id}" class="disableClick" --%>
<!-- 													onClick="return confirm('Are you sure want to delete this record');"><span -->
<!-- 														class="glyphicon glyphicon-remove"></span></a>< -->
														
<%-- 																	</c:when> --%>
                                                                     
															
<%-- 																	<c:otherwise> --%>

<%-- 											<a href="updateItem/${itemsList.id}" class="disableClick"><span --%>
<!-- 														class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp; -->
                                            
<%-- 													<a href="${pageContext.request.contextPath}/deleteConfigItem/${itemsList.id}"  --%>
<!-- 													onClick="return confirm('Are you sure want to delete this record');"><span -->
<!-- 														class="glyphicon glyphicon-remove"></span></a> -->
<%-- 																	</c:otherwise> --%>
<%-- 																</c:choose> --%>
 									            
 									            
 									            
 									            
 									            
 									            
 									            
 									            
 									            </td>
 									            
 									       	
											</tr>
											</c:forEach>
											<%-- 		<td align="left"><c:out value="${orderList.id}" /></td> --%>

										</tbody>
									</table>
						<div class="form-group" id="range">



<!-- 							<div class="col-sm-3  controls"> -->
<!-- 								<input type="button" id="expExcel" class="btn btn-primary" -->
<!-- 									value="EXPORT TO Excel" onclick="exportToExcel();" -->
<!-- 									disabled="disabled"> -->
<!-- 							</div> -->
						</div>
						<div align="center" id="showchart" style="display: none"></div>
					</div>
                                          
							</form>
							
							
							
							
							
							
							
							</div>

						</div>

				</div>
			</div>
			<!-- END Main Content -->
			<footer>
				<p>2019 © MONGINIS.</p>
			</footer>


			<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
				class="fa fa-chevron-up"></i></a>
		</div>
		<!-- END Content -->
	</div>
	<!-- END Container -->

<script type="text/javascript">

</script>


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

	<script type="text/javascript">



</script>

</body>
</html>

