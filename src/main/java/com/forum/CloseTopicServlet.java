package com.forum;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.PreparedStatement;

@WebServlet("/closeTopic")
public class CloseTopicServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8"); 
		
		int topicID = Integer.parseInt(request.getParameter("topicID"));
		closeTopic(topicID, request, response);
		response.sendRedirect("index.jsp");
	}
	
	private void closeTopic(int topicID, HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String query = "UPDATE `topics` SET `open`= 0 WHERE `topicID` = ?";
			PreparedStatement preStatement = (PreparedStatement) MySQLConnector.connectToDB()
					.prepareStatement(query);
			preStatement.setInt(1, topicID);
			preStatement.executeUpdate();
		} catch(Exception e) {
			HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.POST_NOT_ADDED));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
