package org.zerock.j2.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@ToString
public class ReplyPageRequestDTO extends PageRequestDTO {

  private Long bno;

  //@Builder.Default
  private int page = 1;

  //@Builder.Default
  private int size = 20;

  private boolean last;

}
