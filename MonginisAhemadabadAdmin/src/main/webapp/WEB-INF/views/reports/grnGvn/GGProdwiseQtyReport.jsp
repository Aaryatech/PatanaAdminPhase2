<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body>

	<jsp:include page="/WEB-INF/views/include/logout.jsp"></jsp:include>


	<c:url var="getGGProdWiseQtyReport" value="/getGGProdWiseQtyReport"></c:url>
	<c:url var="getFrListofAllFr" value="/getFrListForDatewiseReport"></c:url>

	<c:url var="getGroup2ByCatId" value="/getSubCateListByCatId" />
	<c:url var="getItemsBySubCatIdAjax" value="/getItemsBySubCatIdAJAX" />



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
					<i class="fa fa-file-o"></i>GrnGvn Item Wise Quantity Report
				</h1>
				<h4></h4>
			</div>
		</div>
		<!-- END Page Title -->


		<!-- BEGIN Main Content -->
		<div class="box">
			<div class="box-title">
				<h3>
					<i class="fa fa-bars"></i>GrnGvn Franchise Item Quantity Report
				</h3>

			</div>

			<div class="box-content">
				<div class="row">


					<div class="form-group">
						<label class="col-sm-3 col-lg-2	 control-label">From Date</label>
						<div class="col-sm-6 col-lg-4 controls date_select">
							<input class="form-control date-picker" id="fromDate"
								name="fromDate" size="30" type="text" value="${todaysDate}" />
						</div>

						<!-- </div>

					<div class="form-group  "> -->

						<label class="col-sm-3 col-lg-2	 control-label">To Date</label>
						<div class="col-sm-6 col-lg-4 controls date_select">
							<input class="form-control date-picker" id="toDate" name="toDate"
								size="30" type="text" value="${todaysDate}" />
						</div>
					</div>

				</div>


				<br>


				<div class="row">
					<div class="form-group" style="display: none;">
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
						<label class="col-sm-3 col-lg-2 control-label"><b>OR</b></label>
					</div>
				</div>

				<br>
				<div class="row">
					<label class="col-sm-3 col-lg-2 control-label">&nbsp;Select
						Franchise</label>
					<div class="col-md-10">

						<select data-placeholder="Choose Franchisee"
							class="form-control chosen" multiple="multiple" tabindex="6"
							id="selectFr" name="selectFr" onchange="disableRoute()">
							
<!-- onchange="setAllFrSelected(this.value)" onchange="disableRoute()" -->
							<%-- <option value="-1"><c:out value="All" /></option> --%>

							<c:forEach items="${unSelectedFrList}" var="fr" varStatus="count">
								<option value="${fr.frId}"><c:out value="${fr.frName}" /></option>
							</c:forEach>
						</select>

					</div>


				</div>

				<br>
				<div class="row">
					<label class="col-sm-3 col-lg-2 control-label">Category</label>
					<div class="col-sm-3 col-lg-2">

						<select data-placeholder="Select Category"
							class="form-control chosen" tabindex="6" id="selectCat"
							name="selectCat">

							<option value=" ">Select</option>

							<c:forEach items="${catList}" var="cat" varStatus="count">
							<c:if test="${cat.catId!=5}">
								<c:choose>
									<c:when test="${cat.catId==catId}">
										<option value="${cat.catId}" selected><c:out
												value="${cat.catName}" /></option>
									</c:when>
									<c:otherwise>
										<option value="${cat.catId}"><c:out
												value="${cat.catName}" /></option>
									</c:otherwise>
								</c:choose>
								</c:if>
							</c:forEach>
						</select>
					</div>

					<label class="col-sm-2 col-lg-1 control-label">Sub Category</label>
					<div class="col-sm-3 col-lg-5 controls">
						<select data-placeholder="Select Sub Category"
							class="form-control chosen" name="item_grp2" id="item_grp2"
							tabindex="-1" data-rule-required="true">
						</select>
					</div>
				</div>
				<br>
				<div class="row">
					<label class="col-sm-3 col-lg-2 control-label">&nbsp;Select
						Items</label>
					<div class="col-md-10">

						<select data-placeholder="Choose Items"
							class="form-control chosen" multiple="multiple" tabindex="6"
							id="item_ids" name="item_ids" onchange="clearSelectedItems(this.value)">
							<option value="-1"><c:out value="All" /></option>
								<option value="0"><c:out value="Clear Selection" /></option>
							<%-- <c:forEach items="${unSelectedFrList}" var="fr" varStatus="count">
								<option value="${fr.frId}"><c:out value="${fr.frName}" /></option>
							</c:forEach> --%>
						</select>

					</div>


				</div>
				<br>
				<div class="row">
					<div class="form-group">

						<label class="col-sm-3 col-lg-2 control-label">Select Type</label>
						<div class="col-sm-6 col-lg-4 controls">
							<select data-placeholder="Select Route"
								class="form-control chosen" name="selectStatus"
								id="selectStatus">
								<option value="-1">All</option>
								<option value="1">GRN</option>
								<option value="0">GVN</option>
							</select>

						</div>

						<div class="col-md-6">
							<button class="btn btn-primary" onclick="searchReport()">Search</button>

							<!-- <button class="btn search_btn" onclick="showChart()">Graph</button> -->

							<!-- <button class="btn btn-primary" value="PDF" id="PDFButton"
								onclick="genPdf()">PDF</button> -->

							
						</div>

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
					<i class="fa fa-list-alt"></i>Date-wise Report
				</h3>

			</div> -->


			<div class=" box-content" id="allTable">
				<div class="row">
					<div class="col-md-12 table-responsive">
						<table class="table table-bordered table-striped fill-head "
							style="width: 100%" id="table_grid">
							<thead style="background-color: #f3b5db;">
								<tr>
									<th style="text-align: center;">Sr.No.</th>
									<th style="text-align: center;">Date</th>
									<!-- <th style="text-align: center;">Taxable Value</th>
									<th style="text-align: center;">Tax Value</th> -->
									<th style="text-align: center;">Grand Total</th>
									<!-- <th style="text-align: center;">GRN Taxable Value</th>
									<th style="text-align: center;">GRN Tax Value</th> -->
									<th style="text-align: center;">GRN Grand Total</th>
									<!-- <th style="text-align: center;">GVN Taxable Value</th>
									<th style="text-align: center;">GVN Tax Value</th> -->
									<th style="text-align: center;">GVN Grand Total</th>
									<th style="text-align: center;">NET Taxable Total</th>
									<th style="text-align: center;">NET Tax Total</th>
									<th style="text-align: center;">NET Grand Total</th>
									<!-- <th>Total</th> -->
								</tr>
							</thead>
							<tbody>

							</tbody>
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
				var frIdArr = [];
					$.each($("#selectFr option:selected"), function() {
						frIdArr.push($(this).val());
					});
				//alert(frIdArr.length)
				var selectedFr = $("#selectFr").val();
				var routeId = $("#selectRoute").val();
				var from_date = $("#fromDate").val();
				var to_date = $("#toDate").val();
				var selectStatus = document.getElementById("selectStatus").value;
				var selectCat = $("#selectCat").val();
				var selectedSubCat = $("#item_grp2").val();
				var item_ids = $("#item_ids").val();
				//alert(item_ids)
				if(frIdArr.length>7){
					alert("Please Select Maximum 7 Franchise")
				}else{
				$('#loader').show();
				$('#table_grid td').remove();
				$.getJSON('${getGGProdWiseQtyReport}', {
					fr_id_list : JSON.stringify(selectedFr),
					fromDate : from_date,
					toDate : to_date,
					route_id : routeId,
					sub_cat_id_list : JSON.stringify(selectedSubCat),
					cat_id : selectCat,
					item_id_list : JSON.stringify(item_ids),
					selectStatus: selectStatus,
					ajax : 'true'
				}, function(data) {
					//alert("data" +JSON.stringify(data));
					
					//console.log(frIdArr);
					var frNameArr = [];
					$.each($("#selectFr option:selected"), function() {
						frNameArr.push($(this).text());
					});
					//console.log(frNameArr);
					var itemIdArr = [];
					$.each($("#item_ids option:selected"), function() {
						itemIdArr.push($(this).val());
					});
					//console.log(itemIdArr);

					var itemNameArr = [];
					$.each($("#item_ids option:selected"), function() {
						itemNameArr.push($(this).text());
					});
					//console.log(itemNameArr);
					//console.log(data);

					$('#table_grid th').remove();
					var tr = $('<tr class=bgpink></tr>');
				//	tr.append($('<th align=left>Item Id</th>'));
					tr.append($('<th align=left>Item Name</th>'));

					for (var i = 0; i < frNameArr.length; i++) {
						tr.append($('<th align=center>' + frNameArr[i]
								+ '</th>'));
					}//end of frNameArr loop

					$('#table_grid').append(tr);

					for (var i = 0; i < itemNameArr.length; i++) {
						tr = $('<tr ></tr>');
						tr.append($('<td   class="col-md-1"></td>').html(
								itemNameArr[i]));

						for (var j = 0; j < frIdArr.length; j++) {

							var flag = 0;

							for (var d = 0; d < data.length; d++) {

								if (data[d].frId == frIdArr[j]
										&& data[d].itemId == itemIdArr[i]) {
									tr.append($('<td  class="col-md-1"></td>')
											.html(data[d].grnGvnQty));
									flag = 1
									break;
								}
							}
							if (flag == 0) {
								tr.append($('<td  class="col-md-1"></td>')
										.html(0));
							}
						}//end of frIdArr loop
						$('#table_grid tbody').append(tr);
					}//end of itemIdArr loop
					$('#loader').hide();
				});
				}
				$('#loader').hide();
			}
		</script>

		<script type="text/javascript">
			function validate() {

				var selectedFr = $("#selectFr").val();
				var selectedRoute = $("#selectRoute").val();

				var isValid = true;

				if ((selectedFr == "" || selectedFr == null)
						&& (selectedRoute == 0)) {

					alert("Please Select Route  Or Franchisee");
					isValid = false;

				}
				return isValid;

			}
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

		<script>
			function setAllFrSelected(frId) {
				//alert("frId" + frId);
				//alert("hii")
				if (frId == -1) {

					$.getJSON('${getFrListofAllFr}', {

						ajax : 'true'
					}, function(data) {

						var len = data.length;

						//alert(len);

						$('#selectFr').find('option').remove().end()
						$("#selectFr").append(
								$("<option value='-1'>All</option>"));
						for (var i = 0; i < len; i++) {
							$("#selectFr").append(
									$("<option selected ></option>").attr(
											"value", data[i].frId).text(
											data[i].frName));
						}
						$("#selectFr").trigger("chosen:updated");
					});
				}
			}
		</script>



		<script type="text/javascript">
			function genPdf() {
				var selectedFr = $("#selectFr").val();
				var routeId = $("#selectRoute").val();
				var from_date = $("#fromDate").val();
				var to_date = $("#toDate").val();
				var selectCat = $("#selectCat").val();
				var selectedSubCat = $("#item_grp2").val();
				var selectStatus = document.getElementById("selectStatus").value;

				window
						.open('pdfForReport?url=pdf/showSaleBillwiseGrpByDatePdf/'
								+ from_date
								+ '/'
								+ to_date
								+ '/'
								+ selectedFr
								+ '/'
								+ routeId
								+ '/'
								+ selectCat
								+ '/'
								+ selectedSubCat+'/'+selectStatus);

			}
			function exportToExcel() {

				window
						.open("${pageContext.request.contextPath}/exportToExcelNew");
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
													$
															.getJSON(
																	'${getGroup2ByCatId}',
																	{
																		catId : $(
																				this)
																				.val(),
																		ajax : 'true'
																	},
																	function(
																			data) {
																		var len = data.length;
																		$(
																				'#item_grp2')
																				.find(
																						'option')
																				.remove()
																				.end()
																		for (var i = 0; i < len; i++) {
																			$(
																					"#item_grp2")
																					.append(
																							$(
																									"<option></option>")
																									.attr(
																											"value",
																											data[i].subCatId)
																									.text(
																											data[i].subCatName));
																		}
																		$(
																				"#item_grp2")
																				.trigger(
																						"chosen:updated");
																	});
												});
							});
		</script>
		<script type="text/javascript">
			function maxAllowedMultiselect(obj, maxAllowedCount) {
				var selectedOptions = ('#' + obj.id
						+ " option[value!=\'\']:selected");
				if (selectedOptions.length >= maxAllowedCount) {
					if (selectedOptions.length > maxAllowedCount) {
						selectedOptions.each(function(i) {
							if (i >= maxAllowedCount) {
								(this).prop("selected", false);
							}
						});
					}
					('#' + obj.id + ' option[value!=\'\']').not(
							':selected').prop("disabled", true);
				} else {
					('#' + obj.id + ' option[value!=\'\']').prop(
							"disabled", false);
				}
			}
		</script>
		<script type="text/javascript">
			$(document)
					.ready(
							function() {
								$('#item_grp2')
										.change(
												function() {
													$
															.getJSON(
																	'${getItemsBySubCatIdAjax}',
																	{
																		subCatId : $(
																				this)
																				.val(),
																		ajax : 'true'
																	},
																	function(
																			data) {
																		
																		var len = data.length;
																		$(
																				'#item_ids')
																				.find(
																						'option')
																				.remove()
																				.end()
				 $("#item_ids") .append( $( "<option></option>") .attr( "value", -1) .text( "ALL"));
				 $("#item_ids") .append( $( "<option></option>") .attr( "value", 0) .text( "Clear Selection"));
				 
																		for (var i = 0; i < len; i++) {
																			$(
																					"#item_ids")
																					.append(
																							$(
																									"<option></option>")
																									.attr(
																											"value",
																											data[i].id)
																									.text(
																											data[i].itemName));
																		}
																		$(
																				"#item_ids")
																				.trigger(
																						"chosen:updated");
																	});
												});
							});
		</script>

<script type="text/javascript">
			$(document)
					.ready(
							function() {
								$('#item_ids')
										.change(
												function() {
													if(parseInt($(this).val())<0){
													$
															.getJSON(
																	'${getItemsBySubCatIdAjax}',
																	{
																		subCatId : $("#item_grp2").val(),
																		ajax : 'true'
																	},
																	function(
																			data) {
																		var len = data.length;
																		$(
																				'#item_ids')
																				.find( 'option')
																				.remove()
																				.end()
				 $("#item_ids").append( $( "<option></option>") .attr( "value", -1) .text( "ALL"));
				 
																		for (var i = 0; i < len; i++) {
																			$(
																					"#item_ids")
																					.append(
																							$(
																									"<option selected></option>")
																									.attr(
																											"value",
																											data[i].id)
																									.text(
																											data[i].itemName));
																		}
																		 $("#item_ids").append( $( "<option onchange='clearSelectedItems(0)'></option>") .attr( "value", 0) .text( "Clear Selection"));

																		$(
																				"#item_ids")
																				.trigger(
																						"chosen:updated");
																	});
													}  /* else if(parseInt($(this).val())==0){
														//alert("Ok Else")
														 $("#item_ids option:selected").removeAttr("selected");
											    	    	$("#item_ids").trigger("chosen:updated");
											    	    	//$('#loader').hide();
													}  */
												});
							});
			function clearSelectedItems(value1){
				//alert("Ok" +value1);
				if(parseInt(value1)==0){
				$("#item_ids option:selected").removeAttr("selected");
    	    	$("#item_ids").trigger("chosen:updated");
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