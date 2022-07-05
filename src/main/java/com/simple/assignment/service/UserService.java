package com.simple.assignment.service;

import java.util.List;

import com.simple.assignment.model.User;

public interface UserService {
	List<User> getUserList();
	List<User> getAllUserList();
	User getUserById(long userId);
	User findUserByEmail(String emails);
	boolean delete(long userId);
	boolean permanentDelete(long userId);
	User update(User user);
	User add(User user);
}
