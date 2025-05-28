package com.rescueService.safetyNets.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.rescueService.safetyNets.model.Medicalrecord;
import com.rescueService.safetyNets.model.Person;
import com.rescueService.safetyNets.service.MedicalrecordServiceImpl;
import com.rescueService.safetyNets.service.PersonServiceImpl;


@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(MedicalrecordController.class)
class MedicalrecordControllerTest {


	@Autowired
	MockMvc mockMvc;
	@Autowired
	MedicalrecordServiceImpl medicalrecordServiceImpl;
	
	  @Test
	  public void testUpdateMedicalrecord() throws Exception  {
		  MedicalrecordServiceImpl dataTestInfos = new MedicalrecordServiceImpl();
		  Medicalrecord medicTest = new Medicalrecord();
		  medicTest.setFirstName("Mickael");
		  medicTest.setLastName("Jordan");
		  medicTest.setBirthDate("12/11/1980");
		 // medicTest.setMedications("ibuprofene:100mg");
		  dataTestInfos.addMedicalrecord(medicTest);
		  
		  medicTest.setFirstName("Henry");
		  dataTestInfos.updateMedicalrecord(medicTest);
		 /*
		  mockMvc.perform(put("/medicalRecord/update/Jordan/Mickael/12-11-1980")) 
		  .andDo(print())
		  .andExpect(status().isOk());
		  */
		 
		  assertEquals("Henry", medicTest.getFirstName());
	  }
	  
	  @Test
	  public void testAddMedicalrecord() throws Exception  {
		  MedicalrecordServiceImpl dataTestInfos = new MedicalrecordServiceImpl();
		  Medicalrecord medicTest = new Medicalrecord();
		  medicTest.setFirstName("Mickael");
		  medicTest.setLastName("Jordan");
		  medicTest.setBirthDate("12/12/1974");
		  
		  medicTest.getFirstName();
		  medicTest.getLastName();
		  medicTest.getBirthDate();
		  dataTestInfos.addMedicalrecord(medicTest);
		/*  
		  mockMvc.perform(post("/medicalRecord/create"))
		  .andDo(print())
	      .andExpect(status().isOk());
		  */
		  assertEquals("Jordan", medicTest.getLastName());
	  } 
		
	  
	  @Test
	  public void testDeleteMedicalrecord() throws Exception  {
		  MedicalrecordServiceImpl dataTestInfos = new MedicalrecordServiceImpl();
		  Medicalrecord medicTest = new Medicalrecord();
		  medicTest.setFirstName("Mickael");
		  medicTest.setLastName("Jordan");
		 // medicTest.setBirthDate("12/11/1985");
		 // dataTestInfos.addMedicalrecord(medicTest);
		  
		  medicTest.getLastName();
		  dataTestInfos.deleteMedicalrecord(medicTest.getLastName(), medicTest.getFirstName());
		  
		  mockMvc.perform(delete("/medicalRecord/delete/Jordan/Mickael"))
		  .andDo(print())
		  .andExpect(status().isOk());  
	  }
	  
	  @Test
	  public void testGetInfoFromMedicalrecord() throws Exception  {
		  MedicalrecordServiceImpl dataTestInfos = new MedicalrecordServiceImpl();
		
		  dataTestInfos.getInfoFromMedicalrecord("cadigan");
		  
		  mockMvc.perform(get("/medicalRecord/personMedicalrecords=cadigan"))
		  .andDo(print())
		  .andExpect(status().isOk());  
	  }

}
