package com.apirest.disney.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "personajes")
public class Personage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "personaje_id", unique = true, nullable = false)
	private Long id;
	
	@NotBlank
	private String imagen;
	@NotBlank
	@Column (unique = true)
	private String nombre;
	
	@Min (value=1)
	private int edad;
	
	@NotNull
	private Double peso;
	
	@NotBlank
	private String historia;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "personaje_peliculas",
			joinColumns = @JoinColumn (name = "FKpersoje_id", nullable = false),
			inverseJoinColumns= @JoinColumn (name = "FKpelicula_id", nullable = false))
	private List<Movie> movies = new ArrayList<>();
}
