package org.zerock.j2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {
  //변수
  @Id
  private String email;     //ID (pk)
  private String pw;        //비밀번호
  private String nickname;  //닉네임
  private boolean admin;    //어드민 사용자 체크
  
}
