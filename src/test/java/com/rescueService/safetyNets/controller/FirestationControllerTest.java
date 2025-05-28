package com.rescueService.safetyNets.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rescueService.safetyNets.model.Firestation;
import com.rescueService.safetyNets.model.Person;
import com.rescueService.safetyNets.service.FirestationServiceImpl;
import com.rescueService.safetyNets.service.MedicalrecordServiceImpl;
import com.rescueService.safetyNets.service.PersonServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
class FirestationControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	@Autowired
	FirestationServiceImpl firestationServiceImpl;
	
	 @Test
	  public void testGetFirestations() throws Exception  {
		 FirestationServiceImpl dataTestInfos = new FirestationServiceImpl();
		 dataTestInfos.readJsonFileForFirestations();
		  
		mockMvc.perform(get("/firestation/read"))
		.andDo(print())
		.andExpect(status().isOk());
	}

	 @Test
	  public void testAddFirestation() throws Exception {
		 FirestationServiceImpl dataTestInfos = new FirestationServiceImpl();
		 
		 Firestation firestationTest = new Firestation();
		 firestationTest.setAddress("1509 Culver St");
		 firestationTest.setStationNumber(3);
		 firestationTest.setId(0);

		 dataTestInfos.addFirestation(firestationTest);
		 /*
		 mockMvc.perform(post("/firestation/create"))
		.andDo(print())
		.andExpect(status().isOk());
		 */
		
		assertEquals("1509 Culver St", firestationTest.getAddress());
	}
	 
	  @Test
	  public void testUpdateFirestation() throws Exception  {
		  FirestationServiceImpl dataTestInfos = new FirestationServiceImpl();
		  Firestation firestationTest = new Firestation();
		  firestationTest.setAddress("112 Steppes Pl");
		  firestationTest.setStationNumber(4);
		  firestationTest.setId(10);		
		  dataTestInfos.addFirestation(firestationTest);
		  
		  firestationTest.getStationNumber();
		  firestationTest.setStationNumber(5);
		  firestationTest.getStationNumber();
		  firestationTest.getAddress();
		  dataTestInfos.updateFirestation(firestationTest);
		 /*
		  mockMvc.perform(put("/firestation/update/112 Steppes Pl")) 
		  .andDo(print())
		  .andExpect(status().isOk());
		  */
		  assertEquals(5, firestationTest.getStationNumber());
	
	  }
	 
	 @Test
	  public void testDelete() throws Exception  {
		FirestationServiceImpl dataTestInfos = new FirestationServiceImpl();
		//dataTestInfos.readJsonFileForFirestations();
		Firestation fireTest = new Firestation();
		fireTest.setAddress("112 Steppes Pl");
		fireTest.setStationNumber(4);
		dataTestInfos.addFirestation(fireTest);  
		
		fireTest.getStationNumber();
		dataTestInfos.deleteFirestation(fireTest.getStationNumber());
		
		mockMvc.perform(delete("/firestation/delete/4"))
		.andDo(print())
		.andExpect(status().isOk());
	}
}
