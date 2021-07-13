package com.apirest.disney.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.apirest.disney.models.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class MovieDTO implements Serializable {

	private static final long serialVersionUID = -8066054402995864543L;

	private Long id;
	
	private String imagen;
	
	private String titulo;
	
	private String creacion;

	private int calificacion;
	
	@JsonIgnoreProperties("movies")
	private Genre genero;
	
	@JsonIgnoreProperties("movies")
	@JsonInclude(Include.NON_EMPTY)
	private List<PersonageDTO> personages = new ArrayList<>();
}
