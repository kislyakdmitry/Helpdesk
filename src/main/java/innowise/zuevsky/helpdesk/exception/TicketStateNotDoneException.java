package innowise.zuevsky.helpdesk.exception;

public class TicketStateNotDoneException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "Status for ticket is not DONE! ticketId:%s";

	public TicketStateNotDoneException(long ticketId) {
		super(String.format(DEFAULT_MESSAGE, ticketId));
	}
}
