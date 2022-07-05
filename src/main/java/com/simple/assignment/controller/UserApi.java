package com.simple.assignment.controller;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple.assignment.dto.UserRegisterDTO;
import com.simple.assignment.model.User;
import com.simple.assignment.model.UserEmail;
import com.simple.assignment.service.UserEmailService;
import com.simple.assignment.service.UserService;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

@RestController
@RequestMapping("api/users")
public class UserApi {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserEmailService userEmailService;
	
	@GetMapping
	public List<User> getAllUser() {
		List<User> users = userService.getUserList();
		return users;
	}
	
	private final Bucket bucket;
	
	@PostMapping
	public String addUser(@RequestBody @Valid UserRegisterDTO userDTO, BindingResult result) {
		UserEmail exitedUser = userEmailService.findUserEmail(userDTO.getEmail());
		if (exitedUser != null) {
			return "User Exited";
		}
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		Set<UserEmail> emails = new HashSet<UserEmail>();
		UserEmail email = new UserEmail(userDTO.getEmail(), true, user);
		emails.add(email);
		
		user.setEmails(emails);
		userService.add(user);
		return "saved";
	}
	
	
	public UserApi () {
		Bandwidth limit = Bandwidth.classic(5, Refill.greedy(5, Duration.ofSeconds(5)));
		this.bucket = Bucket4j.builder()
				.addLimit(limit)
				.build();
	}
	
	@GetMapping("/bucket4j")
	public ResponseEntity<String> testBucket4j(){
		
		if (bucket.tryConsume(1)) {
			return ResponseEntity.ok("success");
		}
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
	}

}
