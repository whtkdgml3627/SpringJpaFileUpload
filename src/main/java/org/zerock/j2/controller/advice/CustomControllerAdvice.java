package org.zerock.j2.controller.advice;

/*
* RestController 의 예외처리
* @RestControllerAdvice 어노테이션 추가
* 얘가 예외처리 담당해줌
* */

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zerock.j2.service.MemberServiceImpl;

import java.util.Map;

@RestControllerAdvice
@Log4j2
public class CustomControllerAdvice {

  //login exception service에 있는 애로 만들어줌
  @ExceptionHandler(MemberServiceImpl.MemberLoginException.class)
  public ResponseEntity<Map<String, String>> handleException(MemberServiceImpl.MemberLoginException e){
    log.info("--------------------------------------------------");
    log.info(e.getMessage());
    
    //return 타입에 status로 HttpStatus(200)메세지로 전달 후 Map으로 메세지 전달
    return ResponseEntity.status(HttpStatus.OK).body(Map.of("errorMsg", "Login Fail"));
  }

}
