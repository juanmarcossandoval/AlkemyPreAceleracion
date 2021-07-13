package com.apirest.disney.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apirest.disney.models.User;
import com.apirest.disney.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	
	public User createUser(User usuario){
		usuario.setActive(true);
		usuario.setRoles("USER");
		return userRepo.save(usuario);
	}
	
	public User updateUser(User usuario) {
		return userRepo.save(usuario);
	}
	
	public User deleteUser(User usuario) {
		usuario.setActive(false);
		return userRepo.save(usuario);
	}
	
	public Optional<User> findById(Long id) {
		return userRepo.findById(id);
	}
	
	public List<User> findAllUsers(){
		return userRepo.findAll();
	}
	
	public Optional<User> buscarpornombre(String userName) {
		return userRepo.findByUserName(userName);
	}
	
	public Optional<User> buscarporemail(String email) {
		return userRepo.findByEmail(email);
	}
}
