package com.rescueService.safetyNets.dto;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.rescueService.safetyNets.model.Person;

@Service
public class PersonMapperServiceDTO implements Function<Person, PersonDto> {

	@Override
	public PersonDto apply(Person person) {
		return new PersonDto(person.getId(), person.getFirstName(), person.getLastName(), person.getAddress(), 
				person.ageCalculated(), person.getEmail(), person.getMedications(), person.getAllergies());
	}
}
