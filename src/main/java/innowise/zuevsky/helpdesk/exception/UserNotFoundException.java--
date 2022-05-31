package innowise.zuevsky.helpdesk.exception;

public class UserNotFoundException extends EntityNotFoundException {

	private static final String MESSAGE = "User doesn't exist! Email: %s";

	public UserNotFoundException(String email) {
		super(String.format(MESSAGE, email));
	}
}
