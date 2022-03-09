package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getCurrentUser() {
        //Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return null;
    }
}
