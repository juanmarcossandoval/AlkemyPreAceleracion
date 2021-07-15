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
public class GenreDTO implements Serializable{
	
	private static final long serialVersionUID = -4026418499003183963L;

	private Long id;
	
	private String nombre;
	
	private String imagen;
	
	@JsonIgnoreProperties ("genero")
	@JsonInclude(Include.NON_EMPTY)
	private List<MovieDTO> movies = new ArrayList<>();
}
