package com.simple.assignment.service;

import java.util.Optional;
import java.util.Set;

import com.simple.assignment.model.UserEmail;

public interface UserEmailService {
	UserEmail findUserEmail(String email);
	Optional<UserEmail> findUserEmails(Set<UserEmail> emails);
	UserEmail add(UserEmail userEmail);
}
