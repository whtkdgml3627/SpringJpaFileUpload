package org.zerock.j2.util;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
@Log4j2
public class JWTTests {

  @Autowired
  private JWTUtil jwtUtil;

  @Test
  public void testCreate(){

    Map<String, Object> claim = Map.of("email", "user00@gmail.com");

    String jwtStr = jwtUtil.generate(claim, 10);

    log.info(jwtStr);
  }

  @Test
  public void testToken(){
    String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJlbWFpbCI6InVzZXIwMEBnbWFpbC5jb20iLCJpYXQiOjE2ODk3NDQzNzMsImV4cCI6MTY4OTc0NDk3M30.xSfLY4AkB-ObVwvlC9DJU60cFJmacM1je_4eOu7HbqE";

    try {
      jwtUtil.validateToken(token);
    }catch (Exception e){
      log.info(e);
    }
  }

}
