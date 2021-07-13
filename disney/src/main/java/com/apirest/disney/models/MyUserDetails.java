package com.apirest.disney.models;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class MyUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 5353393597569223275L;
	
	private String userName;
	private String password;
	private String email;
	private boolean active;
	private List<GrantedAuthority> authorities;
	
	public MyUserDetails(User user) {
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.active = user.isActive();
		this.email = user.getEmail();
		this.authorities = Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		//PREGUNTAR
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// PREGUNTAR
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// PREGUNTAR
		return true;
	}

	@Override
	public boolean isEnabled() {
		// PREGUNTAR
		return active;
	}

	
}
