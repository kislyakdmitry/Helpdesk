package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.domain.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    public static final User USER = User.builder().id(1L).email("user@email.com").password("pass").role(Role.EMPLOYEE).build();

    @Override
    public UserDetails loadUserByUsername(String email) {
        PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(USER.getRole().name()));
        return new org.springframework.security.core.userdetails.User(USER.getEmail(),
                                                                      passwordEncoder.encode(USER.getPassword()),
                                                                      roles);
    }
}
