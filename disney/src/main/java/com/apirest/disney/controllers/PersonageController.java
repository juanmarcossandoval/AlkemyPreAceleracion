package com.apirest.disney.controllers;

import java.util.ArrayList;
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

import com.apirest.disney.dtos.PersonageDTO;
import com.apirest.disney.dtos.PersonageDTOSimple;
import com.apirest.disney.mapper.MarvelModelMapper;
import com.apirest.disney.services.MovieService;
import com.apirest.disney.services.PersonageService;
import com.apirest.disney.models.Movie;
import com.apirest.disney.models.Personage;

@Controller
@RequestMapping("/characters")
public class PersonageController {

	@Autowired
	PersonageService personageServ;
	@Autowired
	MovieService movieServ;

	@Autowired
	MarvelModelMapper mmm;

	// LISTADO DETALLES MINIMOS
	@GetMapping
	public ResponseEntity<?> listarPersonajes() {
		List<Personage> personajes = new ArrayList<>();
		personajes = personageServ.findAllPersonages();
		if (personajes.isEmpty()) {
			return new ResponseEntity<>("No existen personajes", HttpStatus.BAD_REQUEST);
		} else {
			List<PersonageDTOSimple> dtoper = new ArrayList<>();
			dtoper = mmm.personajeListToListDTOSimple(personajes);
			return new ResponseEntity<>(dtoper, HttpStatus.OK);
		}
	}

	// CREACION DE PERSONAJE
	@PostMapping
	public ResponseEntity<?> crearPersonje(@RequestBody PersonageDTO personajeDTO) {
		Personage encontrado = personageServ.findByName(personajeDTO.getNombre());
		if (encontrado != null) {
			return new ResponseEntity<>("El personaje ya existe", HttpStatus.BAD_REQUEST);
		} else {
			Personage personajeaux = mmm.DTOToPersonaje(personajeDTO);
			personajeaux = personageServ.createPersonage(personajeaux);
			PersonageDTO dtoresp = mmm.personajeToDTO(personajeaux);
			return new ResponseEntity<>(dtoresp, HttpStatus.CREATED);
		}
	}

	// ELIMINACION DE UN PERSONAJE POR ID
	@DeleteMapping(value = "{id}")
	public ResponseEntity<?> eliminarPersonaje(@PathVariable Long id) {
		Personage encontrado = personageServ.findById(id);
		if (encontrado != null) {
			personageServ.deletePersonage(id);
			return new ResponseEntity<>("Se elimino con exito el personaje..", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("El personaje que desea eliminar no existe..", HttpStatus.BAD_REQUEST);
		}
	}

	// ACTUALIZACION DE UN PERSONAJE
	@PutMapping(value = "{id}")
	public ResponseEntity<?> actualizarPersonaje(@RequestBody PersonageDTO personajeDTO, @PathVariable Long id) {
		Personage encontrado = personageServ.findById(id);
		if (encontrado != null) {
			Personage personajeaux = mmm.DTOToPersonaje(personajeDTO);
			personajeaux.setId(id);
			personageServ.updatePersonage(personajeaux);
			return new ResponseEntity<>(mmm.personajeToDTO(personajeaux), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("El personaje que desea actualizar no existe", HttpStatus.BAD_REQUEST);
		}
	}

	// DETALLA UN PERSONAJE POR EL ID
	@GetMapping(value = "{id}")
	public ResponseEntity<?> detallarpersonaje(@PathVariable Long id) {
		Personage encontrado = personageServ.findById(id);
		if (encontrado != null) {
			return new ResponseEntity<>(mmm.personajeToDTO(encontrado), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No se encuentra ese personaje", HttpStatus.BAD_REQUEST);
		}
	}

	// RELACIONAR PERSONAJE CON PELICULAS
	@PostMapping(value = "/{id}/addMovie/{idMovie}")
	public ResponseEntity<?> agregarpeliculas(@PathVariable Long id, @PathVariable Long idMovie) {
		Personage personajeaux = personageServ.findById(id);
		if (personajeaux != null) { /////////////////////////////////////////////////// comprobar que la pelicula
									/////////////////////////////////////////////////// exista
			Movie peliculaAux = movieServ.findById(idMovie);
			if (peliculaAux != null) {
				List<Movie> personajepeliculas = new ArrayList<>();
				personajepeliculas = personajeaux.getMovies();
				Boolean ok = personajepeliculas.contains(peliculaAux);
				if (ok) {
					return new ResponseEntity<>("La pelicula que desea agregar ya esta asociada con el personaje",
							HttpStatus.BAD_REQUEST);
				} else {
					personajeaux.getMovies().add(peliculaAux);
					personageServ.updatePersonage(personajeaux);
					return new ResponseEntity<>("Pelicula asociada con exito", HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>("La pelicula que desea asociar no existe", HttpStatus.BAD_REQUEST);
			}

		} else {
			return new ResponseEntity<>("El personaje no existe en la BBDD", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/filter")
	public ResponseEntity<?> filtrarpersonajes(@RequestParam(name = "name", required = false) Optional<String> name,
			@RequestParam(name = "age", required = false) Optional<Integer> age,
			@RequestParam(name = "weight", required = false) Optional<Double> weight,
			@RequestParam(name = "idMovie", required = false) Optional<Long> idMovie) {
		if (name.isPresent()) {
			List<Personage> encontrados = personageServ.findAllByNombre(name.get());
			return new ResponseEntity<>(encontrados, HttpStatus.OK);
		} else if (age.isPresent()) {
			List<Personage> encontrados = personageServ.findByEdad(age.get());
			return new ResponseEntity<>(encontrados, HttpStatus.OK);
		} else if (weight.isPresent()) {
			List<Personage> encontrados = personageServ.findAllByPeso(weight.get());
			return new ResponseEntity<>(encontrados, HttpStatus.OK);
		} else if (idMovie.isPresent()) {

			List<Personage> encontrados = personageServ.findAllByMovies(idMovie.get());
			return new ResponseEntity<>(encontrados, HttpStatus.OK);

		} else {
			return new ResponseEntity<>("lista vacia", HttpStatus.BAD_REQUEST);
		}
	}

}
