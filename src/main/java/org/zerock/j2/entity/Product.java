package org.zerock.j2.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "images")
public class Product {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pno;

  private String pname;

  private String pdesc;

  private String writer;

  private int price;

  //@ElementCollection(fetch = FetchType.LAZY)
  //
  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  private List<ProductImage> images = new ArrayList<>();

  public void addImage(String name){
    ProductImage PImage = ProductImage.builder()
      .fname(name)
      .ord(images.size())
      .build();

    images.add(PImage);
  }

  public void clearImages(){
    images.clear();
  }

  public void changePrice(int price){
    this.price = price;
  }
}
