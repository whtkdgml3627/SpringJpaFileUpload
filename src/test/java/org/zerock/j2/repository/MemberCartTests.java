package org.zerock.j2.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.j2.entity.MemberCart;

import java.util.List;

@SpringBootTest
@Log4j2
public class MemberCartTests {

  @Autowired(required = false)
  private MemberCartRepository cartRepository;

  @Test
  public void testCartList(){
    String email = "user00@gmail.com";

    //cartRepository.selectCart(email);
  }
}
