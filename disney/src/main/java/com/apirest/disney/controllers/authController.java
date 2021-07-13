package com.apirest.disney.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apirest.disney.models.AuthenticationRequest;
import com.apirest.disney.models.AuthenticationResponse;
import com.apirest.disney.models.User;
import com.apirest.disney.services.JwtUtil;
import com.apirest.disney.services.MyUserDetailsService;
import com.apirest.disney.services.UserService;

@Controller
@RequestMapping("/auth")
public class authController {
	
	@Autowired 
	AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtTokenUtil;
	@Autowired
	private UserService userServ;

	@PostMapping(value="/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)throws Exception{
		
		try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(),
						authenticationRequest.getPassword())
				);
		
		}catch (BadCredentialsException e) {
			throw new Exception("Credenciales invalidas, contraseña y/p usuario incorrectos..", e);
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return new ResponseEntity<>(new AuthenticationResponse(jwt),HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value="/register")
	public ResponseEntity<?> createUser(@RequestBody User user){
		Optional<User> usuario = userServ.buscarpornombre(user.getUserName());
		if (usuario.isPresent()) {
			return new ResponseEntity<>("Ya existe este usuario",HttpStatus.BAD_REQUEST);
		}else {
			if (user.getEmail()==null) {
				return new ResponseEntity<>("El campo email es requerido",HttpStatus.BAD_REQUEST);
			}else {
				return new ResponseEntity<>(userServ.createUser(user),HttpStatus.CREATED);
			}	
		}
		
	}
}
