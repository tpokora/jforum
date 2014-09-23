package com.forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpRetryException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.forum.beans.Post;
import com.forum.beans.Topic;
import com.mysql.jdbc.PreparedStatement;
import com.forum.MySQLConnector;

@WebServlet("/addTopic")
public class AddTopicServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		
		int userID = Integer.parseInt(request.getParameter("userID"));
		String userLogin = String.valueOf(request.getParameter("userLogin"));
		String category = String.valueOf(request.getParameter("categoryName"));
		String title = String.valueOf(request.getParameter("title"));
		
		StringBuffer postContent = new StringBuffer(request.getParameter("postContent"));
		String postContentString = postContent.toString();
		
		int loc = (new String(postContent)).indexOf('\n');
		while(loc > 0) {
			postContent.replace(loc, loc + 1, "<br>");
			loc = (new String(postContent).indexOf('\n'));
		}
		
		
		out.println("userID: " + userID);
		out.println("userLogin: " + userLogin);
		out.println("category: " + category);
		out.println("title: " + title);
		out.println("content: " + postContent);
		out.println("lastPostDate: " + currentDate());
		
		Topic newTopic = new Topic();
		newTopic.setTopicName(title);
		newTopic.setCategoryName(category);
		newTopic.setUserID(userID);
		newTopic.setUserLogin(userLogin);
		
		Post newPost = new Post();
		System.out.println("topicName from newTopic: " + newTopic.getTopicName());
		System.out.println("topicName from newTopic: " + title);
		newPost.setTopicName(title);
		System.out.println("topicName from newPost: " + newPost.getTopicName());
		newPost.setUserID(userID);
		newPost.setPostContent(postContent);
		newPost.setUserLogin(userLogin);

		addTopicToDB(newPost, newTopic, request, response);
		response.sendRedirect("index.jsp");
	}
	
	private void addTopicToDB(Post newPost, Topic topic, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			String query = "INSERT INTO topics VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preStatement = (PreparedStatement) MySQLConnector.connectToDB()
					.prepareStatement(query);
			preStatement.setString(1, "0");
			preStatement.setString(2, topic.getTopicName());
			preStatement.setString(3, currentDate());
			preStatement.setString(4, newPost.getUserLogin());
			preStatement.setString(5, String.valueOf(newPost.getUserID()));
			preStatement.setString(6, topic.getCategoryName());
			preStatement.setString(7, String.valueOf(newPost.getUserID()));
			preStatement.setString(8, topic.getUserLogin());
			preStatement.setString(9, "1");
			preStatement.setString(10, "1");
			preStatement.executeUpdate();
			
			newPost.setTopicID();
			System.out.println("topicID from post: " + newPost.getTopicID());
			incrementUserTopictNumber(newPost.getUserID());
			addPostToDB(newPost, request, response);
		} catch (SQLException e) {
			HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.TOPIC_NOT_ADDED));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
		} catch (Exception e) {
			HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.TOPIC_NOT_ADDED));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
	
	public static void addPostToDB(Post post, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			System.out.println("Adding post");
			String query = "INSERT INTO posts VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement preStatement = (PreparedStatement) MySQLConnector.connectToDB()
					.prepareStatement(query);
			preStatement.setString(1, "0");
			preStatement.setString(2, post.getPostContent().toString());
			preStatement.setString(3, currentDate());
			preStatement.setString(4, String.valueOf(post.getUserID()));
			preStatement.setString(5, post.getUserLogin());
			preStatement.setString(6, String.valueOf(post.getTopicID()));
			preStatement.executeUpdate();
			
			incrementUserPostNumber(post.getUserID());
		} catch (SQLException e) {
			HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.POST_NOT_ADDED));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
		} catch (Exception e) {
			HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.POST_NOT_ADDED));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
	
	private static void incrementUserTopictNumber(int userID) {
		
		try {
			String query = "UPDATE forumUsers SET topicNumber = topicNumber + 1 WHERE userID = ?";
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
	
	private static void incrementUserPostNumber(int userID) {
		
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
	
	public static String currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
        Date date = new Date();
        return dateFormat.format(date);
    }
	
}