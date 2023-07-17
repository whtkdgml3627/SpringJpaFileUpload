package org.zerock.j2.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.j2.dto.MemberDTO;

@Transactional
public interface MemberService {

  MemberDTO login(String email, String pw);

}
