package org.zerock.j2.service;

import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductListDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface ProdcutService {
  
  PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO);
}
