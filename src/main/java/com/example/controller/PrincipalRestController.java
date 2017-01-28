package com.example.controller;

import java.security.Principal;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class PrincipalRestController {
	
//	@Inject
//	private AccountUserDetailService detailService;
//	
//	@Inject
//	private AccountRepository accountRepository;
//	
	@RequestMapping("/user")
	Principal principal (Principal principal){
		return principal;
	}
	
//	@RequestMapping(value="authentication")
//	public void getUser(){
//		
//		accountRepository.findAll().forEach(u ->{
//
//			detailService.loadUserByUsername(u.getUserName());
//		});
//	}
	
}
