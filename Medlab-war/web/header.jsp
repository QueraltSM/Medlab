<%@page import="ejbs.CartitemsFacade"%>
<%@page import="ejbs.CartFacade"%>
<%@page import="ejbs.UsersFacade"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="entities.Cart"%>
<%@page import="entities.Users"%>
<%@page import="entities.Cartitems"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Medlab</title>
        <link rel="stylesheet" type="text/css" href="css/menu.css">
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <script src='https://kit.fontawesome.com/a076d05399.js'></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link type="text/css" rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" type="text/css" href="css/form.css">
    </head>
    <body>
        <% if (session.getAttribute("logged") != null && session.getAttribute("logged").equals("true")) {%>
        <div class="menu__holder">
            <div class="user_info" style="padding-top: 27px;">
                <label class="user_name"><%=session.getAttribute("fullname")%> </label><form action="FrontController"><button type="submit"><i class="fa fa-sign-out"></i></button><input name="command" type="hidden" value="LogoutCommand"></form>
            </div>
            <div class="menu_options">
                <ul class="main_menu"> 
                    <li><a class="menu_a" onclick="showAll('News', 'news')"><i class="fas fa-brain"></i></a></li>
                    <li><a class="menu_a" onclick="showAll('Cases', 'cases')">CLINICAL CASES</a></li>
                    <li><a class="menu_a" onclick="showAll('Discussions', 'discussions')">DISCUSSIONS</a></li> 
                    <li><a class="menu_a" onclick="showAll('Researches', 'researches')">RESEARCHES</a></li>
                    <li><a class="menu_a" href="FrontController?command=ShowBooksCommand">BOOKS</a></li>
                    <li><a class="menu_a" onclick="javascript:show_hide_action_box();"><i id="action_box_icon" class="fa fa-plus"></i></a></li>
                    <li><a class="menu_a" onclick="javascript:show_hide_filter_box();"><i id="search_box_icon" class="fa fa-search"></i></a></li>
                            <% if (session.getAttribute("usertype").equals("doctor")) {%>
                    <li><a class="menu_a" href="FrontController?command=ShowCartCommand">
                            <label>
                                <%
                                    UsersFacade usersDB = (UsersFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/UsersFacade!ejbs.UsersFacade");
                                    CartFacade cartsDB = (CartFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CartFacade!ejbs.CartFacade");
                                    Users user = usersDB.find(Long.parseLong(String.valueOf(session.getAttribute("userID"))));
                                    List<Cart> user_cart = cartsDB.findCartByUserID(user);
                                    CartitemsFacade cartitemsDB = (CartitemsFacade) InitialContext.doLookup("java:global/Medlab/Medlab-ejb/CartitemsFacade!ejbs.CartitemsFacade");
                                    List<Cartitems> cart = cartitemsDB.findCartitemsByCartID(user_cart.get(0));
                                    if (cart.isEmpty()) {%>0
                                <%} else {%><%=cart.size()%>
                                <%}%></label><i class="fas fa-shopping-cart"></i></a></li>

                </ul>
                <% } %>
            </div> 

        </div>

        <% if (session.getAttribute("logged") == null || session.getAttribute("logged").equals("false")) { %>
        <div class="menu__holder" style ="padding-top:20px;">
            <div class="menu_options">
                <ul class="main_menu"> 
                    <li><a class="menu_a" href="login.jsp">LOGIN</a></li>
                    <li><a class="menu_a" href="signup.jsp">CREATE ACCOUNT</a></li> 
                </ul>
            </div>
        </div>
        <% } %>
        <form action ="FrontController">
            <div class="search_box" id="search_box">        
                <div class="form-group">
                    <select class="search-input" style="width:250px" id="speciality_search" name="speciality_search">
                        <option value="All specialities" selected>All specialities</</option>
                        <option value="Allergy_and_Inmunology">Allergy and Inmunology</option>
                        <option value="Anesthesiology">Anesthesiology</option>
                        <option value="Dermatology">Dermatology</option>
                        <option value="Diagnostic_Radiology">Diagnostic Radiology</option>
                        <option value="Emergency_Medicine">Emergency Medicine</option>
                        <option value="Family_Medicine">Family Medicine</option>
                        <option value="Internal_Medicine">Internal Medicine</option>
                        <option value="Medical_Genetics">Medical Genetics</option>
                        <option value="Neurology">Neurology</option>
                        <option value="Nuclear_Medicine">Nuclear Medicine</option>
                        <option value="Opthalmology">Opthalmology</option>
                        <option value="Pathology">Pathology</option>
                        <option value="Pediatrics">Pediatrics</option>
                        <option value="Preventive_Medicine">Preventive Medicine</option>
                        <option value="Psychiatry">Psychiatry</option>
                        <option value="Radiation_Oncology">Radiation Oncology</option>
                        <option value="Surgery">Surgery</option>
                        <option value="Urology">Urology</option>
                    </select>
                    <input class="search-input" type="text" name="keyword_search" id="keyword_search" placeholder="Enter keyword" />
                    <button class="search-btn"><i class="fa fa-search"></i></button>
                    <input name="command" id="search_command" type="hidden" value="<%out.print(session.getAttribute("search_command"));%>">
                </div>
            </div>
        </form>
        <div class="action_box" id="action_box">        
            <div class="search-field-wrap">
                <% if (session.getAttribute("usertype") != null && session.getAttribute("usertype").equals("doctor")) { %>
                <a href="FrontController?command=MyCasesCommand" style="padding-right: 10px;padding-left: 10px;"> My <i class="fas fa-stethoscope"></i></a>
                <a href="addCase.jsp" class="menu_action">Case <i class="fas fa-plus"></i></a>
                <a href="FrontController?command=MyDiscussionsCommand" class="menu_action"> My <i class="fas fa-comment-medical"></i></a>
                <a href="addDiscussion.jsp" class="menu_action">Discussion <i class="fas fa-plus"></i></a>
                <a href="FrontController?command=MyResearchesCommand" class="menu_action"> My <i class="fas fa-book-medical"></i></a>
                <a href="addResearch.jsp" style="border-left: 1px solid #000000;padding-left: 10px;padding-right: 10px;">Research <i class="fas fa-plus"></i></a>
                    <% } %>
                    <% if (session.getAttribute("usertype") != null && session.getAttribute("usertype").equals("admin")) { %>
                <a href="addNews.jsp" style="padding-right: 10px;padding-left: 10px;">Create news</a>
                <a href="addBook.jsp" class="menu_action" style="padding-right: 10px;padding-left: 10px;">Register book</a>
                <a href="FrontController?command=ShowLogCommand" class="menu_action" style="padding-right: 10px;padding-left: 10px;" > Log</a>
                <a href="FrontController?command=ShowLoginStatsCommand" class="menu_action"  style="padding-right: 10px;padding-left: 10px;"> Login stats</a>
                <a href="FrontController?command=ShowPaginationCommand&entity=news" class="menu_action" style="padding-right: 10px;padding-left: 10px;">Pagination</a>
                <% } %>
            </div>
        </div>
        <% if (request.getAttribute("error") != null) {%>
        <div class="error_box" id="error_box">        
            <label><%=request.getAttribute("error")%></label>
        </div>    
        <% }
            }%>
        <script src="js/header_box.js"></script>
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>