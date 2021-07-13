package com.apirest.disney.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Data
@Table (name  = "usuarios")

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String userName;
	@NotEmpty
	private String email;
	@NotEmpty
	private String password;
	private boolean active;
	private String roles;
}
