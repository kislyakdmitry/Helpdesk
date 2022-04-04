package innowise.zuevsky.helpdesk.exception;

public class AuthenticationTokenNotFoundException extends RuntimeException{

    public AuthenticationTokenNotFoundException(String msg) {
        super(msg);
    }
}
