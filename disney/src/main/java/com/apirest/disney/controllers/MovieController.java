package com.apirest.disney.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apirest.disney.dtos.MovieDTO;
import com.apirest.disney.dtos.MovieDTOSimple;
import com.apirest.disney.mapper.MarvelModelMapper;
import com.apirest.disney.models.Movie;
import com.apirest.disney.services.MovieService;

@Controller
@RequestMapping(value = "/movies")
public class MovieController {

	@Autowired
	MovieService movieServ;

	@Autowired
	MarvelModelMapper mmm;

	// lista las peliculas
	@GetMapping
	public ResponseEntity<?> listarPeliculas() {
		List<Movie> listado = movieServ.findAllMovies();
		if (listado.isEmpty()) {
			return new ResponseEntity<>("No Existen peliculas en la base de datos", HttpStatus.OK);
		} else {
			List<MovieDTOSimple> encontradas = new ArrayList<>();
			encontradas = mmm.movieListToDTOSimple(listado);
			return new ResponseEntity<>(encontradas, HttpStatus.OK);
		}
	}

	// crea una nueva pelicula
	@PostMapping
	public ResponseEntity<?> createMovie(@RequestBody MovieDTO movieDto) {
		Movie movieAux = mmm.DTOToMovie(movieDto);
		movieAux = movieServ.createMovie(movieAux);
		// MovieDTO DTOResponse = mmm.MovieToDto(movieAux);
		// return new ResponseEntity<>(DTOResponse, HttpStatus.CREATED);
		return new ResponseEntity<>(mmm.MovieToDto(movieAux), HttpStatus.CREATED);
	}

	// Elimina una pelicula
	@DeleteMapping(value = "{idMovie}")
	public ResponseEntity<?> deleteMovie(@PathVariable Long idMovie) {
		movieServ.deleteMovie(idMovie);
		return new ResponseEntity<>("Se elimino la pelicula..", HttpStatus.OK);
	}

	// Actualiza una pelicula
	@PutMapping(value = "{idMovie}")
	public ResponseEntity<?> updateMovie(@PathVariable Long idMovie, @RequestBody MovieDTO movieDTO) {
		Movie movieAux = mmm.DTOToMovie(movieDTO);
		movieAux.setId(idMovie);
		movieAux = movieServ.updateMovie(movieAux);
		return new ResponseEntity<>(mmm.MovieToDto(movieAux), HttpStatus.OK);
	}

	// Muesta los detalles de una pelicula
	@GetMapping(value = "{idMovie}")
	public ResponseEntity<?> MovieDetails(@PathVariable Long idMovie) {
		Movie movieAux = movieServ.findById(idMovie);
		if (movieAux != null) {
			return new ResponseEntity<>(mmm.MovieToDto(movieAux), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No se encontro la pelicula", HttpStatus.BAD_REQUEST);
		}
	}

	// asocia una lista de personajes con la pelicula
	@PostMapping(value = "{idMovie}/addCharacters")
	public ResponseEntity<?> addCharacters(@PathVariable Long idMovie, @RequestBody List<Long> idPersonages) {
		if (idPersonages.isEmpty()) {
			return new ResponseEntity<>("Debe haber al menos 1 personaje para asociar", HttpStatus.BAD_REQUEST);
		} else if (idPersonages.size() == 1) {
			Movie movieAux = movieServ.addCharacter(idPersonages.get(0), idMovie);
			if (movieAux != null) {
				return new ResponseEntity<>(mmm.MovieToDto(movieAux), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se pudo agregar el personaje", HttpStatus.BAD_REQUEST);
			}
		} else {
			Movie movieAux = movieServ.addCharacters(idMovie, idPersonages);
			// es la forma mas eficiente que se me ocurrio de agregar personajes
			if (movieAux != null) {
				return new ResponseEntity<>(mmm.MovieToDto(movieAux), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se pudieron agregar los personajes", HttpStatus.BAD_REQUEST);
			}
		}
	}

	// desasocia una lista de personajes de una pelicula
	@PostMapping(value = "{idMovie}/removeCharacters")
	public ResponseEntity<?> removeCharacters(@PathVariable Long idMovie, @RequestBody List<Long> idPersonages) {
		if (idPersonages.isEmpty()) {
			return new ResponseEntity<>("Debe haber al menos 1 personaje para desasociar", HttpStatus.BAD_REQUEST);
		} else if (idPersonages.size() == 1) {
			Movie movieAux = movieServ.removeCharacter(idPersonages.get(0), idMovie);
			if (movieAux != null) {
				return new ResponseEntity<>(mmm.MovieToDto(movieAux), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se pudo desasociar el personaje", HttpStatus.BAD_REQUEST);
			}
		} else {
			Movie movieAux = movieServ.removeCharacters(idMovie, idPersonages);
			if (movieAux != null) {
				return new ResponseEntity<>(mmm.MovieToDto(movieAux), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se pudieron desasociar los personajes", HttpStatus.BAD_REQUEST);
			}
		}
	}

}
