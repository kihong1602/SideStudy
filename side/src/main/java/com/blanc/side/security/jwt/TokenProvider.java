package com.blanc.side.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

/**
 * 추가된 라이브러리를 사용해서 JWT를 생성하고 검증하는 컴포넌트
 */
@Component
@Slf4j
public class TokenProvider implements InitializingBean {

  private static final String AUTHORITIES_KEY = "auth";
  private final String secret;
  private final long tokenValidityInMilliseconds;
  private Key key;

  /**
   * Bean이 생성되고 주입을 받은 후에 secret 값을 Base64 Decode해서 Key변수에 할당하기 위함.
   */
  public TokenProvider(@Value("${secret}") String secret,
      @Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds) {
    this.secret = secret;
    this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * Authentication 의 권한정보를 이용해서 토큰을 생성하는 메서드
   */
  public String createToken(Authentication authentication) {
    String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(
        Collectors.joining(","));

    long now = (new Date()).getTime();
    Date validity = new Date(now + this.tokenValidityInMilliseconds);

    return Jwts.builder().setSubject(authentication.getName())
               .claim(AUTHORITIES_KEY, authorities)
               .signWith(key, SignatureAlgorithm.HS512)
               .setExpiration(validity)
               .compact();
  }

  /** token에 담겨있는 정보를 이용해 Authentication객체를 리턴하는 메서드*/
  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
              .map(SimpleGrantedAuthority::new).toList();
    User principal = new User(claims.getSubject(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  /**
   * token의 유효성 검사 메서드
   */
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT 토큰이 잘못되었습니다.");
    }
    return false;
  }
}
