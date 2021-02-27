<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	 

	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<body>
	
	<c:url value="/getSubCategoryByPrefix" var="getSubCategoryByPrefix"/>
	<c:url value="/getProductsBySubCatId" var="getProductsBySubCatId"/>
	
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
						<i class="fa fa-file-o"></i>Sub Category
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
								<i class="fa fa-bars"></i>Add Sub Category 
							</h3>
							<div class="box-tool">
								<a href="${pageContext.request.contextPath}/showSubCatList"></a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>
							
						</div>
						<div class="box-content">
							<form action="${pageContext.request.contextPath}/addSubCategoryProcess" class="form-horizontal"
								method="post" id="validation-form">

                        <input type="hidden" name="subCatId" id="subCatId" value="${subCategory.subCatId}"/>
                              <div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">Category</label>
									<div class="col-sm-9 col-lg-3 controls">
										<select data-placeholder="Select Category"
											class="form-control chosen" name="cat_id" tabindex="-1"
											id="cat_id" data-rule-required="true">
											<option value="" selected>Select Category</option>

											<c:forEach items="${catList}" var="catList">
											<c:choose>
											<c:when test="${catList.catId==subCategory.catId}">
											<option value="${catList.catId}" selected><c:out value="${catList.catName}"></c:out></option>
											</c:when>
											<c:otherwise>
											<option value="${catList.catId}"><c:out value="${catList.catName}"></c:out></option>
											</c:otherwise>
											</c:choose>
											</c:forEach>

										</select>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Prefix</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="prefix" id="prefix"
											placeholder="Prefix" class="form-control"
											data-rule-required="true" value="${subCategory.prefix}"/>
											<span for="prefix" id="unq_prefix" style="display: none; color: #b94a48;">Prefix already exits</span>
									</div>
								</div>
						
								<div class="col2">
									<label class="col-sm-3 col-lg-2 control-label" for="item_name">Sub Category
										Name</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="sub_cat_name" id="sub_cat_name"
											placeholder="Sub Category Name" class="form-control"
											data-rule-required="true" value="${subCategory.subCatName}"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Sequence No.</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="seqNo" id="seqNo"
											placeholder="Sequence No." class="form-control"
											data-rule-required="true" value="${subCategory.seqNo}" />
									</div>
								</div>
                              
								<div class="form-group">
									<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2">
										<input type="submit" class="btn btn-primary" value="Submit" onclick="return validate()">
									</div>
								</div>
							</form>
						</div>
						
						
					</div>
				</div>
			</div>
				<div class="box">
			 	<div class="box-title">
					<h3>
						<i class="fa fa-table"></i> Sub Category List
					</h3>
					<div class="box-tool">
						<a data-action="collapse" href="#"><i class="fa fa-chevron-up"></i></a>
						<a data-action="close" href="#"><i class="fa fa-times"></i></a>
					</div>
				</div>
               	<div class="box-content">
 
                 <jsp:include page="/WEB-INF/views/include/tableSearch.jsp"></jsp:include>

							<div class="clearfix"></div>
							
								<div id="table-scroll" class="table-scroll">
							
									<div id="faux-table" class="faux-table" aria="hidden">
									<table id="table2" class="table table-advance" >
											<thead>
												<tr class="bgpink">										
													<th class="col-md-1">#</th>
										            <th class="col-md-4" style="text-align: center;">Name</th>
											        <th class="col-md-4" style="text-align: center;">Category Name</th>
											        <th class="col-md-1" style="text-align: center;">Action</th>
												</tr>
												</thead>
												</table>
									
									</div>
									<div class="table-wrap">

							<table id="table1" class="table table-advance">
								<thead>
									<tr class="bgpink">
										<th class="col-md-1">#</th>
										<th class="col-md-4" style="text-align: center;">Name</th>
										<th class="col-md-4" style="text-align: center;">Category
											Name</th>
										<th class="col-md-1" style="text-align: center;">Action</th>
									</tr>
								</thead>
								<tbody>
									<c:set var="cnt" value="0"></c:set>
									<c:forEach items="${catList}" var="catList" varStatus="count">

										<c:forEach items="${catList.subCategoryList}" var="subCatList"
											varStatus="count">

											<c:set value="0" var="flag" />
											<c:forEach items="${subCatIds}" var="subCatIds">
												<c:choose>
													<c:when test="${subCatList.subCatId==subCatIds}">
														<c:set value="1" var="flag" />
													</c:when>

												</c:choose>

											</c:forEach>

											<c:set var="cnt" value="${cnt+1}"></c:set>
											<c:choose>
												<c:when test="${subCatList.catId==catList.catId}">
													<tr>
														<td><c:out value="${cnt}" /></td>
														<td style="text-align: left; padding-left: 15%;"><a
															href="javascript:void(0);"
															onclick="showProducts(${subCatList.subCatId})"> <c:out
																	value="${subCatList.subCatName}" /></a></td>
														<td style="text-align: left; padding-left: 13%;"><c:out
																value="${catList.catName}" /></td>


														<td style="text-align: center;"><a
															href="updateSubCategory/${subCatList.subCatId}"><span
																class="glyphicon glyphicon-edit"></span></a>&nbsp;&nbsp;&nbsp;&nbsp;

															<c:if test="${flag==0}">
																<a href="deleteSubCategory/${subCatList.subCatId}"
																	onClick="return confirm('Are you sure want to delete this record');"><span
																	class="glyphicon glyphicon-remove"></span></a>
															</c:if></td>
													</tr>
												</c:when>

											</c:choose>
										</c:forEach>
									</c:forEach>
								</tbody>
							</table>
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

	<!--basic scripts-->
	<!-- Product Model -->
	<div id="itemModal" class="modal">
		<!-- Modal content -->
		<div class="modal-content" id="modal_fr">

			<div class="box">
				<div class="box-title">
					<h3>
						<i class="fa fa-table"></i> Sub Category Alloted Products
					</h3>
				</div>

				<div class="box-content">
					<div class="clearfix"></div>
					<div class="table-responsive" style="border: 0">
						<table width="100%" class="table table-advance" id="frtable_grid">
							<thead style="background-color: #f3b5db;">
								<tr>
									<th width="15">Sr No.</th>
									<th style="text-align: center;">Item</th>
									<th style="text-align: center;">UOM</th>
									<th style="text-align: center;">MRP1</th>
									<th style="text-align: center;">MRP2</th>
									<th style="text-align: center;">MRP3</th>
									<th style="text-align: center;">Tax Applicable</th>
									<th style="text-align: center;">HSN Code</th>
									<th style="text-align: center;">Shelf Life</th>
									<th style="text-align: center;">Sequence No.</th>
									<th style="text-align: center;">Is Active</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>

			</div>
			<div class="form-group" style="background-color: white;">
				&nbsp; &nbsp; &nbsp; &nbsp; <input type="button"
					margin-right: 5px;"
											class="btn btn-primary"
					id="btn_close" onclick="clsitemModal()" value="Close" />

			</div>
		</div>
	</div>
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
$( "#prefix" ).change(function() {
	var prefix = $("#prefix").val();
	var subCatId = $("#subCatId").val();	
	if(subCatId==""){
		subCatId=0;
	}
	$
			.getJSON(
					'${getSubCategoryByPrefix}',
					{
						prefix : prefix,								
						subCatId : subCatId,
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
	
var modalFr = document.getElementById("itemModal");
function showProducts(subCatId){	
	$('#frtable_grid td').remove();
	$
	.getJSON(
			'${getProductsBySubCatId}',
			{							
				subCatId : subCatId,
				ajax : 'true'
			},
			function(data) {	
				if(data==""){
					alert("No Item Found");
				}else{
					modalFr.style.display = "block";						
				}
				$.each(data, function(key, item) {
					
					var index = key + 1;	
					var tr = $('<tr></tr>');
					tr.append($('<td></td>').html(index));
					
					tr.append($('<td style="text-align:left;"></td>').html(
							item.itemName+'-'+item.itemId));
					
					tr.append($('<td style="text-align:left;"></td>').html(
							item.uom));
					
					tr.append($('<td style="text-align:right;"></td>').html(
							item.itemMrp1));
					
					tr.append($('<td style="text-align:right;"></td>').html(
							item.itemMrp2));
					
					tr.append($('<td style="text-align:right;"></td>').html(
							item.itemMrp3));
					
					tr.append($('<td style="text-align:right;"></td>').html(
							item.itemTax1+item.itemTax2));
					
					tr.append($('<td style="text-align:center;"></td>').html(
							item.itemHsncd));
					
					tr.append($('<td style="text-align:right;"></td>').html(
							item.shelfLife));
					
					tr.append($('<td style="text-align:right;"></td>').html(
							item.itemSortId));					
					
					
					tr.append($('<td style="text-align:left;"></td>').html(
							item.itemIsUsed == 1 ? 'Active' :
								item.itemIsUsed == 4 ? 'Inactive' :
									item.itemIsUsed == 11 ? 'Monday Active' :
										item.itemIsUsed == 12 ? 'Tuesday Active' :
											item.itemIsUsed == 13 ? 'Wednesday Active' :
												item.itemIsUsed == 14 ? 'Thursday Active' :
													item.itemIsUsed == 15 ? 'Friday Activee' :
														item.itemIsUsed == 16 ? 'Saturday Active' : 'Sunday Active'));	
					
					$('#frtable_grid tbody').append(tr);

				})
			});
}	
function clsitemModal() {
	modalFr.style.display = "none";
}
</script>
</body>

</html>