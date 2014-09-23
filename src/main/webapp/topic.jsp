<%-- 
    Document   : index
    Created on : Mar 10, 2014, 11:16:18 AM
    Author     : denat
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="Cp1250"%>
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
        <link rel="stylesheet" type="text/css" href="css/topicStyle.css" />
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
	                <div class="topic">
	                	<c:if test="${!topic.open}">
	                		<b style="font-size: 16px; color: #FCB840; text-transform:uppercase;">
	                			<center style="margin: 4px;">Topic closed</center>
	                		</b>
	                	</c:if>
			    		<div class="topicTitle">
			    			<a href="index.jsp">Forum </a> /
				    		<a href="category?category=${param.category}">${param.category}</a>
				    		/ ${topic.shortTopicName} 
			    			</div>
			    		<% int i = 1; %>
			    		<c:forEach items="${topic.posts}" var="post">
				    		<div class="post">
				    			<div class="postHeader">
				    				<span class="postAutor">#<%= i %> - ${post.userLogin}</span>
				    				
									<c:if test="${topic.open}">
										<c:if test="${user.login == post.userLogin || user.level == 2}">
											<form method="post" action="editPost.jsp">
												<input type="hidden" name="topicID" value="${param.topicID}" />
												<input type="hidden" name="category" value="${param.category}" />
												<input type="hidden" name="postID" value="${post.postID}" />
												<input type="hidden" name="userLogin" value="${user.login}" />												
												<input type="submit" value="Edit" />
											</form>
										</c:if>
									</c:if>
									
									
				    				<hr>
				    				<span class="postDate">${post.postDate }</span>
				    			</div>
				    			<div class="postContent">
				    				${post.postContent}
				    			</div>
				    		</div>
				    		<% i++; %> 
			    		</c:forEach>
			    		<c:if test="${!topic.open}">
			    			<b style="font-size: 16px; color: #FCB840; text-transform:uppercase;">
	                			<center style="margin: 14px;">Topic closed</center>
	                		</b>
			    		</c:if>
			    		
			    		<c:if test="${user.logged && topic.open}"> 
				    		<div class="addPost">
				    			<h3>Anwser: </h3>
					    		<form method="POST" action="anwser">
					    			<input type="hidden" name="userID" value="${user.userID }" />
					    			<input type="hidden" name="userLogin" value="${user.login}" />
					    			<input type="hidden" name="topicID" value="${param.topicID }" />
					    			<input type="hidden" name="topicName" value="${topic.topicName }" />
					    			
					    			<textarea rows="10" name="postContent"></textarea>
					    			<input type="submit" value="Anwser" />
					    		</form>
				    		</div>
			    		</c:if>
	    			</div>
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
