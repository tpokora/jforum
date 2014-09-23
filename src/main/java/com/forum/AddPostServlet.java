package com.forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.forum.beans.Post;
import com.mysql.jdbc.PreparedStatement;
import com.forum.AddTopicServlet;
import com.forum.MySQLConnector;

/**
 * Servlet implementation class AddPostServlet
 */
@WebServlet("/anwser")
public class AddPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("Cp1250");
		response.setContentType("text/html; Cp1250"); 
		PrintWriter out = response.getWriter();
		
		int userID = Integer.parseInt(request.getParameter("userID"));
		String userLogin = String.valueOf(request.getParameter("userLogin"));
		int topicID = Integer.parseInt(request.getParameter("topicID"));
		String topicName = String.valueOf(request.getParameter("topicName"));
		
		StringBuffer postContent = new StringBuffer(request.getParameter("postContent"));
		
		int loc = (new String(postContent)).indexOf('\n');
		while(loc > 0) {
			postContent.replace(loc, loc + 1, "<br>");
			loc = (new String(postContent).indexOf('\n'));
		}
		
		
		out.println("userID: " + userID);
		out.println("userLogin: " + userLogin);
		out.println("topicID: " + topicID);
		out.println("topicName: " + topicName);
		
		Post post = new Post();
		post.setUserID(userID);
		post.setUserLogin(userLogin);
		post.setTopicIDForAnwser(topicID);
		post.setTopicName(topicName);
		post.setPostContent(postContent);
		
		out.println("\nFrom Post\n");
		out.println("userID: " + post.getUserID());
		out.println("userLogin: " + post.getUserLogin());
		out.println("topicID: " + post.getTopicID());
		out.println("topicName: " + post.getTopicName());
		out.println("postContent: " + post.getPostContent());
		
		addAnwserPost(post);
		response.sendRedirect("index.jsp");
	}
	
	private void addAnwserPost(Post post) {
		String date = AddTopicServlet.currentDate();
		try {
			System.out.println("Adding post");
			String query = "INSERT INTO posts VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement preStatement = (PreparedStatement) MySQLConnector.connectToDB()
					.prepareStatement(query);
			preStatement.setString(1, "0");
			preStatement.setString(2, post.getPostContent().toString());
			preStatement.setString(3, date);
			preStatement.setString(4, String.valueOf(post.getUserID()));
			preStatement.setString(5, post.getUserLogin());
			preStatement.setString(6, String.valueOf(post.getTopicID()));
			preStatement.executeUpdate();
			
			incrementPostNumber(post.getTopicID());
			incrementUserPostNumber(post.getUserID());
			lastPostUserInfo(post.getTopicID(), post.getUserLogin(), post.getUserID());
			lastPostDate(post.getTopicID(), date);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void incrementPostNumber(int topicID) {
		
		try {
			String query = "UPDATE topics SET postNumber = postNumber + 1 WHERE topicID = ?";
			PreparedStatement preStatement = (PreparedStatement) MySQLConnector.connectToDB()
					.prepareStatement(query);
			preStatement.setInt(1, topicID);
			preStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void incrementUserPostNumber(int userID) {
		
		try {
			String query = "UPDATE forumUsers SET postNumber = postNumber + 1 WHERE userID = ?";
			PreparedStatement preStatement = (PreparedStatement) MySQLConnector.connectToDB()
					.prepareStatement(query);
			preStatement.setInt(1, userID);
			preStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void lastPostUserInfo(int topicID, String userLogin, int userID) {
		
		try {
			String query = "UPDATE topics SET lastPostUser = ?, lastPostUserID = ? WHERE topicID = ?";
			PreparedStatement preStatement = (PreparedStatement) MySQLConnector.connectToDB()
					.prepareStatement(query);
			preStatement.setString(1, userLogin);
			preStatement.setInt(2, userID);
			preStatement.setInt(3, topicID);
			preStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void lastPostDate(int topicID, String date) {
		try {
			String query = "UPDATE topics SET lastPostDate = ?  WHERE topicID = ?";
			PreparedStatement preStatement = (PreparedStatement) MySQLConnector.connectToDB()
					.prepareStatement(query);
			preStatement.setString(1, date);
			preStatement.setInt(2, topicID);
			preStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
