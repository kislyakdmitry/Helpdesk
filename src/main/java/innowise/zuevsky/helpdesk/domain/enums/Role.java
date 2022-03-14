package innowise.zuevsky.helpdesk.domain.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public enum Role {
    EMPLOYEE, MANAGER, ENGINEER;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(this.name()));
        return authorities;
    }
}
