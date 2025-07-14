package com.rescueService.safetyNets.dto;

import com.fasterxml.jackson.databind.JsonNode;


public record PersonDto(
		
		 int id,
			
		 String firstName,

	     String lastName,
	     
	     String address,
	     
	     int age,
		
	     String email,
	     
	     JsonNode medications,
		    
	     JsonNode allergies) {
	
}
