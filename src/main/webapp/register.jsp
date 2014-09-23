<%-- 
    Document   : index
    Created on : Mar 10, 2014, 11:16:18 AM
    Author     : denat
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
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
    <body>
        <!-- loginBox -->
        <div class="loginBox">
            <form method="POST" action="login.jsp">
                <c:choose>
                    <c:when test="${sessionScope.logged == null}">
                        Login: <input type="text" name="login" size="12"/>
                        Password: <input type="password" name="password" size="12" />
                        <input type="submit" value="Login" /> 
                    </c:when>
                    <c:otherwise>
                        Hello Zalogowany!
                    </c:otherwise>
                </c:choose>
                        <a href="register.jsp">Register</a>
            </form>
        </div>
        <!-- wrapper -->
        <div class="wrapper">
            <div class="wrapperContent">
                <!-- header -->
                <div class="header">
                </div>  
                <!-- content -->
                <div class="content">
                    <!-- content menu -->
                     <div class="menu">
	                    <div class="menuContent">
	                        <ul>
	                            <li><a href="index.jsp">HOME</a></li>
	                            <li><a href="myprofile.jsp">MY PROFILE</a></li>
	                            <li><a href="userList.jsp">Users list</a></li>
	                        </ul>
	                    </div>
	                </div>
                    <h2>Register form</h2>
                    <form method="POST" action="/registration">
                        <div>
                            <div class="label">Login:</div>
                            <div class="field"><input type="text" name="login" /></div>   
                        </div>
                        <div>
                            <div class="label">Email:</div>
                            <div class="field"><input type="text" name="email" /></div>
                        </div>
                        <div>
                            <div class="label">Password:</div>
                            <div class="field"><input type="password" name="password" /></div>
                        </div>
                        <div>
                            <div class="label">Confirm password:</div>
                            <div class="field"><input type="password" name="password2" /></div>
                        </div>
                        <div class="label"><input type="submit" value="Register" /></div>
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