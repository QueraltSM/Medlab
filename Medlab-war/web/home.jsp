
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.Time"%>
<%@page import="java.time.Instant"%>
<%@page import="java.text.DateFormat"%>
<%@page import="ejbs.NewsVisitorCounter"%>
<%@page import="entities.News"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="javax.naming.InitialContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <title>Medlab</title>
        <link rel="stylesheet" type="text/css" href="css/menu.css">
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <script src='https://kit.fontawesome.com/a076d05399.js'></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link type="text/css" rel="stylesheet" href="css/style.css"/>
        <link rel="icon" href="Images/icon.jpg"> 
    </head>
    <body onload='loadLastSortSelection("news")'>
        <jsp:include page="header.jsp" />   
        <div class="section">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="section-title" style="padding-bottom: 80px;">
                            <% if (request.getAttribute("error") == null) { %>
                            <h2 id="title_news">All news</h2>
                            <% } %>
                            <div class="pull-right">
                                <select onchange='sortList("News", "news")' id="sort_type_news" class="select_speciality">
                                    <option value="recent">Sort by most recent</option>
                                    <option value="viewed">Sort by most viewed</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <%
                        int count = 2;
                        int i = 0;
                        List<News> all_news = (List<News>) request.getAttribute("all_news");
                        if (request.getParameter("sort") != null && request.getParameter("sort").equals("viewed")) {
                            all_news = (List<News>) request.getAttribute("all_sorted_news");
                        } else if (request.getParameter("command").equals("SearchNewsCommand")) {
                            all_news = (List<News>) request.getAttribute("all_matched_news");
                        }
                        if (!all_news.isEmpty()) {
                            for (News news : all_news) {
                    %>
                    <div class="col-md-4">
                        <div class="post">
                            <a class="post-img"></a>
                            <div class="post-body">
                                <div class="post-meta">
                                    <a class="post-category cat"><% out.println(news.getSpeciality().getType().toString().replaceAll("_", " "));%></a>
                                    <%  Date d = new Date(news.getDate().getTime());
                                        DateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                    %>
                                    <span class="post-date"><br><% out.println(f.format(d));%>
                                        <%NewsVisitorCounter counter = (NewsVisitorCounter) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/NewsVisitorCounter!ejbs.NewsVisitorCounter");
                                        %><%=counter.getVisitsCount(news)%> <i class="fa fa-eye" aria-hidden="true"></i></span>
                                </div>
                                <h3 class="post-title"><a href="FrontController?command=NewsDetailsCommand&type=news&id=<% out.println(news.getId());%>"><% out.println(news.getTitle());%></a></h3>	
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
