package org.zerock.j2.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.j2.dto.MemberCartDTO;
import org.zerock.j2.entity.MemberCart;

import java.util.List;

@Transactional
public interface MemberCartService {

  List<MemberCartDTO> addCart(MemberCartDTO memberCartDTO);

  List<MemberCartDTO> getCart(String email);

  //dto를 entity로 바꿔줌
  default MemberCart dtoToEntity(MemberCartDTO dto){
    MemberCart entity = MemberCart.builder()
      .cno(dto.getCno())
      .email(dto.getEmail())
      .pno(dto.getPno())
      .build();

    return entity;
  };

  //entity를 dto로 바꿔줌
  default MemberCartDTO entityToDto(MemberCart entity){
    MemberCartDTO dto = MemberCartDTO.builder()
      .cno(entity.getCno())
      .email(entity.getEmail())
      .pno(entity.getPno())
      .build();

    return dto;
  };
}
