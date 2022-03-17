package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UsersRepository usersRepository;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return Optional.empty(); // пробросить ексепшн
        }
        String email = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
        return usersRepository.findByEmail(email);
    }
}
