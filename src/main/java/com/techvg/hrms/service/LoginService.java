package com.techvg.hrms.service;


import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.techvg.hrms.web.rest.vm.LoginRequest;
import com.techvg.hrms.web.rest.vm.TokenRequest;
import com.techvg.hrms.service.dto.Token;



@Service
public class LoginService {
	 
	 
	@Autowired
	RestTemplate restTemplate;
	
	
	@Value("${auth.server-url}")
	private String serverUrl;
	

	@Value("${auth.client-id}")
	private String clientId;
	
	@Value("${auth.client-secret}")
	private String clientSecret;
	
	@Value("${auth.authorization-grant-type}")
	private String grantType;
	

	public ResponseEntity<Object> login(LoginRequest loginrequest) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id", clientId);
		map.add("client_secret", clientSecret);
		map.add("grant_type", grantType);
		map.add("scope", "openid");
		map.add("username", loginrequest.getUsername());
		map.add("password", loginrequest.getPassword());
		
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map,headers);
		
		ResponseEntity<Token> response=null;
		ResponseEntity<Object> response1 =null;
		try {
			response = restTemplate.postForEntity(serverUrl+"/protocol/openid-connect/token", httpEntity, Token.class);			
			map = new LinkedMultiValueMap<>();
			map.add("client_id", clientId);
			map.add("client_secret", clientSecret);
			map.add("grant_type", "refresh_token");
			map.add("refresh_token", response.getBody().getRefresh_token());
			httpEntity = new HttpEntity<>(map,headers);
			response1 = restTemplate.postForEntity(serverUrl+"/protocol/openid-connect/token", httpEntity, Object.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();			
			return new ResponseEntity<>(e.getMessage(),response.getStatusCode());
		}	
		
		return new ResponseEntity<>(response1.getBody(),response1.getStatusCode()); 
	
		
	}


	public ResponseEntity<Object> logout(TokenRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id", clientId);
		map.add("client_secret", clientSecret);
		map.add("refresh_token", request.getToken());
		
		
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map,headers);
		
		ResponseEntity<Object> response=null;
		try {
			response = restTemplate.postForEntity(serverUrl+"/protocol/openid-connect/logout", httpEntity, Object.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
//			Response res = new Response();
//			res.setMessage(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<>(response,HttpStatus.OK);
		
		
	}


	public ResponseEntity<Object> introspect(TokenRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id", clientId);
		map.add("client_secret", clientSecret);
		map.add("token", request.getToken());
		
		
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map,headers);
		
		ResponseEntity<Object> response=null;
		try {
			response = restTemplate.postForEntity(serverUrl+"/protocol/openid-connect/token/introspect", httpEntity, Object.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
//			Response res = new Response();
//			res.setMessage(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response.getBody(),HttpStatus.OK);
	}
	
	
}
