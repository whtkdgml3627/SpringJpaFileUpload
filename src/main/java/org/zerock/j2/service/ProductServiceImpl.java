package org.zerock.j2.service;

import org.springframework.stereotype.Service;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductListDTO;
import org.zerock.j2.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProdcutService {

  private final ProductRepository productRepository;

  @Override
  public PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO) {
    
    return productRepository.listWithReview(requestDTO);
  }
  
}
