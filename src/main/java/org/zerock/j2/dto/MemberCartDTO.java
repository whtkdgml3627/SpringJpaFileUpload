package org.zerock.j2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCartDTO {
  //변수
  private Long cno;       //pk
  private String email;   //email 회원
  private Long pno;       //pno 상품
}
