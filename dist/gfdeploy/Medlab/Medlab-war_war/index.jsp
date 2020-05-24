<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Medlab</title>
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet">
  <link href="vendor/simple-line-icons/css/simple-line-icons.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">
  <link href="css/landing-page.min.css" rel="stylesheet">
  <link rel="icon" href="Images/icon.jpg">
</head>

<body>

<style>
    nav {
    background-color: #D6E6F2;
    }

    a {
    text-decoration: none;
    color: #303481;
    }

    .hola {
    text-align: center;
    background-color: white;
    width: 100%;
    height: 370px;
    }

    .all_rights_reserved { 
    width: 100%;
    text-align:center;
    color: #303481;
    }

    .signup-brand {
    position:absolute;
    top: 6%;
    left: 90%;
    }

    .login-brand {
    position:absolute;
    top: 6%;
    left: 82%;
} 
</style>
  <nav>
    <div class="container">
      <a class="navbar-brand" href="#"><img src="Images/logo.png" alt="Logo" height="100" width="150"></a>
      <a class="login-brand" href="login.jsp">Sign In</a>
      <a class="signup-brand" href="signup.jsp">Sign Up</a>
    </div>
  </nav>
  <section class="showcase">
    <div class="container-fluid p-0">
      <div class="row no-gutters">
        <div class="col-lg-6 order-lg-2 text-white showcase-img"></div>
        <div class="col-lg-6 order-lg-1 my-auto showcase-text">
          <h2>Share knowledge and delve into the best discussions</h2>
          <p class="lead mb-0">Join in the most interesting conversations on various topics and give your expert opinion</p>
        </div>
      </div>
      <div class="row no-gutters">
        <div class="col-lg-6 text-white showcase-img"></div>
        <div class="col-lg-6 my-auto showcase-text">
          <h2>Challenge your mind and solve several clinical cases</h2>
          <p class="lead mb-0">Help patients to get the best diagnosis and other doctors to solve their more complex clinical cases</p>
        </div>
      </div>
      <div class="row no-gutters">
        <div class="col-lg-6 order-lg-2 text-white showcase-img"></div>
        <div class="col-lg-6 order-lg-1 my-auto showcase-text">
          <h2>Publish your medical articles</h2>
          <p class="lead mb-0">And stay tuned of new studies that arise daily!</p>
        </div>
      </div>
    </div>
  </section>
  <!-- Footer -->
  <footer class="footer bg-light">
    <div class="container">
      <div class="row">
        <div class="all_rights_reserved">
            <p>&copy; Medlab 2020. All Rights Reserved.</p>
	</div>
        <div class="col-lg-6 h-100 text-center text-lg-right my-auto">
        </div>
      </div>
    </div>
  </footer>
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>