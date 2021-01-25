<%@page import="com.ats.adminpanel.model.franchisee.SubCategory"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page
	import="java.io.*, java.util.*, java.util.Enumeration, java.text.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>Franchisee Pending Bills</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!--base css styles-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/data-tables/bootstrap3/dataTables.bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap-fileupload/bootstrap-fileupload.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/chosen-bootstrap/chosen.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap-timepicker/compiled/timepicker.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/clockface/css/clockface.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap-datepicker/css/datepicker.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/daterangepicker.css" />

<!--page specific css styles-->

<!--flaty css styles-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/flaty.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/flaty-responsive.css">

<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/img/favicon.png">

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/common.js"></script>

<!--basic scripts-->

<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
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
<!-- http://forum.springsource.org/showthread.php?110258-dual-select-dropdown-lists -->
<!-- http://api.jquery.com/jQuery.getJSON/ -->

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/additional-methods.min.js"></script>
<jsp:include page="/WEB-INF/views/include/logout.jsp"></jsp:include>
<style type="text/css">
select {
    width: 180px;
    height: 30px;
}
</style>


</head>
<body>

	<c:url var="getPendingBills" value="/getPendingBills" />

	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>


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
						<i class="fa fa-file-o"></i> Franchisee Pending Bills
					</h1>
				</div>
			</div>
			<!-- END Page Title -->


			<!-- BEGIN Main Content -->
			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="col-md-12">
							<div class="box">
								<div class="box-title">
									<h3>
										<i class="fa fa-bars"></i> Pending Bills
									</h3>
									<div class="box-tool">
										<a href="${pageContext.request.contextPath}/configureFranchiseesList">Back to
											List</a> <a data-action="collapse" href="#"><i
											class="fa fa-chevron-up"></i></a>
									</div>
								</div>


								<c:set var="allFranchiseeAndMenuList"
									value="${allFranchiseeAndMenuList}" />
								<div class="box-content">
									<form action="${pageContext.request.contextPath}/insertPendingBills" class="form-horizontal"
										id="validation-form" method="post">
                                      <div class="form-group">
											<label class="col-sm-3 col-lg-2 control-label">Franchisee</label>
											<div class="col-sm-9 col-lg-10 controls">
												<select  name="fr_id"
													class="form-control chosen" tabindex="-1" id="fr_id"
													data-rule-required="true">
													<optgroup label="All Franchisee">														
															<c:forEach
															items="${allFranchiseeAndMenuList.getAllFranchisee()}"
															var="franchiseeList">
															<option value="${franchiseeList.frId}">${franchiseeList.frName}</option>

														</c:forEach>
													</optgroup>

												</select>
											</div>
										<br> <br>
											<div
												class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2">
												<input type="button" class="btn btn-primary"
													value="Search" onclick="getData()">
												<!-- <button type="button" class="btn">Cancel</button> -->
											</div>
										</div>
										<div align="center" id="loader" style="display: none">

											<span>
												<h4>
													<font color="#343690">Loading</font>
												</h4>
											</span> <span class="l-1"></span> <span class="l-2"></span> <span
												class="l-3"></span> <span class="l-4"></span> <span
												class="l-5"></span> <span class="l-6"></span>
										</div>

										<div class="box">
									<div class="box-title">
										<h3>
											<i class="fa fa-table"></i> Pending Bill List
										</h3>
										<div class="box-tool">
											<a data-action="collapse" href="#"><i
												class="fa fa-chevron-up"></i></a>
											<!--<a data-action="close" href="#"><i class="fa fa-times"></i></a>-->
										</div>
									</div>

									<div class="box-content">

										<div class="clearfix"></div>
										<div id="table-scroll" class="table-scroll">
											<div class="table-wrap col-md-12 ">
												<table id="table1" class="table table-advance" border="1" >
													<thead>
														<tr class="bgpink">
														<th class="col-sm-1"><input type="checkbox"
													onClick="selectBillNo(this)" /> All<br /></th>
															<th class="col-sm-1" align="center">Bill No.</th>
															<th class="col-md-1" align="center">Invoice No</th>
															<th class="col-md-1" align="center">Bill Date</th>
															<th class="col-md-1" align="center">Taxable Amt.</th>
															<th class="col-md-1" align="center">Total Tax</th>
															<th class="col-md-1" align="center">Total</th>
															<!-- <th class="col-md-1" align="center">Action</th> -->
														</tr>
													</thead>
													<tbody>
													</tbody>
												</table>
											</div>
											<div class="clearfix"></div>
											<br>
											<div class="table-wrap col-md-12">
												<table id="table2" class="table table-advance" border="1" >
													<thead>
														<tr class="bgpink">
														<th class="col-sm-1"><input type="checkbox"
													onClick="selectBillNo(this)" /> All<br /></th>
															<!-- <th class="col-sm-1" align="center">Sr. No.</th> -->
															<th class="col-md-1" align="center">CRN.No</th>
															<th class="col-md-1" align="center">Taxable Amt.</th>
															<th class="col-md-1" align="center">Total Tax</th>
															<th class="col-md-1" align="center">Total</th>
														</tr>
													</thead>
													<tbody>
													</tbody>
												</table>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 col-lg-2 control-label">Payment
											Option</label>
											<div class="col-sm-5 col-lg-4 controls">
												<select name="paymentmode" id="paymentmode"
															class="form-control chosen" data-rule-required="true">													
													<option value="1">Cash</option>
													<option value="2">Card</option>
													<option value="3">E-Pay</option>
												</select>
											</div>
										</div>


										<div class="form-group">
											<label class="col-sm-3 col-lg-2 control-label" for="fr_name">Remark
											</label>
											<div class="col-sm-6 col-lg-8 controls">
												<input type="text" name="remark" id="remark"
													class="form-control"
													placeholder="Remark"/>
											</div>
										</div>

										<input type="submit" id="btn_submit" class="btn btn-primary" value="Submit" disabled="disabled"/>

									</div>
									</div>
									<input type="hidden" id="headTaxable" name="headTaxable">
									<input type="hidden" id="headTax" name="headTax">
									<input type="hidden" id="headTotal" name="headTotal">
									
									<input type="hidden" id="crnTaxable" name="crnTaxable">
									<input type="hidden" id="crnTax" name="crnTax">
									<input type="hidden" id="crnTotal" name="crnTotal">
									</form>

								</div>
							</div>
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
 <script
	src="${pageContext.request.contextPath}/resources/assets/bootstrap/js/bootstrap.min.js"></script> 

<script>
$('#select_all').click(function() {
    $('#items option').prop('selected', true);
    $('#items').chosen('destroy').val(["hola","mundo","cruel"]).chosen();
});
</script>

<script type="text/javascript">
		function getData() {		
			var isValid=validate();
			if(isValid==true){
			var frId = $("#fr_id").val();			

			$('#loader').show();			
			$
					.getJSON(
							'${getPendingBills}',
							{
								frId : frId,								
								ajax : 'true'
							},
							function(data) {
								$('#table1 td').remove();
								$('#table2 td').remove();
								$('#loader').hide();
								if (data == "") {
									alert("No Bill Found");
								}
								document.getElementById("btn_submit").disabled = false;
								$
										.each(
												data.billList,
												function(key, bill) {
													

													var tr = $('<tr></tr>');
													var action="<td><a href='${pageContext.request.contextPath}/revertOp/"+0+"'>0</td>";

													tr
													.append($(
															'<td class="col-sm-1"></td>')
															.html( 
																	"<input type='checkbox' class='chk_bill_header' name=chk"+bill.billNo+" id=chk"+bill.billNo+" value="+bill.billNo+" onclick='getValues()' />"));
													tr
													.append($(
															'<td class="col-sm-1" style="display: none;"></td>')
															.html("<input type='hidden' id='typeBill' name='typeBill' value='1' readonly='readonly'>"
																	));
				
													tr
															.append($(
																	'<td class="col-sm-1"></td>')
																	.html("<input type='hidden' id=billNo"+bill.billNo+" name=billNo"+bill.billNo+" value="+bill.billNo+" readonly='readonly'>"
																			+"&nbsp"+bill.billNo));

													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html("<input type='hidden' id=invoice"+bill.billNo+" name=invoice"+bill.billNo+" value="+bill.invoiceNo+" readonly='readonly'>"
																			+"&nbsp"+bill.invoiceNo));

													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html(
																			+"&nbsp"+bill.billDate));
													

													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right;"></td>')
																	.html("<input type='hidden' class='head-taxable' id=taxable"+bill.billNo+" name=taxable"+bill.billNo+" value="+bill.taxableAmt+" readonly='readonly'>"
																			+"&nbsp"+bill.taxableAmt));

													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right;"></td>')
																	.html("<input type='hidden' class='head-tax' id=tax"+bill.billNo+" name=tax"+bill.billNo+" value="+bill.totalTax+" readonly='readonly'>"
																			+"&nbsp"+bill.taxableAmt));

													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right;"></td>')
																	.html("<input type='hidden' class='head-grand' id=total"+bill.billNo+" name=total"+bill.billNo+" value="+bill.grandTotal+" readonly='readonly'>"
																			+"&nbsp"+bill.grandTotal));
													
												/* 	tr
													.append($(
															'<td></td>')
															.html(
																	action)); */

													
												
													$('#table1 tbody').append(
															tr);

												});	
								/* -------------------------------CRN--------------------------------- */
								$
										.each(
												data.crNoteList,
												function(key, credit) {

													var tr1 = $('<tr></tr>');
													tr1
															.append($(
																	'<td class="col-sm-1"></td>')
																	.html(
																			"<input type='checkbox' class='chk_crn' name=crnChk"+credit.crnId+" id=crnChk"+credit.crnId+" value="+credit.crnId+" onclick='getCrnValues()'/>"));

													/* tr1
															.append($(
																	'<td class="col-sm-1"></td>')
																	.html(
																			key + 1)); */

													tr1
															.append($(
																	'<td class="col-md-1" style="text-align:right;"></td>')
																	.html("<input type='hidden' id=crno"+credit.crnId+" name=crno"+credit.crnId+" value="+credit.crnNo+" readonly='readonly'>"
																			+"&nbsp"+credit.crnNo));
													tr1
													.append($(
															'<td class="col-sm-1" style="display: none;"></td>')
															.html("<input type='hidden' id='typeCrn' name='typeCrn' value='2' readonly='readonly'>"
																	));

													tr1
															.append($(
																	'<td class="col-md-1" style="text-align:right;"></td>')
																	.html("<input type='hidden' class='crn-taxable' id=crnTaxable"+credit.crnId+" name=crnTaxable"+credit.crnId+" value="+credit.crnTaxableAmt+" readonly='readonly'>"
																			+"&nbsp"+credit.crnTaxableAmt));

													tr1
															.append($(
																	'<td class="col-md-1" style="text-align:right;"></td>')
																	.html("<input type='hidden' class='crn-tax' id=crnTax"+credit.crnId+" name=crnTax"+credit.crnId+" value="+credit.crnTotalTax+" readonly='readonly'>"
																			+"&nbsp"+credit.crnTotalTax));

													tr1
															.append($(
																	'<td class="col-md-1" style="text-align:right;"></td>')
																	.html("<input type='hidden' class='crn-grand' id=crnTotal"+credit.crnId+" name=crnTotal"+credit.crnId+" value="+credit.crnGrandTotal+" readonly='readonly'>"
																			+"&nbsp"+credit.crnGrandTotal));

													$('#table2 tbody').append(
															tr1);

												});
								var tr0 = $('<tr></tr>');								
								tr0.append($('<td></td>').html('Total'));
								/* tr0.append($('<td></td>').html('')); */
								tr0.append($('<td></td>').html(''));
								tr0.append($('<td align="right"></td>').html('<input type="hidden" id="calTaxable" name="calTaxable" readonly="readonly"> <span  style="float: right;" id="showTaxable"></span>'));
								tr0.append($('<td align="right"></td>').html('<input type="hidden" id="calTax" name="calTax" readonly="readonly"> <span style="float: right;" id="showTax">'));
								tr0.append($('<td align="right"></td>').html('<input type="hidden" id="calGrandTotal" name="calGrandTotal" readonly="readonly"> <span style="float: right;" id="showTtl"></span>'));
								$('#table2 tbody').append(tr0);

							});
		}

	}
		
</script>
<script type="text/javascript">
function getValues(){
	
	var ttlTaxable = 0;
	var ttlTax = 0;
	var ttlGrandTotal = 0;
	
	$(".chk_bill_header")
	.each(
			function(counter) {

				if (document
						.getElementsByClassName("chk_bill_header")[counter].checked==true) {

					var taxable = document
							.getElementsByClassName("head-taxable")[counter].value;					
					var taxAmt = document
						.getElementsByClassName("head-tax")[counter].value;
					var grandTotal = document
						.getElementsByClassName("head-grand")[counter].value;
					
					ttlTaxable = ttlTaxable+parseFloat(taxable);
					ttlTax = ttlTax+parseFloat(taxAmt);
					ttlGrandTotal = ttlGrandTotal+parseFloat(grandTotal);
										
				}

			});
	
	document.getElementById("headTaxable").value=ttlTaxable;
	document.getElementById("headTax").value=ttlTax;
	document.getElementById("headTotal").value=ttlGrandTotal;
	
	setPendingBillVal();
}
</script>
<script type="text/javascript">
function getCrnValues(){
	
	var ttlCrnTaxable = 0;
	var ttlCrnTax = 0;
	var ttlCrnGrandTotal = 0;
	
	$(".chk_crn")
	.each(
			function(counter) {

				if (document
						.getElementsByClassName("chk_crn")[counter].checked==true) {

					var crnTaxable = document
							.getElementsByClassName("crn-taxable")[counter].value;
					var crnTaxAmt = document
						.getElementsByClassName("crn-tax")[counter].value;
					var crngrandTotal = document
						.getElementsByClassName("crn-grand")[counter].value;
					
					ttlCrnTaxable = ttlCrnTaxable+parseFloat(crnTaxable);
					ttlCrnTax = ttlCrnTax+parseFloat(crnTaxAmt);
					ttlCrnGrandTotal = ttlCrnGrandTotal+parseFloat(crngrandTotal);
										
				}

			});
	
	document.getElementById("crnTaxable").value=ttlCrnTaxable;
	document.getElementById("crnTax").value=ttlCrnTax;
	document.getElementById("crnTotal").value=ttlCrnGrandTotal;
	
	setPendingBillVal();
}
</script>

<script type="text/javascript">
		function setPendingBillVal(){			
			var finalTaxable=$("#headTaxable").val() - $("#crnTaxable").val();
			var finalTax=$("#headTax").val() - $("#crnTax").val();
			var finalTotal=$("#headTotal").val() - $("#crnTotal").val();
			
			if(finalTotal>=0){
				document.getElementById("calTaxable").value=00;
				document.getElementById("calTax").value=00;
				document.getElementById("calGrandTotal").value=finalTotal;
				
				document.getElementById("showTaxable").innerHTML=0;
				document.getElementById("showTax").innerHTML=0;
				document.getElementById("showTtl").innerHTML=finalTotal;
			}
		}
	</script>

	<script>
	function validate() {
		var selectedFr = $("#fr_id").val();

		var isValid = true;

		if ((selectedFr == "" || selectedFr == null)) {

			alert("Please Select Franchisee");
			isValid = false;

		}
		return isValid;
	}
	
	
	$(document).ready(function(){
	    $("#validation-form").submit(function(){
			if ($('input:checkbox').filter(':checked').length < 1){
	        alert("Check at least one record!");
			return false;
			}
	    });
	});
</script>

</body>
</html>