package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.exception.AuthenticationTokenNotFoundException;
import innowise.zuevsky.helpdesk.exception.UserNotFoundException;
import innowise.zuevsky.helpdesk.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersService {

	private UsersRepository usersRepository;

	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || auth instanceof AnonymousAuthenticationToken) {
			throw new AuthenticationTokenNotFoundException("Authentication token not found!");
		}
		String email = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
		return usersRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User doesn't exist!"));
	}
}
