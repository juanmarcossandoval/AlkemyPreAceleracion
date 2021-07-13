package com.apirest.disney.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "generos")

public class Genre {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "genero_id", unique = true, nullable = false)
	private Long id;
	
	@NotBlank
	@Column (unique = true)
	private String nombre;
	@NotBlank
	private String imagen;
	
	@OneToMany(mappedBy = "genero", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Movie> movies = new ArrayList<>();
}
