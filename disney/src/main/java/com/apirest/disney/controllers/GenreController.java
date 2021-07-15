package com.apirest.disney.controllers;

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

import com.apirest.disney.dtos.GenreDTOCreate;
import com.apirest.disney.mapper.MarvelModelMapper;
import com.apirest.disney.models.Genre;
import com.apirest.disney.services.GenreService;

@Controller
@RequestMapping(value = "/genres")
public class GenreController {
	
	@Autowired
	GenreService genreServ;
	
	@Autowired
	MarvelModelMapper mmm;
	
	// lista los generos en base de datos 
	@GetMapping
	public ResponseEntity<?> GenreFilters(){
		List<Genre> listado = genreServ.findAllGenres();
		if (listado.isEmpty()) {
			return new ResponseEntity<>("No existen generos en la base de datos", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(mmm.genreListToDTOCreate(listado),HttpStatus.OK);
		}
	}
	
	// crea un nuevo genero
	@PostMapping
	public ResponseEntity<?> createGenre(@RequestBody GenreDTOCreate genreDTOC){
		Genre genreAux = mmm.genreDTOCreateToGenre(genreDTOC);
		genreAux = genreServ.createGenre(genreAux);
		return new ResponseEntity<>(mmm.genreToDTO(genreAux),HttpStatus.CREATED);
	}
	
	// elimina un genero
	@DeleteMapping(value = "/{idGenre}")
	public ResponseEntity<?> deleteGenre(@PathVariable Long idGenre){
		genreServ.deleteGenre(idGenre);
		return new ResponseEntity<>("Se elimino el genero..",HttpStatus.OK);
	}
	
	// actualiza un genero
	@PutMapping(value = "/{idGenre}")
	public ResponseEntity<?> updateGenre(@PathVariable Long idGenre, @RequestBody GenreDTOCreate genreDto){
		Genre genreAux = mmm.genreDTOCreateToGenre(genreDto);
		genreAux.setId(idGenre);
		genreAux = genreServ.updateGenre(genreAux);
		if (genreAux!=null) {
			return new ResponseEntity<>(mmm.genreToDTO(genreAux),HttpStatus.OK);
		}else {
			return new ResponseEntity<>("No se pudo actualizar el genero..",HttpStatus.BAD_REQUEST);
		}
	}
	
	// muestra el detalle de un genero
	@GetMapping (value ="/{idGenre}")
	public ResponseEntity<?> GenreDetails(@PathVariable Long idGenre){
		Genre genreAux = genreServ.findById(idGenre);
		if (genreAux!=null) {
			return new ResponseEntity<>(mmm.genreToDTO(genreAux),HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No se encontro el genero",HttpStatus.BAD_REQUEST);
		}
	}
	
	// asocia una lista de peliculas con un genero
	@PostMapping(value = "/{idGenre}/addMovies")
	public ResponseEntity<?> addMovies (@PathVariable Long idGenre, @RequestBody List<Long> idMovies) {
		if (idMovies.isEmpty()) {
			return new ResponseEntity<>("Debe haber al menos 1 pelicula para asociar",HttpStatus.BAD_REQUEST);
		} else if (idMovies.size()==1) {
			Genre genreAux = genreServ.addMovie(idMovies.get(0), idGenre);
			if (genreAux != null) {
				return new ResponseEntity<>(mmm.genreToDTO(genreAux),HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se pudo asociar la pelicula", HttpStatus.OK);
			}
		} else {
			Genre genreAux = genreServ.addMovies(idGenre,idMovies);
			if (genreAux!= null) {
				return new ResponseEntity<>(mmm.genreToDTO(genreAux),HttpStatus.OK);
			}else {
				return new ResponseEntity<>("No se pudieron asociar las peliculas",HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	//desasicua una lista de peliculas de un genero
	@PostMapping (value ="/{idGenre}/removeMovies")
	public ResponseEntity<?> removeMovies (@PathVariable Long idGenre, @RequestBody List<Long> idMovies) {
		if (idMovies.isEmpty()) {
			return new ResponseEntity<>("Debe al menos contener 1 pelicula para desasociar",HttpStatus.BAD_REQUEST);
		} else if (idMovies.size()==1) {
			Genre genreAux = genreServ.removeMovie(idMovies.get(0), idGenre);
			if (genreAux != null) {
				return new ResponseEntity<>(mmm.genreToDTO(genreAux),HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se pudo desaciosar la pelicula",HttpStatus.BAD_REQUEST);
			}
		} else {
			Genre genreAux = genreServ.removeMovies(idGenre,idMovies);
			if (genreAux != null) {
				return new ResponseEntity<>(mmm.genreToDTO(genreAux),HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se pudieron desasociar las peliculas..",HttpStatus.BAD_REQUEST);
			}
		}
	}
	
}
