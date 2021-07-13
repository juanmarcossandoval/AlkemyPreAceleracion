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
public class MovieService {
	@Autowired
	MovieRepository movieRepo;
	@Autowired
	PersonageRepository personageRepo;

	public Movie createMovie(Movie peli) {
		return movieRepo.save(peli);
	}

	public Movie updateMovie(Movie peli) {
		return movieRepo.save(peli);
	}

	public void deleteMovie(Long id) {
		movieRepo.deleteById(id);
	}

	public Movie findById(Long id) {
		return movieRepo.findById(id).orElse(null);
	}

	public List<Movie> findAllMovies() {
		return movieRepo.findAll();
	}

	public Movie addCharacter(Long idCharacter, Long idMovie) {
		Personage characterAux = new Personage();
		characterAux = personageRepo.findById(idCharacter).orElse(null);
		Movie movieAux = new Movie();
		movieAux = movieRepo.findById(idMovie).orElse(null);
		if (characterAux != null & movieAux != null) {
			characterAux.getMovies().add(movieAux);
			characterAux = personageRepo.save(characterAux);
			movieAux.getPersonages().add(characterAux);
			movieAux = movieRepo.save(movieAux);
			return movieAux;
		} else {
			return null;
		}
	}

	public Movie removeCharacter(Long idCharacter, Long idMovie) {
		Personage characterAux = new Personage();
		characterAux = personageRepo.findById(idCharacter).orElse(null);
		Movie movieAux = new Movie();
		movieAux = movieRepo.findById(idMovie).orElse(null);
		if (characterAux != null & movieAux != null) {
			characterAux.getMovies().remove(movieAux);
			characterAux = personageRepo.save(characterAux);
			movieAux.getPersonages().remove(characterAux);
			movieAux = movieRepo.save(movieAux);
			return movieAux;
		} else {
			return null;
		}
	}

	public Movie addCharacters(Long idMovie, List<Long> idCharacters) {
		List<Personage> listaPersonages = personageRepo.findAll();
		List<Personage> encontrados = new ArrayList<>();
		Movie movieAux = movieRepo.findById(idMovie).orElse(null);

		if (movieAux != null & !listaPersonages.isEmpty()) {
			for (Personage p : listaPersonages) {
				if (idCharacters.contains(p.getId())) {
					p.getMovies().add(movieAux);
					encontrados.add(p);
				}
			}
			personageRepo.saveAll(encontrados);
			movieAux.getPersonages().addAll(encontrados);
			movieAux = movieRepo.save(movieAux);
			return movieAux;
		} else {
			return null;
		}
	}

	public Movie removeCharacters(Long idMovie, List<Long> idCharacters) {
		Movie movieAux = movieRepo.findById(idMovie).orElse(null);
		if (movieAux != null) {
			List<Personage> listaPersonages = movieAux.getPersonages();
			//List<Personage> encontrados = new ArrayList<>();
			if (listaPersonages.isEmpty()) {
				return null;
			} else {
				for (Personage p : listaPersonages) {
					if (idCharacters.contains(p.getId())) {
						p.getMovies().size();
						p.getMovies().remove(movieAux);
						//encontrados.add(p);
						personageRepo.save(p);
						movieAux.getPersonages().remove(p);
						movieRepo.save(movieAux);
					}
				}
				//personageRepo.saveAll(encontrados);
				//movieAux = movieRepo.save(movieAux);
				return movieAux;
			}
		} else {
			return null;
		}
	}

}
