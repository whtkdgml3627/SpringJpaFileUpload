package org.zerock.j2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
  //변수
  private String email;     //ID (pk)
  private String pw;        //비밀번호
  private String nickname;  //닉네임
  private boolean admin;    //어드민 사용자 체크

}
