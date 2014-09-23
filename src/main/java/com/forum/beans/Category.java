package com.forum.beans;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.mysql.jdbc.Statement;
import com.forum.beans.Topic;
import com.forum.MySQLConnector;

public class Category {
	private String name;
	private int position;
	private List<Topic> topics = new ArrayList<Topic>();
	
	public Category() {
		setTopicsFromDB();
	}
	
	public void setTopicsFromDB() {
		String query = "SELECT * FROM topics ORDER BY `lastPostDate` DESC";
        Statement statement = null;
        try {
            statement = (Statement) MySQLConnector.connectToDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                Topic topic = new Topic();
                topic.setTopicID(result.getInt("topicID"));
                topic.setTopicName(String.valueOf(result.getString("topicName")));
                topic.setLastPostDate(result.getTimestamp("lastPostDate"));
                topic.setCategoryName(String.valueOf(result.getString("categoryName")));
                topic.setUserID(result.getInt("userID"));
                topic.setUserLogin(String.valueOf(result.getString("userLogin")));
                topic.setPostNumber(result.getInt("postNumber"));
                topic.setLastPostUser(String.valueOf(result.getString("lastPostUser")));
                topic.setLastPostUserID(result.getInt("lastPostUserID"));
                topic.isOpen(result.getBoolean("open"));
                topics.add(topic);
            }
        } catch (Exception ex) {
            Logger.getLogger("Bład wczytania listy");
        }
	}
	
	public List<Topic> getTopics() {
		return topics;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}
}
