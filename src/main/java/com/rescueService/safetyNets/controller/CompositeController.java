package com.rescueService.safetyNets.controller;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rescueService.safetyNets.model.Person;
import com.rescueService.safetyNets.service.FirestationService;
import com.rescueService.safetyNets.service.MedicalrecordService;
import com.rescueService.safetyNets.service.PersonService;


@RestController
public class CompositeController {
	
		@Autowired
		private PersonService personService;
		@Autowired
		private FirestationService firestationService;
		@Autowired
		private MedicalrecordService medicalrecordService;
	
		 
		@GetMapping("/firestation")
		//public String checkStationFromNumber(@PathVariable String station_number) {
		public List<String> checkStationFromNumber(@RequestParam int stationNumber) {
			return firestationService.checkStationFromNumber(stationNumber);
		}
		 
		@GetMapping("/childAlert")
		public List<String> getChildrenAndFamilyLeavingAtOneAddress (@RequestParam String address) {
			return personService.getChildrenAndFamilyLeavingAtOneAddress(address);
		}
		
		@GetMapping("/phoneAlert")
		@ResponseBody
		public List<String> checkPhoneFromAroundFirestation (@RequestParam int stationNumber) {
			return firestationService.checkPhoneFromAroundFirestation(stationNumber);
		}
		
		@GetMapping("/fire")
		public List<String> getPersonAroundFirestationWithMedicalrecords (@RequestParam String address) {
			return personService.getPersonAroundFirestationWithMedicalrecords(address);
		}
		
		@GetMapping("/flood/stations")
		public List<String> getFamilyAroundFirestationWithMedicalrecords (@RequestParam int stationNumber) {
			return personService.getFamilyAroundFirestationWithMedicalrecords(stationNumber);
		}
		
		@GetMapping("/personInfolastName={lastName}")
		public Stream<Person> getInfoFromOnePerson (@PathVariable String lastName) {
			return personService.getInfoFromOnePerson(lastName);
		}
		
		@GetMapping("/communityEmail")
		//@GetMapping(value = "/communityEmail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public List<String> getEmailFromAllPersonsOfCity (@RequestParam String city) {
			return personService.getEmailFromAllPersonsOfCity(city);
		}
		
}

 
