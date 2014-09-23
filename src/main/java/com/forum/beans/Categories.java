package com.forum.beans;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.forum.MySQLConnector;
import com.mysql.jdbc.Statement;

public class Categories {
	private ArrayList<Category> list = new ArrayList<Category>();
	
	public Categories() {
		setList();
	}
	
	
	
	public ArrayList<Category> getList() {
		return list;
	}
	
	public void setList() {
		String query = "SELECT * FROM category ORDER BY `position`";
        Statement statement = null;
        try {
            statement = (Statement) MySQLConnector.connectToDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                Category categoryFromDB = new Category();
                categoryFromDB.setName(String.valueOf(result.getString("categoryName")));
                categoryFromDB.setPosition(result.getInt("position"));
                list.add(categoryFromDB);
            }
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}
