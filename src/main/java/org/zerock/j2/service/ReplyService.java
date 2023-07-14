package org.zerock.j2.service;

import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ReplyDTO;
import org.zerock.j2.dto.ReplyPageRequestDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface ReplyService {

  PageResponseDTO<ReplyDTO> list(ReplyPageRequestDTO requestDTO);

  Long register(ReplyDTO replyDTO);

  ReplyDTO read(Long rno);

  void remove(Long rno);

  void modify(ReplyDTO replyDTO);

}
