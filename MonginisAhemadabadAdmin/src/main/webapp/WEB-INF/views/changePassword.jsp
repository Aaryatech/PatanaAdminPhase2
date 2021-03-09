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

<link rel="shortcut icon" href="resources/img/favicon.png">

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

.login_bx{background: #FFF; border-radius:10px; color:#000; margin: 200px 0 0 0;}
.login_left{padding:40px 10px 30px 40px;}
.login_right{width: 100%; border-radius:0 10px 10px 0; padding:45px 20px; background: #ec268f; min-height: 240px;
text-align: center; color: #FFF;} 
.login_right img{width: 150px;}
.login_head{color: #333; font-size: 22px; margin: 0 0 20px 0; text-align: center; font-weight: 400; font-family: 'Source Sans Pro', sans-serif;}
.welcome{color: #FFF; font-size: 15px; font-weight: 600; font-family: 'Source Sans Pro', sans-serif; 
text-transform: uppercase; margin: 15px 0 5px 0;}
.welcome_txt{color: #ffe3f2; font-size: 13px; line-height: 18px;}

</style>

</head>
<body class="container bg-overlay"><!-- login-page -->



	<div class="row">
		<div class="col-md-2">&nbsp;</div>
		<div class="col-md-8">
			<div class="login_bx">
				<div class="row">
				<h3>${msg}</h3>
					<div class="col-md-6">
						<div class="login_left">
							<form id="form-forgot" action="${pageContext.request.contextPath}/changeToNewPassword" method="post">
			<h3 class="login_head">Change Password</h3>
			
			<div class="form-group">
				<div class="controls">
				<input type="hidden" id="userId" name="userId" value="${userId}">
					<input type="password" placeholder="Enter New Password" class="form-control" id="newPass" name="newPass" required/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="controls">
					<input type="password" placeholder="Confirfm New Password" class="form-control" id="confrmPass" name="confrmPass" required/>
				</div>
			</div>
			<div class="form-group">
				<div class="controls">
					<button type="submit" class="btn btn-primary form-control" id="updtPass">Change Password</button>
				</div>
			</div>
			
			<p class="clearfix">
				<a href="${pageContext.request.contextPath}/login" class="goto-login pull-left">Back to Login</a>
			</p>
		</form>
						</div>
					</div>
					<div class="col-md-6">
						<div class="login_right">
						<img src="${pageContext.request.contextPath}/resources/img/monginislogo.png">
						<h2 class="welcome">Welcome to Monginis</h2>
						<p class="welcome_txt">	Lets make Monginis a part of everybody’s celebration!!</p>
							
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-2">&nbsp;</div>
	</div>
	<!-- END Main Content -->

	<!--basic scripts-->
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
	<script>window.jQuery || document.write('<script src="resources/assets/jquery/jquery-2.0.3.min.js"><\/script>')</script>
	<script src="resources/assets/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript">

$("#confrmPass").keyup(function(){
	var newPass = $("#newPass").val();
	var conrfmPass = $("#confrmPass").val();
	
	if(newPass!=conrfmPass){
		document. getElementById("updtPass"). disabled = true;
	}else{
		document. getElementById("updtPass"). disabled = false;
	}
	
	});
</script>
</body>
</html>


<%-- <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Dashboard - Admin</title>
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

<link rel="shortcut icon" href="resources/img/favicon.png">

</head>
<body class="login-page">

	<!-- BEGIN Main Content -->
	<div class="login-wrapper">
	
	<!-- BEGIN Forgot Password Form -->
		<form id="form-forgot" action="${pageContext.request.contextPath}/changeToNewPassword" method="post">
			<h3>Change Password</h3>
			<hr />
			<div class="form-group">
				<div class="controls">
					<input type="password" placeholder="Enter New Password" class="form-control" id="newPass" name="newPass" required/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="controls">
					<input type="password" placeholder="Confirfm New Password" class="form-control" id="confrmPass" name="confrmPass" required/>
				</div>
			</div>
			<input type="hidden" id="userId" name="userId" value="${userId}">
			<div class="form-group">
				<div class="controls">
					<button type="submit" class="btn btn-primary form-control" id="sendOTP">Change Password</button>
				</div>
			</div>
			<hr />
			<!-- <p class="clearfix">
				<a href="#" class="goto-login pull-left" onclick="reGenOtp()">Re Generate OTP</a>
			</p> -->
		</form>
		<!-- END Forgot Password Form -->
		
	</div>
	<!-- END Main Content -->

	<!--basic scripts-->
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
	<script>window.jQuery || document.write('<script src="resources/assets/jquery/jquery-2.0.3.min.js"><\/script>')</script>
	<script src="resources/assets/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript">

$("#confrmPass").keyup(function(){
	var newPass = $("#newPass").val();
	var conrfmPass = $("#confrmPass").val();
	
	if(newPass!=conrfmPass){
		document. getElementById("sendOTP"). disabled = true;
	}else{
		document. getElementById("sendOTP"). disabled = false;
	}
	
	});





</script>

</body>
</html>
 --%>