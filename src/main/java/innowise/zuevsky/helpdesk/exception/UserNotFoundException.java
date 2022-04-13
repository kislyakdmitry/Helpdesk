package innowise.zuevsky.helpdesk.exception;

public class UserNotFoundException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "User doesn't exist! Email: %s";

	public UserNotFoundException(String email) {
		super(String.format(DEFAULT_MESSAGE, email));
	}
}
