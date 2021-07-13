package com.apirest.disney.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.modelmapper.AbstractConverter;
import org.modelmapper.AbstractProvider;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apirest.disney.dtos.PersonageDTO;
import com.apirest.disney.models.Personage;

@Configuration
public class ModelMapperConfig {
	/*
	 
	@Bean
	public ModelMapper ModelMapper() {

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
	*/
}
