package com.apirest.disney.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
public class PersonageDTO implements Serializable{

	private static final long serialVersionUID = -4274311080432569780L;

	private Long id;

	private String imagen;

	private String nombre;

	private int edad;

	private Double peso;

	private String historia;
	
	@JsonIgnoreProperties ("characters")
	private List<MovieDTO> movies = new ArrayList<>();
}
