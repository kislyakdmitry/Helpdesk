package innowise.zuevsky.helpdesk.controller;

import innowise.zuevsky.helpdesk.api.IAuthenticationController;
import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.dto.AuthenticationDto;
import innowise.zuevsky.helpdesk.exception.LoginException;
import innowise.zuevsky.helpdesk.exception.UserNotFoundException;
import innowise.zuevsky.helpdesk.repository.UsersRepository;
import innowise.zuevsky.helpdesk.response.AuthenticationResponse;
import innowise.zuevsky.helpdesk.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController implements IAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationDto request) {
        AuthenticationResponse response = AuthenticationResponse.builder().email(request.getEmail()).build();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = usersRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException(request.getEmail()));
            response.setToken(jwtTokenProvider.createToken(request.getEmail(), user.getRole().name()));
        } catch (Exception exception) {
            throw new LoginException(exception.getMessage());
        }
        return response;
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}