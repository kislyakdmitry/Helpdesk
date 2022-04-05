package innowise.zuevsky.helpdesk.exception.feedback;

public class FeedbackTicketBelongsToUserException extends RuntimeException {

  private static final String DEFAULT_MESSAGE =
      "User can't create feedback for ticket! userId:%s, ticketId=%s";

  public FeedbackTicketBelongsToUserException(long userId, long ticketId) {
    super(String.format(DEFAULT_MESSAGE, userId, ticketId));
  }
}
