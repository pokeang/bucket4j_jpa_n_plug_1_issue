package com.exercise.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.simple.assignment.model.User;
import com.simple.assignment.model.UserEmail;
import com.simple.assignment.model.UserLoginHistory;
import com.simple.assignment.repository.UserLoginHistoryRepository;
import com.simple.assignment.repository.UserRepository;
import com.simple.assignment.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
public class UserServiceTest {
	@Mock
	UserLoginHistoryRepository userLoginHistoryRepository;
	

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;
	
	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	

	@Mock
	private PasswordEncoder passwordEncoder;
	
	Set<UserEmail> userEmails = new HashSet<UserEmail>();
	// userEmails.add(new UserEmail("user1@gmail.com", true));
//	userEmails.add(new UserEmail("user2@gmail.com", false));
	User user1 = new User("user-first-name-1", "user-last-name-2", userEmails, "password");
	User user2 = new User("user-first-name-1", "user-last-name-2", userEmails, "password");
	User user3 = new User("user-first-name-1", "user-last-name-2", userEmails, "password");
	
	@Test
	public void getUserListTest() {
		List<User> list = new ArrayList<User>();
		list.add(user1);
		list.add(user2);
		list.add(user3);
		
		when(userService.getUserList()).thenReturn(list);
		
		List<User> userList = userRepository.getUserList();
		
		assertEquals(3, userList.size());
		verify(userRepository, times(1)).getUserList();
	}

	@Test
	public void getAllUserWithLoginHistoryTest() {
		List<User> list = new ArrayList<User>();
		UserLoginHistory userLoginHistory = new UserLoginHistory(new Date(), "0.0.0.0", user1);
		List<UserLoginHistory> userLoginHistories = new ArrayList<>();
		userLoginHistories.add(userLoginHistory);
		user1.setUserLoginHistories(userLoginHistories);
		list.add(user1);
		
		when(userService.getUserList()).thenReturn(list);
		
		List<User> userList = userRepository.getUserList();
		
		assertEquals(1, userList.size());
		assertEquals(1, userList.get(0).getUserLoginHistories().size());
		verify(userRepository, times(1)).getUserList();
	}
	
	@Test
	public void addUserAndUserHistoryTest() {
		
		User userSaved = userService.add(user1);
		UserLoginHistory userLoginHistory = new UserLoginHistory(new Date(), "0.0.0.0", userSaved);
		userLoginHistoryRepository.save(userLoginHistory);
		
		verify(userRepository, times(1)).save(user1);
		verify(userLoginHistoryRepository, times(1)).save(userLoginHistory);
	}
	
	@Test
	void updateUser() {
		userService.update(user1);
		
		verify(userRepository, times(1)).save(user1);
	}
	
	@Test
	void deleteUser() {
		long userId = 1;
		userRepository.deleteById(userId);
		verify(userRepository, times(1)).deleteById(userId);
	}
}
