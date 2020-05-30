<%@page import="entities.Users"%>
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
        <title>Medlab | Login Statistics</title>
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
            <center><p class="header_title">Login Statistics</p></center>
        </div><br><br><br>
        <div class="wrapper">
            <div class="form-group" style="width:70%; margin: 0 auto; text-align:center;">
                <table style="width:70%;">
                    <thead>
                        <tr>
                            <th scope="col">Date</th>
                            <th scope="col">User</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% List<Loginstats> log = (Vector) request.getAttribute("LoginStats");
                            for (Loginstats entry : log) {
                                Date d = new Date(entry.getDate().getTime());
                                DateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        %>
                        <tr>
                            <td><%=f.format(d)%></td>
                            <td><%=entry.getUsers().getFullname()%></td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
        </div>  
    </body>
</html>   