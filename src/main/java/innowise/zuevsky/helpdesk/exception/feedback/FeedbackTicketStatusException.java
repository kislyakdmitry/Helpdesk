package innowise.zuevsky.helpdesk.exception.feedback;

public class FeedbackTicketStatusException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Status for ticket is not DONE! ticketId:%s";

    public FeedbackTicketStatusException(long ticketId) {
        super(String.format(DEFAULT_MESSAGE,ticketId));
    }
}
