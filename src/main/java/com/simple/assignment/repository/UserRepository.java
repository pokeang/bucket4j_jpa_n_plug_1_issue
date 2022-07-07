package com.simple.assignment.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.simple.assignment.model.Gender;
import com.simple.assignment.model.Role;
import com.simple.assignment.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Serializable>{
	
	// @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths="emails")
	@Query("SELECT u FROM User u INNER JOIN FETCH UserEmail ue ON u.id = ue.user WHERE ue.email = :email AND ue.isActive = true AND u.isDeleted = false")
	public User getUserByEmail(@Param("email") String email);
	
	@EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths="userLoginHistories")
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.emails WHERE u.isDeleted = false")
	public List<User> getUserList();
	
	@Modifying
	@Query(value = "UPDATE users SET first_name= :firstName, last_name = :lastName, emails = :email, role = :role, gender = :gender, phone_number = :phoneNumber, city = :city, address1 = :address1, update_date = :updateDate WHERE user_id = :userId", nativeQuery = true)
	public void updateUser(@Param("userId") long userId, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email
			, @Param("role") Role role, @Param("gender") Gender gender, @Param("phoneNumber") String phoneNumber, @Param("city") String city, @Param("address1") String address1, @Param("updateDate") Date updateDate);


}
