package innowise.zuevsky.helpdesk.exception;

public class TicketNotFoundException extends EntityNotFoundException {

	private static final String DEFAULT_MESSAGE = "Ticket doesn't exist! ID: ";

	public TicketNotFoundException(Long id) {
		super(DEFAULT_MESSAGE + id);
	}
}
