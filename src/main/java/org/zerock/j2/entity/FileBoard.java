package org.zerock.j2.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "images")
public class FileBoard {
  //도메인 주도개발의 onetomany는 board가 전부 관리함
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bno;

  private String title;

  private String content;

  private String writer;

  //FileBoard가 여러개의 이미지를 가짐
  //연관관계 설정 필수!! @OneToMany -> 하나의 게시판에 여러개의 이미지를 가진다
  //crud 작업도 FileBoard가 관리
  //OneToMany 면 PK를 가진 애가 주최임 @OneToMany(cascade = CascadeType.PERSIST) 줘야됨
  //fetch = FetchType.EAGER join 시켜줌 - 하나의 게시물을 조회할 떈 좋음
  //list를 뽑아올 때는 N+1일떄는 계속 호출하는 문제가 발생함
  //************* FetchType은 무조건 LAZY!!!!!!!!!!!!!!!!!!!!!!!!!!!
  /*
   * 1. join해서 ord값 쓰는법 
   * - 단점은 이미지를 하나밖에 못가져옴
   * 2. query를 한번 더 날리는 방법
   * - 쿼리 두번날리기
   */
  //BathSize는 일괄처리 (20개까지는 한번에 처리)
  //쿼리가 in 조건에 들어가짐
  @BatchSize(size = 20)
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
  //image는 board쪽에 소속하게 만드는 속성
  @JoinColumn(name = "board")
  @Builder.Default
  //선언해줘서 사용해야함
  //new 객체로 만들어줘서 해당 객체를 계속 가리키도록 초기화해서 관리
  //FileBoard를 저장하면 하위 이미지들까지 같이 저장
  //컬랙션을 절대로 새로 만들 수 없다 -> 초기화 시켜주면 안됨
  //연관관계가 걸리게되면 select 할 때 limit가 걸리는지 꼭 확인해야함!!
  private List<FileBoardImage> images = new ArrayList<>();

  public void addImage(FileBoardImage boardImage){
    boardImage.changeOrd(images.size());
    images.add(boardImage);
  };
  
}
