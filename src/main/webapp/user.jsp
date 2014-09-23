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
<jsp:useBean id="userList" scope="session" class="com.forum.beans.UserList">
</jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forum</title>
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <link rel="stylesheet" type="text/css" href="css/myprofileStyle.css" />
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
	                    <c:forEach items="${userList.list}" var="u">
	                    	<c:if test="${u.userID == param.userID}">
		                    	<center><h2>${u.login}</h2></center>
		                        <table>
		                            <tr>
		                                <td class="rightColumn">e-mail address:</td><td class="leftColumn">${u.email}</td>
		                            </tr>
		                            <tr>
		                                <td class="rightColumn">Joined: </td><td class="leftColumn">${u.registerDate}</td>
		                            </tr>
		                             <tr>
		                                <td class="rightColumn">Level: </td>
		                                <td class="leftColumn">
		                                    <c:choose>
		                                        <c:when test="${u.level == 1}">
		                                            User</td>
		                                        </c:when>
		                                        <c:when test="${u.level == 2}">
		                                            Admin</td>
		                                            
		                                        </c:when>
		                                    </c:choose>
		                            </tr>
		                            <tr>
		                            	<td class="rightColumn">Topics created: </td><td class="leftColumn">${u.topicNumber}</td>
		                            </tr>
		                            <tr>
		                            	<td class="rightColumn">Posts written: </td><td class="leftColumn">${u.postNumber}</td>
		                            </tr>
	                        	</table>
	                    	</c:if>
	                    </c:forEach>
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
