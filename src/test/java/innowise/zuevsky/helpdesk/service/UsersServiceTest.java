package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.exception.AuthenticationTokenNotFoundException;
import innowise.zuevsky.helpdesk.exception.UserNotFoundException;
import innowise.zuevsky.helpdesk.repository.UsersRepository;
import innowise.zuevsky.helpdesk.security.SecurityUser;
import innowise.zuevsky.helpdesk.util.UserTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;

    private static final User testUser = UserTestUtil.createTestUser();

    @InjectMocks
    private UsersService usersService;

    @Test
    void getCurrentUser_ShouldPass_IfCurrentUserIsValid() {

        //given
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(auth.getPrincipal()).thenReturn(SecurityUser.fromUser(testUser));
        when(securityContext.getAuthentication()).thenReturn(auth);
        when(usersRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

        //when
        User currentUser = usersService.getCurrentUser();

        //then
        assertThat(currentUser.getEmail()).isEqualTo(testUser.getEmail());
        assertThat(currentUser.getPassword()).isEqualTo(testUser.getPassword());
    }

    @Test
    void getCurrentUser_ShouldThrowException_WhenUserNotFound() {

        //given
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(auth.getPrincipal()).thenReturn(SecurityUser.fromUser(testUser));
        when(securityContext.getAuthentication()).thenReturn(auth);
        when(usersRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> usersService.getCurrentUser())
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(String.format("User doesn't exist! Email: %s", testUser.getEmail()));
    }

    @Test
    void getCurrentUser_shouldThrowAuthenticationTokenNotFoundException_when(){
        //given
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(null);
        //when
        //then
        assertThatThrownBy(()-> usersService.getCurrentUser())
                .isInstanceOf(AuthenticationTokenNotFoundException.class)
                .hasMessage("Authentication token not found!");
    }
    @Test
    void getCurrentUser_shouldThrowAuthenticationTokenNotFoundException_whenUserNotAuthenticated(){
        Authentication auth = mock(Authentication.class);
        auth.setAuthenticated(false);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        assertThatThrownBy(()-> usersService.getCurrentUser())
                .isInstanceOf(AuthenticationTokenNotFoundException.class)
                .hasMessage("Authentication token not found!");

    }
}