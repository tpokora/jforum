package com.forum.beans;

import java.sql.ResultSet;
import java.util.Date;

import com.forum.MySQLConnector;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class Post {
	private int postID;
	private StringBuffer postContent;
	private Date postDate;
	private String topicName;
	private int topicID;
	private int userID;
	private String userLogin;
	
	public Post() {}
	
	public void setPostID(int postID) {
		this.postID = postID;
	}
	
	public int getPostID() {
		return postID;
	}
	
	public void setPostContent(StringBuffer postContent) {
		this.postContent = postContent;
	}
	
	public StringBuffer getPostContent() {
		return postContent;
	}
	
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	
	public Date getPostDate() {
		return postDate;
	}
	
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
	public String getTopicName() {
		return topicName;
	}
	
	public void setTopicIDFromDB(int topicID) {
		this.topicID = topicID;
	}
	
	public void setTopicIDForAnwser(int topicID) {
		this.topicID = topicID;
	}
	
	public void setTopicID() {
		try {
			String query = "SELECT topicID FROM topics WHERE topicName = ?";
			PreparedStatement prepStmt = (PreparedStatement) MySQLConnector.connectToDB().prepareStatement(query);
			prepStmt.setString(1, topicName);
			ResultSet rs = (ResultSet) prepStmt.executeQuery();
			while(rs.next()) {
				topicID = Integer.parseInt(rs.getString("topicID"));
				System.out.println("topicID:" + topicID);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public int getTopicID() {
		return topicID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	
	public String getUserLogin() {
		return userLogin;
	}
	
	
}
