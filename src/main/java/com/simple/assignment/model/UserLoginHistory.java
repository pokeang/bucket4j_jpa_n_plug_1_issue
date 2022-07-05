package com.simple.assignment.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="user_login_historiest")
public class UserLoginHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private Date loginDateTime;
	private String loginIP;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	

	public Date getLoginDateTime() {
		return loginDateTime;
	}

	public void setLoginDateTime(Date loginDateTime) {
		this.loginDateTime = loginDateTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserLoginHistory(Date loginDateTime, String loginIP, User user) {
		this.loginDateTime = loginDateTime;
		this.loginIP = loginIP;
		this.user = user;
	}
	
	public UserLoginHistory() {

	}
	
}
