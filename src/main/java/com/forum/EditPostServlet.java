package com.forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.forum.beans.Post;
import com.mysql.jdbc.PreparedStatement;

@WebServlet("/editPost")
public class EditPostServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		
		int topicID = Integer.parseInt(request.getParameter("topicID"));
		String category = String.valueOf(request.getParameter("category"));
		int postID = Integer.parseInt(request.getParameter("postID"));
		String userLogin = String.valueOf(request.getParameter("userLogin"));
		StringBuffer postContent = new StringBuffer(request.getParameter("postContent"));
		String url = "topic?topicID=" + topicID + "&category=" + category;
		
		int loc = (new String(postContent)).indexOf('\n');
		while(loc > 0) {
			postContent.replace(loc, loc + 1, "<br>");
			loc = (new String(postContent).indexOf('\n'));
		}
		
		postContent.append("<br><br>___________________" + 
				"<br><i>Edited " + AddTopicServlet.currentDate() + " by " + userLogin + "</i>");
		
		Post post = new Post();
		post.setPostID(postID);
		post.setUserLogin(userLogin);
		post.setPostContent(postContent);
		
		out.println("topicID: " + topicID);
		out.println("category: " + category);
		out.println("userLogin: " + post.getUserLogin());
		out.println("postID: " + post.getPostID());
		out.println("userLogin: " + post.getUserLogin());
		out.println("postContent: " + post.getPostContent());
		
		editPost(post, request, response);
		response.sendRedirect(url);
		
	}
	
	private void editPost(Post post, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			String query = "UPDATE posts SET postContent = ? WHERE postID = ?";
			PreparedStatement preStatement = (PreparedStatement) MySQLConnector.connectToDB()
					.prepareStatement(query);
			preStatement.setString(1, post.getPostContent().toString());
			preStatement.setInt(2, post.getPostID());
			preStatement.executeUpdate();
		} catch (SQLException e) {
			HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.POST_NOT_EDITED));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
		} catch (Exception e) {
			HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.POST_NOT_EDITED));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
