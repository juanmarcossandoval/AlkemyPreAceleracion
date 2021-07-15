package com.apirest.disney.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apirest.disney.models.Movie;
import com.apirest.disney.models.Personage;

@Repository
public interface PersonageRepository extends JpaRepository<Personage,Long>{
	Personage findByNombre(String nombre);
	List<Personage> findByEdad(Integer edad);
	List<Personage> findAllByNombre(String nombre);
	List<Personage> findAllByPeso(Double peso);
	List<Personage> findAllByMovies(Movie pelicula);
}
