package innowise.zuevsky.helpdesk.security;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import innowise.zuevsky.helpdesk.exception.JwtAuthenticationException;
import innowise.zuevsky.helpdesk.util.SecurityUtil;
import innowise.zuevsky.helpdesk.util.UserUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

  private UserDetailsService userDetailsService;
  private JwtTokenProvider jwtTokenProvider;
  private HttpServletRequest request;

  @BeforeEach
  void init() {
    userDetailsService = Mockito.mock(UserDetailsServiceImpl.class);
    request = Mockito.mock(HttpServletRequest.class);
    jwtTokenProvider = new JwtTokenProvider(
        userDetailsService, 3600, SecurityUtil.ENCODED_SECRET_KEY, "Authorization");
  }

  @Test
  void createToken_ShouldPass_WhenTokenCreatedSuccessfully() {

    //when
    String token = jwtTokenProvider.createToken(SecurityUtil.USERNAME,
        SecurityUtil.USER_ROLE.name());
    String username = Jwts.parserBuilder().setSigningKey(SecurityUtil.ENCODED_SECRET_KEY).build()
        .parseClaimsJws(token).getBody().getSubject();

    //then
    assertThat(token).isNotNull();
    assertThat(username).isEqualTo(SecurityUtil.USERNAME);
  }

  @Test
  void validateToken_ShouldPass_WhenTokenIsNotExpired() throws JwtAuthenticationException {

    //when
    String token = SecurityUtil.createTestToken();

    //then
    assertThat(jwtTokenProvider.validateToken(token)).isTrue();
  }

  @Test
  void validateToken_ShouldThrowException_WhenTokenIsExpired() {

    //when
    Claims claims = Jwts.claims().setSubject(SecurityUtil.USERNAME);
    claims.put("role", SecurityUtil.USER_ROLE.name());
    Date now = new Date();
    Date validity = new Date(now.getTime() + 100);
    String token = Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(SecurityUtil.SECRET_KEY, SignatureAlgorithm.HS256)
        .compact();

    //then
    assertThatThrownBy(() -> jwtTokenProvider.validateToken(token))
        .isInstanceOf(JwtAuthenticationException.class)
        .hasMessage("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
  }

  @Test
  void getAuthentication_ShouldPass_WhenActualAuthenticationIsEqualToExpectedAuthentication() {

    //given
    String token = SecurityUtil.createTestToken();
    UserDetails userDetails = SecurityUser.fromUser(UserUtil.createTestUser());
    Authentication expectedAuthentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    when(userDetailsService.loadUserByUsername(SecurityUtil.USERNAME)).thenReturn(userDetails);

    //when
    Authentication actualAuthentication = jwtTokenProvider.getAuthentication(token);

    //then
    assertThat(actualAuthentication).isEqualTo(expectedAuthentication);
  }

  @Test
  void getUsername_ShouldPass_WhenActualUsernameIsEqualToExpectedUsername() {

    //given
    String token = SecurityUtil.createTestToken();
    String expectedUsername = Jwts.parserBuilder().setSigningKey(SecurityUtil.ENCODED_SECRET_KEY).build().parseClaimsJws(token).getBody().getSubject();

    //when
    String actualUsername = jwtTokenProvider.getUsername(token);

    //then
    assertThat(actualUsername).isEqualTo(expectedUsername);
  }

  @Test
  void resolveToken_ShouldPass_WhenActualTokenIsEqualToExpectedToken() {

    //given
    String expectedToken = "token";
    when(request.getHeader("Authorization")).thenReturn(expectedToken);

    //when
    String actualToken = jwtTokenProvider.resolveToken(request);

    //then
    assertThat(actualToken).isEqualTo(expectedToken);
  }
}