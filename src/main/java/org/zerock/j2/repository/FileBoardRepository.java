package org.zerock.j2.repository;

/*
 * Entity에서 설정할 때는 fetch는 무조건 LAZY임
 * 조회 할땐 Repository에서 @EntityGraph 를 사용
 * Select 동적으로 할때는 무조건 Querydsl
 */

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.j2.entity.FileBoard;
import org.zerock.j2.repository.search.FileBoardSearch;

public interface FileBoardRepository extends JpaRepository<FileBoard, Long>, FileBoardSearch {
  
  @EntityGraph(attributePaths = {"images"})
  @Query("select b from FileBoard b where b.bno = :bno")
  FileBoard selectOne(@Param("bno") Long bno);

}
