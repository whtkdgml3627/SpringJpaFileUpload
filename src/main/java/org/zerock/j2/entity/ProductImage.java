package org.zerock.j2.entity;

/*
 * PK가 없음 Product에 기생하는 존재
 */

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//Embeddable주면 자동으로 FK가 생성됨
@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

  private String fname;

  private int ord;

}
