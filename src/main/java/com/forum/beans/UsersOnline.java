package com.forum.beans;

import java.awt.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.forum.MySQLConnector;
import com.mysql.jdbc.Statement;

public class UsersOnline {
	private int count;
	private ArrayList<User> usersOnline = new ArrayList<User>();
	
	public UsersOnline() {
		count = 0;
		setUsersOnlineList();
	}
	
	public int getCount() {
		return count;
	}
	
	public ArrayList<User> getUsersOnline() {
		return usersOnline;
	}
	
	private void setUsersOnlineList() {
		String query = "SELECT userID, login, logged FROM forumUsers";
        Statement statement = null;
        try {
            statement = (Statement) MySQLConnector.connectToDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                User nextUserFromDB = new User();
                nextUserFromDB.setUserIdForList(result.getInt("userID"));
                nextUserFromDB.setLogin(result.getString("login"));
                nextUserFromDB.isLogged(result.getBoolean("logged"));
                usersOnline.add(nextUserFromDB);
                if(nextUserFromDB.isLogged())
                	count += 1;
            }
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}
