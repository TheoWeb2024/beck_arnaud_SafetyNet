package com.rescueService.safetyNets.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.rescueService.safetyNets.model.Person;
import com.rescueService.safetyNets.service.PersonServiceImpl;

	@Service
	public class ChildrenAndFamilyMapperServiceDTO implements Function<Person, ChildrenAndFamilyDto> {

		 @Override
			public ChildrenAndFamilyDto apply(Person person) {
				return new ChildrenAndFamilyDto(person.getFirstName(), person.getLastName(), person.ageCalculated(),null);
			}
	 }

