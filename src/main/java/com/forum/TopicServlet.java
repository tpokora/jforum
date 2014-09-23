package com.forum;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.forum.beans.Topic;
import com.forum.beans.Post;

@WebServlet("/topic")
public class TopicServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8"); 
		
		String topicID = String.valueOf(request.getParameter("topicID"));
		String category = request.getParameter("category");
		
		Topic topic = new Topic();
		topic.setPosts(Integer.parseInt(topicID));
		topic.setTopicNameFromDB(Integer.parseInt(topicID));
		topic.setShortTopicName();
		topic.getOpenFromDB(Integer.parseInt(topicID));
		
		PrintWriter out = response.getWriter();
		
		out.println("category: " + category);
		out.println("topicID: " + topicID);
		out.println("topicOpen: " + topic.isOpen());
		out.println("Posts From DB: ");
		int i = 1;
		for(Post post : topic.getPosts()) {
			out.println("#" + i);
			out.println("postID: " + post.getPostID());
			out.println("postContent: " + post.getPostContent());
			out.println("postDate: " + post.getPostDate());
			out.println("userID: " + post.getUserID());
			out.println("userLogin: " + post.getUserLogin());
			out.println("topicID: " + post.getTopicID());
			out.println("=========================");
			i++;
		}
		
		request.setAttribute("topic", topic);
		request.setAttribute("category", category);
		request.getRequestDispatcher("/topic.jsp").forward(request, response);
	}
}
