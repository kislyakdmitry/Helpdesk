package innowise.zuevsky.helpdesk.domain.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    EMPLOYEE(Set.of(Permission.TICKET_GET)),
    MANAGER(Set.of(Permission.TICKET_GET, Permission.TICKET_POST)),
    ENGINEER(Set.of(Permission.TICKET_GET));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
