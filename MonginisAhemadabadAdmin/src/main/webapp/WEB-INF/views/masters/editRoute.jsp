<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	 

	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<body>
	<c:url value="/chkUnqRoutePrefix" var="chkUnqRoutePrefix"></c:url>
	<c:url value="/chkUnqRouteShortName" var="chkUnqRouteShortName"></c:url>
	<jsp:include page="/WEB-INF/views/include/logout.jsp"></jsp:include>
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
						<i class="fa fa-file-o"></i> Route
					</h1>

				</div>
			</div> -->
			<!-- END Page Title -->



			<!-- BEGIN Main Content -->
			<div class="row">
				<div class="col-md-12">
					<div class="box">
						<div class="box-title">
							<h3>
								<i class="fa fa-bars"></i> Edit Route
							</h3>
							<div class="box-tool">
								<a href="${pageContext.request.contextPath}/addroute">Back to List</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>
							<!-- <div class="box-tool">
								<a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a> <a data-action="close" href="#"><i
									class="fa fa-times"></i></a>
							</div> -->
						</div>


						<div class="box-content">
							<form action="editRouteProcess" method="post"
								class="form-horizontal" id="validation-form">
							<input type="hidden" name="route_id" id="route_id"
											value="${route.routeId}" />


								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Route</label>
									<div class="col-sm-9 col-lg-8 controls">
										<input type="text" name="route_name" id="route_name"
											placeholder="Route" class="form-control"
											data-rule-required="true" value="${route.routeName}"/>
									</div>
								</div>
								<div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">Prefix</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="prefix" id="prefix"
											placeholder="Route" class="form-control"
											data-rule-required="true" value="${route.routePrefix}"/>
											<span for="prefix" id="unq_prefix" style="display: none; color: #b94a48;">Prefix already exits</span>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Short Name</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="short_name" id="short_name"
											placeholder="Route Short Name" class="form-control"
											data-rule-required="true" value="${route.shortName}"/>
											<span for="short_name" id="unq_short_name" style="display: none; color: #b94a48;">Short Name already exits</span>
									</div>
								</div>
								
								<div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">Min Km</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="min_km" id="min_km"
											placeholder="Min Km" class="form-control"
											data-rule-required="true" value="${route.minKm}"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Max Km</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="max_km" id="max_km"
											placeholder="Max Km" class="form-control"
											data-rule-required="true" value="${route.maxKm}"/>
									</div>
								</div>
								
								
								<div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">ABC Type</label>
									<div class="col-sm-9 col-lg-3 controls">
										<select   class="form-control chosen" name="acbType" id="acbType"  >
											 <c:forEach items="${valList}" var="list">
											 	<c:choose>
											 		<c:when test="${list.abcId==route.abcType}">
											 			<option selected="selected"   value="${list.abcId}">${list.abcVal}</option>
											 		</c:when>
											 		<c:otherwise>											 		
											 			<option   value="${list.abcId}">${list.abcVal}</option>
											 		</c:otherwise>
											 	</c:choose>
											 	
											 </c:forEach>										 
										</select>
									</div>
								</div>
								
								<div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">Sequence No.</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="number" name="seqNo" id="seqNo"
											placeholder="Route" class="form-control"
											data-rule-required="true" value="${route.seqNo}"/>
									</div>
								</div>
								<div class="form-group">

									<div class="col-sm-9 col-lg-10 controls">
										
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2">
										<button type="submit" class="btn btn-primary" style="width: 70px">
											Save
										</button>
										<!--<button type="button" class="btn">Cancel</button>-->
									</div>
								</div>




								<div class="box">
									<div class="box-title">
										<h3>
											<i class="fa fa-table"></i> Route List
										</h3>
										<div class="box-tool">
											<a data-action="collapse" href="#"><i
												class="fa fa-chevron-up"></i></a>
											<!--<a data-action="close" href="#"><i class="fa fa-times"></i></a>-->
										</div>
									</div>

									<div class="box-content">
										<jsp:include page="/WEB-INF/views/include/tableSearch.jsp"></jsp:include>
										<div class="clearfix"></div>
										<div class="table-responsive" style="border: 0">
											<table width="100%" class="table table-advance" id="table1">
												<thead style="background-color: #f3b5db;">
													<tr>																						
														<th style="text-align: center;">#</th>
														<th style="text-align: center;">Name</th>
														<th style="text-align: center;">Sequence No</th>
														<th style="text-align: center;">ABC Type</th>
														<th style="text-align: center;">Action</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${routeList}" var="routeList" varStatus="count">
														<tr>
															<td>${count.index+1}</td>
															<td align="center"><c:out
																	value="${routeList.routeName}"></c:out></td>
															<td align="center"><c:out
																	value="${routeList.seqNo}"></c:out></td>
																	<c:set value="-" var="type"> </c:set>
															<c:choose>
																<c:when test="${routeList.abcType==1}">
																	<c:set value="A" var="type"> </c:set>
																</c:when>
																<c:when test="${routeList.abcType==2}">
																	<c:set value="B" var="type"> </c:set>
																</c:when>
																<c:when test="${routeList.abcType==3}">
																	<c:set value="C" var="type"> </c:set>
																</c:when>
															</c:choose>

															<c:set value="0" var="flag" />
															<c:forEach items="${routeIds}" var="routeIds">
																<c:choose>
																	<c:when test="${routeList.routeId==routeIds}">
																		<c:set value="1" var="flag" />
																	</c:when>

																</c:choose>

															</c:forEach>
															<td align="center"><c:out
																	value="${type}"></c:out></td>
															<td align="center"><a
																href="${pageContext.request.contextPath}/updateRoute/${routeList.routeId}"><span
																	class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp;&nbsp;&nbsp;
															<c:if test="${flag==0}">
																<a href="${pageContext.request.contextPath}/deleteRoute/${routeList.routeId}"
																onClick="return confirm('Are you sure want to delete this record');"><span
																	class="glyphicon glyphicon-remove"></span></a></c:if></td>
														</tr>

													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</div></form>
						</div>

					</div>
				</div>
			</div>
			<!-- END Main Content -->
			<footer>
			<p>2018 © MONGINIS.</p>
			</footer>

			<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
				class="fa fa-chevron-up"></i></a>
		</div>
		<!-- END Content -->
	</div>
	<!-- END Container -->

	<!--basic scripts-->
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>


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
	<!-- <script type="text/javascript"
		src="resources/assets/jquery-validation/dist/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="resources/assets/jquery-validation/dist/additional-methods.min.js"></script>

 -->
	<!--flaty scripts-->
	<script src="${pageContext.request.contextPath}/resources/js/flaty.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/flaty-demo-codes.js"></script>
	<!--page specific plugin scripts-->


	<script
		src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/additional-methods.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/jquery.validate.min.js"></script>


	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/chosen-bootstrap/chosen.jquery.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/clockface/js/clockface.js"></script>

	<%-- <spring:url value="/resources/assets/jquery-validation/dist/jquery.validate.min.js" var="jqueryJs" />
		    <script src="${jqueryJs}"></script>
		
			<spring:url value="/resources/assets/jquery-validation/dist/additional-methods.min.js" var="jqueryAdd" />
		    <script src="${jqueryAdd}"></script> --%>
<script>
$( "#prefix" ).change(function() {
	var routePrefix = $("#prefix").val();
	var routeId = $("#route_id").val();	
	$
			.getJSON(
					'${chkUnqRoutePrefix}',
					{
						routePrefix : routePrefix,								
						routeId : routeId,
						ajax : 'true'
					},
					function(data) {						
						if(data.error){
							$( "#unq_prefix" ).show();
							$( "#prefix" ).val('');
						}else{
							$( "#unq_prefix" ).hide();
						}
					});
	});
	
$( "#short_name" ).change(function() {
	var shortName = $("#short_name").val();
	var routeId = $("#route_id").val();	
	$
			.getJSON(
					'${chkUnqRouteShortName}',
					{
						shortName : shortName,								
						routeId : routeId,
						ajax : 'true'
					},
					function(data) {						
						if(data.error){
							$( "#unq_short_name" ).show();
							$( "#short_name" ).val('');
						}else{
							$( "#unq_short_name" ).hide();
						}
					});
	});
</script>
</body>
</html>