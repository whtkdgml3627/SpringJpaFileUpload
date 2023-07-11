package org.zerock.j2.repository.search;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/*
 * 모든 동적 쿼리는 쿼리dsl로 처리하였다
 * limit가 걸리는지 확인했다
 * 대표이미지만 가져오게 처리함
 * 
 * 이미지 여러개 가져오는 부분은 ajax로 가져옴
 * 
 * select는 단순 조회가 아닌 이상 Querydsl을 사용해야함
 */

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.j2.dto.FileBoardListDTO;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.entity.FileBoard;
import org.zerock.j2.entity.QFileBoard;
import org.zerock.j2.entity.QFileBoardImage;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class FileBoardSearchImpl extends QuerydslRepositorySupport implements FileBoardSearch {
  
  public FileBoardSearchImpl() {
    super(FileBoard.class);
  }

  //전체 데이터 가져오기 Version1
  // @Override
  // public PageResponseDTO<FileBoardListDTO> list(PageRequestDTO pageRequestDTO) {

  //   //Q로 선언
  //   QFileBoard board = QFileBoard.fileBoard;
  //   //QFileBoardImage boardImage = QFileBoardImage.fileBoardImage;

  //   //query 선언 select
  //   JPQLQuery<FileBoard> query = from(board);
  //   //left outer join
  //   //같은 조건이 없으므로 on조건을 사용할 수 없다
  //   //그래서 조인을 할 수 없다
  //   //query.leftJoin(boardImage);

  //   //boardImage의 ord가 0인걸로 where
  //   //query.where(boardImage.ord.eq(0));

  //   //페이지가 음수값이면 0으로 초기화
  //   int pageNum = pageRequestDTO.getPage() - 1 < 0 ? 0 : pageRequestDTO.getPage() - 1;

  //   //페이징처리
  //   Pageable pageable = PageRequest.of(
  //     pageNum,
  //     pageRequestDTO.getSize(), 
  //     Sort.by("bno").descending()
  //   );

  //   //페이징
  //   this.getQuerydsl().applyPagination(pageable, query);

  //   //리스트 출력
  //   List<FileBoard> list = query.fetch();

  //   list.forEach(fb -> {
  //     log.info(fb);
  //     log.info(fb.getImages());
  //   });

  //   return null;
  // }

  @Override
  public PageResponseDTO<FileBoardListDTO> list(PageRequestDTO pageRequestDTO) {

    //Q로 선언
    QFileBoard board = QFileBoard.fileBoard;
    QFileBoardImage boardImage = QFileBoardImage.fileBoardImage;

    //query 선언 select
    JPQLQuery<FileBoard> query = from(board);
    //left outer join
    //같은 조건이 없으므로 on조건을 사용할 수 없다
    //board.images를 boardImage alias를 줌
    query.leftJoin(board.images, boardImage);

    //boardImage의 ord가 0인걸로 where
    query.where(boardImage.ord.eq(0));

    //페이지가 음수값이면 0으로 초기화
    int pageNum = pageRequestDTO.getPage() - 1 < 0 ? 0 : pageRequestDTO.getPage() - 1;

    //페이징처리
    Pageable pageable = PageRequest.of(
      pageNum,
      pageRequestDTO.getSize(), 
      Sort.by("bno").descending()
    );

    //페이징
    this.getQuerydsl().applyPagination(pageable, query);

    //단방향 참조에서 join으로 목록을 뽑아오는 Query
    // **************** 이게 바로 정답 ****************
    JPQLQuery<FileBoardListDTO> listQuery = query.select(
      Projections.bean(
        FileBoardListDTO.class,
        board.bno,
        board.title,
        boardImage.uuid,
        boardImage.fname
      ));

    List<FileBoardListDTO> list = listQuery.fetch();

    long totalCount = listQuery.fetchCount();


    return new PageResponseDTO<>(list, totalCount, pageRequestDTO);
  }


}
