package com.forum.beans;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.forum.MySQLConnector;
import com.mysql.jdbc.Statement;

public class UserList {
	private ArrayList<User> userList = new ArrayList<User>();
	
	public UserList() {
		setList();
	}
	
	
	
	public ArrayList<User> getList() {
		return userList;
	}
	
	public void setList() {
		String query = "SELECT * FROM forumUsers";
        Statement statement = null;
        try {
            statement = (Statement) MySQLConnector.connectToDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                User nextUserFromDB = new User();
                nextUserFromDB.setUserIdForList(result.getInt("userID"));
                nextUserFromDB.setLogin(result.getString("login"));
                nextUserFromDB.setEmailForList(result.getString("email"));
                nextUserFromDB.setLevelForList(result.getInt("level"));
                nextUserFromDB.setRegisterDateForList(result.getTimestamp("registerDate"));
                nextUserFromDB.setPostNumber(result.getInt("postNumber"));
                nextUserFromDB.setTopicNumber(result.getInt("topicNumber"));
                userList.add(nextUserFromDB);
            }
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}
