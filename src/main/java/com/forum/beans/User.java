package com.forum.beans;

import com.forum.MySQLConnector;
import com.forum.UserRegistrationServlet;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

public class User {
    private int userID;
    private String login;
    private String password;
    private String email;
    private int level;
    private boolean logged;
    private Date registerDate;
    private int postNumber;
    private int topicNumber;

    public User() {
        this.logged = false;
    }
    
    public void UserLogin() {
    	setLoggedStatusInDB(true, getUserID());
        setUserID();
        setEmail();
        setLevel();
        isLogged(true);
        setRegisterDate();
    }
    
    public void UserLogout() {
    	setLoggedStatusInDB(false, getUserID());
        this.userID = 0;
        this.login = null;
        this.password = null;
        this.email = null;
        this.level = 0;
        isLogged(false);
        this.registerDate = null;
    }
    
    private void setLoggedStatusInDB(boolean logged, int userID) {
    	try {
			String query = "UPDATE forumUsers SET logged = ? WHERE userID = ?";
			PreparedStatement preStatement = (PreparedStatement) MySQLConnector.connectToDB()
					.prepareStatement(query);
			preStatement.setBoolean(1, logged);
			preStatement.setInt(2, userID);
			preStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public int getUserID() {
        return userID;
    }
    
    public void setUserIdForList(int userID) {
    	this.userID = userID;
    }

    public void setUserID() {
        String query = "SELECT userID FROM forumUsers WHERE login = '" + this.login + "'";
        Statement statement = null;
        try {
            statement = (Statement) MySQLConnector.connectToDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                int userIDFromDB = result.getInt("userID");
                if(login.equals(this.login)) {
                    this.userID = userIDFromDB;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setEmailForList(String email) {
    	this.email = email;
    }

    public void setEmail() {
        String query = "SELECT email FROM forumUsers WHERE login = '" + this.login + "'";
        Statement statement = null;
        try {
            statement = (Statement) MySQLConnector.connectToDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                String emailFromDB = result.getString("email");
                if(login.equals(this.login)) {
                    this.email = emailFromDB;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevelForList(int level) {
    	this.level = level;
    }
    
    public void setLevel() {
        String query = "SELECT level FROM forumUsers WHERE login = '" + this.login + "'";
        Statement statement = null;
        try {
            statement = (Statement) MySQLConnector.connectToDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                int levelFromDB = result.getInt("level");
                if(login.equals(this.login)) {
                    this.level = levelFromDB;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    public boolean isLogged() {
        return logged;
    }

    public void isLogged(boolean logged) {
        this.logged = logged;
    }
    
    
    
    public Date getRegisterDate() {
        return registerDate;
    }
    
    public void setRegisterDateForList(java.sql.Timestamp date) {
    	this.registerDate = date;
    }
    
    public void setRegisterDate() {
        String query = "SELECT registerDate FROM forumUsers WHERE login = '" + this.login + "'";
        Statement statement = null;
        try {
            statement = (Statement) MySQLConnector.connectToDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                java.sql.Timestamp timestampFromDB = result.getTimestamp("registerDate");
                if(login.equals(this.login)) {
                    this.registerDate = timestampFromDB;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String myprofileLink() {
        return "myprofile?id=" + this.userID;
    }
    
    public boolean isPasswordCorrect() {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT password FROM forumUsers WHERE userID = '" 
                + this.userID + "'";
        Statement statement = null;
        boolean correct = false;
        try {
            statement = (Statement) sql.connectToDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                String passwordFromDB = result.getString("password");
                
                String md5Password = UserRegistrationServlet.md5Password(this.password);
                if(passwordFromDB.equals(md5Password)) {
                    correct = true;
                }
                
                
            }
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            
        }

        return correct;
    }
    
    public boolean userExists() {
        String query = "SELECT login FROM forumUsers WHERE userID = '" 
                + this.userID + "'";
        Statement statement = null;
        boolean exist = false;
        try {
            statement = (Statement) MySQLConnector.connectToDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                String loginFromDB = result.getString("login");
                if(loginFromDB.equals(this.login)) {
                    exist = true;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            
        }

        return exist;
    }
    
    public void setPostNumberFromDB(int userID) {
    	String query = "SELECT postNumber FROM forumUsers WHERE userID = '" 
                + userID + "'";
        Statement statement = null;
        boolean exist = false;
        try {
            statement = (Statement) MySQLConnector.connectToDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                int postNumberFromDB = Integer.parseInt(result.getString("postNumber"));
                postNumber = postNumberFromDB;
                
            }
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    public void setPostNumber(int postNumber) {
    	this.postNumber = postNumber;
    }
    
    public int getPostNumber() {
    	return postNumber;
    }
    
    public void setTopicNumberFromDB(int userID) {
    	String query = "SELECT topicNumber FROM forumUsers WHERE userID = '" 
                + userID + "'";
        Statement statement = null;
        boolean exist = false;
        try {
            statement = (Statement) MySQLConnector.connectToDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                int topicNumberFromDB = Integer.parseInt(result.getString("topicNumber"));
                topicNumber = topicNumberFromDB;
                
            }
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    public void setTopicNumber(int topicNumber) {
    	this.topicNumber = topicNumber;
    }
    
    public int getTopicNumber() {
    	return topicNumber;
    }
}
