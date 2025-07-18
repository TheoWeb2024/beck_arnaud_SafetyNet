package com.rescueService.safetyNets.dto;

import com.fasterxml.jackson.databind.JsonNode;

public record MedicalrecordDto(
		
		 int id,
		
		 String firstName,
		
		 String lastName,
		
		 String birthDate,
		
		 JsonNode  medications,
		
		 JsonNode allergies
		) {

}
