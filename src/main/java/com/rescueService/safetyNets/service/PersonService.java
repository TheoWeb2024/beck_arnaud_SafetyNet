package com.rescueService.safetyNets.service;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rescueService.safetyNets.model.Person;


@Service
public interface PersonService {
	

	boolean addPerson(Person person);
	
	List<Person> updatePerson (Person person);
	
	List<Person> deletePerson (String email);

	Stream<Person> getInfoFromOnePerson(String lastName);

	List<String> getEmailFromAllPersonsOfCity(String city) ;
	
	List<String> getChildrenAndFamilyLeavingAtOneAddress(String address);

	List<String> getPersonAroundFirestationWithMedicalrecords(String address);
	
	List<String> getFamilyAroundFirestationWithMedicalrecords(int stationNumber);
}
