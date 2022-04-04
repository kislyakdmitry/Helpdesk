package innowise.zuevsky.helpdesk.exception.feedback;

public class FeedbackTicketBelongsToUserException extends RuntimeException{
    public FeedbackTicketBelongsToUserException(String message) {
        super(message);
    }
}
