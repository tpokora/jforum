package com.forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.PreparedStatement;

@WebServlet("/changeCatOrder")
public class ChangeCatOrderServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8"); 
		
		PrintWriter out = response.getWriter();
		
		Enumeration<String> parameterNames = request.getParameterNames();
		Map<String, Integer> categoriesOrder = new HashMap<String, Integer>();
		
		while(parameterNames.hasMoreElements()) {
			String categoryName = parameterNames.nextElement();
			int order = Integer.parseInt(request.getParameter(categoryName));
			
			categoriesOrder.put(categoryName, order);
		}
		
		for(String key : categoriesOrder.keySet()) {
			out.println(key + " : " + categoriesOrder.get(key));
			changeCategoryOrder(key, categoriesOrder.get(key), request, response);
		}
		
		response.sendRedirect("myprofile.jsp");
	}
	
	private void changeCategoryOrder(String categoryName, Integer position, 
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
		try {
			String query = "UPDATE category SET position = ? WHERE categoryName = ?";
			PreparedStatement preStatement = (PreparedStatement) MySQLConnector.connectToDB()
					.prepareStatement(query);
			preStatement.setInt(1, position);
			preStatement.setString(2, categoryName);
			preStatement.executeUpdate();
		} catch (SQLException e) {
			HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.CATEGORY_ORDER_NOT_CHANGED));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
		} catch (Exception e) {
			HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.CATEGORY_ORDER_NOT_CHANGED));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

}
