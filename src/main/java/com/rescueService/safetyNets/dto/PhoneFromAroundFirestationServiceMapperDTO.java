package com.rescueService.safetyNets.dto;

import java.util.function.Function;

import com.rescueService.safetyNets.model.Person;

public class PhoneFromAroundFirestationServiceMapperDTO implements Function<Person, PhoneFromAroundFirestationDto> {
	
	@Override
	public PhoneFromAroundFirestationDto apply(Person person) {
		return new PhoneFromAroundFirestationDto(person.getId(), person.getLastName(), person.getPhone());
	}
}
