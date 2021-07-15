package com.apirest.disney.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.disney.models.Movie;
import com.apirest.disney.models.Personage;
import com.apirest.disney.repositories.MovieRepository;
import com.apirest.disney.repositories.PersonageRepository;

@Service
public class PersonageService {
	
	@Autowired
	PersonageRepository personageRepo;
	
	@Autowired
	MovieRepository movieRepo;

	public Personage createPersonage(Personage personaje){
		return personageRepo.save(personaje);
	}
	
	public Personage updatePersonage(Personage personaje) {
		Personage personageAux = personageRepo.findById(personaje.getId()).orElse(null);
		if (personageAux!= null) {
			personaje.setMovies(personageAux.getMovies());
			return personageRepo.save(personaje);
		} else {
			return null;
		}
	}
	
	public void deletePersonage(Long idPersonage) {
		Personage personageAux = personageRepo.findById(idPersonage).orElse(null);
		if (personageAux !=null) {
			personageAux = removeAllMovies(personageAux);
			personageRepo.delete(personageAux);
		}
	}
	
	private Personage removeAllMovies (Personage personage) {
		List<Movie> listaAux= new ArrayList<>();
		for (Movie m:personage.getMovies()) {
			listaAux.add(m);
		}
		for (Movie movie: listaAux) {
			movie.removePersonage(personage);
			personage.removeMovie(movie);
			movieRepo.save(movie);
			personageRepo.save(personage);
		}
		return personage;
	}
	
	public Personage findById(Long id) {
		return personageRepo.findById(id).orElse(null);
	}
	
	public List<Personage> findAllPersonages(){
		return personageRepo.findAll();
	}
	
	
	////////////////////////////////////////////////////////////
	////////////FUNCIONES DE FILTRADO//////////////////////////
	///////////////////////////////////////////////////////////
	
	
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
