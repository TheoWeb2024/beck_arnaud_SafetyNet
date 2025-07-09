package com.rescueService.safetyNets.dto;

import com.fasterxml.jackson.databind.JsonNode;

public record FloodStationsDto(
		

		 String firstName,

	     String lastName,

	     String phone,
	 
	     int age,

	     JsonNode medications,
	    
	     JsonNode allergies) {

}
