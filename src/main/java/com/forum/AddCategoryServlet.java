package com.forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import com.mysql.jdbc.PreparedStatement;

@WebServlet("/addCategory")
public class AddCategoryServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String category = String.valueOf(request.getParameter("category"));
		addCategoryToDB(category);
		out.println("Category: " + category);
		response.sendRedirect("myprofile.jsp");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void addCategoryToDB(String category) {
		try {
			System.out.println("Adding category");
			String query = "INSERT INTO category(categoryName) VALUES (?)";
			PreparedStatement preStatement = (PreparedStatement) MySQLConnector
					.connectToDB().prepareStatement(query);
			preStatement.setString(1, category);
			preStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
