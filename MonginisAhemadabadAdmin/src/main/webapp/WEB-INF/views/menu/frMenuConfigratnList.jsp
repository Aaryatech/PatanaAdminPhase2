<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body>
<c:url value="/getFrMenuConfigPrintIds" var="getFrMenuConfigPrintIds"/>
<c:url value="/getFranchiseByFrMenuId" var="getFranchiseByFrMenuId"/>
<style>
	.modal-content{
	    margin-top: 10%;
	    margin-left: 35%;
	    width: 40%;
	    height: 50%;
	}
	
	.modal-content-fr{
	    margin-top: 5%;
	    margin-left: 10%;
	    width: 80%;
	    height: 80%;
	}
	</style>
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
			<div class="page-title">
				<div>
					<h1>
						<i class="fa fa-file-o"></i> Franchise Menus Configuration
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
								<i class="fa fa-table"></i> Franchise Menus Configuration List
							</h3>
							<div class="box-tool">
								<a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>

							</div>
						</div>

						<div class="box-content">
							<jsp:include page="/WEB-INF/views/include/tableSearch.jsp"></jsp:include>


							<div class="clearfix"></div>
							<div id="table-scroll" class="table-scroll">

								<div id="faux-table" class="faux-table" aria="hidden">
									<table id="table2" class="main-table">
										<thead>
											<tr class="bgpink">
												<th width="17" style="width: 18px">#</th>
												<th width="221" align="left">Menu Title</th>
												<th width="185" align="left">Category</th>
												<th width="185" align="left">Type</th>
												<th width="301" align="left">Profit%</th>
												<th width="190" align="center">GRN%</th>
												<th width="190" align="center">Discount%</th>
											</tr>
										</thead>
									</table>

								</div>
								<div class="table-wrap">

									<table id="table1" class="table table-advance">
										<thead>
											<tr class="bgpink">
												<th width="17" style="width: 18px">#</th>
												<th width="221" align="left">Menu Title</th>
												<th width="185" align="left">Category</th>
												<th width="185" align="left">Type</th>
												<th width="301" align="left">Profit%</th>
												<th width="190" align="center">GRN%</th>
												<th width="190" align="center">Discount%</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${mesnuShowList}" var="menu"
												varStatus="count">
												<tr>
													<td><c:out value="${count.index+1}" /></td>
													<td style="text-align: left;">
													<a href="javascript:void(0);" onclick="showFranchise(${menu.menuId}, '${menu.menuTitle}')">
													<c:out value="${menu.menuTitle}" /></a></td>
													<td style="text-align: left;"><c:out value="${menu.catName}" /></td>
													<td style="text-align: left;"><c:out
															value="${menu.type==0 ? 'Regular' : 
															menu.type==1 ? 'Same Day Regular' : 
															menu.type==2 ? 'Regular with limit' : 
														  	menu.type==3 ? 'Regular cake As SP Order' : 'Delivery And Production Date'}" /></td>
													<td style="text-align: right;"><c:out value="${menu.grnPer}" /></td>
													<td style="text-align: right;"><c:out value="${menu.profitPer}" /></td>
													<td style="text-align: right;"><c:out value="${menu.discPer}" /></td>
												</tr>
											</c:forEach>

										</tbody>

									</table>
								</div>
							</div>
<div class="form-group" style="background-color: white;">
										<input type="button" margin-right: 5px;" id="btn_exl_pdf"
											class="btn btn-primary" onclick="getHeaders()" 
											value="Excel / Pdf" />
									</div>
						</div>					
					</div>
				</div>
			</div>



			<!-- END Main Content -->
			<footer>
				<p>2017 © MONGINIS.</p>
			</footer>

			<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
				class="fa fa-chevron-up"></i></a>
		</div>
		<!-- END Content -->
	</div>
	<!-- END Container -->
	<table width="100%" class="table table-advance" id="printtable2"
		style="display: none;">
		<thead style="background-color: #f3b5db;">
			<tr>
				<th>Menu Title</th>
				<th>Category</th>
				<th>Type</th>
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
										id="selAllChk" /></th>
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
					&nbsp; &nbsp; &nbsp; &nbsp; <input type="button"
						margin-right: 5px;"
											class="btn btn-primary"
						id="expExcel" onclick="getIdsReport(1)" value="Excel" /> &nbsp;
					&nbsp; &nbsp; &nbsp; <input type="button"
						margin-right: 5px;"
											class="btn btn-primary"
						onclick="getIdsReport(2)" value="Pdf" />
				</div>
			</div>

		</div>

	</div>
	
	<!-- Franchise Model -->
	<div id="frModal" class="modal">
		<!-- Modal content -->
		<div class="modal-content-fr" id="modal_fr">

			<div class="box">
				<div class="box-title">
					<h3>
						<i class="fa fa-table"></i> <u><span id="menu_title"></span></u> - Menu Alloted Franchises
					</h3>
					<input type="button" class="btn btn-primary" style=" float: right;
						id="btn_close" onclick="clsFrModal()" value="Close" />
				</div>

				<div class="box-content">
					<div class="clearfix"></div>
					<div class="table-responsive" style="border: 0">
						<table width="100%" class="table table-advance" id="frtable_grid">
							<thead style="background-color: #f3b5db;">
								<tr>
									<th width="15">Sr No.</th>
									<th style="text-align: center;">Franchise</th>
									<th style="text-align: center;">Code</th>
									<th style="text-align: center;">City</th>
									<th style="text-align: center;">Owner name</th>
									<th style="text-align: center;">Mobile No.</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>

			</div>
			<!-- <div class="form-group" style="background-color: white;">
				&nbsp; &nbsp; &nbsp; &nbsp; <input type="button"
					margin-right: 5px;"
											class="btn btn-primary"
					id="btn_close" onclick="clsFrModal()" value="Close" />

			</div> -->
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
								'${getFrMenuConfigPrintIds}',
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
											 window.open('${pageContext.request.contextPath}/pdfForReport?url=pdf/getFrMenuConfigListPdf/'+elemntIds.join());
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
<script type="text/javascript">
var modalFr = document.getElementById("frModal");
function showFranchise(menuId, menuName){	
	$('#frtable_grid td').remove();
	document.getElementById( "menu_title" ).innerHTML=menuName;
	$
	.getJSON(
			'${getFranchiseByFrMenuId}',
			{							
				menuId : menuId,
				ajax : 'true'
			},
			function(data) {	
				if(data==""){
					alert("No Franchise Found");
				}else{
					modalFr.style.display = "block";						
				}
				$.each(data, function(key, fr) {
					
					var index = key + 1;	
					var tr = $('<tr></tr>');
					tr.append($('<td></td>').html(index));
					
					tr.append($('<td style="text-align:left;"></td>').html(
							fr.frName));
					
					tr.append($('<td style="text-align:center;"></td>').html(
							fr.frCode));
					
					tr.append($('<td style="text-align:left;"></td>').html(
							fr.frCity));
					
					tr.append($('<td style="text-align:left;"></td>').html(
							fr.frOwner));
					
					tr.append($('<td style="text-align:center;"></td>').html(
							fr.frTarget));
					
					$('#frtable_grid tbody').append(tr);

				})
			});
}	

function clsFrModal() {
	modalFr.style.display = "none";
}
</script>
</body>
</html>