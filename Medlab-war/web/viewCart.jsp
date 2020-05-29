<%@page import="entities.Book"%>
<%@page import="ejbs.BookFacade"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entities.Cartitems"%>
<%@page import="entities.Cart"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <title>Medlab | Cart</title>
        <link rel="stylesheet" type="text/css" href="css/menu.css">
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <script src='https://kit.fontawesome.com/a076d05399.js'></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link type="text/css" rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
        <link rel="icon" href="Images/icon.jpg">

        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    </head>
    <body>
        <jsp:include page="header.jsp" /> <br><br>
        <h2>My book cart</h2>
        <div class="container mb-4">        
            <div class="row">
                <div class="col-12">
                    <div class="table-responsive">
                        <table class="table table-striped" id="cart_table">
                            <%List<Cartitems> cart = (List<Cartitems>) session.getAttribute("Cart");
                              if (!cart.isEmpty()) {%>
                            <thead><br><br>
                            <tr><td>Title</td>
                            <td>Author</td>
                            <td class="text-right">Quantity</td>
                            <td class="text-right">Price</td>
                            </tr>
                            </thead>
                            <tbody>
                                <%  BookFacade books = (BookFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/BookFacade!ejbs.BookFacade");
                                    double total_price = 0.0;
                                    
                                    for (Cartitems c : cart) {
                                        Book book = books.find(c.getBookid());
                                        total_price += book.getPrice();
                                %> 
                                <tr>
                                    <th scope="col"><%=book.getTitle()%></th>
                                    <th scope="col"><%=book.getAuthor()%></th>
                                    <th scope="col" class="text-center"><%=c.getQuantity()%></th>
                                    <th scope="col" class="text-right"><%=book.getPrice()%>$</th>
                                    <th></th>
                                    <td class="text-right"><a href="FrontController?command=ModifyCartCommand&action=add&id=<%=book.getId()%>" class="btn btn-sm btn-danger"><i class="fa fa-plus"></i> </button>
                                    <a href="FrontController?command=ModifyCartCommand&action=decrease&id=<%=book.getId()%>" class="btn btn-sm btn-danger"><i class="fa fa-minus"></i> </button>
                                    <a href="FrontController?command=ModifyCartCommand&action=remove&id=<%=book.getId()%>" class="btn btn-sm btn-danger"><i class="fa fa-trash"></i> </button></td>
                                </tr>
                                
                                <%}%>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td><strong>Total</strong></td>
                                    <td class="text-right"><strong><%=total_price%></strong></td>
                                </tr>
                            </tbody>
                            
                        </table>
                    </div>
                </div>
                <div class="col mb-2">
                    <div class="row">
                        <div class="col-sm-12  col-md-6">
                        </div>
                        <div class="col-sm-12 col-md-6 text-right">
                            <a href="FrontController?command=BuyCartCommand" class="btn btn-lg btn-block btn-success text-uppercase" id="continue">Continue</a>
                        </div>
                    </div>
                </div>
            </div>
              <%}%>               
        </div>  
        <br><br><jsp:include page="footer.jsp" /> 
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
