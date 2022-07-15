package innowise.zuevsky.helpdesk.exception;

import innowise.zuevsky.helpdesk.response.ErrorResponse;
import innowise.zuevsky.helpdesk.util.FeedbackTestUtil;
import innowise.zuevsky.helpdesk.util.TicketTestUtil;
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
        FeedbackExistException exception = new FeedbackExistException(FeedbackTestUtil.FEEDBACK_ID);
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
        FeedbackNotFoundException exception = new FeedbackNotFoundException(FeedbackTestUtil.FEEDBACK_ID);
        ErrorResponse expected = ErrorResponse.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now()).build();
        // when
        ErrorResponse actual = globalExceptionHandler.handleEntityNotFoundException(exception);
        // then
        assertThat(actual).isNotNull();
        assertThat(expected.getMessage()).isEqualTo(actual.getMessage());
        assertThat(expected.getStatus()).isEqualTo(actual.getStatus());
    }

    @Test
    void handleTicketOwnerNotBelongsToUserException_shouldReturnErrorResponse() {
        // given
        TicketOwnerNotBelongsToUserException exception = new TicketOwnerNotBelongsToUserException(TicketTestUtil.TICKET_ID,
                TicketTestUtil.OWNER_NAME);
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
        TicketStateNotDoneException exception = new TicketStateNotDoneException(TicketTestUtil.TICKET_ID);
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
    void handleTicketNotFoundException_shouldReturnTicketNotFoundException() {
        // given
        TicketNotFoundException exception = new TicketNotFoundException(TicketTestUtil.TICKET_ID);
        ErrorResponse expected = ErrorResponse.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now()).build();
        // when
        ErrorResponse actual = globalExceptionHandler.handleEntityNotFoundException(exception);
        // then
        assertThat(actual).isNotNull();
        assertThat(expected.getMessage()).isEqualTo(actual.getMessage());
        assertThat(expected.getStatus()).isEqualTo(actual.getStatus());
    }

}