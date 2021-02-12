<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body>

	<jsp:include page="/WEB-INF/views/include/logout.jsp"></jsp:include>

	<c:url var="currentStockDetailList" value="/getStockDetails"></c:url>
	<c:url var="getSubCatByCatId" value="/getSubCatByCatIdAjax" />
	<c:url var="getItemsBySubCatId" value="/getItemsBySubCatIdAjax" />
	<c:url var="getAllCurrentStockFr" value="/getAllCurrentStockFr"></c:url>
	<c:url var="getAllItemsFrCutStock" value="/getAllItemsFrCutStock"></c:url>

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
					<i class="fa fa-file-o"></i>Show Current Stock
				</h1>
				<h4></h4>
			</div>
		</div>
		<!-- END Page Title -->

		<!-- BEGIN Breadcrumb -->
		<%-- <div id="breadcrumbs">
			<ul class="breadcrumb">
				<li><i class="fa fa-home"></i> <a
					href="${pageContext.request.contextPath}/home">Home</a> <span
					class="divider"><i class="fa fa-angle-right"></i></span></li>
				<li class="active">Bill Report</li>
			</ul>
		</div> --%>
		<!-- END Breadcrumb -->

		<!-- BEGIN Main Content -->
		<div class="box">
			<div class="box-title">
				<h3>
					<i class="fa fa-bars"></i>Show Current Stock

				</h3>

			</div>

			<div class="box-content">

				<div class="row">
					<div class="form-group">
						<label class="col-sm-3 col-lg-2 control-label">Select
							Route</label>
						<div class="col-sm-6 col-lg-4 controls">
							<select data-placeholder="Select Route"
								class="form-control chosen" name="selectRoute" id="selectRoute"
								onchange="disableFr()">
								<option value="0">Select Route</option>
								<c:forEach items="${routeList}" var="route" varStatus="count">
									<option value="${route.routeId}"><c:out
											value="${route.routeName}" />
									</option>

								</c:forEach>
							</select>

						</div>

						<label class="col-sm-3 col-lg-2 control-label"><b>OR </b>
						</label>

					</div>
				</div>

				<br>
				<div class="row">
					<div class="form-group">
						<label class="col-sm-3 col-lg-2 control-label"> Select
							Franchisee</label>
						<div class="col-lg-10">

							<select data-placeholder="Choose Franchisee"
								class="form-control chosen" multiple="multiple" tabindex="6"
								id="selectFr" name="selectFr"
								onchange="setAllFranchisee(this.value);">

								<option value="-1"><c:out value="All" /></option>

								<c:forEach items="${unSelectedFrList}" var="fr"
									varStatus="count">
									<option value="${fr.frId}"><c:out value="${fr.frName}" /></option>
								</c:forEach>
							</select>

						</div>
					</div>
				</div>
				<br>

				<div class="row">
					<div class="form-group">
						<label class="col-sm-3 col-lg-2 control-label">Category</label>
						<div class="col-sm-6 col-lg-4 controls">
							<select data-placeholder="Select Route"
								class="form-control chosen" name="selectCat" id="selectCat"
								tabindex="6">
								<option value="0">Select Category</option>
								<c:forEach items="${catList}" var="cat" varStatus="count">
									<option value="${cat.catId}"><c:out
											value="${cat.catName}" />
									</option>
								</c:forEach>
							</select>
						</div>

						<label class="col-sm-3 col-lg-1 control-label"> Sub
							Category</label>
						<div class="col-sm-6 col-lg-4 controls">

							<select data-placeholder="Select Sub Category"
								class="form-control chosen" name="item_grp2" multiple="multiple"
								tabindex="6" id="item_grp2" data-rule-required="true">

							</select>
						</div>

					</div>

				</div>
				<br>

				<div class="row">
					<div class="form-group">
						<label class="col-sm-3 col-lg-2 control-label">Items</label>
						<div class="col-lg-9 controls">
							<select data-placeholder="Select Route"
								class="form-control chosen" name="itemsIds" id="itemsIds"
								multiple="multiple" tabindex="6">

							</select>
						</div>

					</div>

				</div>

				<!-- <div class="col-sm-9 col-lg-5 controls">
 -->


				<br>
				<div class="row">
					<div class="col-md-12" style="text-align: center;">
						<button class="btn btn-primary" onclick="searchReport()">Search</button>


						<button class="btn btn-primary" value="PDF" id="PDFButton"
							onclick="genPdf()">PDF</button>



						<%-- 
						<a
							href='${pageContext.request.contextPath}/pdfForReport?url=showSaleReportItemwisePdf'
							target="_blank">PDF</a> --%>

					</div>
				</div>


				<div align="center" id="loader" style="display: none">

					<span>
						<h4>
							<font color="#343690">Loading</font>
						</h4>
					</span> <span class="l-1"></span> <span class="l-2"></span> <span
						class="l-3"></span> <span class="l-4"></span> <span class="l-5"></span>
					<span class="l-6"></span>
				</div>

			</div>
		</div>


		<div class="box">
			<!-- <div class="box-title">
				<h3>
					<i class="fa fa-list-alt"></i>Bill Report
				</h3>

			</div> -->

			<form id="submitBillForm" method="post">
				<div class=" box-content">
					<div class="row">
						<div class="col-md-12 table-responsive">
							<table id="table_grid"
								class="table table-bordered table-striped fill-head">
								<thead style="background-color: #f3b5db;">
									<tr class="bgpink">
										<th class="col-md-1">Item Id</th>
										<th class="col-md-1">Item_Name</th>
										<!-- <th class="col-md-1">Rate/MRP</th>
												<th class="col-md-1">Op Stock</th>
												<th class="col-md-1">Op Stock Value</th>
												<th class="col-md-1">Pur Qty</th>
												<th class="col-md-1">Pur Value</th>
												<th class="col-md-1">Grn-Gvn Qty</th>
												<th class="col-md-1">Grn-Gvn VAlue</th>
												<th class="col-md-1">Reg Sale</th>
												<th class="col-md-1">Reg Sale Value</th> -->
										<th class="col-md-1">Curr Stock</th>
										<th class="col-md-1">Curr Stock Value</th>
										<c:if test="${isMonthCloseApplicable eq true}">
											<th>Physical Stock</th>
											<th>Stock Difference</th>
										</c:if>

									</tr>
								</thead>
								<tbody>

								</tbody>

								<!-- <tr>
											<td></td>
											<td></td>
											<td class="col-md-1">Total</td>
											<td><input type="text" id="opTotal" style="width: 80px;"
												name="opTotal" value="0" readonly></td>
											<td><input type="text" id="opTotalVal"
												style="width: 80px;" name="opTotalVal" value="0" readonly></td>


											<td><input type="text" id="purQty" style="width: 80px;"
												name="purQty" value="0" readonly></td>
											<td><input type="text" id="purQtyVal"
												style="width: 80px;" name="purQtyVal" value="0" readonly></td>



											<td><input type="text" id="grnGvnQty"
												style="width: 80px;" name="grnGvnQty" value="0" readonly></td>
											<td><input type="text" id="grnGvnQtyVal"
												style="width: 80px;" name="grnGvnQtyVal" value="0" readonly></td>



											<td><input type="text" id="regSale" style="width: 80px;"
												name="regSale" value="0" readonly></td>
											<td><input type="text" id="regSaleVal"
												style="width: 80px;" name="regSaleVal" value="0" readonly></td>


											<td><input type="text" id="reOrderQty"
												style="width: 80px;" name="reOrderQty" value="0" readonly></td>



											<td><input type="text" id="curStock"
												style="width: 80px;" name="curStock" value="0" readonly></td>
											<td><input type="text" id="curStockVal"
												style="width: 80px;" name="curStockVal" value="0" readonly></td>

										</tr>  -->



							</table>
						</div>
						<div class="form-group" style="display: none;" id="range">



							<div class="col-sm-3  controls">
								<input type="button" id="expExcel" class="btn btn-primary"
									value="Export To Excel" onclick="exportToExcel();"
									disabled="disabled">
							</div>
						</div>
					</div>

				</div>
			</form>
		</div>
	</div>
	<!-- END Main Content -->

	<footer>
		<p>2018 © Monginis.</p>
	</footer>

	<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
		class="fa fa-chevron-up"></i></a>


	<script type="text/javascript">
		function searchReport() {
			//	var isValid = validate();

			var selectedFr = $("#selectFr").val();
			var routeId = $("#selectRoute").val();
			var catId = $("#selectCat").val();
			var itemsIds = $("#itemsIds").val();

			var selectRate = 1;
			var stType = 1;/* $("#st_type").val(); */

			$('#loader').show();

			$
					.getJSON(
							'${currentStockDetailList}',

							{
								fr_id_list : JSON.stringify(selectedFr),
								route_id : routeId,
								catId : catId,
								itemsIds : JSON.stringify(itemsIds),
								ajax : 'true'

							},
							function(data) {
								$('#loader').hide();
								//alert(JSON.stringify(data.frStockList));
								document.getElementById("expExcel").disabled = false;
								document.getElementById("PDFButton").disabled = false;

								if (data == "") {
									alert("No records found !!");
									document.getElementById("expExcel").disabled = true;
									document.getElementById("PDFButton").disabled = true;

								}

								var len = data.length;
								$('#table_grid td').remove();
								//alert(data.monthClosed+ "month close");			
								//alert(data.monthClosed);	alert(selectedStockOption);

								if (data.frStockList.monthClosed
										&& selectedStockOption == 1) {

									if (stType == 1) {
										document.getElementById('monthEnd').style.display = "block";
									}

									$('#table_grid th').remove();
									var tr = $('<tr class=bgpink></tr>');
									/* tr.append($('<th align=left>Item Id</th>'));
									tr.append($('<th align=left>Item Name</th>'));
									tr.append($('<th align=center>Rate/MRP</th>'));
									tr.append($('<th align=left>Op Stock</th>'));
									tr.append($('<th align=left>Op Stock Value</th>'));
									tr.append($('<th align=left>Pur Qty</th>'));
									tr.append($('<th align=left>Pur Value</th>'));
									tr.append($('<th align=left>Grn-Gvn Qty</th>'));
									tr.append($('<th align=left>Grn-Gvn Value</th>'));
									tr.append($('<th align=left>Regular Sale</th>'));
									tr.append($('<th align=left>Reg Sale Val</th>'));
									tr.append($('<th align=center>Cur Stock</th>'));
									tr.append($('<th align=center>CurStock Val</th>'));
									tr.append($('<th align=left>Physical Stock</th>'));
									tr.append($('<th align=left>Stock Diff</th>')); */

									$('#table_grid').append(tr);
								}

								$('#table_grid th').remove();
								var tr = $('<tr class=bgpink></tr>');
								tr.append($('<th align=left>Item Id</th>'));
								tr.append($('<th align=left>Item Name</th>'));
								/* tr.append($('<th align=center>Cur Stock</th>'));
								tr.append($('<th align=center>CurStock Val</th>')); */

								var frArr; 
								if(selectedFr!=null){
									frArr = selectedFr;
								}else{
									frArr = data.routeFrId;
								}
								var frListArr = data.frList;
								
								for (var i = 0; i < frArr.length; i++) {
									for (var j = 0; j < frListArr.length; j++) {
										if (parseInt(frArr[i]) == parseInt(frListArr[j].frId)) {
											tr.append($('<th align=center>'
													+ frListArr[j].frName
													+ ' Cur Stock</th>'));
										}
									}
								}

								$('#table_grid').append(tr);
								for (var h = 0; h < data.itemIdList.length; h++) {

									var flag = 0;

									var tr = $('<tr ></tr>');

									tr.append($('<td class="col-md-1"></td>')
											.html(data.itemIdList[h]));

									for (var i = 0; i < data.frStockList.length; i++) {

										for (var k = 0; k < frArr.length; k++) {

											if (data.frStockList[i].frId == parseInt(frArr[k])) {

												var list = data.frStockList[i].currentStockDetailList;

												for (var j = 0; j < list.length; j++) {

													var item = list[j];

													var key = 0

													var regCurrentStock = item.currentRegStock;
													var reOrderQty = item.reOrderQty;

													if (parseInt(item.id) == parseInt(data.itemIdList[h])) {

														if (flag == 0) {
															tr
																	.append($(
																			'<td class="col-md-1" ></td>')
																			.html(
																					item.itemName));
															flag = 1;
														}
														//alert(flag)
														if (regCurrentStock < 0) {
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"> </td>')
																			.html(
																					0));
														} else {
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"> </td>')
																			.html(
																					regCurrentStock));
														}
														break;
													}

												}
											}

										}
										$('#table_grid tbody').append(tr);

									}
								}
							});

		}
	</script>

	<script type="text/javascript">
		function validate() {

			var selectedFr = $("#selectFr").val();
			var selectedMenu = $("#selectMenu").val();
			var selectedRoute = $("#selectRoute").val();

			var isValid = true;

			if (selectedFr == "" || selectedFr == null) {

				if (selectedRoute == "" || selectedRoute == null) {
					alert("Please Select atleast one ");
					isValid = false;
				}
				//alert("Please select Franchise/Route");

			} else if (selectedMenu == "" || selectedMenu == null) {

				isValid = false;
				alert("Please select Menu");

			}
			return isValid;

		}
	</script>

	<script type="text/javascript">
		function updateTotal(orderId, rate) {

			var newQty = $("#billQty" + orderId).val();

			var total = parseFloat(newQty) * parseFloat(rate);

			$('#billTotal' + orderId).html(total);
		}
	</script>

	<script>
		$('.datepicker').datepicker({
			format : {
				/*
				 * Say our UI should display a week ahead,
				 * but textbox should store the actual date.
				 * This is useful if we need UI to select local dates,
				 * but store in UTC
				 */
				format : 'mm/dd/yyyy',
				startDate : '-3d'
			}
		});
	</script>

	<script type="text/javascript">
		function disableFr() {

			//alert("Inside Disable Fr ");
			document.getElementById("selectFr").disabled = true;

		}

		function disableRoute() {

			//alert("Inside Disable route ");
			var x = document.getElementById("selectRoute")
			//alert(x.options.length);
			var i;
			for (i = 0; i < x; i++) {
				document.getElementById("selectRoute").options[i].disabled;
				//document.getElementById("pets").options[2].disabled = true;
			}
			//document.getElementById("selectRoute").disabled = true;

		}
	</script>

	<script type="text/javascript">
		function genPdf() {
			var from_date = $("#fromDate").val();
			var to_date = $("#toDate").val();
			var selectedFr = $("#selectFr").val();
			var routeId = $("#selectRoute").val();
			var catId = $("#selectCat").val();
			var sub_cat = $("#item_grp2").val();
			window
					.open('${pageContext.request.contextPath}/pdfForReport?url=pdf/showSaleReportItemwisePdf/'
							+ from_date
							+ '/'
							+ to_date
							+ '/'
							+ selectedFr
							+ '/' + routeId + '/' + catId + '/' + sub_cat);

			//window.open('${pageContext.request.contextPath}/pdfForReport?url=showSaleReportItemwisePdf/'+from_date+'/'+to_date);

		} //window.open('pdfForReport?url=showSaleBillwiseByFrPdf/'+from_date+'/'+to_date);
		function exportToExcel() {

			window.open("${pageContext.request.contextPath}/exportToExcelNew");
			document.getElementById("expExcel").disabled = true;
		}
	</script>

	<script type="text/javascript">
		function addCommas(x) {

			x = String(x).toString();
			var afterPoint = '';
			if (x.indexOf('.') > 0)
				afterPoint = x.substring(x.indexOf('.'), x.length);
			x = Math.floor(x);
			x = x.toString();
			var lastThree = x.substring(x.length - 3);
			var otherNumbers = x.substring(0, x.length - 3);
			if (otherNumbers != '')
				lastThree = ',' + lastThree;
			return otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",")
					+ lastThree + afterPoint;
		}
	</script>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('#selectCat')
									.change(
											function() {
												var selectItem = $("#selectCat")
														.val();
												$
														.getJSON(
																'${getSubCatByCatId}',
																{
																	catId : JSON
																			.stringify(selectItem),
																	ajax : 'true'
																},
																function(data) {

																	var html = '<option value="-1"><c:out value=""/>All</option>';

																	var len = data.length;

																	$(
																			'#item_grp2')

																			.find(
																					'option')

																			.remove()

																			.end()

																	for (var i = 0; i < len; i++) {

																		/*  $("#item_grp2").append(
																		 $("<option></option>").attr("value",1).text("All")); */

																		$(
																				"#item_grp2")
																				.append(

																						$(
																								"<option ></option>")
																								.attr(

																										"value",
																										data[i].subCatId)
																								.text(
																										data[i].subCatName)

																				);

																	}
																	$(
																			"#item_grp2")
																			.trigger(
																					"chosen:updated");

																});
											});
						});

		$(document)
				.ready(
						function() {
							$('#item_grp2')
									.change(
											function() {
												var subCatId = $("#item_grp2")
														.val();
												$
														.getJSON(
																'${getItemsBySubCatId}',
																{
																	subCatId : JSON
																			.stringify(subCatId),
																	ajax : 'true'
																},
																function(data) {

																	var html = '<option value="-1"><c:out value=""/></option>';

																	var len = data.length;

																	$(
																			'#itemsIds')

																			.find(
																					'option')

																			.remove()

																			.end()

																	$(
																				"#itemsIds")
																				.append(
																						$(
																								"<option></option>")
																								.attr(
																										"value",
																										-1)
																								.text(
																										"All"));
																	for (var i = 0; i < len; i++) {

																		

																		$(
																				"#itemsIds")
																				.append(

																						$(
																								"<option ></option>")
																								.attr(

																										"value",
																										data[i].id)
																								.text(
																										data[i].itemName)

																				);

																	}
																	$(
																			"#itemsIds")
																			.trigger(
																					"chosen:updated");

																});
											});
						});

		$(document)
				.ready(
						function() {
							$('#itemsIds')
									.change(
											function() {
												var allItms = $("#itemsIds")
														.val();
												if (allItms == -1) {
													var subCatId = $(
															"#item_grp2").val();
													$
															.getJSON(
																	'${getAllItemsFrCutStock}',
																	{
																		subCatId : JSON
																				.stringify(subCatId),
																		ajax : 'true'
																	},
																	function(
																			data) {

																		var html = '<option value="-1"><c:out value=""/></option>';

																		var len = data.length;

																		$(
																				'#itemsIds')

																				.find(
																						'option')

																				.remove()

																				.end()

																		for (var i = 0; i < len; i++) {

																			/* $(
																					"#itemsIds")
																					.append(
																							$(
																									"<option></option>")
																									.attr(
																											"value",
																											1)
																									.text(
																											"All")); */

																			$(
																					"#itemsIds")
																					.append(

																							$(
																									"<option selected></option>")
																									.attr(

																											"value",
																											data[i].id)
																									.text(
																											data[i].itemName)

																					);

																		}
																		$(
																				"#itemsIds")
																				.trigger(
																						"chosen:updated");

																	});
												}
											});
						});

		function setAllFranchisee(frId) {
			if (frId == -1) {
				$.getJSON('${getAllCurrentStockFr}', {
					ajax : 'true'
				}, function(data) {
					var len = data.length;
					$('#selectFr').find('option').remove().end()

					$("#selectFr").append(
							$("<option ></option>").attr("value", -1).text(
									"Select All"));

					for (var i = 0; i < len; i++) {

						$("#selectFr").append(
								$("<option selected></option>").attr("value",
										data[i].frId).text(data[i].frName));
					}

					$("#selectFr").trigger("chosen:updated");
				});
			}
		}
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
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/chosen-bootstrap/chosen.jquery.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-inputmask/bootstrap-inputmask.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-tags-input/jquery.tagsinput.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-pwstrength/jquery.pwstrength.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-duallistbox/duallistbox/bootstrap-duallistbox.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/dropzone/downloads/dropzone.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/clockface/js/clockface.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/date.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/daterangepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-switch/static/js/bootstrap-switch.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/ckeditor/ckeditor.js"></script>

	<!--flaty scripts-->
	<script src="${pageContext.request.contextPath}/resources/js/flaty.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/flaty-demo-codes.js"></script>

</body>
</html>