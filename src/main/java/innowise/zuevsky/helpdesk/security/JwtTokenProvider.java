package innowise.zuevsky.helpdesk.security;

import innowise.zuevsky.helpdesk.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private final UserDetailsService userDetailsService;

  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.header}")
  private String authorizationHeader;

  @Value("${jwt.expiration}")
  private long validityInMilliseconds;

  public JwtTokenProvider(
      @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  private Key getSecretKey() {
    byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String createToken(String username, String role) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("role", role);
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds * 1000);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(getSecretKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean validateToken(String token) throws JwtAuthenticationException {
    try {
      Jws<Claims> claimsJws =
          Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
      return !claimsJws.getBody().getExpiration().before(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      throw new JwtAuthenticationException(
          "JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
    }
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public String resolveToken(HttpServletRequest request) {
    return request.getHeader(authorizationHeader);
  }
}
