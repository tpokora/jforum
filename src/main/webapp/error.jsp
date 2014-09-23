<%-- 
    Document   : index
    Created on : Mar 10, 2014, 11:16:18 AM
    Author     : denat
--%>

<%@page import="com.forum.Errorer"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<jsp:useBean id="user" scope="session" class="com.forum.beans.User" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forum</title>
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <link rel="stylesheet" type="text/css" href="css/errorStyle.css" />
        <link rel="Shortcut icon" href="images/favicon.ico" />
    </head>
    <!-- loginBox -->
        <div class="loginBox">
            <form method="POST" action="login.jsp">
                <c:choose>
                    <c:when test="${user.logged}">
                        Hello, <a href="myprofile.jsp">${user.login}!</a>
                        |
                        <a href="login.jsp">Logout</a>
                        
                    </c:when>
                    <c:otherwise>
                        Login: <input type="text" name="login" size="12"/>
                        Password: <input type="password" name="password" size="12" />
                        <input type="checkbox" name="logged" value="true" checked="true" hidden="true" />
                        <input type="submit" value="Login" /> 
                        <a href="register.jsp">Register</a>
                    </c:otherwise>
                </c:choose>
                        
            </form>
		</div>
    <body>
        <!-- wrapper -->
        <div class="wrapper">
            <div class="wrapperContent">
                <!-- header -->
                <div class="header">
                </div>  
                <!-- content -->
                <div class="content">
                    <div class="error">
                        <h2>Error: </h2>
                        <%=  session.getAttribute("error") %>
                        
                    </div>
                </div>
                <!-- footer -->
                <div class="footer">
                    Designed by <br />
                    Tomasz Pokora
                </div>
            </div>
        </div>
    </body>
</html>