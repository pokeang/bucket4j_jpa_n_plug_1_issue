package com.simple.assignment.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.simple.assignment.model.UserEmail;
import com.simple.assignment.model.Status;
import com.simple.assignment.model.User;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private User user;

	public CustomUserDetails(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority(this.user.getRole().toString()));
		return list;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.getStatus() == Status.ENABLE;
	}

	@Override
	public String getUsername() {
		UserEmail email = user.getEmails().stream().filter(e -> e.isActive()).findFirst().get();
		return email.getEmail(); // 
		// return user.getFirstName() + " " + this.user.getLastName();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	// Just an example to put some addititional Data to your logged in user
	public String getUserDatabase() {
		return "usertable" + 1 + this.user.getUserId();
	}

}
