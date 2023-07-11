package org.zerock.j2.repository;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.entity.FileBoard;
import org.zerock.j2.entity.FileBoardImage;

import jakarta.transaction.Transactional;

@SpringBootTest
public class RepositoryTests {
  
  @Autowired
  private FileBoardRepository repository;

  @Test
  public void insert(){

    for (int i = 0; i < 100; i++) {
      FileBoard fileBoard = FileBoard.builder()
        .title("AA")
        .content("AAA")
        .writer("AAB")
        .build();

      FileBoardImage img1 = FileBoardImage.builder()
        .uuid(UUID.randomUUID().toString())
        .fname("AAA.jpg")
        .build();

      FileBoardImage img2 = FileBoardImage.builder()
        .uuid(UUID.randomUUID().toString())
        .fname("BBB.jpg")
        .build();

      fileBoard.addImage(img1);
      fileBoard.addImage(img2);

      repository.save(fileBoard);
    }

  }

  @Test
  @Transactional
  @Commit
  public void testRemove(){
    Long bno = 1L;

    repository.deleteById(bno);
  }

  @Test
  @Transactional
  public void testRead(){
    Long bno = 100L;

    Optional<FileBoard> result = repository.findById(bno);

    FileBoard board = result.orElseThrow();

    System.out.println(board);
  }

  @Test
  @Transactional
  public void testList(){

    Pageable pageable = PageRequest.of(0, 10);

    Page<FileBoard> result = repository.findAll(pageable);

    //System.out.println(result);
    result.get().forEach(board -> {
      System.out.println(board);
      System.out.println(board.getImages());
    });

  }

  @Test
  @Transactional
  public void testListQuerydsl(){
    PageRequestDTO requestDTO = PageRequestDTO.builder().build();

    System.out.println(repository.list(requestDTO));
  }

  @Test
  public void testSelectOne(){
    Long bno = 100L;

    FileBoard board = repository.selectOne(bno);

    System.out.println(board);
    System.out.println(board.getImages());
  }

  @Test
  @Transactional
  @Commit
  public void testDelete(){
    Long bno = 101L;

    repository.deleteById(bno);
  }

  @Test
  @Transactional
  @Commit
  public void testUpdate(){
    Optional<FileBoard> result = repository.findById(20L);

    FileBoard board = result.orElseThrow();

    board.cleanImages();

    FileBoardImage img1 = FileBoardImage.builder()
      .uuid(UUID.randomUUID().toString())
      .fname("ZZZZ.jpg")
      .build();

    board.addImage(img1);

    repository.save(board);
  }

}
