package com.maingocdieu.shopping.security.jwt;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.maingocdieu.shopping.security.userprincal.UserPrinciple;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {
  private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

  private String jwtSercret = "dieumaingoc";
  private int jwtExpiration = 86400;

  public String createToken(Authentication authentication) {

    UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

    return Jwts.builder().setSubject(userPrinciple.getUsername()).setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + jwtExpiration * 1000))
        .signWith(SignatureAlgorithm.HS512, jwtSercret).compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(jwtSercret).parseClaimsJws(token);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature");
    } catch (ExpiredJwtException e) {
      logger.error("Invalid JWT ExpiredJwtException");
    } catch (UnsupportedJwtException e) {
      logger.error("Invalid JWT UnsupportedJwtException");
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT MalformedJwtException");
    } catch (IllegalArgumentException e) {
      logger.error("Invalid JWT IllegalArgumentException");
    }
    return false;
  }
  
  public String getUserNameFromToken(String token) {
    String userName = Jwts.parser().setSigningKey(jwtSercret).parseClaimsJws(token).getBody().getSubject();
    return userName;
  }

}
