package innowise.zuevsky.helpdesk.exception;

import innowise.zuevsky.helpdesk.response.ErrorResponse;
import innowise.zuevsky.helpdesk.util.FeedbackUtil;
import innowise.zuevsky.helpdesk.util.TicketUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

	@InjectMocks
	GlobalExceptionHandler globalExceptionHandler;

	@Test
	void handleFeedbackExistException_shouldReturnErrorResponse() {
		// given
		FeedbackExistException exception = new FeedbackExistException(FeedbackUtil.FEEDBACK_ID);
		ErrorResponse expected = ErrorResponse.builder().message(exception.getMessage()).status(HttpStatus.BAD_REQUEST)
				.timestamp(LocalDateTime.now()).build();
		// when
		ErrorResponse actual = globalExceptionHandler.handleFeedbackExistException(exception);
		// then
		assertThat(actual).isNotNull();
		assertThat(expected.getMessage()).isEqualTo(actual.getMessage());
		assertThat(expected.getStatus()).isEqualTo(actual.getStatus());
	}

	@Test
	void handleFeedbackNotFoundException_shouldReturnErrorResponse() {
		FeedbackNotFoundException exception = new FeedbackNotFoundException(FeedbackUtil.FEEDBACK_ID);
		ErrorResponse expected = ErrorResponse.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND)
				.timestamp(LocalDateTime.now()).build();
		// when
		ErrorResponse actual = globalExceptionHandler.handleFeedbackNotFoundException(exception);
		// then
		assertThat(actual).isNotNull();
		assertThat(expected.getMessage()).isEqualTo(actual.getMessage());
		assertThat(expected.getStatus()).isEqualTo(actual.getStatus());
	}

	@Test
	void handleTicketOwnerNotBelongsToUserException_shouldReturnErrorResponse() {
		// given
		TicketOwnerNotBelongsToUserException exception = new TicketOwnerNotBelongsToUserException(TicketUtil.TICKET_ID,
				TicketUtil.USER_ID);
		ErrorResponse expected = ErrorResponse.builder().message(exception.getMessage()).status(HttpStatus.BAD_REQUEST)
				.timestamp(LocalDateTime.now()).build();

		// when
		ErrorResponse actual = globalExceptionHandler.handleTicketOwnerNotBelongsToUserException(exception);

		// then
		assertThat(actual).isNotNull();
		assertThat(expected.getMessage()).isEqualTo(actual.getMessage());
		assertThat(expected.getStatus()).isEqualTo(actual.getStatus());
	}

	@Test
	void handleTicketStateNotDoneException_shouldReturnErrorResponse() {
		// given
		TicketStateNotDoneException exception = new TicketStateNotDoneException(TicketUtil.TICKET_ID);
		ErrorResponse expected = ErrorResponse.builder().message(exception.getMessage()).status(HttpStatus.BAD_REQUEST)
				.timestamp(LocalDateTime.now()).build();
		// when
		ErrorResponse actual = globalExceptionHandler.handleTicketStateNotDoneException(exception);

		// then
		assertThat(actual).isNotNull();
		assertThat(expected.getMessage()).isEqualTo(actual.getMessage());
		assertThat(expected.getStatus()).isEqualTo(actual.getStatus());
	}

	@Test
	void handleJwtFilterException_shouldReturnErrorResponse() {
		// given
		JwtFilterException exception = new JwtFilterException("Error", HttpStatus.UNAUTHORIZED);
		ErrorResponse expected = ErrorResponse.builder().message(exception.getMessage()).status(HttpStatus.UNAUTHORIZED)
				.timestamp(LocalDateTime.now()).build();

		// when
		ErrorResponse actual = globalExceptionHandler.handleJwtFilterException(exception);
		// then
		assertThat(actual).isNotNull();
		assertThat(expected.getMessage()).isEqualTo(actual.getMessage());
		assertThat(expected.getStatus()).isEqualTo(actual.getStatus());
	}

	@Test
	void handleTicketNotFoundException_shouldReturnTicketNotFoundException() {
		// given
		TicketNotFoundException exception = new TicketNotFoundException(TicketUtil.TICKET_ID);
		ErrorResponse expected = ErrorResponse.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND)
				.timestamp(LocalDateTime.now()).build();
		// when
		ErrorResponse actual = globalExceptionHandler.handleTicketNotFoundException(exception);
		// then

		assertThat(actual).isNotNull();
		assertThat(expected.getMessage()).isEqualTo(actual.getMessage());
		assertThat(expected.getStatus()).isEqualTo(actual.getStatus());
	}

	@Test
	void handleAuthenticationTokenNotFoundException_shouldReturnAuthenticationTokenNotFoundException() {
		AuthenticationTokenNotFoundException exception = new AuthenticationTokenNotFoundException("Error");
		ErrorResponse expected = ErrorResponse.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND)
				.timestamp(LocalDateTime.now()).build();
		// when
		ErrorResponse actual = globalExceptionHandler.handleAuthenticationTokenNotFoundException(exception);

		// then
		assertThat(expected).isNotNull();
		assertThat(expected.getMessage()).isEqualTo(actual.getMessage());
		assertThat(expected.getStatus()).isEqualTo(actual.getStatus());
	}
}