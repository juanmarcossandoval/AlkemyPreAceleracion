package com.apirest.disney.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.disney.models.Genre;
import com.apirest.disney.models.Movie;
import com.apirest.disney.repositories.GenreRepository;
import com.apirest.disney.repositories.MovieRepository;

@Service
public class GenreService {

	@Autowired
	GenreRepository genreRepo;

	@Autowired
	MovieRepository movieRepo;

	public Genre createGenre(Genre genero) {
		return genreRepo.save(genero);
	}

	public Genre updateGenre(Genre genero) {
		Genre genreAux = genreRepo.findById(genero.getId()).orElse(null);
		if (genreAux!=null) {
			genero.setMovies(genreAux.getMovies());
			return genreRepo.save(genero);
		} else {
			return null;
		}
	}

	public void deleteGenre(Long idGenre) {
		Genre genreAux = genreRepo.findById(idGenre).orElse(null);
		if (genreAux != null) {
			genreAux = removeAllMovies(genreAux);
			genreRepo.delete(genreAux);
		}
	}

	public Genre findById(Long id) {
		return genreRepo.findById(id).orElse(null);
	}

	public List<Genre> findAllGenres() {
		return genreRepo.findAll();
	}

	// remueve todas las relaciones entre el genero y la pelicula para despues
	// eliminarlo
	// de lo contrario al eliminar un genero se eliminaran todas las peliculas
	// asociadas
	private Genre removeAllMovies(Genre genre) {
		List<Movie> listaAux = new ArrayList<>();
		for (Movie m : genre.getMovies()) {
			listaAux.add(m);
		}
		for (Movie movie : listaAux) {
			genre.removeMovie(movie);
			movie.setGenero(null);
			movieRepo.save(movie);
			genreRepo.save(genre);
		}
		return genre;
	}

	// supongo que lo sobrepense pero si agrego peliculas a la coleccion de un
	// genero
	// y ya tenian un genero asignado primero hay que desasociar la vieja relacion y
	// despues persistir la nueva
	public Genre addMovie(Long idMovie, Long idGenre) {
		Genre genreAux = genreRepo.findById(idGenre).orElse(null);
		Movie movieAux = movieRepo.findById(idMovie).orElse(null);
		if (genreAux != null & movieAux != null) {
			if (movieAux.getGenero() == null) {
				movieAux.setGenero(genreAux);
				movieAux = movieRepo.save(movieAux);
				genreAux.addMovie(movieAux);
				genreAux = genreRepo.save(genreAux);
				return genreAux;
			} else if (movieAux.getGenero() != genreAux) {
				Genre oldGenre = movieAux.getGenero();
				oldGenre.removeMovie(movieAux);
				movieAux.setGenero(genreAux);
				genreRepo.save(oldGenre);
				movieAux = movieRepo.save(movieAux);
				genreAux.addMovie(movieAux);
				genreAux = genreRepo.save(genreAux);
				return genreAux;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	// al igual que la situacion anterior deberia quitar todas las relaciones antes
	// de actualizarlas
	public Genre addMovies(Long idGenre, List<Long> idMovies) {
		Genre genreAux = genreRepo.findById(idGenre).orElse(null);
		if (genreAux != null) {
			for (Long idMov : idMovies) {
				Movie movieAux = movieRepo.findById(idMov).orElse(null);
				if (movieAux != null) {
					if (movieAux.getGenero() == null) {
						movieAux.setGenero(genreAux);
						movieAux = movieRepo.save(movieAux);
						genreAux.addMovie(movieAux);
						genreAux = genreRepo.save(genreAux);
					} else {
						if (movieAux.getGenero() != genreAux) {
							Genre oldGenre = movieAux.getGenero();
							oldGenre.removeMovie(movieAux);
							movieAux.setGenero(genreAux);
							genreRepo.save(oldGenre);
							movieAux = movieRepo.save(movieAux);
							genreAux.addMovie(movieAux);
							genreAux = genreRepo.save(genreAux);
						}
					}
				}
			}
			return genreAux;
		} else {
			return null;
		}
	}

	public Genre removeMovie(Long idMovie, Long idGenre) {
		Genre genreAux = genreRepo.findById(idGenre).orElse(null);
		Movie movieAux = movieRepo.findById(idMovie).orElse(null);
		if (genreAux != null & movieAux != null) {
			if (movieAux.getGenero() == genreAux) {
				genreAux.removeMovie(movieAux);
				movieAux.setGenero(null);
				movieAux = movieRepo.save(movieAux);
				genreAux = genreRepo.save(genreAux);
				return genreAux;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public Genre removeMovies(Long idGenre, List<Long> idMovies) {
		Genre genreAux = genreRepo.findById(idGenre).orElse(null);
		List<Movie> moviesListAux = movieRepo.findAllById(idMovies);
		if (genreAux != null) {
			for (Movie mAux : moviesListAux) {
				if (mAux.getGenero() == genreAux) {
					genreAux.removeMovie(mAux);
					mAux.setGenero(null);
					movieRepo.save(mAux);
				}
			}
			genreAux = genreRepo.save(genreAux);
			return genreAux;
		} else {
			return null;
		}
	}
}
