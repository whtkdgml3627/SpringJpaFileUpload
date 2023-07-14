package org.zerock.j2.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.BoardListRcntDTO;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.entity.Board;

/*
 * BoardSearc로 만들었으면 반드시 뒤에 Impl만 붙여서 Class를 만들어줘야됨!!!!!!
 * 1. 메소드 구현
 * 2. Impl Override
 */

public interface BoardSearch {

  /*
   * V3
   */
  PageResponseDTO<BoardListRcntDTO> searchDTORcnt(PageRequestDTO requestDTO);
  default Pageable makePageable(PageRequestDTO requestDTO){
    Pageable pageable = PageRequest.of(
      requestDTO.getPage() - 1,
      requestDTO.getSize(),
      Sort.by("bno").descending()
    );

    return pageable;
  };

}
