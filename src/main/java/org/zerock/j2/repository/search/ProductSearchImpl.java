package org.zerock.j2.repository.search;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductListDTO;
import org.zerock.j2.entity.Product;
import org.zerock.j2.entity.QProduct;
import org.zerock.j2.entity.QProductImage;
import org.zerock.j2.entity.QProductReview;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

  public ProductSearchImpl(){
    super(Product.class);
  }

  @Override
  public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {

    QProduct product = QProduct.product;
    QProductImage productImage = QProductImage.productImage;

    //JPQL 사용해주는 선언
    JPQLQuery<Product> query = from(product);
    //참조가 없는 상태에서도 outer join이 가능하게 해줌
    query.leftJoin(product.images, productImage);

    //where 조건 ord가 0인 이미지 하나만 불러오기
    query.where(productImage.ord.eq(0));

    //페이지번호 설정
    //음수 나왔을 때 0으로 만들어줌
    int pageNum = pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1;

    //페이징 설정
    Pageable pageable = PageRequest.of(
      pageNum,
      pageRequestDTO.getSize(),
      Sort.by("pno").descending()
    );

    this.getQuerydsl().applyPagination(pageable, query);

    // log.info(query.fetch());
    JPQLQuery<ProductListDTO> dtoQuery =
      query.select(
        Projections.bean(
          ProductListDTO.class,
          product.pno,
          product.pname,
          product.price,
          productImage.fname
        )
      );
    
      List<ProductListDTO> dtoList = dtoQuery.fetch();

      Long totalCount = dtoQuery.fetchCount();

    return new PageResponseDTO<>(dtoList, totalCount, pageRequestDTO);
  }

  //list review
  @Override
  public PageResponseDTO<ProductListDTO> listWithReview(PageRequestDTO pageRequestDTO) {

    QProduct product = QProduct.product;
    QProductImage productImage = QProductImage.productImage;
    QProductReview review = QProductReview.productReview;

    //JPQL 사용해주는 선언
    JPQLQuery<Product> query = from(product);
    //참조가 없는 상태에서도 outer join이 가능하게 해줌
    query.leftJoin(product.images, productImage);
    //review join on조건
    query.leftJoin(review).on(review.product.eq(product));

    //where 조건 ord가 0인 이미지 하나만 불러오기
    query.where(productImage.ord.eq(0));
    query.where(product.delFlag.eq(Boolean.FALSE));

    //페이지번호 설정
    //음수 나왔을 때 0으로 만들어줌
    int pageNum = pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1;

    //페이징 설정
    Pageable pageable = PageRequest.of(
      pageNum,
      pageRequestDTO.getSize(),
      Sort.by("pno").descending()
    );

    this.getQuerydsl().applyPagination(pageable, query);

    query.groupBy(product);

    // log.info(query.fetch());
    JPQLQuery<ProductListDTO> dtoQuery =
      query.select(
        Projections.bean(
          ProductListDTO.class,
          product.pno,
          product.pname,
          product.price,
          productImage.fname.min().as("fname"),
          review.score.avg().as("reviewAvg"),
          review.count().as("reviewCnt")
        )
      );
    
      List<ProductListDTO> dtoList = dtoQuery.fetch();

      Long totalCount = dtoQuery.fetchCount();

    return new PageResponseDTO<>(dtoList, totalCount, pageRequestDTO);
  }
  
}
