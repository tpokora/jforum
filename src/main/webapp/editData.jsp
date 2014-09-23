<%-- 
    Document   : index
    Created on : Mar 10, 2014, 11:16:18 AM
    Author     : denat
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<jsp:useBean id="user" scope="session" class="com.forum.beans.User">
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
                        Hello, <a href="myprofile.jsp?id=${user.userID}">${user.login}!</a>
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
                    <h3>Edit data of user ${user.login}</h3>
                    <form method="POST" action="/edit">
                    	<input type="hidden" name="userID" value="${user.userID}" />
                        <div>
                            <div class="label">Email:</div>
                            <div class="field"><input type="text" name="email" value="${user.email}"/></div>
                        </div>
                        <div>
                            <div class="label">Password:</div>
                            <div class="field"><input type="password" name="password1" /></div>
                        </div>
                        <div>
                            <div class="label">Confirm password:</div>
                            <div class="field"><input type="password" name="password2" /></div>
                        </div>
                        <div class="label"><input type="submit" value="Edit" /></div>
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
        </body>
</html>
