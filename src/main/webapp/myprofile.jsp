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
<jsp:useBean id="categories" scope="request" class="com.forum.beans.Categories">
</jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forum</title>
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <link rel="stylesheet" type="text/css" href="css/myprofileStyle.css" />
        <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
        <link rel="Shortcut icon" href="images/favicon.ico" />
		<script src="//code.jquery.com/jquery-1.10.2.js"></script>
		<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script>
				function deleteCategory(category) {
				   var retVal = confirm('Do you want to delete category ' + category + '?' +
						   'This will remove all topics and posts within this category.');
				   if(retVal == true) {
					  return true;
				   } else {
					  return false;
				   }
				}
		</script>
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
                    <center><h2>User data</h2></center>
                        <table>
                            <tr>
                                <td class="rightColumn">User: </td><td class="leftColumn">${user.login}</td>
                            </tr>
                            <tr>
                                <td class="rightColumn">e-mail address: </td><td class="leftColumn">${user.email}</td>
                            </tr>
                            <tr>
                                <td class="rightColumn">Joined: </td><td class="leftColumn">${user.registerDate}</td>
                            </tr>
                            <tr>
                                <td class="rightColumn">Level: </td>
                                <td class="leftColumn">
                                    <c:choose>
                                        <c:when test="${user.level == 1}">
                                            User</td>
                                        </c:when>
                                        <c:when test="${user.level == 2}">
                                            Admin</td>
                                            
                                        </c:when>
                                    </c:choose>
                            </tr>
                            <%
                            	user.setPostNumberFromDB(user.getUserID());
                            	user.setTopicNumberFromDB(user.getUserID());
                            %>
                            <tr>
                            	<td class="rightColumn">Topics created: </td><td class="leftColumn">${user.topicNumber}</td>
                            </tr>
                            <tr>
                            	<td class="rightColumn">Posts written: </td><td class="leftColumn">${user.postNumber}</td>
                            </tr>
                            
                        </table>
                        <center  style="padding: 10px;"><h3><a href="editData.jsp">Edit data</a></h3></center>
                    
                        <c:choose>
                            <c:when test="${user.level == 2}">
                                <hr>       
                                <center>                 
								<div id="adminPanel">
	                                <center><h2>Admin panel</h2></center>
	                                <center><h3>Categories list</h3></center>
	                                
	                                	<table>
	                                		<c:forEach items="${categories.list}" var="c">
	                                			<tr>
	                                				<td style="padding: 10px">${c.name}</td>
	                                				<td style="padding: 10px">
	                                				<form method="POST" action="/deleteCategory" 
	                                					onSubmit="return confirm('Do you want to delete category ${c.name}?\nThis will remove all topics and posts within this category.');">
	                                					<input type="hidden" name="category" value="${c.name}" />
	                                					<input type="submit" name="submit" value="Delete" />
	                                				</form>
	                                				</td>
	                                			</tr>
	                                		</c:forEach>
	                                	</table>
	                                
	                                <script>
				    				$(function() {
				    					$( "#dialog" ).dialog({
				    						autoOpen: false,
				    						width: 450,
				    						resizable: false,
				    						show: {
				    							effect: "blind",
				    							duration: 1000
				    							},
				    						hide: {
				    							effect: "explode",
				    							duration: 1000
				    							}
				    						});
				    					
									    $( "#opener" ).click(function() {
									    	$( "#dialog" ).dialog( "open" );
									    });
									});
									</script>
									<div id="dialog" title="Change Order">
										<form method="post" action="/changeCatOrder">
											<table>
		                                		<c:forEach items="${categories.list}" var="c">
		                                			<tr>
			                                			<td style="padding: 10px">${c.name}</td>
			                                			<td style="padding: 10px">
															<input type="text" name="${c.name}" value="${c.position}" size="2"/>
														</td>
			                                		</tr>
			                                	</c:forEach>
			                                	<tr>
			                                		<td colspan="2" style="text-align: center;">
			                                			<input type="submit" value="Change" />
			                                		</td>
			                                	</tr>
		                                	</table>
										</form>
									</div>
									<button id="opener">Change order</button>
	                                
	                                <form method="POST" action="/addCategory" style="margin-top: 10px">
	                                	Category name: <input type="text" name="category" /> 
	                                	| <input type="submit" value="Add" />
	                                </form>
                                </div>
                                </center>
                                
                            </c:when>
                        </c:choose>
                    
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
