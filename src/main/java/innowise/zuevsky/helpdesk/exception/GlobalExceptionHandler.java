package innowise.zuevsky.helpdesk.exception;

import innowise.zuevsky.helpdesk.exception.feedback.FeedbackExistException;
import innowise.zuevsky.helpdesk.exception.feedback.FeedbackNotFoundException;
import innowise.zuevsky.helpdesk.exception.feedback.FeedbackTicketBelongsToUserException;
import innowise.zuevsky.helpdesk.exception.feedback.FeedbackTicketStatusException;
import innowise.zuevsky.helpdesk.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeedbackExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFeedbackExistException(FeedbackExistException feedbackServiceException){
        return ErrorResponse.builder()
                .message(feedbackServiceException.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(FeedbackNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(FeedbackNotFoundException notFoundException ){
        return ErrorResponse.builder()
                .message(notFoundException.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(FeedbackTicketBelongsToUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFeedbackBelongsToUserException(FeedbackTicketBelongsToUserException feedbackServiceException){
        return ErrorResponse.builder()
                .message(feedbackServiceException.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(FeedbackTicketStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFeedbackTicketStatusException(FeedbackTicketStatusException feedbackTicketStatusException){
        return ErrorResponse.builder()
                .message(feedbackTicketStatusException.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTicketNotFoundException(TicketNotFoundException exception) {
        return ErrorResponse.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now()).build();
    }

}