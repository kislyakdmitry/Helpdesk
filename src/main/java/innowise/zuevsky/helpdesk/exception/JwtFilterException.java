package innowise.zuevsky.helpdesk.exception;

import org.springframework.http.HttpStatus;

public class JwtFilterException extends RuntimeException {

	public JwtFilterException(String message, HttpStatus httpStatus) {
		super(String.format(message, httpStatus));
	}

}
