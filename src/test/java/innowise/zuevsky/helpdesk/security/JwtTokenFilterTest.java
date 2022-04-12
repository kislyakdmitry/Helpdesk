package innowise.zuevsky.helpdesk.security;

import innowise.zuevsky.helpdesk.exception.JwtAuthenticationException;
import innowise.zuevsky.helpdesk.exception.JwtFilterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenFilterTest {

	@InjectMocks
	JwtTokenFilter jwtTokenFilter;

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	FilterChain chain;
	@Mock
	JwtTokenProvider jwtTokenProvider;

	@Test
	void doFilter_shouldThrowJwtFilterException_whenTokenIsExpiredOrInvalid()
			throws JwtAuthenticationException, ServletException, IOException {
		// when
		when(jwtTokenProvider.resolveToken(request)).thenReturn("token");
		when(jwtTokenProvider.validateToken("token"))
				.thenThrow(new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED));

		JwtFilterException exception = assertThrows(JwtFilterException.class, () -> {
			jwtTokenFilter.doFilter(request, response, chain);
		});
		// then
		assertInstanceOf(JwtFilterException.class, exception);
		assertEquals(String.format("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED), exception.getMessage());

		verify(jwtTokenProvider).resolveToken(any(HttpServletRequest.class));
		verify(jwtTokenProvider).validateToken(anyString());
		verify(jwtTokenProvider, never()).getAuthentication(anyString());
		verify(chain, never()).doFilter(any(), any());
	}

	@Test
	void doFilter_shouldSuccess_whenUserAuthenticated() throws Exception {
		// given
		JwtTokenFilter spy = Mockito.spy(jwtTokenFilter);
		Authentication authentication = mock(Authentication.class);
		when(jwtTokenProvider.resolveToken(request)).thenReturn("token");
		when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
		when(jwtTokenProvider.getAuthentication(anyString())).thenReturn(authentication);
		// when
		spy.doFilter(request, response, chain);
		// then
		verify(spy).doFilter(request, response, chain);
		verify(jwtTokenProvider).resolveToken(any(HttpServletRequest.class));
		verify(jwtTokenProvider).validateToken(anyString());
		verify(jwtTokenProvider).getAuthentication(anyString());
		verify(chain).doFilter(any(), any());
	}

}