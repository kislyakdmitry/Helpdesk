package innowise.zuevsky.helpdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.dto.AuthenticationDto;
import innowise.zuevsky.helpdesk.exception.GlobalExceptionHandler;
import innowise.zuevsky.helpdesk.exception.UserNotFoundException;
import innowise.zuevsky.helpdesk.repository.UsersRepository;
import innowise.zuevsky.helpdesk.security.JwtTokenProvider;
import innowise.zuevsky.helpdesk.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthenticationRestControllerTest {

    @InjectMocks
    AuthenticationController authenticationRestController;

    MockMvc mockMvc;


    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    UsersRepository usersRepository;
    @Mock
    JwtTokenProvider jwtTokenProvider;

    User user = UserUtil.createTestUser();

    AuthenticationDto requestLogin = AuthenticationDto.builder()
            .email(user.getEmail())
            .password(user.getPassword())
            .build();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationRestController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    void authenticate_shouldReturnEmailAndToken() throws Exception {
        //given
        String url = "/api/auth/login";

        String token = "token";
        when(usersRepository.findByEmail(requestLogin.getEmail())).thenReturn(Optional.ofNullable(user));
        when(jwtTokenProvider.createToken(requestLogin.getEmail(), user.getRole().name())).thenReturn(token);
        //when
        //then
        mockMvc.perform(post(url).contentType(APPLICATION_JSON).content(asJson(requestLogin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(requestLogin.getEmail()))
                .andExpect(jsonPath("$.token").value(token));

        verify(usersRepository).findByEmail(requestLogin.getEmail());
        verify(jwtTokenProvider).createToken(requestLogin.getEmail(), user.getRole().name());
    }

    @Test
    void authenticate_shouldThrowUserNotFoundException_whenBadUser() throws Exception {
        //given
        String url = "/api/auth/login";
        when(usersRepository.findByEmail(requestLogin.getEmail())).thenThrow(new UserNotFoundException(requestLogin.getEmail()));
        mockMvc.perform(post(url).contentType(APPLICATION_JSON).content(asJson(requestLogin)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(String.format("User doesn't exist! Email: %s", requestLogin.getEmail())));
    }

    @Test
    @WithMockUser
    void logout_shouldLogout() throws Exception {
        String url = "/api/auth/logout";
        mockMvc.perform(post(url)).andExpect(status().isOk());
    }

    private String asJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}