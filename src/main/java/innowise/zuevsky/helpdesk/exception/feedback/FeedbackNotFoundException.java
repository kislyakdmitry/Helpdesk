package innowise.zuevsky.helpdesk.exception.feedback;

public class FeedbackNotFoundException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "Feedback doesn't exist! feedbackId:%s";

  public FeedbackNotFoundException(long feedbackId) {
    super(String.format(DEFAULT_MESSAGE, feedbackId));
  }
}