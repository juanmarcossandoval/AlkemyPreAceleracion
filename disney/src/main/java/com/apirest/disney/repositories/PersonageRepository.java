package com.apirest.disney.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apirest.disney.models.Movie;
import com.apirest.disney.models.Personage;

@Repository
public interface PersonageRepository extends JpaRepository<Personage,Long>{
	public Personage findByNombre(String nombre);
	public List<Personage> findByEdad(Integer edad);
	public List<Personage> findAllByNombre(String nombre);
	public List<Personage> findAllByPeso(Double peso);
	public List<Personage> findAllByMovies(Movie pelicula);
}
