package com.simple.assignment.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.simple.assignment.model.UserEmail;

@Repository
public interface UserEmailRepository extends JpaRepository<UserEmail, Integer>{
	
	@Query("SELECT e FROM UserEmail e WHERE e.email IN ?1")
	Optional<UserEmail> findByEmails(Set<UserEmail> emails);
	
	@Query("SELECT e FROM UserEmail e WHERE e.email = ?1 AND isActive = true")
	UserEmail findByEmail(String email);
}
