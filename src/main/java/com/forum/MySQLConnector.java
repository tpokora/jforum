package com.forum;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLConnector {
    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement preStatement = null;
    private static ResultSet result = null;
    
    public static Connection connectToDB() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
                      
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.8.65.130:3306/jforum", 
            		"adminj8kUaRf", "KrNALuQM6baJ");
            return connection;
        } catch(SQLException e){
            throw new RuntimeException("Cannot connect the database!", e);
        } 
    }
}
