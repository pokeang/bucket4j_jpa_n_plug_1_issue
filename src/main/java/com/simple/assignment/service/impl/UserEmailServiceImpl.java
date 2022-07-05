package com.simple.assignment.service.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.assignment.model.UserEmail;
import com.simple.assignment.repository.UserEmailRepository;
import com.simple.assignment.service.UserEmailService;

@Service
public class UserEmailServiceImpl implements UserEmailService{
	@Autowired
	private UserEmailRepository userEmailRepository;

	@Override
	public Optional<UserEmail> findUserEmails(Set<UserEmail> emails) {
		return userEmailRepository.findByEmails(emails);
	}

	@Override
	public UserEmail findUserEmail(String email) {
		// TODO Auto-generated method stub
		return userEmailRepository.findByEmail(email);
	}

	@Override
	public UserEmail add(UserEmail userEmail) {
		return userEmailRepository.save(userEmail);
	}

}
