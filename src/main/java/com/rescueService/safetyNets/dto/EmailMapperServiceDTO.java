package com.rescueService.safetyNets.dto;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.rescueService.safetyNets.model.Person;

	@Service
	public class EmailMapperServiceDTO implements Function<Person, EmailPersonDto> {


		@Override
		public EmailPersonDto apply(Person person) {
			return new EmailPersonDto(person.getEmail());
		}
	

}
