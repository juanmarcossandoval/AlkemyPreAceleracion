package com.apirest.disney.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.disney.models.Movie;
import com.apirest.disney.models.Personage;
import com.apirest.disney.repositories.PersonageRepository;

@Service
public class PersonageService {
	
	@Autowired
	PersonageRepository personageRepo;

	public Personage createPersonage(Personage personaje){
		return personageRepo.save(personaje);
	}
	
	public Personage updatePersonage(Personage personaje) {
		return personageRepo.save(personaje);
	}
	
	public void deletePersonage(Long id) {
		personageRepo.deleteById(id);
	}
	
	public Personage findById(Long id) {
		return personageRepo.findById(id).orElse(null);
	}
	
	public List<Personage> findAllPersonages(){
		return personageRepo.findAll();
	}
	
	public Personage findByName(String nombre) {
		Personage encontrado = personageRepo.findByNombre(nombre);
		return encontrado;
	}
	
	public List<Personage> findByEdad(Integer edad){
		return personageRepo.findByEdad(edad);
	}
	
	public List<Personage> findAllByNombre(String nombre){
		return personageRepo.findAllByNombre(nombre);
	}
	
	public List<Personage> findAllByMovies(Long id){
		Movie peli = new Movie();
		peli.setId(id);
		return personageRepo.findAllByMovies(peli);
	}
	
	public List<Personage> findAllByPeso(Double peso){
		return personageRepo.findAllByPeso(peso);
	}
}
