package com.apirest.disney.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class GenreDTOCreate implements Serializable {
	
	private static final long serialVersionUID = 3876809698209239557L;

	private String nombre;
	
	private String imagen;
}
