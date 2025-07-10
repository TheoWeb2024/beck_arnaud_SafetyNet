package com.rescueService.safetyNets.model;


import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.rescueService.safetyNets.dto.ParentsDto;
import com.rescueService.safetyNets.service.PersonServiceImpl;

import lombok.Data;

@Data
public class Person {

	
	public Person() {
		
	}

	private int id;

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

    public List<ParentsDto> getAdultsLivingAtSameAddress(String address) {
    	PersonServiceImpl peper = new PersonServiceImpl();
		List<ParentsDto> adultsLivingAtSameAddress = new ArrayList<>();
		List<Person> persone = peper.readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		for(Person personaFamily : persone) {
			if(personaFamily.getAddress().equalsIgnoreCase(address) && personaFamily.isAdult()) {
				adultsLivingAtSameAddress = persone
						.stream()
						.filter(person -> person.getAddress().equalsIgnoreCase(address))
						.filter(person -> person.ageCalculated()>18)
						.map(person -> new ParentsDto(person.getId(), person.getFirstName(), person.getLastName()))
				
						.collect(Collectors.toList());
			   }
		
	    }
		return adultsLivingAtSameAddress;
    }
    
}


    
