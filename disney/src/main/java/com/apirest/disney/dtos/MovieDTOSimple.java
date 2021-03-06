package com.apirest.disney.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class MovieDTOSimple implements Serializable {
	
	private static final long serialVersionUID = -3517669192548598756L;

	private String imagen;
	
	private String titulo;
	
	private String creacion;
}
