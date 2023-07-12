package org.zerock.j2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//연관관계가 있는 변수는 exclude를 반드시!!
@ToString(exclude = "product")
public class ProductReview {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long rno;

  private String content;

  private String reviewer;

  private int score;

  //리뷰 하나는 한 상품을 보는것 (상세페이지에서 보는거니까)
  //여러개의 리뷰가 하나의 상품에 있으니 ManyToOne
  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

}
