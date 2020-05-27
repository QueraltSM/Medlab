
<%@page import="entities.Discussions"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.Time"%>
<%@page import="java.time.Instant"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="javax.naming.InitialContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <title>Medlab | My discussions</title>
        <link rel="stylesheet" type="text/css" href="css/menu.css">
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <script src='https://kit.fontawesome.com/a076d05399.js'></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link type="text/css" rel="stylesheet" href="css/style.css"/>
        <link rel="icon" href="Images/icon.jpg"> 
    </head>
    <body onload='loadLastSortSelection("discussions")'>
        <jsp:include page="header.jsp" />   
        <div class="section">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="section-title" style="padding-bottom: 80px;">
                            <% if (request.getAttribute("error") == null) { %>
                            <h2 id="title_discussions">All discussions</h2>
                            <% } %>
                        </div>
                    </div>
                    <%
                        int count = 2;
                        int i = 0;
                        List<Discussions> all_discussions = (List<Discussions>) request.getAttribute("discussions");
                        if (!all_discussions.isEmpty()) {
                            for (Discussions discussions : all_discussions) {
                    %>
                    <div class="col-md-4">
                        <div class="post">
                            <a class="post-img"></a>
                            <div class="post-body">
                                <div class="post-meta">
                                    <a class="post-category cat"><% out.println(discussions.getSpeciality().getType().toString().replaceAll("_", " "));%></a>
                                    <%  Date d = new Date(discussions.getDate().getTime());
                                        DateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                    %>
                                    <span class="post-date"><br><% out.println(f.format(d));%>
                                        <%=discussions.getViews()%> <i class="fa fa-eye" aria-hidden="true"></i></span>
                                </div>
                                <h3 class="post-title"><a href="FrontController?command=DiscussionsDetailsCommand&type=discussions&id=<% out.println(discussions.getId());%>"><% out.println(discussions.getTitle());%></a></h3>	
                            </div>
                        </div>
                    </div>       
                    <%if (i == count) {
                            count += 3;%>
                    <div class="clearfix visible-md visible-lg"></div>
                    <%
                                }
                                i++;
                            }
                        }
                    %>
                </div>
            </div>
        </div>
        <jsp:include page="footer.jsp" /> 
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/header_box.js"></script>
    </body>
</html>
