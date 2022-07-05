package com.simple.assignment.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.simple.assignment.dto.UserLoginDTO;
import com.simple.assignment.dto.UserRegisterDTO;
import com.simple.assignment.dto.UserUpdateDTO;
import com.simple.assignment.model.Role;
import com.simple.assignment.model.User;
import com.simple.assignment.model.UserEmail;
import com.simple.assignment.model.UserLoginHistory;
import com.simple.assignment.repository.UserLoginHistoryRepository;
import com.simple.assignment.security.CustomUserDetails;
import com.simple.assignment.service.UserEmailService;
import com.simple.assignment.service.UserService;

@Controller
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserLoginHistoryRepository userLoginHistoryRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private UserEmailService userEmailService;

//	@Autowired
//	private SecurityServiceImpl securityService;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping("/login")
	String login(@Valid UserLoginDTO userLoginDTO, BindingResult result, Model model, RedirectAttributes redirectAttrs,
			HttpServletRequest request) {
		if (result.hasErrors()) {
			return "login";
		}

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userLoginDTO.getEmail(), userLoginDTO.getPassword());
		CustomUserDetails user;
		try {
			Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			user = (CustomUserDetails) auth.getPrincipal(); // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			model.addAttribute("error", "Invalid username or password");
			return "login";
		}
		UserLoginHistory userLoginHistory = new UserLoginHistory(new Date(), request.getRemoteAddr(), user.getUser());
		userLoginHistoryRepo.save(userLoginHistory);
		redirectAttrs.addFlashAttribute("currentUserId", user.getUser().getUserId());
		return "redirect:/users";
	}

	@PostMapping("/adduser")
	public String addUser(@Valid UserRegisterDTO userDTO, BindingResult result, Model model) {
		if (userDTO.getRole() == null) {
			userDTO.setRole(Role.ROLE_USER);
		}

		if (result.hasErrors()) {
			return "signup";
		}
		UserEmail exitedUser = userEmailService.findUserEmail(userDTO.getEmail());
		if (exitedUser != null) {
			model.addAttribute("error", "Email address already exited!");
			return "signup";
		}

		if (!(userDTO.getPassword().matches(userDTO.getConfirmPassword()))) {
			model.addAttribute("error", "Password not match !");
			return "signup";
		}

		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		Set<UserEmail> emails = new HashSet<UserEmail>();
		UserEmail email = new UserEmail(userDTO.getEmail(), true, user);
		emails.add(email);

		return "redirect:/users";
	}

	public String currentUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	@GetMapping("/users")
	public String showUserList(Model model) {
		model.addAttribute("users", userService.getUserList());
		String currentUserLoginEmail = this.currentUserName();
		model.addAttribute("currentUser", currentUserLoginEmail);
		return "user-list";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") int id, Model model) {
		User user = userService.getUserById(id);
		if (user == null) {
			model.addAttribute("error", "Not found user Id:" + id);
			logger.info("Not found user Id:" + id);
			return "user-list";
		}
		UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
		BeanUtils.copyProperties(user, userUpdateDTO);
		userUpdateDTO.setId(id);
		model.addAttribute("userUpdateDTO", userUpdateDTO);

		return "update-user";

	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") long id, @Valid UserUpdateDTO userUpdateDTO, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			userUpdateDTO.setId(id);
			return "update-user";
		}
		User updateUser = new User();
		BeanUtils.copyProperties(userUpdateDTO, updateUser);
		updateUser.setUserId(id);
		userService.update(updateUser);
		return "redirect:/users";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") int id, Model model) {
		User user = userService.getUserById(id);

		if (user == null) {
			model.addAttribute("error", "Invalid user Id:" + id);
			logger.info("Invalid user Id:" + id);
			return "users";
		}

		userService.delete(id);
		return "redirect:/users";
	}

}
