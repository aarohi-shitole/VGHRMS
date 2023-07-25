package com.techvg.hrms.web.rest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.hrms.service.LoginService;
import com.techvg.hrms.web.rest.vm.LoginRequest;
import com.techvg.hrms.web.rest.vm.TokenRequest;


@RestController
@RequestMapping("/api/auth")
public class LoginController {
	
	
	@Autowired
	LoginService loginservice;
	
	@PostMapping("/login")
	public ResponseEntity<Object> login (@RequestBody LoginRequest loginrequest) {
		return loginservice.login(loginrequest);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Object> logout (@RequestBody TokenRequest token) {
		return loginservice.logout(token);
	}
	
	@PostMapping("/introspect")
	public ResponseEntity<Object> introspect(@RequestBody TokenRequest token) {
		return loginservice.introspect(token);
	}

}
