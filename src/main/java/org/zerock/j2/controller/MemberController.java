package org.zerock.j2.controller;

/*
* 로그인 작업은 post방식으로 작업
* */

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.j2.dto.MemberDTO;
import org.zerock.j2.service.MemberService;
import org.zerock.j2.service.SocialService;

import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/member/")
@Log4j2
public class MemberController {

  //service 의존성 주입
  private final MemberService memberService;
  private final SocialService socialService;

  //kakao login
  @GetMapping("kakao")
  public MemberDTO getAuthCode(
    String code
  ){
    //화면에서 받은 인가코드 호출
    log.info("=============================================================");
    log.info(code);

    String email = socialService.getKakaoEmail(code);

    MemberDTO memberDTO = memberService.getMemberWithEmail(email);

    return memberDTO;
  }

  @PostMapping("login")
  public MemberDTO login(
    @RequestBody MemberDTO memberDTO
  ){
    log.info("Parameter: " + memberDTO);

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    MemberDTO result = memberService.login(
      memberDTO.getEmail(),
      memberDTO.getPw()
    );

    log.info("Return: " + result);

    return result;
  }
}
