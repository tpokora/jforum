package com.forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.PreparedStatement;

@WebServlet("/edit")
public class EditUserServlet extends HttpServlet{
	
	@Override 
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
		
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int userID = Integer.parseInt(request.getParameter("userID"));
		String email = String.valueOf(request.getParameter("email"));
		PrintWriter out = response.getWriter();
		String password1 = String.valueOf(request.getParameter("password1"));
		String password2 = String.valueOf(request.getParameter("password2"));

		changeEmail(email, userID, request, response);
		changePassword(password1, password2, userID, request, response);
		response.sendRedirect("myprofile.jsp");
	}
	
	private void changeEmail(String email, int userID, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String query = "UPDATE forumUsers SET email = ? WHERE userID = ?";
		try {
			PreparedStatement preStatement = (PreparedStatement) MySQLConnector.connectToDB()
					.prepareStatement(query);
			preStatement.setString(1, email);
			preStatement.setInt(2, userID);
			preStatement.executeUpdate();
		} catch (SQLException e) {
			HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.EMAIL_NOT_CHANGED));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
		} catch (Exception e) {
			HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.EMAIL_NOT_CHANGED));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
	
	
	
	private void changePassword(String password1, String password2, int userID,
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
		if(!isPasswordEmpty(password1)) {
			if(password1.equals(password2)) {
				editPasswordInDB(password1, userID, request, response);
			}
		}
	}
	
	private boolean isPasswordEmpty(String password1) {
		if(password1 == null || password1.isEmpty() || password1.equals("") || password1.equals("null"))
			return true;
		else
			return false;
	}
	
	private void editPasswordInDB(String newPassword, int userID, 
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
		newPassword = UserRegistrationServlet.md5Password(newPassword);
		
		String query = "UPDATE forumUsers SET password = ? WHERE userID = ?";
		try {
			PreparedStatement preStatement = (PreparedStatement) MySQLConnector.connectToDB()
					.prepareStatement(query);
			preStatement.setString(1, newPassword);
			preStatement.setInt(2, userID);
			preStatement.executeUpdate();
		} catch (SQLException e) {
			HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.PASSWORD_NOT_CHANGED));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
		} catch (Exception e) {
			HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.PASSWORD_NOT_CHANGED));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
	
	
	
	
	

}
