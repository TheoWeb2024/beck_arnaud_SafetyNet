package com.rescueService.safetyNets.dto;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

@Data
public class PersonFireDto {

	 int stationNumber;
		
	 String firstName;

     String lastName;

     String phone;
 
     int age;

     JsonNode medications;
    
     JsonNode allergies;
	
}
