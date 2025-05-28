package com.rescueService.safetyNets.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.rescueService.safetyNets.model.Firestation;
import com.rescueService.safetyNets.model.Person;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

	@Autowired
	private PersonServiceImpl personServiceImpl;
	
	
	@Test
	public void testReadJsonFileForPersonsWithBirthDateAndMedAndAllergies() {
	
		PersonServiceImpl testDataReadingFile = new PersonServiceImpl();
		List<Person>persons = testDataReadingFile.readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		String dataCity = persons.get(0).getCity();
		int dataSize = persons.size();		
		
		assertEquals(23,dataSize);
		assertEquals("Culver", dataCity);
		assertNotNull(persons);
	}
	
	@Test
	public void testWriteJSONData() {
		List<Person>personDatas = new ArrayList<>();
		Person testNewPersonToWrite = new Person();
		testNewPersonToWrite.setFirstName("Jordan");
		testNewPersonToWrite.setLastName("Turner");
		testNewPersonToWrite.setAddress("205 Peugeot St");
		testNewPersonToWrite.setCity("Carcity");
		personDatas.add(testNewPersonToWrite);
		
		PersonServiceImpl testWriteDataInFile = new PersonServiceImpl();
		List<Person> personsWritedInFile = testWriteDataInFile.writeJSONData(personDatas);
		String resultAddressNewPerson=null;
		for(Person pers:personsWritedInFile) {
			 resultAddressNewPerson = pers.getAddress();
		}
		
		assertNotNull(personsWritedInFile);
		assertEquals("205 Peugeot St",resultAddressNewPerson);
	}
	
	@Test
	public void testGetInfoFromOnePerson() {
		PersonServiceImpl testDataReadingFile = new PersonServiceImpl();
		List<Person>persons = testDataReadingFile.readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		List<Person> dataGetInfosOnePerson = new ArrayList<>();
		for (Person persona:persons) {
			persona.getFirstName();
			persona.getLastName();
			persona.getBirthdate();
			dataGetInfosOnePerson.add(persona);
		}
		boolean lastName = testDataReadingFile.getInfoFromOnePerson("Boyd") != null;
		assertTrue(lastName);
	}
	
	@Test
	public void testGetEmailFromAllPersonsOfCity() {
		String cityComingFromList = new String();
		PersonServiceImpl testDataReadingFile = new PersonServiceImpl();
		List<Person>persons = testDataReadingFile.readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		String city = persons.get(0).getCity();
		List<Person> dataGetInfosOnePerson = new ArrayList<>();

		for (Person persona:persons) {
			 cityComingFromList = persona.getCity();
			 dataGetInfosOnePerson.add(persona);
		}
		testDataReadingFile.getEmailFromAllPersonsOfCity(cityComingFromList);
		
		assertEquals(city,cityComingFromList);
	}

	@Test
	public void testGetChildrenAndFamilyLeavingAtOneAddress() {
		List<Person>personDatas = new ArrayList<>();
		Person testNewPersonIfChildren = new Person();
		testNewPersonIfChildren.setFirstName("Wolfgang");
		testNewPersonIfChildren.setLastName("Mozart");
		testNewPersonIfChildren.setAddress("1001 Sonate St");
		testNewPersonIfChildren.setBirthdate("10/10/2019");
		personDatas.add(testNewPersonIfChildren);
		boolean children = false;
		
		String lastNamePersonalities = new String();
		List<Person>personalities = new ArrayList<>();
		for(Person pers:personDatas) {
			 lastNamePersonalities = pers.getLastName();
			personalities.add(pers);
		}
		
		//assertTrue(children=true);
		assertEquals("Mozart", lastNamePersonalities);
	}
	
	@Test
	public void testGetPersonAroundFirestationWithMedicalrecords() {
		PersonServiceImpl testDataReadingFile = new PersonServiceImpl();
		List<Person>persons = testDataReadingFile.readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		
		Person testNewPersonIfAddressMatch = new Person();
		testNewPersonIfAddressMatch.setFirstName("Wolfgang");
		testNewPersonIfAddressMatch.setLastName("Mozart");
		testNewPersonIfAddressMatch.setAddress("1509 Culver St");
		
		FirestationServiceImpl testDataReadingFileFire = new FirestationServiceImpl();
		List<Firestation> fireData = testDataReadingFileFire.readJsonFileForFirestations();
		
		Firestation testNewFirestationIfAddressMatch = new Firestation();
		testNewFirestationIfAddressMatch.setAddress("1509 Culver St");
		
		testDataReadingFile.getPersonAroundFirestationWithMedicalrecords("1509 Culver St");
		
		
		assertNotNull(persons);
		assertNotNull(fireData);
		assertEquals(testNewPersonIfAddressMatch.getAddress(),testNewFirestationIfAddressMatch.getAddress());
	}

	@Test
	public void testGetFamilyAroundFirestationWithMedicalrecords() {
		PersonServiceImpl testDataReadingFile = new PersonServiceImpl();
		List<Person>persons = testDataReadingFile.readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		
		Person testNewPersonIfAddressMatch = new Person();
		testNewPersonIfAddressMatch.setFirstName("Wolfgang");
		testNewPersonIfAddressMatch.setLastName("Mozart");
		testNewPersonIfAddressMatch.setAddress("1509 Culver St");
		
		FirestationServiceImpl testDataReadingFileFire = new FirestationServiceImpl();
		List<Firestation> fireData = testDataReadingFileFire.readJsonFileForFirestations();
		
		Firestation testNewFirestationIfAddressMatch = new Firestation();
		testNewFirestationIfAddressMatch.setAddress("1509 Culver St");
		
		testDataReadingFile.getFamilyAroundFirestationWithMedicalrecords(testNewFirestationIfAddressMatch.getStationNumber());
		
		
		assertNotNull(persons);
		assertNotNull(fireData);
		assertEquals(testNewPersonIfAddressMatch.getAddress(),testNewFirestationIfAddressMatch.getAddress());
	}
	
	@Test
	public void testAddPerson() {
		List<Person> personList = new ArrayList<>();
		Person personperson = new Person();
		personperson.setFirstName("Mickael");
		personperson.setLastName("Jordan");
		personperson.setEmail("mickael@email.com");
		personperson.setBirthdate("12/11/1980");
		personList.add(personperson);
		PersonServiceImpl personRsult = new PersonServiceImpl();
		personRsult.addPerson(personperson);
		
		//assertNotNull(personRsult);
		assertEquals("Jordan",personperson.getLastName());
	}
	
	@Test
	public void testUpdatePerson() {
		PersonServiceImpl testPerson = new PersonServiceImpl();
		Person personperson = new Person();
		personperson.setLastName("Jordan");
		personperson.setFirstName("Mickael");
		personperson.setBirthdate("11/10/1974");
		personperson.setAddress("15055 Orleans St");
		personperson.setEmail("etienne@email.com");
	
		testPerson.updatePerson(personperson);
		
		
		assertEquals("15055 Orleans St",personperson.getAddress());
		assertNotNull(testPerson);
	}
	
	@Test
	public void testDeleteFirestation() {
		//String station_number="10";
		List<Person> personListed = new ArrayList<>();
		Person personperson = new Person();
		personperson.setFirstName("Mickael");
		personperson.setLastName("Jordan");
		personperson.setEmail("jordan@email.com");
		personListed.add(personperson);
		PersonServiceImpl testPerson = new PersonServiceImpl();
		testPerson.deletePerson("jordan@email.com");
		
		//assertEquals("",testFire);
		assertNotNull(personperson.getEmail());
	}	
}















