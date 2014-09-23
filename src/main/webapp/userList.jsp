<%-- 
    Document   : index
    Created on : Mar 10, 2014, 11:16:18 AM
    Author     : denat
--%>

<%@page import="com.forum.beans.Categories"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<jsp:useBean id="user" scope="session" class="com.forum.beans.User">
</jsp:useBean>
<jsp:useBean id="users" scope="request" class="com.forum.beans.UsersOnline">
</jsp:useBean>
<jsp:useBean id="userList" scope="request" class="com.forum.beans.UserList">
</jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forum</title>
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <link rel="stylesheet" type="text/css" href="css/userlistStyle.css" />
        <link rel="Shortcut icon" href="images/favicon.ico" />
    </head>
    <body>
        <c:choose>
            <c:when test="${user.logged == null || user.logged == false}">
                <% 
                    response.setStatus(response.SC_MOVED_TEMPORARILY);
                    response.setHeader("Location", "/notlogged");
                %>
            </c:when>
        </c:choose>
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
                    <center><h2>User list</h2></center>
                    <table>
                    	<tr>
                    		<th>Login</th><th>Email</th><th>Rank</th><th>Register date</th>
                    	</tr>
	                    <c:forEach items="${userList.list}" var="u">
	                    	<tr>
	                    		<td>
	                    			<a href="user.jsp?userID=${u.userID}">${u.login}</a>
	                    		</td>
	                    		<td>${u.email}</td>
	                    		<td>
	                    		<c:choose>
	                    			<c:when test="${u.level == 2}">
	                    				Admin
	                    			</c:when>
	                    			<c:otherwise>
	                    				User
	                    			</c:otherwise>
	                    		</c:choose>
	                    		</td>
	                    		<td>${u.registerDate}</td>
	                    	</tr>
	                    	
	                    </c:forEach>
                    </table>
                    
                        
                </div>
                 <div class="usersOnline" >
                	Users online(${users.count}): 
                	<c:set var="i" scope="request" value="1" />
                	<c:forEach items="${users.usersOnline}" var="online">
                		<c:if test="${online.logged}">
                			<c:if test="${i > 1}">, </c:if>
                			<a href="user?userID=${online.userID}">${online.login}</a>
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
