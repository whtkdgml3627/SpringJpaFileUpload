package org.zerock.j2.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductListDTO;
import org.zerock.j2.service.ProdcutService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin
@RequestMapping("/api/products/")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

  private final ProdcutService service;

  @GetMapping("list")
  public PageResponseDTO<ProductListDTO> list(
    PageRequestDTO pageRequestDTO
  ){
    log.info("---------------------------------------------------");
    log.info(pageRequestDTO);

    return service.list(pageRequestDTO);
  }
  
}
