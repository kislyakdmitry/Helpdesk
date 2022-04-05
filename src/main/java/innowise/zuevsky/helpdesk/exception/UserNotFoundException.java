package innowise.zuevsky.helpdesk.exception;

public class UserNotFoundException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "User doesn't exist! Email: ";

  public UserNotFoundException(String email) {
    super(DEFAULT_MESSAGE + email);
  }
}
