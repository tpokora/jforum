package com.forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

import com.mysql.jdbc.PreparedStatement;

@WebServlet("/deleteCategory")
public class DeleteCategoryServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		
		String category = String.valueOf(request.getParameter("category"));
		out.println("Category: " + category);
		deleteCategoryToDB(category, request, response);
		response.sendRedirect("myprofile.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void deleteCategoryToDB(String category, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("Adding category");
			String query = "DELETE FROM category WHERE categoryName = ?";
			PreparedStatement preStatement = (PreparedStatement) MySQLConnector.connectToDB()
					.prepareStatement(query);
			preStatement.setString(1, category);
			preStatement.executeUpdate();
			
		} catch (SQLException e) {
			HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.CATEGORY_NOT_DELETED));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
		} catch (Exception e) {
			HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.CATEGORY_NOT_DELETED));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
