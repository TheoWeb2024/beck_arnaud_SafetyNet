package com.rescueService.safetyNets.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.rescueService.safetyNets.model.Allergies;
import com.rescueService.safetyNets.model.Firestation;
import com.rescueService.safetyNets.model.Medicalrecord;
import com.rescueService.safetyNets.model.Medications;
import com.rescueService.safetyNets.model.Person;

@ExtendWith(MockitoExtension.class)
class MedicalrecordServiceImplTest {

	@Autowired
	private MedicalrecordServiceImpl medicalrecordServiceImpl;
	
	@Test
	public void testReadJsonFileForMedicalrecords() {
	
		MedicalrecordServiceImpl testDataReadingFile = new MedicalrecordServiceImpl();
		List<Medicalrecord>medicals = testDataReadingFile.readJsonFileForMedicalrecords();
		//List<Medications> dataMedications = medicals.get(0).getMedications();
			
		assertNotNull(medicals);
	}
	
	@Test
	public void testWriteJSONData() {
		List<Medicalrecord>medicals  = new ArrayList<>();
		Medicalrecord testNewMedicalToWrite = new Medicalrecord();
		testNewMedicalToWrite.setFirstName("Jordan");
		testNewMedicalToWrite.setLastName("Turner");
		//testNewMedicalToWrite.setAllergies("Salted peanuts");
		//testNewMedicalToWrite.setMedications("Ibuprofene");
		medicals.add(testNewMedicalToWrite);
		
		MedicalrecordServiceImpl testWriteDataInFile = new MedicalrecordServiceImpl();
		List<Medicalrecord> personsWritedInFile = testWriteDataInFile.writeJSONData(medicals);
		String dataResultAllergies = null;
		for(Medicalrecord medic : personsWritedInFile) {
			dataResultAllergies = medic.getFirstName();
		}
		
		assertNotNull(personsWritedInFile);
		assertEquals("Jordan",dataResultAllergies);
	}
	
	@Test
	public void testGetInfoFromMedicalrecord() {
		PersonServiceImpl testDataReadingFile = new PersonServiceImpl();
		List<Person>persons = testDataReadingFile.readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		List<Person> dataGetInfosOnePerson = new ArrayList<>();
		for (Person persona:persons) {
			persona.getFirstName();
			persona.getLastName();
			persona.getBirthdate();
			dataGetInfosOnePerson.add(persona);
		}
		
		List<Medicalrecord> mediList = new ArrayList<>();
		Medicalrecord medimedi = new Medicalrecord();
		medimedi.setFirstName("Edith");
		medimedi.setLastName("Piaf");
		mediList.add(medimedi);
		
		
		boolean lastName = testDataReadingFile.getInfoFromOnePerson("Boyd") != null;
		assertTrue(lastName);
		assertEquals("Piaf", medimedi.getLastName());
	}
	
	@Test
	public void testAddMedicalrecord() {
		List<Medicalrecord> mediList = new ArrayList<>();
		Medicalrecord medimedi = new Medicalrecord();
		medimedi.setFirstName("Edith");
		medimedi.setLastName("Piaf");
		medimedi.setBirthDate("11/12/1960");
		mediList.add(medimedi);
		MedicalrecordServiceImpl MedicalrecordRsult = new MedicalrecordServiceImpl();
		MedicalrecordRsult.addMedicalrecord(medimedi);
		
		assertNotNull(MedicalrecordRsult);
	}
	
	@Test
	public void testUpdateMedicalrecord() {
		MedicalrecordServiceImpl testMedi = new MedicalrecordServiceImpl();
		Medicalrecord mediTest = new Medicalrecord();
		mediTest.setFirstName("Didier");
		mediTest.setLastName("Deschamps");
		mediTest.setBirthDate("10/11/1967");
		testMedi.addMedicalrecord(mediTest);
		

		mediTest.setFirstName("Donald");
		testMedi.updateMedicalrecord(mediTest);
		
		
		assertEquals("Donald",mediTest.getFirstName());
		assertNotNull(testMedi);
	}
	
	@Test
	public void testDeleteMedicalrecord() {
		//String station_number="10";
		List<Medicalrecord> mediListed = new ArrayList<>();
		Medicalrecord medimedi = new Medicalrecord();
		medimedi.setBirthDate("10/11/1977");
		medimedi.setLastName("Federer");
		medimedi.setFirstName("Roger");
		mediListed.add(medimedi);
		MedicalrecordServiceImpl testMedi = new MedicalrecordServiceImpl();
		testMedi.deleteMedicalrecord(medimedi.getLastName(), medimedi.getFirstName());
		
		//assertEquals("",testFire);
		assertNotNull(medimedi.getLastName());
		assertEquals("10/11/1977",medimedi.getBirthDate());
	}
}

