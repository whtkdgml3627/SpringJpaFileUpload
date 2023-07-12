package org.zerock.j2.repository;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductListDTO;
import org.zerock.j2.entity.Product;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductTests {
  
  @Autowired
  ProductRepository repo;

  // @Test
  // public void testInsert(){

  //   for (int i = 0; i < 200; i++) {
  //     Product product = Product.builder()
  //       .pname("Test" + i)
  //       .pdesc("Test" + i)
  //       .writer("user" + i)
  //       .price(4000)
  //       .build();
      
  //     product.addImage(UUID.randomUUID().toString()+"_aaa.jpg");
  //     product.addImage(UUID.randomUUID().toString()+"_bbb.jpg");
  //     product.addImage(UUID.randomUUID().toString()+"_ccc.jpg");

  //     repo.save(product);
  //   }
  // }

  @Test
  @Transactional
  public void testRead1(){

    Optional<Product> result = repo.findById(1L);

    Product product = result.orElseThrow();

    log.info(product);
    log.info("------------------------------------------------------");
    log.info(product.getImages());

  }

  @Test
  public void testRead2(){

    Product product = repo.selectOne(1L);

    log.info(product);
    log.info("------------------------------------------------------");
    log.info(product.getImages());
  }

  @Test
  public void testDelete(){

    repo.deleteById(1L);

  }

  //EntityGraph로 join해서 불러오고
  //Product, ProductImage select로 조회해 오고
  //ProductImage pno 이미지 싹 다 지우고
  //원래이미지와 추가된 이미지 Insert시켜줌
  @Test
  @Transactional
  @Commit
  public void testUpdate(){

    Optional<Product> result = repo.findById(2L);

    Product product = result.orElseThrow();

    // Product product = repo.selectOne(2L);
    
    product.changePrice(6000);

    //product.clearImages();

    product.addImage(UUID.randomUUID().toString()+"_newImage.jpg");

    repo.save(product);
  }

  @Test
  public void testList1(){
    PageRequestDTO requestDTO = new PageRequestDTO();

    PageResponseDTO<ProductListDTO> result = repo.list(requestDTO);

    for (ProductListDTO dto : result.getDtoList()) {
      log.info(dto);
    }
  }
  
  @Test
  public void testList2(){
    PageRequestDTO requestDTO = new PageRequestDTO();

    PageResponseDTO<ProductListDTO> result = repo.listWithReview(requestDTO);

    for (ProductListDTO dto : result.getDtoList()) {
      System.out.println(dto);
    }
  }


}
