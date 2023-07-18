package org.zerock.j2.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;

@Service
@Log4j2
public class SocialServiceImpl implements SocialService {

  @Value("${org.zerock.kakao.rest_key}")
  private String clientId;
  @Value("${org.zerock.kakao.token_url}")
  private String tokenURL;
  @Value("${org.zerock.kakao.redirect_uri}")
  private String redirectURI;
  @Value("${org.zerock.kakao.get_user}")
  private String getUser;

  @Override
  public String getKakaoEmail(String authCode) {
    log.info("-----------------------------------------------------------------");
    log.info("authCode: " + authCode);
    log.info("clientId: " + clientId);
    log.info("tokenURL: " + tokenURL);
    log.info("redirectURI: " + redirectURI);
    log.info("getUser: " + getUser);

    //토큰 가져오기
    String accessToken = getAccessToken(authCode);
    //토큰 던져서 이메일 가져오기
    String email = getEmailFromAccessToken(accessToken);

    return email;
  }

  //accessToken가져오기
  private String getAccessToken(String authCode){
    //try catch 처리 해줘야함
    String accessToken = null;
    //spring에서 제공하는 애
    RestTemplate restTemplate = new RestTemplate();

    //spring으로 import
    HttpHeaders headers = new HttpHeaders();
    //add(헤더의 이름, 값) / 둘 다 문자열
    headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

    //axios에서 header 추가해주는거와 동일
    HttpEntity<String> entity = new HttpEntity<>(headers);

    //특정한 uri를 호출하는 역할 / tokenURL을 불러옴
    //axios에서 작업한걸 queryParam으로 다 바꿔주는것
    UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(tokenURL)
      .queryParam("grant_type","authorization_code")
      .queryParam("client_id",clientId)
      .queryParam("redirect_uri", redirectURI)
      .queryParam("code", authCode)
      .build(true);

    ResponseEntity<LinkedHashMap> response = restTemplate.exchange(
      uriComponents.toString(), HttpMethod.POST, entity, LinkedHashMap.class
    );

    log.info("-----------------------------------");
    log.info(response);

    //response에 실제 내용물을 HashMap으로 만들어줌
    LinkedHashMap<String, String> bodyMap = response.getBody();

    //access_token을 respons에서 가져오기
    accessToken = bodyMap.get("access_token");
    log.info("accessToken: " + accessToken);

    return accessToken;
  }

  //이메일 가져오기
  private String getEmailFromAccessToken(String accessToken){

    if(accessToken == null){
      throw new RuntimeException("Access Token is null");
    }
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);
    headers.add("Content-Type","application/x-www-form-urlencoded");
    HttpEntity<String> entity = new HttpEntity<>(headers);

    UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(getUser).build();

    ResponseEntity<LinkedHashMap> response =
      restTemplate.exchange(
        uriBuilder.toString(),
        HttpMethod.GET,
        entity,
        LinkedHashMap.class
      );

    log.info(response);

    LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();

    log.info("------------------------------------");
    log.info(bodyMap);

    LinkedHashMap<String, String> kakaoAccount = bodyMap.get("kakao_account");

    log.info("kakaoAccount: " + kakaoAccount);

    return kakaoAccount.get("email");

  }
}
