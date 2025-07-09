package com.rescueService.safetyNets.dto;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.rescueService.safetyNets.model.Person;

	@Service
	public class FloodStationsMapperServiceDTO implements Function<Person, FloodStationsDto> {


		@Override
		public FloodStationsDto apply(Person person) {
			return new FloodStationsDto(person.getFirstName(), person.getLastName(), person.getPhone(), person.ageCalculated(),
					person.getMedications(), person.getAllergies());
		}
}
