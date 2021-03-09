<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Monginis</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

<!--base css styles-->
<link rel="stylesheet"
	href="resources/assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="resources/assets/font-awesome/css/font-awesome.min.css">

<!--page specific css styles-->

<!--flaty css styles-->
<link rel="stylesheet" href="resources/css/flaty.css">
<link rel="stylesheet" href="resources/css/flaty-responsive.css">

<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/monginislogo.png">

<style type="text/css">
.bg-overlay {
	/* background: linear-gradient(rgba(0,0,0,.4), rgba(0,0,0,.4)), url("${pageContext.request.contextPath}/resources/img/cake.jpg"); */
	background:
		url("${pageContext.request.contextPath}/resources/img/lgn_bg.jpg");
	background-repeat: no-repeat;
	background-size: cover;
	background-position: center center;
	color: #fff;
	height: auto;
	width: auto;
	padding-top: 0px;
}

.login_bx {
	background: #FFF;
	border-radius: 10px;
	color: #000;
	margin: 200px 0 0 0;
}

.login_left {
	padding: 40px 10px 30px 40px;
}

.login_right {
	width: 100%;
	border-radius: 0 10px 10px 0;
	padding: 45px 20px;
	background: #ec268f;
	min-height: 240px;
	text-align: center;
	color: #FFF;
}

.login_right img {
	width: 150px;
}

.login_head {
	color: #333;
	font-size: 22px;
	margin: 0 0 20px 0;
	text-align: center;
	font-weight: 400;
	font-family: 'Source Sans Pro', sans-serif;
}

.welcome {
	color: #FFF;
	font-size: 15px;
	font-weight: 600;
	font-family: 'Source Sans Pro', sans-serif;
	text-transform: uppercase;
	margin: 15px 0 5px 0;
}

.welcome_txt {
	color: #ffe3f2;
	font-size: 13px;
	line-height: 18px;
}
</style>

</head>
<body class="container bg-overlay">
	<!-- login-page -->



	<div class="row">
		<div class="col-md-2">&nbsp;</div>
		<div class="col-md-8">
			<div class="login_bx">
				<div class="row">
					<%
						if (session.getAttribute("changePassword") != null) {
					%>

					<p style="color: white;">Password Change Successfully</p>

					<%
						}

						session.removeAttribute("changePassword");
					%>

					<%
						if (session.getAttribute("changePasswordFail") != null) {
					%>

					<p style="color: white;">Password Not Changed</p>

					<%
						}

						session.removeAttribute("changePasswordFail");
					%>
					<c:if test="${not empty loginResponseMessage}">
						<!-- here would be a message with a result of processing -->
						<div style="color: white;">${loginResponseMessage}</div>

					</c:if>

					<div class="col-md-6">
						<div class="login_left">
							<form id="form-forgot"
								action="${pageContext.request.contextPath}/loginProcess"
								method="post">
								<h3 class="login_head">Admin Panel Login</h3>

								<div class="form-group">
									<div class="controls">
										<input type="text" id="username" name="username"
											placeholder="Username" class="form-control" required />
									</div>
								</div>

								<div class="form-group">
									<div class="controls">
										<input type="password" name="userpassword"
											id="userpassword" placeholder="Password" class="form-control"
											required />
									</div>
								</div>
								<div class="form-group">
									<div class="controls">
										<button type="submit" class="btn btn-primary form-control">Login</button>
									</div>
								</div>

								<p class="clearfix">
									<a href="${pageContext.request.contextPath}/forgetPwd"
										class="goto-login pull-left">Forgot Password</a>
								</p>
							</form>
						</div>
					</div>
					<div class="col-md-6">
						<div class="login_right">
							<img
								src="${pageContext.request.contextPath}/resources/img/monginislogo.png">
							<h2 class="welcome">Welcome to Monginis</h2>
							<p class="welcome_txt">Lets make Monginis a part of
								everybody’s celebration!!</p>

						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-2">&nbsp;</div>
	</div>


	<!-- BEGIN Main Content -->
	<div class="login-wrapper">

		<!-- BEGIN Forgot Password Form -->

		<!-- END Forgot Password Form -->

		<%-- 
		<!-- BEGIN Login Form -->
		<form id="form-login" action="loginProcess" method="post">
			<h3>Forget Password</h3>
		
		


			<hr />
			<div class="form-group">
				<div class="controls">
					<input type="text" placeholder="Username" class="	form-control"
						path="username" name="username" id="username" required/>

				</div>
			</div>
			<!-- <div class="form-group">
				<div class="controls">
					<input type="password" placeholder="Password" class="form-control"
						path="userpassword" name="userpassword" id="userpassword"  required/>
				</div>
			</div> -->
			<!-- <div class="form-group">
				<div class="controls">
					<label class="checkbox"> <input type="checkbox"
						value="remember" /> Remember me
					</label>
				</div>
			</div> -->
			<div class="form-group">
				<div class="controls">
					<button type="submit" class="btn btn-primary form-control" onclick="getUserDate()">Submit
						</button>
				</div>
				
				<c:if test="${not empty loginResponseMessage}">
   <!-- here would be a message with a result of processing -->
    <div> ${loginResponseMessage} </div>
        	
</c:if>
				
				
			</div>
			<hr />
			<p class="clearfix">
				<a href="#" class="goto-forgot pull-left">Forgot Password?</a> <a
					href="#" class="goto-register pull-right">Sign up now</a>
			</p>
		</form>
		<!-- END Login Form -->
 --%>



	</div>
	<!-- END Main Content -->

	<!--basic scripts-->
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="resources/assets/jquery/jquery-2.0.3.min.js"><\/script>')
	</script>
	<script src="resources/assets/bootstrap/js/bootstrap.min.js"></script>

	<script type="text/javascript">
		function getUserInfo() {
			var username = $("#username").val();
			alert(username);
			$(document).ready(function() {
				$.getJSON('${getUserInfo}', {
					username : username,
					ajax : 'true'
				}, function(data) {
					//alert(JSON.stringify(data))

				});

			});
		}
	</script>

	<script type="text/javascript">
		function goToForm(form) {
			$('.login-wrapper > form:visible').fadeOut(500, function() {
				$('#form-' + form).fadeIn(500);
			});
		}
		$(function() {
			$('.goto-login').click(function() {
				goToForm('login');
			});
			$('.goto-forgot').click(function() {
				goToForm('forgot');
			});
			$('.goto-register').click(function() {
				goToForm('register');
			});
		});
	</script>
</body>
</html>






<%-- <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Monginis</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/img/monginislogo.png"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/vendor/animate/animate.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/vendor/css-hamburgers/hamburgers.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/vendor/animsition/css/animsition.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/vendor/select2/select2.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/vendor/daterangepicker/daterangepicker.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/util.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
<style type="text/css">
.bg-overlay {
    /* background: linear-gradient(rgba(0,0,0,.4), rgba(0,0,0,.4)), url("${pageContext.request.contextPath}/resources/img/cake.jpg"); */
   	background:  url("${pageContext.request.contextPath}/resources/img/lgn_bg.jpg");
    background-repeat: no-repeat;
    background-size: cover;
    background-position: center center;
    color: #fff;
    height:auto;
    width:auto;
    padding-top: 0px;
}
</style>
</head>
<body class="container bg-overlay">


	<div class="row">
		<div class="col-md-2">&nbsp;</div>
		<div class="col-md-8">
			<div class="login_bx">
				<div class="row">
					<div class="col-md-6">
						<div class="login_left">
						<img src="${pageContext.request.contextPath}/resources/img/mongi.png" style="margin: 30% 0px 0px 30%;"> 
						<!-- <h2 class="welcome">Welcome to ATS</h2>
						<p class="welcome_txt">	Lets make ATS a part of everybody’s celebration!!</p>  -->
							
						</div>
					</div>
					<div class="col-md-6">
						<div class="login_right">
							<form class="login100-form validate-form" id="form-login" action="loginProcess" method="post">
				
			     
					<h2 class="login_head">Admin Panel Login</h2>
					

					<div class="wrap-input100 validate-input" data-validate = "Valid username is required" >
						<input class="input100" type="text" id="username" name="username" placeholder="Username">
						<span class="focus-input100-1"></span>
						<span class="focus-input100-2"></span>
					</div>
                    <br>
					<div class="wrap-input100 rs1 validate-input" data-validate="Password is required" >
						<input class="input100" type="password" name="userpassword" id="userpassword" placeholder="Password"  >
						<span class="focus-input100-1"></span>
						<span class="focus-input100-2"></span>
					</div>

					<div class="container-login100-form-btn m-t-20" >
						<button class="login100-form-btn">
							Sign in
						</button>
					</div>

					<%
						if (session.getAttribute("changePassword") != null) {
					%>
					
						<p style="color: white;">Password Change Successfully</p>
					
					<%
						}

						session.removeAttribute("changePassword");
					%>

					<%
						if (session.getAttribute("changePasswordFail") != null) {
					%>
				
						<p style="color: white;">Password Not Changed</p>
					
					<%
						}

						session.removeAttribute("changePasswordFail");
					%>
					<c:if test="${not empty loginResponseMessage}">
   <!-- here would be a message with a result of processing -->
    <div style="color:white;"> ${loginResponseMessage} </div>
        	
</c:if>
<div class="form-group">
				<!-- <div class="controls">
					<label class="checkbox"> <input type="checkbox"
						value="remember" /> Remember me
					</label>
				</div> -->
			</div>
<div class="text-right">
					
						<span class="txt1" >
							<!-- Forgot -->
							<a href="${pageContext.request.contextPath}/forgetPwd"><span class="links" style="color: #fff;">
							Forgot Password</span></a>
						</span>

						<a href="#" class="txt2 hov1">
							<!-- Username / Password? -->
						</a>
					</div>

					<div class="text-center">
						<span class="txt1">
							<!-- Create an account? -->
						</span>

						<a href="#" class="txt2 hov1">
							<!-- Sign up -->
						</a>
					</div>
				</form>
						</div>
					</div>
					
				</div>
			</div>
		</div>
		<div class="col-md-2">&nbsp;</div>
	</div>	


	<!-- <div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-50">
				
			</div>
		</div>
	</div> -->

<!--===============================================================================================-->
	<script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="${pageContext.request.contextPath}/resources/vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
	<script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/popper.js"></script>
	<script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="${pageContext.request.contextPath}/resources/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
<!--===============================================================================================-->
	<script src="${pageContext.request.contextPath}/resources/vendor/countdowntime/countdowntime.js"></script>
<!--===============================================================================================-->
	<script src="${pageContext.request.contextPath}/resources/js/mains.js"></script>

</body>
</html>
 --%>