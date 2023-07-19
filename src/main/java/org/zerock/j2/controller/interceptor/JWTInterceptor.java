package org.zerock.j2.controller.interceptor;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.zerock.j2.util.JWTUtil;

import java.util.Map;

@Component
@Log4j2
@RequiredArgsConstructor
public class JWTInterceptor implements HandlerInterceptor {

  private final JWTUtil jwtUtil;

  //리턴타입이 boolean
  //다음단계로 넘어갈지 말지 정해줌
  @Override
  public boolean preHandle(
    HttpServletRequest request,
    HttpServletResponse response,
    Object handler
  ) throws Exception {
    //인터셉터를 언제 동작시킬지 설정

    //preflight 라면
    if(request.getMethod().equals("OPTIONS")){
      return true;
    }

    //헤더에 담겨오는 엑세스 토큰 가져오기
    try {
      String headerStr = request.getHeader("Authorization");
      
      //헤더 메시지가 없거나 짧을 때 예외처리
      if(headerStr == null || headerStr.isEmpty() || headerStr.length() < 7){
        throw new JWTUtil.CustomJWTException("NullToken");
      }

      //Bearer 엑세스 토큰
      String accessToken = headerStr.substring(7);

      Map<String, Object> claims = jwtUtil.validateToken(accessToken);

      log.info("result: " + claims);
    }catch (Exception e){
      //지금부터 보내는 데이터의 mimeType - JSON타입
      response.setContentType("application/json");

      //Gson 라이브러리
      //제이슨 문자열로 바꿔주는 라이브러리
      Gson gson = new Gson();

      String str = gson.toJson(Map.of("Error", e.getMessage()));

      response.getOutputStream().write(str.getBytes());

      return false;
    }

    log.info("=========================================================================");
    log.info(handler);
    log.info("=========================================================================");
    log.info(jwtUtil);
    log.info("=========================================================================");
    log.info("=========================================================================");


    return true;
  }

}
