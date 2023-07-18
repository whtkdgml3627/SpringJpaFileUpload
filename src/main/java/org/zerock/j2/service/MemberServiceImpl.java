package org.zerock.j2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.j2.dto.MemberDTO;
import org.zerock.j2.entity.Member;
import org.zerock.j2.repository.MemberRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  //repository, modelMapper(Entity타입을 DTO타입으로 변환) 의존성 주입
  private final MemberRepository memberRepository;
  private final ModelMapper modelMapper;
  
  //예외처리
  //static으로 생성
  //RuntimeException unchecked Exception이라 설계 잡음
  public static final class MemberLoginException extends RuntimeException {
    public MemberLoginException(String msg){
      super(msg);
    }
  }

  //login
  @Override
  public MemberDTO login(String email, String pw) {
    
    MemberDTO memberDTO = null;
    
    try{
      Optional<Member> result = memberRepository.findById(email);
      //Throw 던지면 catch로 감
      //그러면 위에 만들어준 클래스 예외처리로 감
      //catch에서 throw new 만들어서 예외처리 던져줌
      Member member = result.orElseThrow();

      //일치 조건 확인
      //가져온 pw와 입력한 pw가 맞지 않으면
      if(!member.getPw().equals(pw)){
        throw new MemberLoginException("Password Incorrect");
      }

      memberDTO = MemberDTO.builder()
        .email(member.getEmail())
        .pw("")
        .nickname(member.getNickname())
        .admin(member.isAdmin())
        .build();
      
    }catch (Exception e){
      throw new MemberLoginException(e.getMessage());
    }
    return memberDTO;
  }

  @Override
  public MemberDTO getMemberWithEmail(String email) {
    Optional<Member> result = memberRepository.findById(email);

    //DB에 회원 데이터가 존재하는 경우
    //isPresent() = 데이터가 존재 한다면
    if(result.isPresent()){
      //get 데이터 가져오는거
      Member member = result.get();
      //dto로 변환
      MemberDTO dto = MemberDTO.builder()
        .email(member.getEmail())
        .nickname(member.getNickname())
        .admin(member.isAdmin())
        .build();

      return dto;
    }

    //DB에 존재하지 않는 이메일인 경우
    Member socialMember = Member.builder()
      .email(email)
      .pw(UUID.randomUUID().toString())
      .nickname("SOCIAL_MEMBER")
      .build();

    memberRepository.save(socialMember);

    MemberDTO dto = MemberDTO.builder()
      .email(socialMember.getEmail())
      .nickname(socialMember.getNickname())
      .admin(socialMember.isAdmin())
      .build();

    return dto;
  }
}
