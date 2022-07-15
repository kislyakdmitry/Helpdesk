package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.enums.Role;
import innowise.zuevsky.helpdesk.dto.CurrentUser;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
public class UsersService {

    public CurrentUser getCurrentUser() {
        CurrentUser currentUser = CurrentUser.builder().build();
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Principal principal = (Principal) authentication.getPrincipal();
        if (principal instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            currentUser.setUserName(kPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername());

            List<GrantedAuthority> roles = (List<GrantedAuthority>) authentication.getAuthorities();

            roles.forEach(roleIn-> List.of(Role.values()).forEach(role -> {
                if(role.name().equals(roleIn.getAuthority())){
                    currentUser.setRole(role);
                }
            }));
        }
        return currentUser;
    }
}