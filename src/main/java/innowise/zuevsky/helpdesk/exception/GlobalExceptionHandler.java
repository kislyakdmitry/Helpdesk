package innowise.zuevsky.helpdesk.exception;

import innowise.zuevsky.helpdesk.response.ErrorResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(FeedbackExistException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleFeedbackExistException(FeedbackExistException feedbackServiceException) {
		return ErrorResponse.builder().message(feedbackServiceException.getMessage()).status(HttpStatus.BAD_REQUEST)
				.timestamp(LocalDateTime.now()).build();
	}

	@ExceptionHandler(FeedbackNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleFeedbackNotFoundException(FeedbackNotFoundException notFoundException) {
		return ErrorResponse.builder().message(notFoundException.getMessage()).status(HttpStatus.NOT_FOUND)
				.timestamp(LocalDateTime.now()).build();
	}

	@ExceptionHandler(TicketOwnerNotBelongsToUserException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleTicketOwnerNotBelongsToUserException(
			TicketOwnerNotBelongsToUserException feedbackServiceException) {
		return ErrorResponse.builder().message(feedbackServiceException.getMessage()).status(HttpStatus.BAD_REQUEST)
				.timestamp(LocalDateTime.now()).build();
	}

	@ExceptionHandler(TicketStateNotDoneException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleTicketStateNotDoneException(TicketStateNotDoneException ticketStateNotDoneException) {
		return ErrorResponse.builder().message(ticketStateNotDoneException.getMessage()).status(HttpStatus.BAD_REQUEST)
				.timestamp(LocalDateTime.now()).build();
	}

	@ExceptionHandler(JwtFilterException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorResponse handleJwtFilterException(JwtFilterException jwtFilterException) {
		return ErrorResponse.builder().message(jwtFilterException.getMessage()).status(HttpStatus.UNAUTHORIZED)
				.timestamp(LocalDateTime.now()).build();
	}

}
