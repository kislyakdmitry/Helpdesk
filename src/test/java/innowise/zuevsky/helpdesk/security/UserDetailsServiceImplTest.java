package innowise.zuevsky.helpdesk.security;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.exception.UserNotFoundException;
import innowise.zuevsky.helpdesk.repository.UsersRepository;
import innowise.zuevsky.helpdesk.util.UserTestUtil;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

  @Mock
  private UsersRepository usersRepository;

  @InjectMocks
  UserDetailsServiceImpl userDetailsService;

  @Test
  void loadUserByUsername_ShouldPass_WhenActualUserDetailsIsValid() {

    //given
    User user = UserTestUtil.createTestUser();
    UserDetails expectedUserDetails = SecurityUser.fromUser(user);
    when(usersRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

    //when
    UserDetails actualUserDetails = userDetailsService.loadUserByUsername(user.getEmail());

    //then
    assertThat(actualUserDetails).isEqualTo(expectedUserDetails);
  }

  @Test
  void loadUserByUsername_ShouldThrowException_WhenUserNotFound() {
    String wrongEmail = "email@com";
    assertThatThrownBy(() -> userDetailsService.loadUserByUsername(wrongEmail))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessage(String.format("User doesn't exist! Email: %s", wrongEmail));
  }
}