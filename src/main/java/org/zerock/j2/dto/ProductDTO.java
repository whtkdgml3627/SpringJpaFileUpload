package org.zerock.j2.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
  
  private Long pno;
  private String pname;
  private String pdesc;
  private int price;

  //null보다는 비어있는 list를 반환하는게 나아서 Default값을 넣어줌
  @Builder.Default
  private List<String> images = new ArrayList<>();

  @Builder.Default
  //등록, 수정할 때 업로드된 파일 데이터를 수집하는 용도
  private List<MultipartFile> files = new ArrayList<>();

}
