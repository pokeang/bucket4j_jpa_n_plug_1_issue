package com.simple.assignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.simple.assignment.dto.UserLoginDTO;
import com.simple.assignment.dto.UserRegisterDTO;

@Controller
public class DefaultController {
	
	
	@GetMapping("/")
	String showIndex(UserLoginDTO userLoginDTO) {
		return "login";
	}
	
	@GetMapping("/login")
	String showLogin(UserLoginDTO userLoginDTO) {
		return "login";
	}
	
	@GetMapping("/signup")
	public String showSingUpForm(UserRegisterDTO userRegisterDTO) {
		return "signup";
	}
	
	@PostMapping("/logout")
	String showLogout() {
		return "login";
	}
	
	@GetMapping("/home")
	String showHome() {
		return "home";
	}


	@GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
	

}
