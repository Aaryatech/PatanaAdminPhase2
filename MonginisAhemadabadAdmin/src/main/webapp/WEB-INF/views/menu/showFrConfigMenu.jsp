<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body>
<style>
	.modal-content{
	    margin-top: 10%;
	    margin-left: 35%;
	    width: 40%;
	    height: 50%;
	}
	</style>
	<jsp:include page="/WEB-INF/views/include/logout.jsp"></jsp:include>

	<c:url var="getFrConfiguredMenus" value="/getFrConfiguredMenus"></c:url>
	<c:url var="getAllMenusInAjax" value="/getAllMenusInAjax"></c:url>
	<c:url var="getAllFrInAjax" value="/getAllFrInAjax"></c:url>
	<c:url value="/getFrConfigMenuPrintIds" var="getFrConfigMenuPrintIds"/>


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
					<i class="fa fa-file-o"></i>Configure Franchise And Menu
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
				<li class="active">Bill-wise Report</li>
			</ul>
		</div> --%>
		<!-- END Breadcrumb -->

		<!-- BEGIN Main Content -->
		<div class="box">
			<div class="box-title">
				<h3>
					<i class="fa fa-bars"></i>Franchise Menu Configure
				</h3>

			</div>

			<div class="box-content">

				<div class="row">
					<div class="form-group">
						<label class="col-sm-3 col-lg-2 control-label">Select Menu</label>
						<div class="col-sm-6 col-lg-10 controls">
							<select data-placeholder="Select Route"  multiple="multiple" tabindex="6"
								class="form-control chosen" name="menuId" id="menuId"
								 onchange="setAllMenus(this.value);">							
								<option value="-1"><c:out value="All" /></option>
								<c:forEach items="${menuList}" var="menuList" varStatus="count">								
									<option value="${menuList.menuId}"><c:out
											value="${menuList.menuTitle}" />
									</option>
								</c:forEach>
							</select>

						</div>

					</div>
				</div>
				<br>

				<div class="row">
					<div class="form-group">

						<label class="col-sm-3 col-lg-2 control-label">Select
							Franchise</label>
						<div class="col-sm-6 col-lg-10">

							<select data-placeholder="Choose Franchisee"
								class="form-control chosen" multiple="multiple" tabindex="6"
								id="selectFr" name="selectFr"
								onchange="setAllFranchisee(this.value);">

								<option value="-1"><c:out value="All" /></option>

								<c:forEach items="${frList}" var="fr" varStatus="count">
									<option value="${fr.frId}"><c:out value="${fr.frName}" /></option>
								</c:forEach>
							</select>

						</div>
					</div>
				</div>
				
				
				<div class="row">	
				<div class="col-md-2" style="text-align: center;">
						<button class="btn btn-primary" onclick="searchReport()">Search</button>
						
					</div>
					<div class="col-md-1">
						
						<input type="button"  id="btn_exl_pdf"
							class="btn btn-primary" onclick="getHeaders()"
							value="Excel / Pdf" />
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
					<i class="fa fa-list-alt"></i>Bill-wise Report
				</h3>

			</div> -->
				<div class=" box-content">
					<div class="row">
						<div class="col-md-12 table-responsive">
							<table class="table table-bordered table-striped fill-head "
								style="width: 100%" id="table_grid">
								<thead style="background-color: #f3b5db;">
									<tr>
										<th style="text-align: center;">Sr.No.</th>
										<th style="text-align: center;">Franchise</th>
										<th style="text-align: center;">Menu</th>
										<th style="text-align: center;">Category</th>
										<th style="text-align: center;">Type</th>
										<th style="text-align: center;">From Time</th>
										<th style="text-align: center;">To Time</th>
										<th style="text-align: center;">Profit%</th>
										<th style="text-align: center;">GRN%</th>
										<th style="text-align: center;">Discount%</th>
									</tr>
								</thead>
								<tbody>

								</tbody>
							</table>
						</div>						
					</div>

				</div>
			
		</div>
	</div>
	<!-- END Main Content -->

	<footer>
		<p>2017 © Monginis.</p>
	</footer>

	<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
		class="fa fa-chevron-up"></i></a>

<table width="100%" class="table table-advance" id="printtable2" style="display: none;">
		<thead style="background-color: #f3b5db;" >
			<tr>
				<th>Franchise</th>
				<th>Menu Title</th>
				<th>Category</th>
				<th>Type</th>
				<th>From Time</th>
				<th>To Time</th>
				<th>Profit%</th>
				<th>GRN%</th>
				<th>Discount%</th>
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
				<div class="form-group" style="background-color: white; padding:0 0 10px 0;">
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
	
	<script type="text/javascript">
		function setAllFranchisee(frId) {
			if (frId == -1) {
				$.getJSON('${getAllFrInAjax}', {
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
		
		function setAllMenus(menuId) {
			if (menuId == -1) {
				$.getJSON('${getAllMenusInAjax}', {
					ajax : 'true'
				}, function(data) {
					var len = data.length;
					$('#menuId').find('option').remove().end()

					$("#menuId").append(
							$("<option ></option>").attr("value", -1).text(
									"Select All"));

					for (var i = 0; i < len; i++) {

						$("#menuId").append(
								$("<option selected></option>").attr("value",
										data[i].menuId).text(data[i].menuTitle));
					}

					$("#menuId").trigger("chosen:updated");
				});
			}
		}
	</script>


	<script type="text/javascript">
		function searchReport() {
			//	var isValid = validate();

			var frIds = $("#selectFr").val();
			var menuIds = $("#menuId").val();
			
			$('#loader').show();

			$.getJSON('${getFrConfiguredMenus}',

			{
				frIds : JSON.stringify(frIds),
				menuIds : JSON.stringify(menuIds),
				ajax : 'true'

			}, function(data) {

				$('#table_grid td').remove();
				$('#loader').hide();

				if (data == "") {
					alert("No records found !!");
					document.getElementById("expExcel").disabled = true;
				}
				
				$.each(data, function(key, report) {
					
					var index = key + 1;
					

					var tr = $('<tr></tr>');

					tr.append($('<td></td>').html(key + 1));
					
					tr.append($('<td></td>').html(report.frName));
					
					tr.append($('<td></td>').html(report.menuTitle));

					tr.append($('<td></td>').html(report.catName));

					tr.append($('<td></td>').html(report.type == 0 ? "Regular"  : 
						report.type == 1 ? "Same Day Regular"  : 
							report.type == 2 ? "Regular with limit"  : 
								report.type == 3 ? "Regular cake As SP Order"  : "Delivery And Production Date"));
					
					tr.append($('<td></td>').html(report.fromTime));
					tr.append($('<td></td>').html(report.toTime));
					
					tr.append($('<td style="text-align:right;"></td>').html(
								report.profitPer.toFixed(2)));
					
					tr.append($('<td style="text-align:right;"></td>').html(
							report.grnPer.toFixed(2)));
					tr.append($('<td style="text-align:right;"></td>').html(
							report.discPer.toFixed(2)));
					
					$('#table_grid tbody').append(tr);

				});
			
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
								'${getFrConfigMenuPrintIds}',
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
											var frIds = $("#selectFr").val();
											var menuIds = $("#menuId").val();
											 window.open('${pageContext.request.contextPath}/pdfForReport?url=pdf/getFrConfigMenuPdf/'+elemntIds.join()
													 +'/'+frIds+'/'+menuIds);
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
</body>
</html>