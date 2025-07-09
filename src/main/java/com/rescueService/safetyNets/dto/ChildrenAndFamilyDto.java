package com.rescueService.safetyNets.dto;

import java.util.List;

import com.rescueService.safetyNets.model.Person;

public record ChildrenAndFamilyDto(
		
		String firstNameChildren,
		
		String lastNameChildren,
		
		int age,
		 
	    List<ParentsDto> parents
	    
		
		) {
	
}
