package com.rescueService.safetyNets.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rescueService.safetyNets.model.Person;
import com.rescueService.safetyNets.service.PersonServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(PersonController.class)
class PersonControllerTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	PersonServiceImpl personServiceImpl;
	@Autowired
	PersonController personController;

	@Test
	public void testUpdatePerson() throws Exception  {
		  PersonServiceImpl dataTestInfos = new PersonServiceImpl();
		  Person persTest = new Person();
		  persTest.setFirstName("Mickael");
		  persTest.setLastName("Jordan");
		  persTest.setBirthdate("11/10/1974");
		  persTest.setEmail("jordan@email.com");
		  dataTestInfos.addPerson(persTest);

		  persTest.setEmail("jordanMICKAEL@email.com");
		  dataTestInfos.updatePerson(persTest);
		
		  assertEquals("jordanMICKAEL@email.com", persTest.getEmail());
		  assertNotNull(persTest.getEmail());
	 }
	
	  @Test
	  public void testAddPerson() throws Exception  {
		  PersonServiceImpl dataTestInfos = new PersonServiceImpl();
	
		  Person persTest = new Person();
		  persTest.setFirstName("Mickael");
		  persTest.setLastName("Jordan");
		  persTest.setBirthdate("11/10/1974");
		  persTest.setEmail("jordan@email.com");
		  dataTestInfos.addPerson(persTest);
		  
		  dataTestInfos.readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
	
		 assertEquals("jordan@email.com", persTest.getEmail());
		// assertTrue(dataTestInfos.addPerson(persTest));
		 
	  } 

	  @Test
	  public void testDeletePerson() throws Exception  {
		  PersonServiceImpl dataTestInfos = new PersonServiceImpl();
		  Person persTest = new Person();
		  persTest.setFirstName("Mickael");
		  persTest.setLastName("Jordan");
		  persTest.setEmail("jordan@email.com");
		  
		  persTest.getEmail();
		  dataTestInfos.deletePerson(persTest.getEmail());
		  
		  mockMvc.perform(delete("/person/delete/jordan@email.com"))
		  .andDo(print())
		  .andExpect(status().isOk());  
	  }
}
