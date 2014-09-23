<%-- 
    Document   : index
    Created on : Mar 10, 2014, 11:16:18 AM
    Author     : denat
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<jsp:useBean id="user" scope="session" class="com.forum.beans.User">
</jsp:useBean>
<jsp:useBean id="users" scope="request" class="com.forum.beans.UsersOnline">
</jsp:useBean>
<jsp:useBean id="categories" scope="request" class="com.forum.beans.Categories">
</jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forum</title>
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <link rel="stylesheet" type="text/css" href="css/registerStyle.css" />
        <link rel="stylesheet" type="text/css" href="css/topicsList.css" />
        <link rel="Shortcut icon" href="images/favicon.ico" />
    </head>
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
                    <table class="topicsTable">
                    	<th style="min-width: 200px">Topic</th><th>Author</th><th>Posts #</th><th>Last</th>
                    	<c:forEach items="${categories.list}" var="c">
                    	<c:if test="${c.name == param.category}">
                    		<tr class="categoryName"> 
                    			<td colspan="4">
                    				<b style="margin-right: 10px">${c.name}</b> 
                    				<c:if test="${user.logged}">
                    					<a href="addTopic.jsp?category=${c.name}">(+Add topic)</a>
                    				</c:if>
                    					
                    			</td>
                    		</tr>
                    		<c:forEach items="${c.topics}" var="t">
	                    		<c:if test="${t.categoryName == c.name }">
	                    			<tr class="topic">
		                    			<td class="topicName">
		                    				<a href="topic?topicID=${t.topicID}&category=${c.name}" 
		                    					style="font-size: 14px;">
		                    					${t.topicName}
		                    				</a>
		                    				<c:if test="${!t.open}">
		                    					<i style="font-size: 12px; color: #FCB840;">
		                    						<br />
		                    						____________________
		                    						<br />
													Topic closed
													<c:if test="${user.level == 2}">
														<a href="openTopic?topicID=${t.topicID}">- Open topic</a>
													</c:if>
												</i>
											</c:if>
											<c:if test="${t.open && user.level == 2}">
												<i style="font-size: 12px; color: #FCB840;">
													<br />
													____________________
													<br />
													<a href="closeTopic?topicID=${t.topicID}" 
														style="font-size: 12px; color: #ffffff">Close topic</a>
												</i>
											</c:if>
		                    			</td>
		                    			<td style="text-align: center">
		                    				<a href="user?userID=${t.userID}">${t.userLogin}</a>
		                    			</td>
		                    			<td style="text-align: center">${t.postNumber}</td>
		                    			<td style="text-align: right">
		                    				<span style="font-size: 12px; margin-right: -4px;">${t.lastPostDate}</span>
		                    				<br /><a href="user?userID=${t.lastPostUserID}"><b>${t.lastPostUser}</b></a>
		                    			</td>
		                    		</tr>
	                    		</c:if>
                    		</c:forEach>
                    	</c:if>
                    	</c:forEach>
                    	
                    </table>
                    
                </div>
                <!-- users online -->
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
