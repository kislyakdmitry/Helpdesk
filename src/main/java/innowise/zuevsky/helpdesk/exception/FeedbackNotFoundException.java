package innowise.zuevsky.helpdesk.exception;

public class FeedbackNotFoundException extends EntityNotFoundException {

	private static final String DEFAULT_MESSAGE = "Feedback doesn't exist! feedbackId:%s";

	public FeedbackNotFoundException(long feedbackId) {
		super(String.format(DEFAULT_MESSAGE, feedbackId));
	}
}
