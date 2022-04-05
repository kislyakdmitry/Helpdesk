package innowise.zuevsky.helpdesk.domain.enums;

import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
  ROLE_EMPLOYEE,
  ROLE_MANAGER,
  ROLE_ENGINEER;

  public Set<SimpleGrantedAuthority> getAuthorities() {
    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority(this.name()));
    return authorities;
  }
}
