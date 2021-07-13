package com.apirest.disney.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

@Component
public class DateFormat {
	
	private static final String DATE_PATTERN = "dd/MM/yyyy";

	public DateFormat() {
	}
	
    public Boolean comprobarfecha(String fecha)  {
    	Boolean OK = false;
        try
        {
        	DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_PATTERN);
			LocalDate localDate = LocalDate.parse(fecha, format);
			System.out.print(localDate);
			
			OK = true;
			return OK;
        } 
        catch (ParseException e) 
        {
            e.printStackTrace();
            return OK;
        }  
       
    }
}
