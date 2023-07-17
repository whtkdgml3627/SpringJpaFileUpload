package org.zerock.j2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "member_cart", indexes = @Index(columnList = "cno, email"))
public class MemberCart {
  //변수
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cno;       //pk
  private String email;   //email 회원
  private Long pno;       //pno 상품
  
  //시간이 추가되어야함
}
