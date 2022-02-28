package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.User;
import org.springframework.stereotype.Service;

import static innowise.zuevsky.helpdesk.service.UserDetailsServiceImpl.USER;

@Service
public class UserService {

    public User getCurrentUser() {
        //Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return USER;
    }
}
