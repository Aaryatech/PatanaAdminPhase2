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
	<c:url var="getAccPendingItemsGrnGvn" value="/getAccPendingItemsGrnGvn"></c:url>
	<c:url var="getAllFrPndngItm" value="/getAllFrPndngItm"></c:url>

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
					<i class="fa fa-file-o"></i>Grn Gvn Report By Date
				</h1>
				<h4></h4>
			</div>
		</div> -->
		<!-- END Page Title -->

		<!-- BEGIN Breadcrumb -->
		<%-- 	<div id="breadcrumbs">
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
					<i class="fa fa-bars"></i>A/c Pending Item Wise Grn Gvn
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

				<!-- <div class="col-sm-9 col-lg-5 controls">
 -->
				<div class="row">
					<div class="form-group">

						<label class="col-sm-3 col-lg-2 control-label"> View
							Option</label>
						<div class="col-sm-6 col-lg-4 controls">

							<select class="form-control" name="isGrn" id="isGrn">
								<option value="-1">Select View Option</option>

								<option selected value="1">GRN</option>
								<option value="0">GVN</option>
								<option value="2">ALL</option>
							</select>
						</div>
						
						<label class="col-sm-3 col-lg-2 control-label"> Select
							Option</label>
						<div class="col-sm-6 col-lg-4 controls">

							<select class="form-control" name="aprvBy" id="aprvBy">
								<option value="0">A/c Approve Pending</option>
								<option value="1">Credit Note Pending</option>
							</select>
						</div>
					</div>
				</div>

				<br>
				<div class="row">
									
									
									<label class="col-sm-3 col-lg-2 control-label">Select
							Franchisee</label>
						<div class="col-sm-6 col-lg-10">

							<select data-placeholder="Choose Franchisee"
								class="form-control chosen" multiple="multiple" tabindex="6"
								id="selectFr" name="selectFr"
								onchange="setAllFranchisee(this.value);">

								<option value="-1"><c:out value="All" /></option>

								<c:forEach items="${unSelectedFrList}" var="fr" varStatus="count">
									<option value="${fr.frId}"><c:out value="${fr.frName}" /></option>
								</c:forEach>
							</select>

						</div>
				</div>
				
				<br>
				<div class="row">
				
				<div class="col-md-12" style="text-align: center;">
						<button class="btn btn-info" onclick="searchReport()">Search
							Report</button>
						<!-- <button class="btn search_btn" onclick="showChart()">Graph</button> -->

						<button class="btn btn-primary" value="PDF" id="PDFButton"
							onclick="genPdf()">PDF</button>
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
		<div class="box-content" style="background-color: white;">
			<div class="">
				<!-- <div class="box-title">
				<h3>
					<i class="fa fa-list-alt"></i>Grn Gvn Report
				</h3>

			</div> -->

				<form id="submitBillForm" method="post">

					<div class="col-md-12 table-responsive">
						<table class="table table-bordered table-striped fill-head "
							style="width: 100%" id="table_grid">
							<thead style="background-color: #f3b5db;">
								<tr>
									<th>Sr.No.</th>
									<th>GRN GVN SrNo.</th>
									<th>Type</th>
									<th>GRN GVN Date</th>
									<th>Item Name</th>
									<th>Qty.</th>
									<th>GRN GVN Status</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<div class="form-group" style="display: none;" id="range">

							<div class="col-sm-3  controls">
								<input type="button" id="expExcel" class="btn btn-primary"
									value="EXPORT TO Excel" onclick="exportToExcel();"
									disabled="disabled">
							</div>
						</div>
						<div align="center" id="showchart" style="display: none"></div>
					</div>

					<!-- 				</div>
				
				<div id="chart" style="display: none"><br><br><br>
	<hr><div  >
	 
			<div  id="chart_div" style="width:60%; height:300; float:left;" ></div> 
		 
			<div   id="PieChart_div" style="width:40%%; height:300; float: right;" ></div> 
			</div>
			
				 
				</div> -->


					<div id="chart"">
						<br> <br> <br>



						<div id="chart_div" style="width: 100%; height: 100%;"></div>


						<div id="PieChart_div" style="width: 100%; height: 100%;"></div>


					</div>
				</form>
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
			document.getElementById('chart').style.display = "display:none";
			document.getElementById("table_grid").style = "block";
			
			var grngvnStat;
			var type;
			
			var isGrn = $("#isGrn").val();	
			var selectedFr = $("#selectFr").val();
			var from_date = $("#fromDate").val();
			var to_date = $("#toDate").val();
			var apprvBy = $("#aprvBy").val();
			
			$('#loader').show();

			$.getJSON('${getAccPendingItemsGrnGvn}',
			{
				fr_id_list : JSON.stringify(selectedFr),
				from_date : from_date,
				to_date : to_date,
				apprvBy : apprvBy,
				is_grn : isGrn,
				ajax : 'true'

			}, function(data) {

				$('#table_grid td').remove();
				$('#loader').hide();

				if (data == "") {
					alert("No records found !!");
					document.getElementById("expExcel").disabled = true;
				}

				$.each(data, function(key, report) {

					document.getElementById("expExcel").disabled = false;
					document.getElementById('range').style.display = 'block';

					var index = key + 1;
					//var tr = "<tr>";
					var tr = $('<tr></tr>');
					tr.append($('<td></td>').html(key + 1));
					
					if (report.isGrn == 1) {
						type = "GRN"
					} else {
						type = "GVN"
					}
					
					if (report.grngvnStatus == 1){
						grngvnStat = 'Pending';
					}
					else if (report.grngvnStatus == 2){
						grngvnStat = 'Approved By Gate';
					}
					else if (report.grngvnStatus == 3){
						grngvnStat = 'Reject By Gate';
					}
					else if (report.grngvnStatus == 4){
						grngvnStat = 'Approved By Store';
					}
					else if (report.grngvnStatus == 5){
						grngvnStat = 'Reject By Store';
					}
					else if (report.grngvnStatus == 6){
						grngvnStat = 'Approved By Acc'; 
					}
					else{
						grngvnStat = 'Reject By Acc';
					}

					tr.append($('<td></td>').html(report.grngvnSrno));
					tr.append($('<td></td>').html(type));
					tr.append($('<td></td>').html(report.grngvnDate));
					tr.append($('<td></td>').html(report.itemName));
					tr.append($('<td style="text-align:right;"></td>').html(
							report.grnGvnQty.toFixed(2)));
					tr.append($('<td style="text-align:right;"></td>').html(
							grngvnStat));
					$('#table_grid tbody').append(tr);

				});

			});

		}
	</script>

<script type="text/javascript">
		function setAllFranchisee(frId) {
			if (frId == -1) {
				$.getJSON('${getAllFrPndngItm}', {
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
	<script type="text/javascript">
		function validate() {

			var selectedFr = $("#selectFr").val();
			var isGrn = $("#isGrn").val();
			var selectedRoute = $("#selectRoute").val();

			var isValid = true;

			if (selectedFr == "" || selectedFr == null) {

				if (selectedRoute == "0" || selectedRoute == null) {
					alert("Please Select Franchise or Route ");
					isValid = false;
				}
				//alert("Please select Franchise/Route");

			} else if (isGrn == "-1" || isGrn == null) {

				isValid = false;
				alert("Please select Option");

			}
			return isValid;

		}
	</script>
	<script type="text/javascript">
		function showChart() {
			$("#PieChart_div").empty();
			$("#chart_div").empty();
			document.getElementById('chart').style.display = "block";
			document.getElementById("table_grid").style = "display:none";

			document.getElementById('chart').style.display = "display:none";
			document.getElementById("table_grid").style = "block";
			var isGrn = $("#isGrn").val();
			//alert("isGrn " +isGrn);

			//report 1
			var selectedFr = $("#selectFr").val();
			var routeId = $("#selectRoute").val();

			var from_date = $("#fromDate").val();
			var to_date = $("#toDate").val();

			$('#loader').show();

			$
					.getJSON(
							'${getGrnGvnByDatewise}',

							{
								fr_id_list : JSON.stringify(selectedFr),
								from_date : from_date,
								to_date : to_date,
								route_id : routeId,
								is_grn : isGrn,
								ajax : 'true'

							},
							function(data) {

								//alert(data);
								if (data == "") {
									alert("No records found !!");
									$('#loader').hide();

								}
								var i = 0;
								$('#loader').hide();
								google.charts.load('current', {
									'packages' : [ 'corechart', 'bar' ]
								});
								google.charts.setOnLoadCallback(drawStuff);

								function drawStuff() {

									// alert("Inside DrawStuff");

									var chartDiv = document
											.getElementById('chart_div');
									document.getElementById("chart_div").style.border = "thin dotted red";

									var PiechartDiv = document
											.getElementById('PieChart_div');
									document.getElementById("PieChart_div").style.border = "thin dotted red";

									var dataTable = new google.visualization.DataTable();
									dataTable.addColumn('string', 'Date'); // Implicit domain column.
									dataTable.addColumn('number',
											'Requested Value'); // Implicit data column.
									dataTable.addColumn('number',
											'Approved Value');

									var piedataTable = new google.visualization.DataTable();
									piedataTable.addColumn('string', 'Date'); // Implicit domain column.
									piedataTable.addColumn('number',
											'Approved Value');

									$.each(data, function(key, report) {

										// alert("In Data")
										var reqValue = report.totalAmt;
										var aprValue = report.aprGrandTotal;

										//var total=report.taxableAmt+report.sgstSum+report.cgstSum;
										//alert("total ==="+total);
										//alert("base Value "+baseValue);

										var date = report.grngvnDate;
										//alert("frNAme "+frName);
										//var date= item.billDate+'\nTax : ' + item.tax_per + '%';

										dataTable.addRows([

										[ date, reqValue, aprValue ],

										]);

										piedataTable.addRows([

										[ date, aprValue ],

										]);
									}) // end of  $.each(data,function(key, report) {-- function

									// Instantiate and draw the chart.

									var materialOptions = {

										width : 500,
										chart : {
											title : 'Date wise Grn Gvn Report',
											subtitle : 'Requested and Approved Value',

										},
										series : {
											0 : {
												axis : 'distance'
											}, // Bind series 0 to an axis named 'distance'.
											1 : {
												axis : 'brightness'
											}
										// Bind series 1 to an axis named 'brightness'.
										},
										axes : {
											y : {
												distance : {
													label : 'Req Value'
												}, // Left y-axis.
												brightness : {
													side : 'right',
													label : 'Apr  Value'
												}
											// Right y-axis.
											}
										}
									};

									function drawMaterialChart() {
										var materialChart = new google.charts.Bar(
												chartDiv);

										// alert("mater chart "+materialChart);
										materialChart
												.draw(
														dataTable,
														google.charts.Bar
																.convertOptions(materialOptions));
										// button.innerText = 'Change to Classic';
										// button.onclick = drawClassicChart;
									}

									var chart = new google.visualization.ColumnChart(
											document
													.getElementById('chart_div'));

									var Piechart = new google.visualization.PieChart(
											document
													.getElementById('PieChart_div'));
									chart.draw(dataTable, {
										title : 'Date wise Grn Gvn Report'
									});

									Piechart.draw(piedataTable, {
										title : 'Date wise Grn Gvn Report',
										is3D : true
									});
									// drawMaterialChart();
								}
								;

							});

		}

		function genPdf() {
			var isGrn = $("#isGrn").val();	
			var selectedFr = $("#selectFr").val();
			var from_date = $("#fromDate").val();
			var to_date = $("#toDate").val();
			var apprvBy = $("#aprvBy").val();			

			window
					.open('${pageContext.request.contextPath}/pdfForReport?url=pdf/showPndItemGrnGvnReportPdf/'
							+ from_date
							+ '/'
							+ to_date
							+ '/'
							+ selectedFr
							+ '/' + apprvBy + '/' + isGrn + '/');

		}
		function exportToExcel() {

			window.open("${pageContext.request.contextPath}/exportToExcel");
			document.getElementById("expExcel").disabled = true;
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