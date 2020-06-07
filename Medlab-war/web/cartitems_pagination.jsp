<%@page import="entities.Cartitems"%>
<%@page import="entities.Loginstats"%>
<%@page import="java.util.Vector"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="javax.naming.InitialContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <title>Medlab | Cartitems Pagination</title>
        <link rel="stylesheet" type="text/css" href="css/menu.css">
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <link rel="stylesheet" type="text/css" href="css/form.css">
        <script src='https://kit.fontawesome.com/a076d05399.js'></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
        <link rel="icon" href="Images/icon.jpg">

        <link rel="icon" href="Images/icon.jpg"> 
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class ="header">
            <center><p class="header_title" id="pagination_title">CartitemsFacade</p>
                <div>
                    <label for ="page_number">Page: </label>
                    <select onchange='setNewPaginationSearch()' id="page_number" class="form-input">
                        <%
                            int max_page_number = Integer.parseInt(String.valueOf(request.getAttribute("max_page_number")));
                            int page_number = Integer.parseInt(String.valueOf(request.getAttribute("page_number")));
                            for (int i = 1; i <= max_page_number; i++) {
                                if (i == page_number) {%>
                        <option value="<%=i%>" selected><%=i%></option> 
                        <%} else {%>
                        <option value="<%=i%>"><%=i%></option>
                        <%}
                            }%>
                    </select>
                    <label for ="entity">Entity: </label>
                    <select onchange='setNewEntitySearch()' id="entity" class="form-input">
                        <option value="news">News</option> 
                        <option value="cases">Cases</option> 
                        <option value="discussions">Discussions</option> 
                        <option value="researches">Researches</option> 
                        <option value="comments">Comments</option> 
                        <option value="books">Books</option> 
                        <option value="carts">Cart</option> 
                        <option value="cartitems" selected>Cartitems</option> 
                        <option value="users">Users</option> 
                        <option value="specialities">Specialities</option> 
                    </select>
                </div>
            </center>
            <br><br>
        </div><br><br><br><br>
        <div class="wrapper">
            <div class="form-group" style="width:70%; margin: 0 auto; text-align:center;">
                <table style="width:70%;">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Cart ID</th> 
                            <th scope="col">Book ID</th> 
                            <th scope="col">Quantity</th> 
                        </tr>
                    </thead>
                    <tbody>
                        <% List<Cartitems> cartitems = (Vector) request.getAttribute("cartitems");
                            for (Cartitems entry : cartitems) {
                        %>
                        <tr>
                            <td><%=entry.getId()%></td>
                            <td><%=entry.getCartid().getId()%></td>
                            <td><%=entry.getBookid()%></td>
                            <td><%=entry.getQuantity()%></td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>   