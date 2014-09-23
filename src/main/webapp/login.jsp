<%-- 
    Document   : login
    Created on : Mar 18, 2014, 6:06:46 PM
    Author     : denat
--%>

<%@page import="com.forum.Errorer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <jsp:useBean id="user" scope="session" class="com.forum.beans.User" />
    <jsp:setProperty name="user" property="*" />
    <body>  
        <%
            String loginPassedSite = "index.jsp";
            String errorSite = "error.jsp";
            if(user.isLogged()) {
                out.println("Logged: " + user.isLogged());
                user.UserLogout();
                response.setStatus(response.SC_MOVED_TEMPORARILY);
                response.setHeader("Location", loginPassedSite);
                //response.setHeader("Refresh", "5; url=index.jsp");
                
            }
            else {
                out.println("Not Logged: " + user.isLogged());
                user.setUserID();
                out.println("User ID: " + user.getUserID());
                if(user.userExists()) {
                    if(user.isPasswordCorrect()) {
                        user.UserLogin();
                        out.println("Correct password: " + user.getLogin());
                        response.setStatus(response.SC_MOVED_TEMPORARILY);
                        response.setHeader("Location", loginPassedSite);
                        //response.setHeader("Refresh", "5; url=index.jsp");
                    } else {
                        user.isLogged(false);
                        out.println("Wrong password: " + user.getPassword());
                        session.setAttribute("error", Errorer.errorMessage(Errorer.Error.WRONG_PASSWORD));
                        response.setStatus(response.SC_MOVED_TEMPORARILY);
                        response.setHeader("Location", errorSite);
                    }
                } else {
                    user.isLogged(false);
                    out.println("Not Exist " + user.getLogin());
                    session.setAttribute("error", Errorer.errorMessage(Errorer.Error.USER_NOT_FOUND));
                    response.setStatus(response.SC_MOVED_TEMPORARILY);
                    //response.setHeader("Refresh", "5; url=error.jsp");
                    response.setHeader("Location", errorSite);
                }
                
            }
            
        %> 
    </body>
</html>
