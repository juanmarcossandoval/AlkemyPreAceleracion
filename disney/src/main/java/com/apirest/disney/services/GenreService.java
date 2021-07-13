package com.apirest.disney.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.disney.models.Genre;
import com.apirest.disney.repositories.GenreRepository;

@Service
public class GenreService {
	
	@Autowired
	GenreRepository genreRepo;
	
	public Genre createGenre(Genre genero){
		return genreRepo.save(genero);
	}
	
	public Genre updateGenre(Genre genero) {
		return genreRepo.save(genero);
	}
	
	public Boolean deleteGenre(Genre genero) {
		genreRepo.delete(genero);
		Optional<Genre> ok = genreRepo.findById(genero.getId());
		return ok.isPresent();
	}
	
	public Optional<Genre> findById(Long id) {
		return genreRepo.findById(id);
	}
	
	public List<Genre> findAllGenres(){
		return genreRepo.findAll();
	}

}
