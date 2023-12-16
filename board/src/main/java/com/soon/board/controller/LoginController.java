package com.soon.board.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {

//	@GetMapping("/oauth/callback")
//    public ResponseEntity<String> naverLogin(String code) throws URISyntaxException, MalformedURLException, UnsupportedEncodingException {
//		System.out.println("인증화면: " + code);
//		
//		RestTemplate rt = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		params.add("grant_type", "authorization_code");
//		params.add("client_id", "9qD4mWPsSAeEFQiMc_td");
//		params.add("client_secret", URLEncoder.encode("1DM423c_k7", "UTF-8"));
//		params.add("code", code);
//		params.add("state", URLEncoder.encode("1234", "UTF-8"));
////		params.add("redirect_uri", URLEncoder.encode("http://localhost:4000/oauth/callback", "UTF-8"));
//		
//		HttpEntity<MultiValueMap<String, String>> naverTokenRequest = 
//				new HttpEntity<>(params, headers);
//		
//		ResponseEntity<String> response = rt.exchange(
//				"https://nid.naver.com/oauth2.0/token", 
//				HttpMethod.POST,
//				naverTokenRequest,
//				String.class);
//		
//		// Gson, Json Simple, ObjectMapper
//		ObjectMapper objectMapper = new ObjectMapper();
//		NaverTokens authTokens = null;
//		try {
//			authTokens = objectMapper.readValue(response.getBody(), NaverTokens.class);
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println("네이버 엑세스 토큰: " + authTokens.getAccessToken());
//		
//		RestTemplate rt2 = new RestTemplate();
//		HttpHeaders headers2 = new HttpHeaders();
//		headers2.add("Authorization", "Bearer " + authTokens.getAccessToken());
//		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//		
//		HttpEntity<MultiValueMap<String, String>> naverProfileRequest = 
//				new HttpEntity<>(headers2);
//		
//		ResponseEntity<String> response2 = rt2.exchange(
//				"https://openapi.naver.com/v1/nid/me", 
//				HttpMethod.POST,
//				naverProfileRequest,
//				String.class);
//		
//		System.out.println("response2: " + response2.getBody());
//		
//		ObjectMapper objectMapper2 = new ObjectMapper();
//		NaverInfoResponse naverProfile = null;
//		try {
//			naverProfile = objectMapper2.readValue(response2.getBody(), NaverInfoResponse.class);
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		
////		System.out.println("네이버 message: " + naverProfile.getMessage());
//		System.out.println("네이버 email: " + naverProfile.getEmail());
//		System.out.println("네이버 name: " + naverProfile.getName());
//		
////        String url = loginService.getNaverAuthorizeUrl("authorize");
//		System.out.println("response2: " + response2);
//		return response2;  
//    }
	
}
