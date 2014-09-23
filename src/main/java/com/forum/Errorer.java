package com.forum;

public class Errorer {
    public enum Error {
        USER_NOT_FOUND, WRONG_PASSWORD, PASSWORD_NOT_CONFIRMED,
        USER_NAME_TAKEN, TOPIC_NOT_ADDED, POST_NOT_ADDED, USER_NOT_LOGGED,
        CATEGORY_ORDER_NOT_CHANGED, TOPIC_NOT_CLOSED, CATEGORY_NOT_DELETED,
        POST_NOT_EDITED, EMAIL_NOT_CHANGED, PASSWORD_NOT_CHANGED, TOPIC_NOT_OPENED,
        USER_NOT_REGISTERED, BAD_EMAIL
    }

    public static String errorMessage(final Error error) {
        String errorMessage;
        switch(error) {
            case USER_NOT_FOUND:
                errorMessage = "Wrong Username! <a href='index.jsp'>Try again</a>";
                break;
            case WRONG_PASSWORD:
                errorMessage = "Wrong password! <a href='index.jsp'>Try again</a>";
                break;
            case PASSWORD_NOT_CONFIRMED:
                errorMessage = "Password don't match! <a href='register.jsp'>Try again</a>";
                break;
            case USER_NAME_TAKEN:
                errorMessage = "User already exists! <a href='register.jsp'>Try again</a>";
                break;
            case TOPIC_NOT_ADDED:
                errorMessage = "Couldn't add topic! <a href='index.jsp'>Try again</a>";
                break;
            case POST_NOT_ADDED:
                errorMessage = "Couldn't add post! <a href='index.jsp'>Try again</a>";
                break;
            case USER_NOT_LOGGED:
            	errorMessage = "You are not logged in! <a href='index.jsp'>Try again</a>";
            	break;
            case CATEGORY_ORDER_NOT_CHANGED:
            	errorMessage = "Couldn't change category! <a href='myprofile.jsp'>Try again</a>";
            	break;
            case TOPIC_NOT_CLOSED:
            	errorMessage = "Couldn't close topic! <a href='index.jsp'>Try again</a>";
            	break;
            case CATEGORY_NOT_DELETED:
            	errorMessage = "Couldn't delete category! <a href='myprofile.jsp'>Try again</a>";
            	break;
            case POST_NOT_EDITED:
            	errorMessage = "Couldn't edit post! <a href='index.jsp'>Try again</a>";
            	break;
            case EMAIL_NOT_CHANGED:
            	errorMessage = "Couldn't change email! <a href='myprofile.jsp'>Try again</a>";
            	break;
            case PASSWORD_NOT_CHANGED:
            	errorMessage = "Couldn't change password! <a href='myprofile.jsp'>Try again</a>";
            	break;
            case TOPIC_NOT_OPENED:
            	errorMessage = "Couldn't open topic! <a href='index.jsp'>Try again</a>";
            	break;
            case USER_NOT_REGISTERED:
            	errorMessage = "User not registered! <a href='register.jsp'>Try again</a>";
            	break;
            case BAD_EMAIL:
            	errorMessage = "Bad email address! <a href='register.jsp'>Try again</a>";
            	break;
            default:
                errorMessage = "Unknown Error! <a href='index.jsp'>Go back</a>";
                
        }
    
        return errorMessage;
    }
    
}
