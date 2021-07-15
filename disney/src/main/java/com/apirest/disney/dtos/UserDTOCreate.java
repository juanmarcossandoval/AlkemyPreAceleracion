package com.apirest.disney.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDTOCreate implements Serializable {
	
	private static final long serialVersionUID = -5500532046633639710L;

	private String userName;

	private String email;
	
	private String password;
}
