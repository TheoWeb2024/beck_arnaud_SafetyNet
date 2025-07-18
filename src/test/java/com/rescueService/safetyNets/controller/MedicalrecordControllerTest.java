package com.rescueService.safetyNets.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.rescueService.safetyNets.model.Medicalrecord;
import com.rescueService.safetyNets.service.MedicalrecordServiceImpl;


@SpringBootTest
@AutoConfigureMockMvc
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
		  dataTestInfos.addMedicalrecord(medicTest);
		  
		  medicTest.setFirstName("Henry");
		  dataTestInfos.updateMedicalrecord(medicTest);
		
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
	
		  assertEquals("Jordan", medicTest.getLastName());
	  } 
		
	  
	  @Test
	  public void testDeleteMedicalrecord() throws Exception  {
		  MedicalrecordServiceImpl dataTestInfos = new MedicalrecordServiceImpl();
		  Medicalrecord medicTest = new Medicalrecord();
		  medicTest.setFirstName("Mickael");
		  medicTest.setLastName("Jordan");
	  
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
		  
		  mockMvc.perform(get("/medicalRecord/personMedicalrecords?lastName=cadigan"))
		  .andDo(print())
		  .andExpect(status().isOk());  
	  }
}
