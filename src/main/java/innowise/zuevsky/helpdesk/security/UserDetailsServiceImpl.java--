package innowise.zuevsky.helpdesk.security;

import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.exception.UserNotFoundException;
import innowise.zuevsky.helpdesk.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = usersRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(email));
        return SecurityUser.fromUser(user);
    }
}
