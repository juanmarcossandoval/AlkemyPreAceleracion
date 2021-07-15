package com.apirest.disney.controllers;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.apirest.disney.dtos.MovieDTOCreate;
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

	// lista las peliculas en base de datos
	@GetMapping
	public ResponseEntity<?> movieFilters(@RequestParam(name="name",required=false) Optional<String> name,
											@RequestParam(name="genre",required=false) Optional<Long> idGenre,
											@RequestParam(name="order",required=false) Optional<String> order){
		if (name.isPresent()) {
			List<Movie> encontradas = movieServ.findAllByTitulo(name.get());
			return new ResponseEntity<>(mmm.movieListToDTOList(encontradas),HttpStatus.OK);
		} else if (idGenre.isPresent()){
			List<Movie> encontradas = movieServ.findAllByGenero(idGenre.get());
			return new ResponseEntity<>(mmm.movieListToDTOList(encontradas),HttpStatus.OK);
		} else if (order.isPresent()){
			if (order.get()=="ASC") {
				List<Movie> encontradas = movieServ.findByOrderByCreacionAsc();
				return new ResponseEntity<>(mmm.movieListToDTOList(encontradas),HttpStatus.OK);
			} else if (order.get()=="DESC") {
				List<Movie> encontradas = movieServ.findByOrderByCreacionDesc();
				return new ResponseEntity<>(mmm.movieListToDTOList(encontradas),HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Debe indicar el orden",HttpStatus.BAD_REQUEST);
			}
		} else {
			List<Movie> listado = movieServ.findAllMovies();
			if (listado.isEmpty()) {
				return new ResponseEntity<>("No existen peliculas en la base de datos", HttpStatus.OK);
			} else {
				return new ResponseEntity<>(mmm.movieListToDTOSimple(listado), HttpStatus.OK);
			}
		}
	}

	// crea una nueva pelicula
	@PostMapping
	public ResponseEntity<?> createMovie(@RequestBody MovieDTOCreate movieDTOC) {
		Movie movieAux = mmm.DTOCreateToMovie(movieDTOC);
		movieAux = movieServ.createMovie(movieAux);
		return new ResponseEntity<>(mmm.MovieToDto(movieAux), HttpStatus.CREATED);
	}

	// Elimina una pelicula
	@DeleteMapping(value = "/{idMovie}")
	public ResponseEntity<?> deleteMovie(@PathVariable Long idMovie) {
		movieServ.deleteMovie(idMovie);
		return new ResponseEntity<>("Se elimino la pelicula..", HttpStatus.OK);
	}

	// Actualiza una pelicula
	@PutMapping(value = "/{idMovie}")
	public ResponseEntity<?> updateMovie(@PathVariable Long idMovie, @RequestBody MovieDTOCreate movieDTOC) {
		Movie movieAux = mmm.DTOCreateToMovie(movieDTOC);
		movieAux.setId(idMovie);
		movieAux = movieServ.updateMovie(movieAux);
		if (movieAux!=null) {
			return new ResponseEntity<>(mmm.MovieToDto(movieAux), HttpStatus.OK);
		}else {
			return new ResponseEntity<>("No se pudo actualizar la pelicula..", HttpStatus.BAD_REQUEST);
		}
	}

	// Muesta los detalles de una pelicula
	@GetMapping(value = "/{idMovie}")
	public ResponseEntity<?> MovieDetails(@PathVariable Long idMovie) {
		Movie movieAux = movieServ.findById(idMovie);
		if (movieAux != null) {
			return new ResponseEntity<>(mmm.MovieToDto(movieAux), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No se encontro la pelicula", HttpStatus.BAD_REQUEST);
		}
	}

	// asocia una lista de personajes con la pelicula
	@PostMapping(value = "/{idMovie}/addCharacters")
	public ResponseEntity<?> addCharacters(@PathVariable Long idMovie, @RequestBody List<Long> idPersonages) {
		if (idPersonages.isEmpty()) {
			return new ResponseEntity<>("Debe haber al menos 1 personaje para asociar", HttpStatus.BAD_REQUEST);
		} else if (idPersonages.size() == 1) {
			Movie movieAux = movieServ.addCharacter(idPersonages.get(0), idMovie);
			if (movieAux != null) {
				return new ResponseEntity<>(mmm.MovieToDto(movieAux), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se pudo asociar el personaje", HttpStatus.BAD_REQUEST);
			}
		} else {
			Movie movieAux = movieServ.addCharacters(idMovie, idPersonages);
			// es la forma mas eficiente que se me ocurrio de agregar personajes
			if (movieAux != null) {
				return new ResponseEntity<>(mmm.MovieToDto(movieAux), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se pudieron asociar los personajes", HttpStatus.BAD_REQUEST);
			}
		}
	}

	// desasocia una lista de personajes de una pelicula
	@PostMapping(value = "/{idMovie}/removeCharacters")
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
