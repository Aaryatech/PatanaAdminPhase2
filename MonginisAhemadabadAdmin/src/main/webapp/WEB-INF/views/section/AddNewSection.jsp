<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
 
 <!---------------Script For Translate Special Instructions------->   
    <script type="text/javascript">
      // Load the Google Transliterate API
      google.load("elements", "1", {
            packages: "transliteration"
          });

      function onLoad() {
        var options = {
            sourceLanguage:
                google.elements.transliteration.LanguageCode.ENGLISH,
            destinationLanguage:
                [google.elements.transliteration.LanguageCode.MARATHI],
            shortcutKey: 'ctrl+g',
            transliterationEnabled: true
        };

        // Create an instance on TransliterationControl with the required
        // options.
        var control =
            new google.elements.transliteration.TransliterationControl(options);

        // Enable transliteration in the textbox with id
        // 'transliterateTextarea'.
        control.makeTransliteratable(['transliterateTextarea']);
        control.makeTransliteratable(['transliterateTextarea1']);
      }
      google.setOnLoadCallback(onLoad);
    </script>
 <!--------------------------------END------------------------------------>   
	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<body>
	<jsp:include page="/WEB-INF/views/include/logout.jsp"></jsp:include>
<c:url value="/GetAallFrIdName" var="GetAallFrIdName"></c:url>
<c:url value="/GetAallRouteIdName" var="GetAallRouteIdName"></c:url>
<c:url value="/getFrByRouteId" var="getFrByRouteId"></c:url>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
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
						<i class="fa fa-file-o"></i> Messages
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
								<i class="fa fa-bars"></i> Add New Section
							</h3>
							<div class="box-tool">
								<a href="${pageContext.request.contextPath}/showSectionList">Back to List</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>
							<!-- <div class="box-tool">
								<a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a> <a data-action="close" href="#"><i
									class="fa fa-times"></i></a>
							</div> -->
						</div>
						<div class="box-content">
							<form action="${pageContext.request.contextPath}/addNewSectionSubmit" class="form-horizontal" id="validation-form"
								 method="post" enctype="multipart/form-data">





								<input type="hidden" name="secId" id="secId"
									value="${sectionObj.sectionId }">
									<%
									Calendar cal=Calendar.getInstance();
									Date date=cal.getTime();
									pageContext.setAttribute("frdate", date);
									
									%>

								
								
								

								

								
								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Section Name :</label>
									<div class="col-sm-9 col-lg-10 controls">
										<input type="text" name="section_name" id="section_name" value="${sectionObj.sectionName}"
											placeholder="Enter Section Name" class="form-control" data-rule-required="true"  />
									</div>
								</div>

								
								
								<div class="form-group" id="frDiv"  >
											<label class="col-sm-3 col-lg-2 control-label">Select Menu Ids</label>
											<div class="col-sm-9 col-lg-10 controls">
												<select data-placeholder="Select Menu Ids" name="section_mid"
													class="form-control chosen"  id="section_mid" multiple="multiple"
													data-rule-required="true">
													
													
													<c:forEach items="${frConfigList}" var="frConfig">
													<c:forEach items="${menuIds}" var="menu">
													<c:set var="selct" value="0" ></c:set>
													<c:if test="${frConfig.menuId==menu}">
													<c:set var="selct" value="1" ></c:set>
													</c:if>
													</c:forEach>
													<c:choose>
													<c:when test="${selct==1}">
													<option selected="selected" value="${frConfig.menuId}">${frConfig.menuTitle}</option>
													</c:when>
													<c:otherwise>
													<option  value="${frConfig.menuId}">${frConfig.menuTitle}</option>
													</c:otherwise>
													</c:choose>
													
													
													
													
													
													</c:forEach>
													
												</select>
												
											</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Type :</label>
									<div class="col-sm-9 col-lg-10 controls">
										<input type="text" name="section_type" id="section_type" value="${sectionObj.secType}"
											placeholder="Section Type" class="form-control" data-rule-required="true"  />
									</div>
								</div>


								<div class="form-group">
									<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2">
										<button type="submit" class="btn btn-primary">
											<i class="fa fa-check"></i> Submit
										</button>
										<!--<button type="button" class="btn">Cancel</button>-->
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
<!-- 
		</div>
	</div>

 -->










<!-- 
	</form>
	</div>
	</div>
	</div>



 -->



	<!-- END Main Content -->
<script type="text/javascript">
/* function routeSelected(){
	var routeId=$("#routes").val();
	//alert(routeId)
	html=null;
	$('#franchise').html('');
	$("#franchise").trigger("chosen:updated");
	$.getJSON('${getFrByRouteId}',

			{
				routeId:JSON.stringify(routeId),
				ajax : 'true'

			},
			function(data) {
				//alert(JSON.stringify(data))
				if(data.length<=0){
					alert("No Record Found!!!!")
				}else{
					
					var len = data.length;
					for (var i = 0; i < len; i++) {
						 html += '<option class="active-result" selected value="' +data[i].frId+ '">'
								+data[i].frName + data[i].frRouteId
								+ '</option>'; 
							//	alert(data[i].frName)
					}
					
					
					$('#franchise').html(html);
					$("#franchise").trigger("chosen:updated"); 
					
				}
			});
} */
	
	</script>
	<!--
<script type="text/javascript">
function changeoptionsRadios1(val) {
	//alert(document.getElementById("optionsRadios1").value)
	//alert(val)
	var opt=val;
	if(opt==1){
		document.getElementById("routeDiv").style.display = "none";
		//document.getElementById("frDiv").style.display = "block";
		//$('#franchise').html('');
		//$("#franchise").trigger("chosen:updated");
		var	html=null;
	 	$.getJSON('${GetAallFrIdName}',

				{
					ajax : 'true'

				}, function(data) {
					//alert(JSON.stringify(data))
					
					// var html = '<option value="" selected >Select Sub-Category</option>';

					var len = data.length;
					for (var i = 0; i < len; i++) {
						 html += '<option class="active-result"  value="' +data[i].frId+ '">'
								+data[i].frName
								+ '</option>'; 
							//	alert(data[i].frName)
					}
					
					
					$('#franchise').html(html);
					$("#franchise").trigger("chosen:updated"); 
					
				
					
				}); 
	}else if(opt==0){
		document.getElementById("routeDiv").style.display = "block";
		//document.getElementById("frDiv").style.display = "none";
		//$('#routes').html('');
		//$("#routes").trigger("chosen:updated");
		$('#franchise').html('');
		$("#franchise").trigger("chosen:updated"); 
	var	html=null;
		$.getJSON('${GetAallRouteIdName}',
				
				{
					ajax : 'true'

				}, function(data) {
					//alert(JSON.stringify(data))
					
					// var html = '<option value="" selected >Select Sub-Category</option>';

					var len = data.length;
					for (var i = 0; i < len; i++) {
						 html += '<option class="active-result"  value="' +data[i].routeId+ '">'
								+data[i].routeName
								+ '</option>'; 
							//	alert(data[i].frName)
					}
					
					
					$('#routes').html(html);
					$("#routes").trigger("chosen:updated"); 
					
				
					
				});  
	}
}


</script> -->


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
</body>
</html>
