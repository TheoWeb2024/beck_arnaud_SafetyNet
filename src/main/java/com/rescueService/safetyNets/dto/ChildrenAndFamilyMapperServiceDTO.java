package com.rescueService.safetyNets.dto;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.rescueService.safetyNets.model.Person;

	@Service
	public class ChildrenAndFamilyMapperServiceDTO implements Function<Person, ChildrenAndFamilyDto> {

		 @Override
			public ChildrenAndFamilyDto apply(Person person) {
				return new ChildrenAndFamilyDto(person.getFirstName(), person.getLastName(), person.ageCalculated(),person.getAdultsLivingAtSameAddress(person.getAddress()));
			}
	 }

 