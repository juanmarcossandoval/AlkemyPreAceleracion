package com.apirest.disney.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class PersonageDTO implements Serializable{

	private static final long serialVersionUID = -4274311080432569780L;

	private Long id;

	private String imagen;

	private String nombre;

	private int edad;

	private Double peso;

	private String historia;
	
	@JsonIgnoreProperties ("characters")
	@JsonInclude(Include.NON_EMPTY)
	private List<MovieDTO> movies = new ArrayList<>();
}
