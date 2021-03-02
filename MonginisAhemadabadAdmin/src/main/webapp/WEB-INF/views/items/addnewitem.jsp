<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<style>
.alert {
    padding: 20px;
    background-color: red;
    color: white;
    
}

.closebtn {
    margin-left: 15px;
    color: white;
    font-weight: bold;
    float: right;
    font-size: 22px;
    line-height: 20px;
    cursor: pointer;
    transition: 0.3s;
}

.closebtn:hover {
    color: black;
}
</style>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body>
	<jsp:include page="/WEB-INF/views/include/logout.jsp"></jsp:include>

	<c:url var="getGroup2ByCatId" value="/getGroup2ByCatId" />

<c:url var="getItemCode" value="/getItemCode" /> 
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
					<!-- <h1>
						<i class="fa fa-file-o"></i> Items
					</h1> -->
	<c:choose>
				<c:when test="${isError==true}">
				<div class="alert">
					<span class="closebtn"
						onclick="this.parentElement.style.display='none';">&times;</span>
					<strong>Failed !</strong>     Failed to Add New Item !!
				</div>
				</c:when>
				</c:choose>
					<c:set var="isEdit" value="0">
					</c:set>
					<c:set var="isView" value="0">
					</c:set>
					<c:set var="isAdd" value="0">
					</c:set>

					<c:forEach items="${sessionScope.newModuleList}" var="modules">
						<c:forEach items="${modules.subModuleJsonList}" var="subModule">

							<c:choose>
								<c:when test="${subModule.subModuleMapping eq 'addItem'}">

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
										<c:when test="${subModule.addApproveConfig=='visible'}">
											<c:set var="isAdd" value="1">
											</c:set>
										</c:when>
										<c:otherwise>
											<c:set var="isAdd" value="0">
											</c:set>
										</c:otherwise>
									</c:choose>
								</c:when>
							</c:choose>
						</c:forEach>
					</c:forEach>

				</div>
			</div>
			<!-- END Page Title -->

			<!-- BEGIN Main Content -->
			<div class="row">
				<div class="col-md-12">
					<div class="box">
						<div class="box-title">
							<h3>
								<i class="fa fa-bars"></i> Add Products
							</h3>
							<div class="box-tool">

								<a href="${pageContext.request.contextPath}/itemList">Back
									to List</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>


							</div>
							<!-- <div class="box-tool">
								<a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a> <a data-action="close" href="#"><i
									class="fa fa-times"></i></a>
							</div> -->
						</div>


						<div class="box-content">
							<form action="${pageContext.request.contextPath}/addItemProcess" class="form-horizontal"
								method="post" id="validation-form" enctype="multipart/form-data">


								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Product Type</label>
									<div class="col-sm-9 col-lg-10 controls">
										<label class="radio-inline"> <input type="radio"
											name="product_type" id="prdRadios1" value="0" checked>
											Franchise
										</label> <label class="radio-inline"> <input type="radio"
											name="product_type" id="prdRadios2" value="1"
											data-rule-required="false" />3<sup>rd</sup> Party</label>
										
									</div>
								</div>		

								 <div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">Category</label>
									<div class="col-sm-9 col-lg-3 controls">
										<select data-placeholder="Select Category"
											class="form-control chosen" name="item_grp1" tabindex="-1"
											id="item_grp1" data-rule-required="true">
											<option selected>Select Category</option>

											<c:forEach items="${mCategoryList}" var="mCategoryList">


												<option value="${mCategoryList.catId}"><c:out value="${mCategoryList.catName}"></c:out></option>
											</c:forEach>


										</select>
									</div>
								</div>


								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Sub Category</label>
									<div class="col-sm-9 col-lg-3 controls">
										<select data-placeholder="Select Sub Category"
											class="form-control chosen-select" name="item_grp2"
											tabindex="-1" id="item_grp2" onchange="onSubCatChange(this.value)" data-rule-required="true">

										</select>
									</div>
								</div>
								
								 <div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">Item Code</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="item_id" id="item_id"
											placeholder="Item Code" class="form-control"
											data-rule-required="true" value="${itemId}" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label" for="item_name">Item
										Name</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="item_name" id="item_name"
											placeholder="Item Name" class="form-control"
											data-rule-required="true" />
									</div>
								</div><input type="hidden" name="item_grp3" id="item_grp3" value="1"/>
							<!-- 	<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Sub-Sub Category</label>
									<div class="col-sm-9 col-lg-10 controls">
										<select data-placeholder="Select Sub-Sub Category" name="item_grp3"
											class="form-control chosen" tabindex="-1" id="selS0V"
											data-rule-required="true">
											<option value=""> </option>

											<option value="1">Small</option>
											<option value="2">Medium</option>
											<option value="3">Large</option>


										</select>
									</div>
								</div> -->
								 <div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">MIN
										Qty.</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="min_qty" id="min_qty"
											placeholder="Min Quantity" class="form-control"
											data-rule-required="true" data-rule-number="true" />
									</div>
								</div>
								
								  <div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">MAX Qty.</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="item_rate1" id="item_rate1"
											placeholder="Item Rate1" class="form-control"
											data-rule-required="true" data-rule-number="true" value="0" />
									</div>
								</div>	
							
								 <div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">MRP1</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="item_mrp1" id="item_mrp1"
											placeholder="Item Mrp1" class="form-control"
											data-rule-required="true" data-rule-number="true" value="0"/>
									</div>
								</div>
	
								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">MRP2
										</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="item_mrp2" id="item_mrp2"
											placeholder="Item Mrp2" class="form-control"
											data-rule-required="true" data-rule-number="true" value="0" />
									</div>
								</div>
								 <div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">MRP3
										</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="item_mrp3" id="item_mrp3"
											placeholder="Item Mrp3" class="form-control"
											data-rule-required="true" data-rule-number="true" value="0" />
									</div>
								</div>
								
                               <div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">Margin %</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="margin" id="margin"
											placeholder="Enter Margin %" class="form-control"
											data-rule-required="true" data-rule-number="true" value="0" onchange="calMrp()"/>
									</div>
								</div>                              						
                         
                         		
								<div class="col2" style="display: none;">
									<label class="col-sm-3 col-lg-2 control-label">Special
										Rate</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="item_rate3" id="item_rate3"
											placeholder="Item Rate3" class="form-control"
											data-rule-required="true" data-rule-number="true" value="0" />
									</div>
								</div> 
								<div class="form-group"></div>
								
								 <div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">Tax %</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="item_tax3" id="item_tax3"
											placeholder="IGST" class="form-control"
											data-rule-required="true" data-rule-number="true" value="0.0"
											onchange="calTotalGst()" />
									</div>
								</div>
								
								<div style="display: none;">
									<div class="form-group">
										<label class="col-sm-3 col-lg-2 control-label">CGST %</label>
										<div class="col-sm-9 col-lg-3 controls">
											<input type="text" name="item_tax2" id="item_tax2"
												placeholder="CGST" class="form-control"
												data-rule-required="true" data-rule-number="true" value="0.0"/>
										</div>
									</div>
									
								 <div class="col2">
										<label class="col-sm-3 col-lg-2 control-label">SGST %</label>
										<div class="col-sm-9 col-lg-3 controls">
											<input type="text" name="item_tax1" id="item_tax1"
												placeholder="SGST" class="form-control"
												data-rule-required="true" data-rule-number="true" value="0.0" />
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Total
										GST Applicable %</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="total_gst_appli" id="total_gst_appli"
											placeholder="Total GST Applicable" class="form-control"
											data-rule-required="true" data-rule-number="true" disabled />
									</div>
								</div>

								<div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">Is Used?</label>
									<div class="col-sm-9 col-lg-3 controls">
										<select class="form-control chosen" tabindex="1"
											name="is_used">
											<option value="1">Active</option>
										<!--<option value="2">Special Days</option>
											<option value="3">Sp Day Cake</option> -->
											<option value="4">Inactive</option>
											<option value="11">Sunday Active</option>
											<option value="12">Monday Active</option>
											<option value="13">Tuesday Active</option>
											<option value="14">Wednesday Active</option>
											<option value="15">Thursday Active</option>
											<option value="16">Friday Active</option>
											<option value="17">Saturday Active</option>
											
										</select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Item
										SortId</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="item_sort_id" id="item_sort_id"
											placeholder="Item Sort Id" class="form-control"
											data-rule-number="true" value="0" />
									</div>
								</div>
								
								<input type="hidden" name="grn_two" id="grn_two" value="-1"/>
								<!-- <div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">GRN Type</label>
									<div class="col-sm-9 col-lg-10 controls">
										<label class="radio-inline"> <input type="radio"
											name="grn_two" id="optionsRadios1" value="0" checked>
											GRN1 (85% Refund)
										</label> <label class="radio-inline"> <input type="radio"
											name="grn_two" id="optionsRadios1" value="1"
											data-rule-required="false" />GRN2 (75% Refund)
										</label> <label class="radio-inline"> <input type="radio"
											name="grn_two" id="optionsRadios1" value="2"
											data-rule-required="false" />GRN3 (100% Refund)
										</label>
									</div>
								</div> -->
																
								<div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">Item
										Shelf Life</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="item_shelf_life" id="item_shelf_life"
											placeholder="Item Shelf Life" class="form-control"
											data-rule-required="true" data-rule-number="true" />
									</div>
								</div>

                                <div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Station No</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="item_rate2" id="item_rate2"
											placeholder="Station No." class="form-control"
											data-rule-required="true" data-rule-number="true" value="0" />
									</div>
								</div> 
								
								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Product
										Image</label>
									<div class="col-sm-9 col-lg-10 controls">
										<div class="fileupload fileupload-new"
											data-provides="fileupload">
											<div class="fileupload-new img-thumbnail"
												style="width: 200px; height: 150px;">
												<img
													src="http://www.placehold.it/200x150/EFEFEF/AAAAAA&amp;text=no+image"
													alt="" />
											</div>
											<div
												class="fileupload-preview fileupload-exists img-thumbnail"
												style="max-width: 200px; max-height: 150px; line-height: 20px;"></div>
											<div>
												<span class="btn btn-default btn-file"><span
													class="fileupload-new">Select image</span> <span
													class="fileupload-exists">Change</span> <input type="file"
													class="file-input" name="item_image" id="item_image" /></span>
												<a href="#" class="btn btn-default fileupload-exists"
													data-dismiss="fileupload">Remove</a>
											</div>
										</div>

									</div>
								</div>
								
								
								<!-- Start Item Supplement -->
								<div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">HSN Code</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="item_hsncd" id="item_hsncd"
											placeholder="HSN Code" class="form-control"
											data-rule-required="true" value="${itemSupp.itemHsncd}"/>
									</div>
							  </div>
							  <div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">UOM</label>
									<div class="col-sm-9 col-lg-3 controls">
												<select name="item_uom" id="item_uom" class="form-control chosen" placeholder="Item UOM"
												 data-rule-required="true" onchange="uomChanged()">
											<option value="">Select Item UOM</option>
											<c:forEach items="${rmUomList}" var="rmUomList"
													varStatus="count">
													<c:choose>
													<c:when test="${rmUomList.uomId==itemSupp.uomId}">
														<option value="${rmUomList.uomId}" selected><c:out value="${rmUomList.uom}"/></option>
													</c:when>
													<c:otherwise>
														<option value="${rmUomList.uomId}"><c:out value="${rmUomList.uom}"/></option>
													</c:otherwise>
													</c:choose>
												</c:forEach>
										</select>
									</div>
							  </div>
							  <input type="hidden" name="uom" id="uom" value="${itemSupp.itemUom}"/> 
							  <!-- <div>
							  <div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">Actual Weight</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="actual_weight" id="actual_weight"
											placeholder="Actual Weight" class="form-control"
											data-rule-required="true" value="0"/>
									</div>
							  </div>
							   <div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Base Weight</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="base_weight" id="base_weight"
											placeholder="Base Weight" class="form-control"
											data-rule-required="true" value="0"/>
									</div>
							  </div>
							  </div> -->
							   <div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">Short Name</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="short_name" id="short_name"
											placeholder="Short Name" class="form-control"
											data-rule-required="true"  value="${itemSupp.shortName}"/>
									</div>
							  </div> 
							 <!--  <div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">Input Per Unit</label>
									<div class="col-sm-9 col-lg-3 controls"> -->
										<input type="hidden" name="input_per_qty" id="input_per_qty"
											placeholder="Input Per Unit" class="form-control"
											data-rule-required="true" value="1"/>
									<!-- </div>
							  </div> -->
							  
							  <input type="hidden" value="0" name="cut_section">
							   <input type="hidden" value="0" name="tray_type">
							   <input type="hidden" value="0" name="no_of_item">
							   <input type="hidden" value="0" name="actual_weight">
							   <input type="hidden" value="0" name="base_weight">
							  <%-- <div>
							    <div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Cut Section</label>
									<div class="col-sm-9 col-lg-3 controls">
										<select name="cut_section" id="cut_section" class="form-control chosen">
											<option value="">Select Cut Section</option>
											
										<c:choose>
										<c:when test="${itemSupp.cutSection==0}">
										<option value="0" selected>Not Applicable</option>
											<option value="1">Single Cut</option>
											<option value="2">Double Cut</option>
										</c:when>
											<c:when test="${itemSupp.cutSection==1}">
											<option value="0" >Not Applicable</option>
											<option value="1"selected>Single Cut</option>
											<option value="2">Double Cut</option>
											</c:when>
											<c:when test="${itemSupp.cutSection==2}">
											<option value="0" >Not Applicable</option>
											<option value="1">Single Cut</option>
											<option value="2"selected>Double Cut</option>
											</c:when>
											<c:otherwise>
										    <option value="0" selected>Not Applicable</option>
											<option value="1">Single Cut</option>
											<option value="2">Double Cut</option>
											</c:otherwise>
										</c:choose>
										</select>
									</div>
							  </div>
						    <div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">Type Of Tray</label>
									<div class="col-sm-9 col-lg-3 controls">
												<select name="tray_type" id="tray_type" class="form-control chosen" placeholder="Type Of Tray">
											<option value="">Select Type Of Tray</option>
											<c:forEach items="${trayTypes}" var="trayTypes"
													varStatus="count">
													<c:choose>
													<c:when test="${trayTypes.typeId==itemSupp.trayType}">
														<option value="${trayTypes.typeId}" selected><c:out value="${trayTypes.typeName}"/></option>
													</c:when>
													<c:otherwise>
														<option value="${trayTypes.typeId}"><c:out value="${trayTypes.typeName}"/></option>
													</c:otherwise>
													</c:choose>
												</c:forEach>
										</select>
									</div>
							  </div>
							    <div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">No. Of Item Per Tray</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="no_of_item" id="no_of_item"
											placeholder="No. Of Item Per Tray" class="form-control"
											data-rule-required="true" value="0"/>
									</div>
							  </div> 
							  </div> --%>
							   <div class="col2">
									<label class="col-sm-3 col-lg-2 control-label">Cess %</label>
									<div class="col-sm-9 col-lg-3 controls">
										<input type="text" name="cessPer" id="cessPer"
										   class="form-control"
											 value="${itemSupp.itemCess}"/>
									</div>
							  </div>
								
								<!-- End Item Supplement  -->


								<div class="form-group">
									<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2">
										<c:choose>

											<c:when test="${isAdd==1}">

												<input type="submit" class="btn btn-primary" value="Submit">

											</c:when>

											<c:otherwise>
												<input type="submit" disabled="disabled"
													class="btn btn-primary" value="Submit">

											</c:otherwise>
										</c:choose>

										<button type="button" class="btn btn-primary">Cancel</button>
									</div>
								</div>
							</form>
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
	
	$( document ).ready(function() {
	    var prdType = $("#prdRadios1").val();
	    if(prdType == 0){
	    	$('#item_id').prop('readonly', true);
	    }else{
	    	$('#item_id').prop('readonly', false);
	    }
	  
	});

			function onSubCatChange(item_grp2) {
				var item_grp1 = parseFloat($("#item_grp1").val());
				$.getJSON('${getItemCode}', {
					item_grp2 : item_grp2,
					item_grp1:item_grp1,
					ajax : 'true'
				}, function(data) {
			        $("#item_id").val(data.message+"");


				});
			}
</script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('#item_grp1')
									.change(
											function() {
												$
														.getJSON(
																'${getGroup2ByCatId}',
																{
																	catId : $(
																			this)
																			.val(),
																	ajax : 'true'
																},
																function(data) {
																	var html = '<option value="" selected >Select Sub-Category</option>';

																	var len = data.length;
																	for (var i = 0; i < len; i++) {
																		html += '<option value="' + data[i].subCatId + '">'
																				+ data[i].subCatName
																				+ '</option>';
																	}
																	html += '</option>';
																	$(
																			'#item_grp2')
																			.html(
																					html);
																	$(
																			'#item_grp2')
																			.formcontrol(
																					'refresh');

																});
											});
						});
	</script>

	<script>
		function calTotalGst() {
			var igst = parseFloat($("#item_tax3").val());
			
			var cgst = igst/2;
			var sgst = igst/2;
			var totGst = parseFloat(cgst + sgst);
			
			document.getElementById("item_tax1").setAttribute('value', sgst);
			document.getElementById("item_tax2").setAttribute('value', cgst);

			document.getElementById("total_gst_appli").setAttribute('value',
					totGst);
		}
	</script>
<script type="text/javascript">
function calMrp()
{
	var mrp1 = parseFloat($("#item_mrp1").val());
	var mrp2 = parseFloat($("#item_mrp2").val());
	var mrp3 = parseFloat($("#item_mrp3").val());
	var margin= parseFloat($("#margin").val());
	
	var calRate1=mrp1-((mrp1*margin)/100);      
	var calRate2=mrp2-((mrp2*margin)/100);  
	var calRate3=mrp3-((mrp3*margin)/100);  
	//document.getElementById("item_rate1").setAttribute('value', (calRate1).toFixed(2));
	//document.getElementById("item_rate2").setAttribute('value', (calRate2).toFixed(2));
	//document.getElementById("item_rate3").setAttribute('value', (calRate3).toFixed(2));
}

$( "#prdRadios2" ).on( "click", function() {
	  var thirdPatyRadio = $('input[name="product_type"]:checked').val();	
	  
	  if(thirdPatyRadio == 1){
	    	$('#item_id').prop('readonly', false);
	    }else{
	    	$('#item_id').prop('readonly', true);
	    }
	});
	
$( "#prdRadios1" ).on( "click", function() {
	  var thirdPatyRadio = $('input[name="product_type"]:checked').val();	  
	  if(thirdPatyRadio != 1){
	    	$('#item_id').prop('readonly', true);
	    }else{
	    	$('#item_id').prop('readonly', false);
	    }
	});


</script>

</body>
</html>