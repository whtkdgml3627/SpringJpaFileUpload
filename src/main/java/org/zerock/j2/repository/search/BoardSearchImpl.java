package org.zerock.j2.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/*
 * QuerydslRepositorySupport를 상속받았고 부모클래스에 생성자가 있어서 super로 상속 받아야함
 * QueryDSL의 목표는 JAVA로 동적으로 쿼리를 생성해주는 라이브러리!!!
 * QueryDSL에서는 DTO Entity 처리를 이 안에서 가능하게 해줌!!
 * BooleanBuilder 는 괄호로 감싸줌 (우선순위 연산자)
 */

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.j2.entity.QBoard;
import org.zerock.j2.entity.QReply;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;
import org.zerock.j2.dto.BoardListRcntDTO;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.entity.Board;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

  //super로 상속 받아야함 반드시!
  public BoardSearchImpl(){
    super(Board.class);
  }

  //V3
  @Override
  public PageResponseDTO<BoardListRcntDTO> searchDTORcnt(PageRequestDTO requestDTO) {

    Pageable pageable = makePageable(requestDTO);
    //Querydsl 사용하기 위한 선언
    QBoard board = QBoard.board;
    QReply reply = QReply.reply;

    //JPQL을 사용하겠다!!
    JPQLQuery<Board> query = from(board);

    //left outer join
    query.leftJoin(reply).on(reply.board.eq(board));

    //PageRequestDTO에 있는 값으로 가져옴
    String keyword = requestDTO.getKeyword();
    String searchType = requestDTO.getType();

    //검색 조건 설정
    if(keyword != null && searchType != null){

      //배열로 받고 split로 쪼개면 글자열 하나하나씩 쪼개짐
      //tc -> [t,c]
      String[] searchArr = searchType.split("");

      //( )를 만들어주는 객체생성
      BooleanBuilder searchBuilder = new BooleanBuilder();

      for (String type : searchArr) {
        switch(type){
          case "t" -> searchBuilder.or(board.title.contains(keyword));
          case "c" -> searchBuilder.or(board.content.contains(keyword));
          case "w" -> searchBuilder.or(board.writer.contains(keyword));
        }
      }//end for

      query.where(searchBuilder);
    }

    //paging
    this.getQuerydsl().applyPagination(pageable, query);

    //group by 선언 하고 where줘도 지가 알아서 찾아서 where문 실행해줌
    //board 별로 group by 해줌
    query.groupBy(board);

    //Projections를 사용하여 바로 BoardListDTO를 사용가능함
    JPQLQuery<BoardListRcntDTO> listQuery = query.select(Projections.bean(
        BoardListRcntDTO.class,
        board.bno,
        board.title,
        board.writer,
        reply.countDistinct().as("replyCount")
      )
    );

    List<BoardListRcntDTO> list = listQuery.fetch();

    log.info("----------------------------------------------------------------");
    log.info(list);

    //total 가져오기
    long totalCount = listQuery.fetchCount();

    log.info(totalCount);

    return new PageResponseDTO<>(list, totalCount, requestDTO);
  }

}
