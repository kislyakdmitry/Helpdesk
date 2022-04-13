package innowise.zuevsky.helpdesk.controller;

import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.dto.AuthenticationRequestDto;
import innowise.zuevsky.helpdesk.exception.UserNotFoundException;
import innowise.zuevsky.helpdesk.repository.UsersRepository;
import innowise.zuevsky.helpdesk.security.JwtTokenProvider;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationRestController {

	private final AuthenticationManager authenticationManager;
	private final UsersRepository usersRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/login")
	public ResponseEntity<Map<Object, Object>> authenticate(@RequestBody AuthenticationRequestDto request) {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			User user = usersRepository.findByEmail(request.getEmail())
					.orElseThrow(() -> new UserNotFoundException(request.getEmail()));
			String token = jwtTokenProvider.createToken(request.getEmail(), user.getRole().name());
			Map<Object, Object> response = new HashMap<>();
			response.put("email", request.getEmail());
			response.put("token", token);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<Object, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", "Invalid email/password combination");
			errorResponse.put("httpStatus", HttpStatus.FORBIDDEN);
			return ResponseEntity.ok(errorResponse);
		}
	}

	@PostMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
		securityContextLogoutHandler.logout(request, response, null);
	}
}
