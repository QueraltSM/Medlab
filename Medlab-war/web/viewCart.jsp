<%@page import="entities.Book"%>
<%@page import="ejbs.BookFacade"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entities.Cartitems"%>
<%@page import="entities.Cart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <title>Medlab | Cart</title>
        <link rel="stylesheet" type="text/css" href="css/menu.css">
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <link rel="stylesheet" type="text/css" href="css/form.css">
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
                        <table class="table table-striped" id="cart_table"><br><br>
                            <%List<Cartitems> cart = (List<Cartitems>) session.getAttribute("Cart");
                              if (!cart.isEmpty()) {%>
                            <thead>
                                <tr>
                                    <th scope="col">Title</th>
                                    <th scope="col">Author</th>
                                    <th scope="col">Quantity</th>
                                    <th scope="col">Price</th>
                                    <th scope="col">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%  BookFacade books = (BookFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/BookFacade!ejbs.BookFacade");
                                    double total_price = 0.0;
                                    
                                    for (Cartitems c : cart) {
                                        Book book = books.find(c.getBookid());
                                        total_price += book.getPrice()*c.getQuantity();
                                %> 
                                <tr>
                                    <td><%=book.getTitle()%></td>
                                    <td><%=book.getAuthor()%></td>
                                    <td><%=c.getQuantity()%></td>
                                    <td><%=book.getPrice()%>$</td>
                                    <td><a href="FrontController?command=ModifyCartCommand&action=add&id=<%=book.getId()%>"><i class="fa fa-plus"></i> </button>
                                    <a href="FrontController?command=ModifyCartCommand&action=decrease&id=<%=book.getId()%>"><i class="fa fa-minus"></i> </button>
                                    <a href="FrontController?command=ModifyCartCommand&action=remove&id=<%=book.getId()%>"><i class="fa fa-trash"></i> </button></td>
                                </tr>
                                <%}%>
                                
                                
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td scope="col">Total = <%=total_price%></td>
                                    <td scope="col"><a href="FrontController?command=BuyCartCommand"  id="continue">Continue</a></td>
                                </tr>
                            </tbody>
                            
                            </table>
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
