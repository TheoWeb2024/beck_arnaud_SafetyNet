package com.rescueService.safetyNets.dto;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.rescueService.safetyNets.model.Person;

@Service
public class CheckStationFromNumberServiceMapperDto implements Function<Person, CheckStationFromNumberDto>{

	
		@Override
		public CheckStationFromNumberDto apply(Person person) {
			return new CheckStationFromNumberDto(person.getId(), person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone());
		}
	

}
