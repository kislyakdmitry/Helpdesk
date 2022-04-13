package innowise.zuevsky.helpdesk.exception;

import javax.naming.AuthenticationException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtAuthenticationException extends AuthenticationException {

	private final HttpStatus httpStatus;

	public JwtAuthenticationException(String msg, HttpStatus httpStatus) {
		super(msg);
		this.httpStatus = httpStatus;
	}
}
