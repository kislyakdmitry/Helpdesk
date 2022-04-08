package innowise.zuevsky.helpdesk.exception;

public class TicketOwnerNotBelongsToUserException extends RuntimeException {

  private static final String DEFAULT_MESSAGE =
      "Ticket owner not belongs to user for ticket! ticketId=%s, userId:%s";

  public TicketOwnerNotBelongsToUserException(Long ticketId, Long userId) {
    super(String.format(DEFAULT_MESSAGE, ticketId, userId));
  }
}
