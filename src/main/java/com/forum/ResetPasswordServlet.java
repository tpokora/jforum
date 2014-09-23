package com.forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/resetPassword")
public class ResetPasswordServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String email = String.valueOf(request.getAttribute("email"));
		
		PrintWriter out = response.getWriter();
		out.println("Email: " + email);
		out.println("pass:" + randomPassword());
		//sendMail();
	}
	
	private String randomPassword() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder(8);
		for(int i = 0; i < 8; i++) {
			char tmp = (char)('a' + random.nextInt('z' - 'a'));
			sb.append(tmp);
		}
		return sb.toString();
	}
	
	private void sendMail() {
		final String fromEmail = "strzupak@gmail.com";
		final String password ="zcQE#4da";
		String toEmail = "strzupak@gmail.com";
		
		System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        
        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        
        Session session = Session.getInstance(props, auth);
        
        EmailUtil.sendEmail(session, toEmail,"TLSEmail Testing Subject", "TLSEmail Testing Body");
	}
	
	
}
