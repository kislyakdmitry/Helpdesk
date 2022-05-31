package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.dto.CurrentUser;
import lombok.AllArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@AllArgsConstructor
public class UsersService {

    public CurrentUser getCurrentUser() {
        CurrentUser currentUser = CurrentUser.builder().build();
        KeycloakAuthenticationToken auth = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Principal principal = (Principal) auth.getPrincipal();
        if (principal instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            IDToken token = kPrincipal.getKeycloakSecurityContext().getToken();
            currentUser.setUserName(token.getNickName());
            currentUser.setEmail(token.getEmail());
        }
        return currentUser;
    }
}