<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Medlab</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="Images/icon.jpg">
        <link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
        <link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
        <link rel="stylesheet" type="text/css" href="vendor/css-hamburgers/hamburgers.min.css">
        <link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
        <link rel="stylesheet" type="text/css" href="css/util.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">
        <link rel="icon" href="Images/icon.jpg">
</head> 
<body>
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100 p-t-0 p-b-100">
				<form action="FrontController" class="login100-form validate-form">
					<span class="login100-form-title p-t-20 p-b-45" style="color:black">
						Welcome again
					</span>
					<div class="wrap-input100 validate-input m-b-10" data-validate = "Email is invalid">
						<input class="input100" type="email" name="email" id="email" placeholder="Email">
                                                <span class="focus-input100"></span>
						<span class="symbol-input100">
							<i class="fa fa-user"></i>
						</span>
					</div>
					<div class="wrap-input100 validate-input m-b-10" data-validate = "Password is invalid">
						<input class="input100" type="password" name="password" id="password" placeholder="Password" id="password">
						<span class="focus-input100"></span>
						<span class="symbol-input100">
							<i class="fa fa-lock"></i>
						</span>
					</div>
                                            <% if (request.getAttribute("login_error_email") != null) { %>
                                                <script>
                                                    document.getElementById("email").style = "background-color:red;";
                                                </script>   
                                            <% } %> 
                                            <% if (request.getAttribute("login_error_password") != null) { %>
                                                <script>
                                                    document.getElementById("password").style = "background-color:red;";
                                                </script>   
                                            <% } %>                                             
					<div class="container-login100-form-btn p-t-10">
						<button class="login100-form-btn">
							Login
						</button>
                                                <input name="command" type="hidden" value="LoginCommand">
					</div>
				</form>
			</div>
		</div>
	</div>
        <script src="vendor/jquery/jquery-3.2.1.min.js"></script>
        <script src="vendor/bootstrap/js/popper.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="vendor/select2/select2.min.js"></script>
        <script src="js/validate_user_credentials.js"></script>
    </body>
</html>