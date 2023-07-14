package org.zerock.j2.service;

import org.zerock.j2.dto.BoardDTO;
import org.zerock.j2.dto.BoardListRcntDTO;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface BoardService {

  PageResponseDTO<BoardListRcntDTO> listRcnt(PageRequestDTO pageRequestDTO);

  BoardDTO getOne(Long bno);

}
