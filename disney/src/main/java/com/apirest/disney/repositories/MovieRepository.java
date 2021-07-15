package com.apirest.disney.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apirest.disney.models.Genre;
import com.apirest.disney.models.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

	List<Movie> findAllByTitulo(String titulo);

	List<Movie> findAllByGenero(Genre genre);

	List<Movie> findByOrderByCreacionAsc();

	List<Movie> findByOrderByCreacionDesc();
}
