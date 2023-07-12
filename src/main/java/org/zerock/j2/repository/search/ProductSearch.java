package org.zerock.j2.repository.search;

import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductListDTO;

public interface ProductSearch {
  
  //list
  PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO);

  //list
  PageResponseDTO<ProductListDTO> listWithReview(PageRequestDTO pageRequestDTO);

}
