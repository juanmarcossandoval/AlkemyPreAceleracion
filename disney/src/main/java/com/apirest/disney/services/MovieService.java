package com.apirest.disney.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.disney.models.Genre;
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

	public Movie createMovie(Movie movie) {
		return movieRepo.save(movie);
	}

	public Movie updateMovie(Movie movie) {
		Movie movieAux = movieRepo.findById(movie.getId()).orElse(null);
		if (movieAux!=null) {
			movie.setPersonages(movieAux.getPersonages());
			return movieRepo.save(movie);
		}else {
			return null;
		}
	}

	public void deleteMovie(Long idMovie) {
		Movie movieAux = movieRepo.findById(idMovie).orElse(null);
		if (movieAux!= null) {
			movieAux = removeAllCharacters(movieAux);
			movieRepo.delete(movieAux);
		}
	}

	public Movie findById(Long id) {
		return movieRepo.findById(id).orElse(null);
	}

	public List<Movie> findAllMovies() {
		return movieRepo.findAll();
	}

	public Movie addCharacter(Long idCharacter, Long idMovie) {
		Personage characterAux = personageRepo.findById(idCharacter).orElse(null);
		Movie movieAux = movieRepo.findById(idMovie).orElse(null);
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
		Personage characterAux = personageRepo.findById(idCharacter).orElse(null);
		Movie movieAux = movieRepo.findById(idMovie).orElse(null);
		if (characterAux != null & movieAux != null) {
			if (movieAux.getPersonages().contains(characterAux)) {
				characterAux.getMovies().remove(movieAux);
				characterAux = personageRepo.save(characterAux);
				movieAux.getPersonages().remove(characterAux);
				movieAux = movieRepo.save(movieAux);
				return movieAux;
			} else {
				return null;
			}
			
		} else {
			return null;
		}
	}

	public Movie addCharacters(Long idMovie, List<Long> idCharacters) {
		List<Personage> listaPersonages = personageRepo.findAllById(idCharacters);
		List<Personage> encontrados = new ArrayList<>();
		Movie movieAux = movieRepo.findById(idMovie).orElse(null);

		if (movieAux != null & !listaPersonages.isEmpty()) {
			for (Personage p : listaPersonages) {
					p.getMovies().add(movieAux);
					encontrados.add(p);
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
		List<Personage> personagesListAux = personageRepo.findAllById(idCharacters);
		if (movieAux != null) {
			for (Personage pAux : personagesListAux) {
				if (movieAux.getPersonages().contains(pAux)) {
					pAux.getMovies().remove(movieAux);
					personageRepo.save(pAux);
					movieAux.getPersonages().remove(pAux);
				}	
			}
			movieAux = movieRepo.save(movieAux);
			return movieAux;
		} else {
			return null;
		}
	}
	
	private Movie removeAllCharacters(Movie peli) {
		List<Personage> listaAux= new ArrayList<>();
		for(Personage p:peli.getPersonages()) {
			listaAux.add(p);
		}
		for(Personage character:listaAux) {
			character.getMovies().remove(peli);
			peli.getPersonages().remove(character);
			personageRepo.save(character);
			movieRepo.save(peli);
		}
		return peli;
	}
	
	////////////////////////////////////////////////////////////
	////////////FUNCIONES DE FILTRADO//////////////////////////
	///////////////////////////////////////////////////////////

	public List<Movie> findAllByTitulo(String titulo) {
		return movieRepo.findAllByTitulo(titulo);
	}

	public List<Movie> findAllByGenero(Long idGenre) {
		Genre genre = new Genre();
		genre.setId(idGenre);
		return movieRepo.findAllByGenero(genre);
	}

	public List<Movie> findByOrderByCreacionAsc() {
		return movieRepo.findByOrderByCreacionAsc();
	}

	public List<Movie> findByOrderByCreacionDesc() {
		return movieRepo.findByOrderByCreacionDesc();
	}

}
