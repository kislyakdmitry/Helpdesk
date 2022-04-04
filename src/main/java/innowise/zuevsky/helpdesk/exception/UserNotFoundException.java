package innowise.zuevsky.helpdesk.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
