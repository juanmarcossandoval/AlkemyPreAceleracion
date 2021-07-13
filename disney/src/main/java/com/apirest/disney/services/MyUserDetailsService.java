package com.apirest.disney.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apirest.disney.models.MyUserDetails;
import com.apirest.disney.models.User;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserService userServ;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Optional<User> user = userServ.buscarpornombre(username);
		
		user.orElseThrow(()-> new UsernameNotFoundException(username + "no esta registrado.."));
		return user.map(MyUserDetails::new).get();
	}
}
