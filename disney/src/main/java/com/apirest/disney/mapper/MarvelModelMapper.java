package com.apirest.disney.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.AbstractConverter;
import org.modelmapper.AbstractProvider;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.springframework.stereotype.Component;

import com.apirest.disney.dtos.MovieDTO;
import com.apirest.disney.dtos.MovieDTOSimple;
import com.apirest.disney.dtos.PersonageDTO;
import com.apirest.disney.dtos.PersonageDTOSimple;
import com.apirest.disney.models.Movie;
import com.apirest.disney.models.Personage;

@Component
public class MarvelModelMapper extends ModelMapper {

	private final ModelMapper mmm = MyModelMapper();

	private ModelMapper MyModelMapper() {

		ModelMapper modelmapper = new ModelMapper();

		Provider<LocalDate> localDateProvider = new AbstractProvider<LocalDate>() {
			@Override
			public LocalDate get() {
				return LocalDate.now();
			}
		};

		Converter<String, LocalDate> StringtoDate = new AbstractConverter<String, LocalDate>() {
			@Override
			protected LocalDate convert(String source) {
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate localDate = LocalDate.parse(source, format);
				return localDate;
			}
		};

		Converter<LocalDate, String> DatetoString = new AbstractConverter<LocalDate, String>() {
			@Override
			protected String convert(LocalDate source) {
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String string = source.format(format);
				return string;
			}
		};

		modelmapper.createTypeMap(String.class, LocalDate.class);
		modelmapper.addConverter(StringtoDate);
		modelmapper.getTypeMap(String.class, LocalDate.class).setProvider(localDateProvider);

		modelmapper.createTypeMap(LocalDate.class, String.class);
		modelmapper.addConverter(DatetoString);

		return modelmapper;
	}

	// -----------------------------METODOS DE MAPPEO PARA------------------------//
	// -----------------------------------PELICULAS-------------------------------//

	// pelicula a dtoSIMPLE
	public MovieDTOSimple movieToDTOSimple(Movie peli) {
		return mmm.map(peli, MovieDTOSimple.class);
	}

	// Lista de peliculas a lista de dtoSIMPLE
	public List<MovieDTOSimple> movieListToDTOSimple(List<Movie> peliculas) {
		List<MovieDTOSimple> listadto = new ArrayList<>();
		for (Movie m : peliculas) {
			MovieDTOSimple dtoaux = new MovieDTOSimple();
			dtoaux = movieToDTOSimple(m);
			listadto.add(dtoaux);
		}
		return listadto;
	}

	// pelicula a dtoDETALLADO
	public Movie DTOToMovie(MovieDTO movieDTO) {
		return mmm.map(movieDTO, Movie.class);
	}

	// dtoDETALLADO a pelicula
	public MovieDTO MovieToDto(Movie peli) {
		return mmm.map(peli, MovieDTO.class);
	}

	// -----------------------------METODOS DE MAPPEO PARA------------------------//
	// -----------------------------------PERSONAJES------------------------------//

	// personaje a DTO SIMPLE
	public PersonageDTOSimple personajeToDTOSimple(Personage personaje) {
		return mmm.map(personaje, PersonageDTOSimple.class);
	}

	// lista de personaje a lista de DTO SIMPLES
	public List<PersonageDTOSimple> personajeListToListDTOSimple(List<Personage> lista) {
		List<PersonageDTOSimple> listadtos = new ArrayList<>();
		for (Personage c : lista) {
			PersonageDTOSimple d = personajeToDTOSimple(c);
			listadtos.add(d);
		}
		return listadtos;
	}

	// DTO DETALLADO a personaje
	public Personage DTOToPersonaje(PersonageDTO dto) {
		return mmm.map(dto, Personage.class);
	}

	// personaje a DTO DETALLADO
	public PersonageDTO personajeToDTO(Personage personaje) {
		return mmm.map(personaje, PersonageDTO.class);
	}

	// lista de personajes a lista de DTO DETALLADOS
	public List<PersonageDTO> ListToDTOlist(List<Personage> lista) {
		List<PersonageDTO> listadtos = new ArrayList<>();
		for (Personage c : lista) {
			PersonageDTO d = personajeToDTO(c);
			listadtos.add(d);
		}
		return listadtos;
	}
}
