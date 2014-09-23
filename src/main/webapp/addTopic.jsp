<%-- 
    Document   : index
    Created on : Mar 10, 2014, 11:16:18 AM
    Author     : denat
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<jsp:useBean id="user" scope="session" class="com.forum.beans.User">
	<jsp:setProperty name="user" property="logged" value="${user.logged}" />
</jsp:useBean>
<jsp:useBean id="users" scope="request" class="com.forum.beans.UsersOnline">
</jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forum</title>
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <link rel="stylesheet" type="text/css" href="css/registerStyle.css" />
        <link rel="Shortcut icon" href="images/favicon.ico" />
    </head>
    <c:choose>
            <c:when test="${user.logged == null || user.logged == false}">
                <% 
                    response.setStatus(response.SC_MOVED_TEMPORARILY);
                    response.setHeader("Location", "/notlogged");
                %>
            </c:when>
        </c:choose>
    <body>
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
        <!-- wrapper -->
        <div class="wrapper">
            <div class="wrapperContent">
                <!-- header -->
                <div class="header">
                </div>  
                <div class="menu">
                    <div class="menuContent">
                        <ul>
                        	<li><a href="index.jsp">HOME</a></li>
                            <li><a href="myprofile.jsp">MY PROFILE</a></li>
                            <li><a href="userList.jsp">Users list</a></li>
                        </ul>
                    </div>
                </div>
                <!-- content -->
                <div class="content">
                    <h2>New topic in ${param.category}</h2>
                    <form method="POST" action="/addTopic">
                    	<input type="hidden" name="userID" value="${user.userID}" />
                    	<input type="hidden" name="userLogin" value="${user.login}" />
                    	<input type="hidden" name="categoryName" value="${param.category}" />
                        <div>
                            <div class="label">Title:</div>
                            <div class="field"><input type="text" name="title" /></div>   
                        </div>
                        <div>
                            <div class="label">Post content:</div>
                            <div class="field">
                            	<textarea cols="50" rows="5" name="postContent"></textarea>
                            </div>
                        </div>
                        
                        <div class="label"><input type="submit" value="Create topic" /></div>
                    </form>
                </div>
                <div class="usersOnline" >
                	Users online(${users.count}): 
                	<c:set var="i" scope="request" value="1" />
                	<c:forEach items="${users.usersOnline}" var="online">
                		<c:if test="${online.logged}">
                			<c:if test="${i > 1}">, </c:if>
                			<a href="user.jsp?userID=${online.userID}">${online.login}</a>
                			<c:set var="i" scope="request" value="${i + 1}" />
                		</c:if>
                	</c:forEach>  
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