package com.rescueService.safetyNets.dto;

import java.util.List;

public record ChildrenAndFamilyDto(
		
		String firstNameChildren,
		
		String lastNameChildren,
		
		int age,
		 
	    List<ParentsDto> parents
		) {
		
}
