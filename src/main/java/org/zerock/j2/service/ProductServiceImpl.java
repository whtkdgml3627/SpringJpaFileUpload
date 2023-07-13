package org.zerock.j2.service;

import org.springframework.stereotype.Service;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductDTO;
import org.zerock.j2.dto.ProductListDTO;
import org.zerock.j2.entity.Product;
import org.zerock.j2.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.zerock.j2.util.FileUploader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProdcutService {

  private final ProductRepository productRepository;

  private final FileUploader fileUploader;

  @Override
  public PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO) {
    
    return productRepository.listWithReview(requestDTO);
  }

  @Override
  public Long register(ProductDTO productDTO) {

    Product product = Product.builder()
      .pname(productDTO.getPname())
      .pdesc(productDTO.getPdesc())
      .price(productDTO.getPrice())
      .build();

    productDTO.getImages().forEach(fname -> {
      product.addImage(fname);
    });

    return productRepository.save(product).getPno();
  }

  @Override
  public ProductDTO readOne(Long pno) {

    Product product = productRepository.selectOne(pno);

    ProductDTO dto = ProductDTO.builder()
      .pno(product.getPno())
      .pname(product.getPname())
      .pdesc(product.getPdesc())
      .price(product.getPrice())
      .images(product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList()))
      .build();

    return dto;
  }

  @Override
  public void remove(Long pno) {
    Product product = productRepository.selectOne(pno);

    product.changeDel(true);

    productRepository.save(product);

    List<String> fileNames =
      product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList());

    fileUploader.removeFiles(fileNames);
  }

  @Override
  public void modify(ProductDTO productDTO) {
    //기존 Product를 로딩
    Optional<Product> result = productRepository.findById(productDTO.getPno());

    Product product = result.orElseThrow();
    //기본 정보들 수정
    product.changePname(productDTO.getPname());
    product.changePdesc(productDTO.getPdesc());
    product.changePrice(productDTO.getPrice());

    //기존 이미지 목록을 살린다
    List<String> oldFileNames =
      product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList());

    //이미지들은 clearImages()한 후에
    product.clearImages();

    //이미지 문자열들을 추가 addImage()
    productDTO.getImages().forEach(fname -> product.addImage(fname));

    log.info("======================================");
    log.info("======================================");
    log.info(product);
    log.info("======================================");
    log.info("======================================");

    //저장!!
    productRepository.save(product);

    //기존 파일들 중에 productDTO.getImages()여기에 없는 파일들 찾기
    List<String> newFiles = productDTO.getImages();
    //삭제 대상 파일 찾기
    //indexOf 0이면 맞고 -1이면 없음
    List<String> wantDeleteFiles = oldFileNames.stream().filter(
      f -> newFiles.indexOf(f) == -1).collect(Collectors.toList()
    );

    fileUploader.removeFiles(wantDeleteFiles);
  }

}
