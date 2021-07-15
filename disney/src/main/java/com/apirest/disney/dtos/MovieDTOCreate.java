package com.apirest.disney.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class MovieDTOCreate implements Serializable {
	
	private static final long serialVersionUID = -2597063480401076968L;

	private String imagen;
	
	private String titulo;
	
	private String creacion;

	private int calificacion;
	
}
