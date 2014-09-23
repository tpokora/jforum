package com.forum;

import com.forum.beans.User;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * @author denat
 */
@WebServlet("/registration")
public class UserRegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        User userToRegister = new User();
        userToRegister.setLogin(request.getParameter("login"));
        userToRegister.setPassword(request.getParameter("password"));
        String password2 = request.getParameter("password2");
        String email = request.getParameter("email");
        userToRegister.setEmail(email);
      
        if(isLoginFree(userToRegister.getLogin())) {
        	if(isEmailCorrect(userToRegister.getEmail())) {
	            if(registrationPasswordsVerification(userToRegister.getPassword(), password2)) {
	                userRegistration(userToRegister, request, response);
	                response.sendRedirect("index.jsp");
	            }
	            else {
	                // passwords different
	                HttpSession session = request.getSession();
	                session.setAttribute("error", Errorer.errorMessage(Errorer.Error.PASSWORD_NOT_CONFIRMED));
	                request.getRequestDispatcher("/error.jsp").forward(request, response);
	            }
	        } else {
	        	// emeil incorrect
                HttpSession session = request.getSession();
                session.setAttribute("error", Errorer.errorMessage(Errorer.Error.BAD_EMAIL));
                request.getRequestDispatcher("/error.jsp").forward(request, response);
	        }
        }
        else {
            // login taken
            HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.USER_NAME_TAKEN));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    
    private boolean isLoginFree(String login) {
        boolean free = true;
        try {
            
            String query = "SELECT login FROM forumUsers";
            Statement statement = (Statement) MySQLConnector.connectToDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            
            while(result.next()) {
                String loginInDB = result.getString("login");
                if(loginInDB.equals(login)) {
                    free = false;
                }
            }
            
            return free;
        } catch (Exception ex) {
            return free;
        }

    }
    
    private boolean registrationPasswordsVerification(String password, String password2) {
        if(password.equals(password2))
            return true;
        else 
            return false;
    }
    
    private void userRegistration(User user, HttpServletRequest request, 
    		HttpServletResponse response) throws ServletException, IOException {
        try {
            String query = "INSERT INTO forumUsers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preStatement = (PreparedStatement) MySQLConnector.connectToDB().prepareStatement(query);
            preStatement.setString(1, "0");
            preStatement.setString(2, user.getLogin());
            preStatement.setString(3, md5Password(user.getPassword()));
            preStatement.setString(4, user.getEmail());
            preStatement.setString(5, "1");
            preStatement.setString(6, currentDate());
            preStatement.setString(7, "0");
            preStatement.setString(8, "0");
            preStatement.setString(9, "0");
            preStatement.executeUpdate();
        } catch (Exception ex) {
        	HttpSession session = request.getSession();
            session.setAttribute("error", Errorer.errorMessage(Errorer.Error.TOPIC_NOT_ADDED));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
        
        
    }
    
    public static boolean isEmailCorrect(String email) {
    	Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9\\.]+$");
    	Matcher matcher = pattern.matcher(email);
    	if(matcher.matches())
			return true;
		else
			return false;
    }
    
    private String currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    public static String md5Password(String password) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(password.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashPassword = bigInt.toString(16);
            return hashPassword;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserRegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
