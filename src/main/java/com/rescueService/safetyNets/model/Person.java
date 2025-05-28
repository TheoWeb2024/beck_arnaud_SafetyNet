package com.rescueService.safetyNets.model;


import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

@Data
public class Person {

	
	public Person() {
		
	}

	private String id;

	private String address;
	
	private String firstName;

    private String lastName;
	
    private String email;

    private String phone;
	
    private String zip;
	
    private String city;
    
    private String birthdate;

    private JsonNode medications;
    
    private JsonNode allergies;
    
    public boolean isAdult() {
		return ageCalculated()>=18;
    	
    }
    
    public int ageCalculated()  {
		   LocalDate currentDate = LocalDate.now();
	     //  LocalDate birthDay = LocalDate.parse(pBirthDate);
	       DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM/dd/yyyy",Locale.FRANCE);
	       LocalDate formattedDate = LocalDate.parse(birthdate,myFormatObj);
	       int period = Period.between(formattedDate, currentDate).getYears();
	       
	       return period ;
	 }

    
}


    
