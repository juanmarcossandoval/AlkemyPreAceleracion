package com.apirest.disney.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class PersonageDTOCreate implements Serializable {
	
	private static final long serialVersionUID = -4059181173334496407L;

	private String imagen;

	private String nombre;

	private int edad;

	private Double peso;

	private String historia;
}
