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

import com.apirest.disney.dtos.PersonageDTOCreate;
import com.apirest.disney.mapper.MarvelModelMapper;
import com.apirest.disney.services.PersonageService;
import com.apirest.disney.models.Personage;

@Controller
@RequestMapping("/characters")
public class PersonageController {

	@Autowired
	PersonageService personageServ;

	@Autowired
	MarvelModelMapper mmm;

	// lista los personajes en la base de datos
	@GetMapping
	public ResponseEntity<?> personageFilters(@RequestParam(name = "name", required = false) Optional<String> name,
											@RequestParam(name = "age", required = false) Optional<Integer> age,
											@RequestParam(name = "weight", required = false) Optional<Double> weight,
											@RequestParam(name = "idMovie", required = false) Optional<Long> idMovie) {
		if (name.isPresent()) {
			List<Personage> encontrados = personageServ.findAllByNombre(name.get());
			return new ResponseEntity<>(mmm.personageListToDTOList(encontrados), HttpStatus.OK);
		} else if (age.isPresent()) {
			List<Personage> encontrados = personageServ.findByEdad(age.get());
			return new ResponseEntity<>(mmm.personageListToDTOList(encontrados), HttpStatus.OK);
		} else if (weight.isPresent()) {
			List<Personage> encontrados = personageServ.findAllByPeso(weight.get());
			return new ResponseEntity<>(mmm.personageListToDTOList(encontrados), HttpStatus.OK);
		} else if (idMovie.isPresent()) {

			List<Personage> encontrados = personageServ.findAllByMovies(idMovie.get());
			return new ResponseEntity<>(mmm.personageListToDTOList(encontrados), HttpStatus.OK);

		} else {
			List<Personage> listado = personageServ.findAllPersonages();
			if (listado.isEmpty()) {
				return new ResponseEntity<>("No existen personajes", HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<>(mmm.personajeListToListDTOSimple(listado), HttpStatus.OK);
			}
		}
	}

	// crea un nuevo personaje
	@PostMapping
	public ResponseEntity<?> createPersonge(@RequestBody PersonageDTOCreate personajeDTOC) {
		Personage personajeAux = mmm.DTOCreateToPersonage(personajeDTOC);
		personajeAux = personageServ.createPersonage(personajeAux);
		return new ResponseEntity<>(mmm.personajeToDTO(personajeAux), HttpStatus.CREATED);
	}

	// elimina un personaje
	@DeleteMapping(value = "/{idPerso}")
	public ResponseEntity<?> deletePersonage(@PathVariable Long idPerso) {
		personageServ.deletePersonage(idPerso);
		return new ResponseEntity<>("Se elimino con exito el personaje..", HttpStatus.OK);
	}

	// ACTUALIZACION DE UN PERSONAJE
	@PutMapping(value = "/{idPerso}")
	public ResponseEntity<?> updatePersonage(@RequestBody PersonageDTOCreate personajeDTOC,
			@PathVariable Long idPerso) {
		Personage personageAux = mmm.DTOCreateToPersonage(personajeDTOC);
		personageAux.setId(idPerso);
		personageAux = personageServ.updatePersonage(personageAux);
		if (personageAux != null) {
			return new ResponseEntity<>(mmm.personajeToDTO(personageAux), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No se pudo actualizar el personaje..", HttpStatus.BAD_REQUEST);
		}
	}

	// DETALLA UN PERSONAJE POR EL ID
	@GetMapping(value = "/{idPerso}")
	public ResponseEntity<?> personageDetails(@PathVariable Long idPerso) {
		Personage personageAux = personageServ.findById(idPerso);
		if (personageAux != null) {
			return new ResponseEntity<>(mmm.personajeToDTO(personageAux), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No se encuentra ese personaje", HttpStatus.BAD_REQUEST);
		}
	}

}
