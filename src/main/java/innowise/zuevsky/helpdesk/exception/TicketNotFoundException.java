package innowise.zuevsky.helpdesk.exception;

public class TicketNotFoundException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Ticket doesn't exist! ID: %d";

    public TicketNotFoundException(Long id) {
        super(String.format(DEFAULT_MESSAGE, id));
    }
}
