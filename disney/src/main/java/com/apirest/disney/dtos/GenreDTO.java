package com.apirest.disney.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
public class GenreDTO implements Serializable{
	
	private static final long serialVersionUID = -4026418499003183963L;

	private Long id;
	
	@NotBlank
	private String nombre;
	@NotBlank
	private String imagen;
	
	@JsonIgnoreProperties ("genero")
	private List<MovieDTO> movies = new ArrayList<>();
}
