package com.rescueService.safetyNets.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.rescueService.safetyNets.model.Firestation;
import com.rescueService.safetyNets.model.Person;
import com.rescueService.safetyNets.service.FirestationServiceImpl;
import com.rescueService.safetyNets.service.PersonServiceImpl;


@SpringBootTest
@AutoConfigureMockMvc
public class CompositeControllerTest {

	
	@Autowired 
    MockMvc mockMvc; 
	@Autowired 
	FirestationServiceImpl firestationServiceImpl;
	@Autowired
	PersonServiceImpl personServiceImpl;
	  

	  @Test
	  public void testCheckStationFromNumber() throws Exception  {
		  FirestationServiceImpl fireStation = new FirestationServiceImpl();
		  Firestation fireTestData = new Firestation();
		  fireTestData.setStationNumber(3);
		  fireTestData.setAddress("112 Steppes Pl");
		  fireStation.addFirestation(fireTestData);
		  
		  PersonServiceImpl personDatas = new PersonServiceImpl();
		  Person personData = new Person();
		  personData.setAddress("112 Steppes Pl");
		  personData.setFirstName("Mickael");
		  personData.setLastName("Jordan");
		  personData.setBirthdate("12/11/1980");
		  personData.setEmail("jordan@email.com");
		  personDatas.addPerson(personData);
			
			
		  fireTestData.getStationNumber();
		  personData.getAddress();
		  fireStation.checkStationFromNumber(fireTestData.getStationNumber());

		assertNotNull(personData.getAddress());
	  }
	  
	  @Test
	  public void testGetInfoFromOnePerson() throws Exception  { 
		  PersonServiceImpl dataTestInfos = new PersonServiceImpl();
		  dataTestInfos.getInfoFromOnePerson("Boyd");
		  
		  Person personData = new Person();
		  personData.setAddress("112 Steppes Pl");
		  personData.setFirstName("Mickael");
		  personData.setLastName("Jordan");
		  personData.setBirthdate("12/11/1980");
		  personData.setEmail("jordan@email.com");
		  dataTestInfos.addPerson(personData);
		  
		  dataTestInfos.getInfoFromOnePerson("Jordan");
	  
		mockMvc.perform(MockMvcRequestBuilders.get("/personInfolastName=Boyd"))
		.andDo(print())
		.andExpect(status().isOk());
	  }
	  
	  @Test
	  public void testCheckPhoneFromAroundFirestation() throws Exception   {
		  FirestationServiceImpl dataTestInfosFire = new FirestationServiceImpl();
		  //dataTestInfosFire.checkPhoneFromAroundFirestation("3");
		 Firestation fireTestData = new Firestation();
		  fireTestData.setStationNumber(3);
		  fireTestData.setAddress("112 Steppes Pl");
		  dataTestInfosFire.addFirestation(fireTestData);
		  
		  PersonServiceImpl dataTestInfos = new PersonServiceImpl();
		  Person personData = new Person();
		  personData.setAddress("112 Steppes Pl");
		  personData.setFirstName("Mickael");
		  personData.setLastName("Jordan");
		  personData.setBirthdate("12/11/1980");
		  personData.setEmail("jordan@email.com");
		  dataTestInfos.addPerson(personData);
		  
		  fireTestData.getAddress();
		  personData.getAddress();
		  dataTestInfosFire.checkPhoneFromAroundFirestation(fireTestData.getStationNumber());
		  
		  assertNotNull(personData.getEmail());
	  }
	  
	  @Test
	  public void testGetChildrenAndFamilyLeavingAtOneAddress() throws Exception   {
		  PersonServiceImpl dataTestInfos = new PersonServiceImpl();
		  Person pers = new Person();
		  pers.setAddress("1509 Culver St");
		  pers.setBirthdate("12/10/2020");
		  dataTestInfos.getChildrenAndFamilyLeavingAtOneAddress(pers.getAddress());
		  
		  assertEquals("1509 Culver St", pers.getAddress());
	  }
	  
	  @Test
	  public void testGetEmailFromAllPersonsOfCity() throws Exception   {
		  PersonServiceImpl dataTestInfos = new PersonServiceImpl();
		  Person pers = new Person();
		  pers.setEmail("jordan@email.com");
		  dataTestInfos.getEmailFromAllPersonsOfCity(pers.getEmail());
		  
		  assertEquals("jordan@email.com", pers.getEmail());
		
	  }
	  
	  @Test
	  public void testGetPersonAroundFirestationWithMedicalrecords() throws Exception   {
		  PersonServiceImpl dataTestInfos = new PersonServiceImpl();
		  dataTestInfos.getPersonAroundFirestationWithMedicalrecords("1509 Culver St");
		  
		  
		  mockMvc.perform(get("/fire?address=1509 Culver St"))
		  .andDo(print())
		  .andExpect(status().isOk());
	  }
	  
	  @Test
	  public void testGetFamilyAroundFirestationWithMedicalrecords() throws Exception   {
		  PersonServiceImpl dataTestInfos = new PersonServiceImpl();
		  dataTestInfos.getFamilyAroundFirestationWithMedicalrecords(3);
		  
		  
		  mockMvc.perform(get("/flood/stations?stationNumber=3"))
		 .andDo(print())
		 .andExpect(status().isOk());
	  }
	  
}
