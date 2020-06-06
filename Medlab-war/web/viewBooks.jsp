<%@page import="entities.Comment"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="entities.Book"%>
<%@page import="javax.naming.InitialContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <title>Medlab | Books</title>
        <link rel="stylesheet" type="text/css" href="css/menu.css">
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <script src='https://kit.fontawesome.com/a076d05399.js'></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link type="text/css" rel="stylesheet" href="css/style.css"/>
        <link rel="icon" href="Images/icon.jpg">
    </head>
    <body>
        <jsp:include page="header.jsp" /> 
        <header id="header">
            <div id="post-header" class="page-header">
                <%  session.setAttribute("old_url", request.getQueryString());
                    Book book = (Book) request.getAttribute("books");%>
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="post-meta">
                                <a class="post-category cat"><% out.println(book.getSpeciality().getType().toString().replaceAll("_", " "));%></a>
                                <%  Date d = new Date(book.getDate().getTime());
                                    DateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                %>
                                <span class="post-date"><% out.println(f.format(d));%></span>
                            </div>
                            <h1><% out.println(book.getTitle());%></h1>
                        </div>
                    </div>
                </div>
            </div>
        </header>
        <div class="section">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="main-post"  style="padding-bottom: 100px;">
                            <p class="case_description"><% out.println(book.getDescription());%></p><br><br><br>
                            <br><br><p class="case_title">Author</p>
                            <p class = "case_description"><% out.println(book.getAuthor());%></p>
                            <br><br><p class="case_title">Price</p>
                            <p class = "case_description"><% out.println(book.getPrice());%></p><br><br><br>
                            <p class="statistics">
                                <%=book.getViews()+1%> views<br><br><br>
                                <% if (session.getAttribute("usertype").equals("admin")) {%>
                                    <a href="FrontController?command=DeleteBooksCommand&id=<%=book.getId()%>"><i class="fas fa-trash"></i> Delete</a>   <a href="FrontController?command=BooksDetailsCommand&action=edit&id=<%=book.getId()%>"><i class="fas fa-edit"></i> Modify</a>
                                <%} else {%>
                                    <a href="FrontController?command=AddCartCommand&id=<%=book.getId()%>"><i class="fas fa-shopping-cart"></i> Add to cart</a>
                                <%}%>
                        </div>    
                    </div> 
                    <%List<Comment> comments = (List<Comment>) request.getAttribute("comments");%>						
                    <div class="section-row">
                        <div class="section-title">
                            <h2><% out.println(comments.size());%> Comments</h2>
                        </div>
                        <div class="post-comments">
                            <%
                                    for (Comment comment : comments) { %>
                            <div class="media">
                                <div class="media-body">
                                    <div class="media-heading">
                                        <h4><% out.println(comment.getAuthor().getFullname());%></h4>
                                        <%  d = new Date(comment.getDate().getTime());
                                            f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                        %>
                                        <span class="time"><% out.println(f.format(d));%></span>
                                    </div>
                                    <p><% out.println(comment.getMessage());%></p>

                                </div>
                            </div>
                            <%
                                }
                            %>
                        </div>
                    </div>
                    <div class="section-row" style="padding-top: 50px;">
                        <div class="section-title">
                            <h2>Leave a reply</h2>
                        </div>
                        <form action="FrontController" class="post-reply">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <textarea class="input" name="message" id="message" placeholder="Message" required></textarea>
                                    </div>
                                    <input name="command" type="hidden" value="AddCommentCommand">
                                    <input name="type" type="hidden" value="book">
                                    <input name="id_type" type="hidden" value=<% out.println(book.getId());%> />
                                    <button class="primary-button">Share</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>   
    <jsp:include page="footer.jsp" /> 
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/header_box.js"></script>		 
</body>
</html>