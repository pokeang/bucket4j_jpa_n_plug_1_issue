package com.simple.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simple.assignment.model.UserLoginHistory;

public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Long>{

}
