package com.rescueService.safetyNets.service;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.rescueService.safetyNets.dto.ChildrenAndFamilyDto;
import com.rescueService.safetyNets.dto.EmailPersonDto;
import com.rescueService.safetyNets.dto.FloodStationsDto;
import com.rescueService.safetyNets.dto.PersonDto;
import com.rescueService.safetyNets.model.Person;


@Service
public interface PersonService {
	

	boolean addPerson(Person person);
	
	List<Person> updatePerson (Person person);
	
	List<Person> deletePerson (String email);

	Stream<PersonDto> getInfoFromOnePerson(String lastName);

	List<EmailPersonDto> getEmailFromAllPersonsOfCity(String city) ;
	
	Stream<Object> getChildrenAndFamilyLeavingAtOneAddress(String address);

	Stream<Object> getPersonAroundFirestationWithMedicalrecords(String address);
	
	List<FloodStationsDto> getFamilyAroundFirestationWithMedicalrecords(int stationNumber);
}
