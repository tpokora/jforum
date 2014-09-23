package com.forum.beans;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.forum.MySQLConnector;
import com.mysql.jdbc.Statement;

public class Topic {
	private List<Post> posts = new ArrayList<Post>();
	private int topicID;
	private String topicName;
	private String shortTopicName;
	private String categoryName;
	private int userID;
	private String userLogin;
	private Date lastPostDate;
	private String lastPostUser;
	private int lastPostUserID;
	private int postNumber;
	private boolean open;
	
	public Topic() {}
	
	public void setTopicID(int topicID) {
		this.topicID = topicID;
	}
	
	public int getTopicID() {
		return topicID;
	}
	
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
	public void setTopicNameFromDB(int topicID) {
		String query = "SELECT topicName FROM topics WHERE topicID = " + topicID;
        Statement statement = null;
        try {
            statement = (Statement) MySQLConnector.connectToDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                setTopicName(result.getString("topicName"));
            }
        } catch (Exception ex) {
            Logger.getLogger("Bład wczytania listy");
        }
	}
	
	public String getTopicName() {
		return topicName;
	}
	
	public void setShortTopicName() {
		if(topicName.length() > 27) {
			shortTopicName = topicName.substring(0, 24) + "...";
		}
		else 
			shortTopicName = topicName;
	}
	
	public String getShortTopicName() {
		setShortTopicName();
		return shortTopicName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getCategoryName() {
		return categoryName;
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
	
	public void setLastPostDate(Date lastPostDate) {
		this.lastPostDate = lastPostDate;
	}
	
	public Date getLastPostDate() {
		return lastPostDate;
	}
	
	public void setLastPostUser(String lastPostUser) {
		this.lastPostUser = lastPostUser;
	}
	
	public String getLastPostUser() {
		return lastPostUser;
	}
	
	public void setLastPostUserID(int lastPostUserID) {
		this.lastPostUserID = lastPostUserID;
	}
	
	public int getLastPostUserID() {
		return lastPostUserID;
	}
	
	public void setPostNumber(int postNumber) {
		this.postNumber = postNumber;
	}
	
	public int getPostNumber() {
		return postNumber;
	}
	
	public void setPosts(int topicID) {
		String query = "SELECT * FROM posts WHERE topicID = " + topicID + 
				" ORDER BY `postDate` ";
        Statement statement = null;
        try {
            statement = (Statement) MySQLConnector.connectToDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                Post post = new Post();
                post.setPostID(result.getInt("postID"));
                post.setPostContent(new StringBuffer(result.getString("postContent")));
                post.setPostDate(result.getTimestamp("postDate"));
                post.setUserID(result.getInt("userID"));
                post.setUserLogin(result.getString("userLogin"));
                post.setTopicIDFromDB(result.getInt("topicID"));
                posts.add(post);
            }
        } catch (Exception ex) {
            Logger.getLogger("Bład wczytania listy");
        }
	}
	
	public List<Post> getPosts() {
		return posts;
	}
	
	public void isOpen(boolean open) {
		this.open = open;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void getOpenFromDB(int topicID) {
		String query = "SELECT open FROM topics WHERE topicID = " + topicID;
        Statement statement = null;
        try {
            statement = (Statement) MySQLConnector.connectToDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
            	int openInDB = result.getInt("open");
            	if(openInDB != 0)
                	open = true;
                 else 
                	 open = false;
            }
        } catch (Exception ex) {
            Logger.getLogger("Bład wczytania listy");
        }
	}
}
