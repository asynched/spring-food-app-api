package tech.asynched.ilunch.security;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j()
@Component()
public class JwtTokenGenerator {
  private static int JWT_EXPIRATION = 86400000;
  private static String JWT_SECRET = "osdjfogjosdijfgoisjdfgosdmofinsodijfgoisdjfgoijsdfkgosidmfogijsdoifjgsodijfgoijsdofijgsodijfogsijdofijgsodijfgosidjfogjsdfjgo";

  public String generateToken(Authentication auth) {
    var username = auth.getName();
    var date = new Date();
    var expiration = new Date(date.getTime() + JWT_EXPIRATION);

    return Jwts.builder().setSubject(username).setExpiration(expiration).setIssuedAt(date)
        .signWith(SignatureAlgorithm.HS384, JWT_SECRET).compact();
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validate(String token) {
    try {
      Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      log.error("Invalid token: {}", e.getMessage());
      throw new AuthenticationCredentialsNotFoundException("Invalid token (expired or incorrect)");
    }
  }
}
