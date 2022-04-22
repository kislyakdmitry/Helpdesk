package innowise.zuevsky.helpdesk.util;

import innowise.zuevsky.helpdesk.domain.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class SecurityTestUtil {

  public static final String USERNAME = "test@mail.com";
  public static final Role USER_ROLE = Role.ROLE_EMPLOYEE;
  public static final String ENCODED_SECRET_KEY = Base64.getEncoder().encodeToString("helpdeskhelpdeskhelpdeskhelpdesk".getBytes());
  public static final Key SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(ENCODED_SECRET_KEY));

  public static String createTestToken() {
    Claims claims = Jwts.claims().setSubject(SecurityTestUtil.USERNAME);
    claims.put("role", SecurityTestUtil.USER_ROLE.name());
    Date now = new Date();
    Date validity = new Date(now.getTime() + 3600 * 1000);
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(SecurityTestUtil.SECRET_KEY, SignatureAlgorithm.HS256)
        .compact();
  }
}
