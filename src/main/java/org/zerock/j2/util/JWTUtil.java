package org.zerock.j2.util;

/*
* # 0719
### OAuth
인터넷 사용자들이 비밀번호를 제공하지 않고 다른 웹사이트 상의 자신들의 정보에 대해 웹사이트나 애플리케이션 접근 권한을 부여할 수 있는 수단 (접근 위임)
토큰을 주고 받으면서 이용하는 인증 방식

### 토큰 기반 인증 (JWT)
AIP 서버의 컨텐츠를 보호하기 위한 목적 → 로그인과는 무관하게 토큰을 이용해 데이터를 가져옴
일반적으로 로그인 시에 액세스/리프레쉬 토큰을 발급해주는 것 뿐임

→ ex) 날씨
토큰 유지는 쿠키나 local storage로
![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a4a52754-6f31-4ce1-b9b2-e2c1ddfa7400/Untitled.png)
액세스 토큰의 보안 문제점을 해결하기 위해 토큰에 제한 시간을 둠
→ 제한 시간이 끝나기 전에 탈취 당해서 이용하는 건 막지 못함
→ refresh Token: 정상적인 사용자가 액세스 토큰과 리프레쉬 토큰을 전달하면 새로운 액세스 토큰을 발급해서 리프레쉬 토큰과 함께 사용자에게 전달

토큰은 JWT 문자열
REFTful: 상태가 없음, 무상태 → 사용자의 로그인 정보를 유지하지 않음 → ‘토큰 기반’

결정 포인트
1. 언제 발행할 것인지
    → 로그인 시에 액세스/리프레쉬 토큰 발급
2. 어떻게 검증할 것인지 (시큐리티 / 인터셉터 / 필터)
    → 인터셉터
3. 예외 처리는 어떻게 할 것인지
    → 토큰이 잘못된 경우, 만료됐을 경우

클라이언트
1. 저장을 어떻게 할 것인지
    → 쿠키로 저장
2. 호출을 어떤 방식으로 할 것인지
    → axios에 헤더 추가 (액세스 토큰 전달)
3. 만료됐을 때 리프레쉬 해줘야 함, 사용자에게 예외 메시지를 보여주면 안 됨 (Silent Refresh)
    리프레쉬 토큰도 만료됐을 땐 로그인하게 해야 함
* */

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class JWTUtil {

  //예외처리
  public static class CustomJWTException extends RuntimeException {
    public CustomJWTException(String msg){
      super(msg);
    }
  }

  @Value("${org.zerock.jwt.secret}")
  private String key;

  public String generate(
   Map<String, Object> claimMap, int min
  ){
    //header
    Map<String, Object> headers = new HashMap<>();
    //헤더의 타입을 지정해줌
    //알고리즘은 기본값이 있어서 패스함
    headers.put("type", "JWT");

    //claims
    Map<String, Object> claims = new HashMap<>();
    //파라미터로 받은 claimMap을 putAll로 전부 넣어줌
    claims.putAll(claimMap);

    //key
    SecretKey key = null;

    try{
      key = Keys.hmacShaKeyFor(this.key.getBytes(StandardCharsets.UTF_8));
    }catch(Exception e){
      e.printStackTrace();
    }

    String jwtStr = Jwts.builder()
      .setHeader(headers)
      .setClaims(claims)
      .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
      .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
      .signWith(key)
      .compact();

    return jwtStr;
  }

  public Map<String, Object> validateToken(String token){
    Map<String, Object> claims = null;

    if(token == null){
      throw new CustomJWTException("NullToken");
    }

    //Exception 오류 try catch로 다중 catch를 쓸 때는 마지막에 Exception으로 해줘야된다
    try {
      SecretKey key = Keys.hmacShaKeyFor(this.key.getBytes(StandardCharsets.UTF_8));

      claims = Jwts.parserBuilder().setSigningKey(key).build()
        .parseClaimsJws(token).getBody();

    }catch (MalformedJwtException e){
      //MalformedJwtException JWT문자열 구성이 잘못됐을 때
      throw new CustomJWTException("Malformed");
    }catch (ExpiredJwtException e){
      //ExpiredJwtException JWT 토근이 만료됐을 때
      throw new CustomJWTException("Expired");
    }catch (InvalidClaimException e){
      //InvalidClaimException 안에 내용물이 잘못 되었을 때 (구성이 잘못 되었을 때?)
      throw new CustomJWTException("Invalid");
    }catch (JwtException e){
      //JwtException JWT에서 뭔가 오류가 났을 때
      throw new CustomJWTException(e.getMessage());
    }catch (Exception e){
      throw new CustomJWTException("Error");
    }

    return claims;
  }

}
