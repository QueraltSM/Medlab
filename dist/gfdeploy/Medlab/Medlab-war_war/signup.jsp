<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Medlab</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="Images/icon.jpg">
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
						Welcome
					</span>
                                        <div class="wrap-input100 validate-input m-b-10" data-validate = "Medical license number is invalid">
						<input class="input100" type="text" id="license_number" name="license_number" placeholder="Medical License Number" id="medical_license_number">
						<span class="focus-input100"></span>
						<span class="symbol-input100">
							<i class="fa fa-address-card"></i>
						</span>
					</div>
                                    	<div class="wrap-input100 validate-input m-b-10" data-validate = "Full name is invalid">
						<input class="input100" type="text" id="fullname" name="fullname" placeholder="Full name" id="fullname" 
                                                       title="Full name must contain between 3 and 40 characters long.">
						<span class="focus-input100"></span>
						<span class="symbol-input100">
							<i class="fa fa-user-md"></i>
						</span>
					</div>
					<div class="wrap-input100 validate-input m-b-10" data-validate = "Email is invalid">
						<input class="input100" type="email" id="email" name="email" placeholder="Email" id="email">
						<span class="focus-input100"></span>
						<span class="symbol-input100">
							<i class="fa fa-envelope"></i>
						</span>
					</div>
					<div class="wrap-input100 validate-input m-b-10" data-validate = "Password is invalid">
						<input class="input100" type="password" id="password" 
                                                       name="password" placeholder="Password" id="password" title="Password must contain between 8 and 40 characters long, at least one digit, one lower case character and one upper case character.">
						<span class="focus-input100"></span>
						<span class="symbol-input100">
						<i class="fa fa-lock"></i>
						</span>
					</div>
                                            <% if (request.getAttribute("signup_error_email") != null) { %>
                                                <script>
                                                    document.getElementById("email").style = "background-color:red;";
                                                </script>   
                                            <% } %> 
                                            <% if (request.getAttribute("signup_error_license_number") != null) { %>
                                                <script>
                                                    document.getElementById("license_number").style = "background-color:red;";
                                                </script>   
                                            <% } %>  
                                    <div class="container-login100-form-btn p-t-10">
                                        <button class="login100-form-btn">
                                            Create account
                                        </button>
                                        <input name="command" type="hidden" value="SignupCommand">
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
