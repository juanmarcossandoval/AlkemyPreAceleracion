package com.apirest.disney.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class PersonageDTOSimple implements Serializable {

	private static final long serialVersionUID = -8323321652030305024L;

	private String imagen;
	
	private String nombre;
}
