package org.zerock.j2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.j2.dto.MemberCartDTO;
import org.zerock.j2.service.MemberCartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@CrossOrigin
@RequestMapping("/api/cart/")
public class MemberCartController {

  private final MemberCartService cartService;

  @PostMapping("add")
  //return타입은 파라미터로 담은 정보
  public List<MemberCartDTO> add(
    //목록 데이터 담을 정보
    @RequestBody MemberCartDTO memberCartDTO
  ){
    log.info("Param: " + memberCartDTO);

    return cartService.addCart(memberCartDTO);
  }

  @GetMapping("{email}")
  public List<MemberCartDTO> get(
    @PathVariable("email") String email
  ){
    return cartService.getCart(email);
  }

}
