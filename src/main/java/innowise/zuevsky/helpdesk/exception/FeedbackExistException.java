package innowise.zuevsky.helpdesk.exception;

public class FeedbackExistException extends EntityNotFoundException {

	private static final String DEFAULT_MESSAGE = "Feedback already exists! TicketId:%s";

	public FeedbackExistException(long id) {
		super(String.format(DEFAULT_MESSAGE, id));
	}
}
