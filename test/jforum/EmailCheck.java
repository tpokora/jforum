package jforum;

import static org.junit.Assert.*;

import org.junit.Test;

import com.forum.UserRegistrationServlet;

public class EmailCheck {

	String email = "strzupak@gmail.com";
	String email1 = "@gmail.com";
	String email2 = "strzupak.gmail.com";
	String email3 = "strzupak@";
	String email4 = "strzupak@gmail";
	
	@Test
	public void test1() {
		assertTrue("Mail " + email + " pasuje", UserRegistrationServlet.isEmailCorrect(email));
	}
	
	@Test
	public void test2() {
		assertTrue("Mail " + email1 + " nie pasuje", UserRegistrationServlet.isEmailCorrect(email1));
	}
	
	@Test
	public void test3() {
		assertTrue("Mail " + email2 + " nie pasuje", UserRegistrationServlet.isEmailCorrect(email2));
	}
	
	@Test
	public void test4() {
		assertTrue("Mail " + email3 + " nie pasuje", UserRegistrationServlet.isEmailCorrect(email3));
	}
	
	@Test
	public void test5() {
		assertTrue("Mail " + email4 + " nie pasuje", UserRegistrationServlet.isEmailCorrect(email4));
	}
	

}
