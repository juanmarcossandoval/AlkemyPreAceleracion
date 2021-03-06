package com.apirest.disney.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apirest.disney.models.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long>{

}
