package com.apirest.disney.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
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
@Table(name= "peliculas")

public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "pelicula_id", unique = true, nullable = false)
	private Long id;
	
	@NotBlank
	private String imagen;
	@NotBlank
	@Column(unique = true)
	private String titulo;
	
	@NotNull
	private LocalDate creacion;
	
	@Min (value=1)
	@Max (value=5)
	private int calificacion;
	
	@ManyToOne
	@JoinColumn(name = "FKgenero_id", referencedColumnName = "genero_id")
	private Genre genero;
	
	@ManyToMany(mappedBy="movies", cascade= CascadeType.ALL)
	private List<Personage> personages = new ArrayList<>();
}
