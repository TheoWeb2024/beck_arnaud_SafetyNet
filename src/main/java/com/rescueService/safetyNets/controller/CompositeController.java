package com.rescueService.safetyNets.controller;

import java.util.List;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rescueService.safetyNets.SafetyNetsApplication;
import com.rescueService.safetyNets.dto.CheckStationFromNumberDto;
import com.rescueService.safetyNets.dto.EmailPersonDto;
import com.rescueService.safetyNets.dto.FloodStationsDto;
import com.rescueService.safetyNets.dto.PersonDto;
import com.rescueService.safetyNets.dto.PhoneFromAroundFirestationDto;
import com.rescueService.safetyNets.service.FirestationService;
import com.rescueService.safetyNets.service.PersonService;


@RestController
public class CompositeController {
	
	private static final Logger logger = LogManager.getLogger(SafetyNetsApplication.class);

	
		@Autowired
		private PersonService personService;
		@Autowired
		private FirestationService firestationService;
		
	
		
		@GetMapping("/firestation")
		public List<CheckStationFromNumberDto> checkStationFromNumber(@RequestParam int stationNumber) {
			logger.info("Returning stationNumber regarding address");
			return firestationService.checkStationFromNumber(stationNumber);
		}
		 
		@GetMapping("/childAlert")
		public Stream<Object> getChildrenAndFamilyLeavingAtOneAddress (@RequestParam String address) {
			logger.info("Returning children and adults living at the same address");
			return personService.getChildrenAndFamilyLeavingAtOneAddress(address);
		}
		
		@GetMapping("/phoneAlert")
		public List<PhoneFromAroundFirestationDto> checkPhoneFromAroundFirestation (@RequestParam int stationNumber) {
			logger.info("Returning phone Number regarding firestation");
			return firestationService.checkPhoneFromAroundFirestation(stationNumber);
		}
		
		@GetMapping("/fire")
		public Stream<Object> getPersonAroundFirestationWithMedicalrecords (@RequestParam String address) {
			logger.info("Returning one person with medicals around firestation");
			return personService.getPersonAroundFirestationWithMedicalrecords(address);
		}
		
		@GetMapping("/flood/stations")
		public List<FloodStationsDto> getFamilyAroundFirestationWithMedicalrecords (@RequestParam int stationNumber) {
			logger.info("Returning family with medicals around firestation");
			return personService.getFamilyAroundFirestationWithMedicalrecords(stationNumber);
		}
		
		@GetMapping("/personInfolastName")
			public Stream<PersonDto> getInfoFromOnePerson (@RequestParam String lastName) {
			logger.info("Returning informations from one person regarding lastName");
			return personService.getInfoFromOnePerson(lastName);
		}
		
		@GetMapping("/communityEmail")
		public List<EmailPersonDto> getEmailFromAllPersonsOfCity (@RequestParam String city) {
			logger.info("Returning emails from city");
			return personService.getEmailFromAllPersonsOfCity(city);
		}
}


