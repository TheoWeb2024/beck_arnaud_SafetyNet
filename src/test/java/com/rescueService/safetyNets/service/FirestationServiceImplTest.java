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

import com.rescueService.safetyNets.model.Firestation;
import com.rescueService.safetyNets.model.Person;

@ExtendWith(MockitoExtension.class)
class FirestationServiceImplTest {
	
	@Autowired
	 PersonServiceImpl personServiceImpl;
	@Autowired
	 FirestationServiceImpl firestationServiceImpl;

	@Test
	public void testReadJsonFileForFirestations() {
	
		FirestationServiceImpl testDataReadingFile = new FirestationServiceImpl();
		List<Firestation>firestations = testDataReadingFile.readJsonFileForFirestations();
		//String dataStation = firestations.get(0).getStationNumber();
		//int num = Integer.parseInt(dataStation);
		int dataSize = firestations.size();	
		
		
		assertEquals(13,dataSize);
	}
	
	@Test
	public void testWriteJSONData() {
		List<Firestation>firestationDatas = new ArrayList<>();
		Firestation newFirestationToWrite = new Firestation();
		newFirestationToWrite.setAddress(" 10009 Manager St");
		newFirestationToWrite.setStationNumber(10);
		firestationDatas.add(newFirestationToWrite);
	
		FirestationServiceImpl testWriteDataInFile = new FirestationServiceImpl();
		List<Firestation> firestationsWritedInFile = testWriteDataInFile.writeJSONData(firestationDatas);
		String resultFirestationData=null;
		for(Firestation fireS:firestationsWritedInFile) {
			resultFirestationData = fireS.getAddress();
		}
		assertNotNull(firestationsWritedInFile);
		assertEquals(" 10009 Manager St", resultFirestationData);
	}
	
	@Test
	public void testCheckStationFromNumber() {
		int STATION_NUMBER =3;
		final String  ADDRESS ="1509 Culver St";
		
		FirestationServiceImpl dataReadingFileFire = new FirestationServiceImpl();
		List<Firestation> fireData = dataReadingFileFire.readJsonFileForFirestations();
		boolean resultFireAddress = false;
		List<Firestation> fireListed = new ArrayList<>();
		for(Firestation fired:fireData) {
			if(fired.getStationNumber()==(STATION_NUMBER)&& fired.getAddress().equals(ADDRESS))  {
				 resultFireAddress =true;
				fireListed.add(fired);
			}
		}
		assertTrue(resultFireAddress);
	}
	
	@Test
	public void testCheckPhoneFromAroundFirestation() {
		int station_number=2;
		
		PersonServiceImpl psi = new PersonServiceImpl();
		List<Person> resultList = psi.readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		
		FirestationServiceImpl firestationRsult = new FirestationServiceImpl();
		List<Firestation>firesta = firestationRsult.readJsonFileForFirestations();
		List<String>pompier = new ArrayList<>();
		String phone=null;
		String resultAddress=null;
		String addressPerson=null;
		int stationNum = 0; 
		for(Firestation fir :firesta) {
			stationNum = fir.getStationNumber();
			 if(fir.getStationNumber()==(station_number)) {
				 resultAddress = fir.getAddress();
				 for(Person rreessuu:resultList) {
				 addressPerson = rreessuu.getAddress();
				 phone = rreessuu.getPhone();
				 pompier.add(phone);
				 }
			 }
		}
		assertEquals(addressPerson,resultAddress);
	}
	
	@Test
	public void testAddFirestation() {
		List<Firestation> fireList = new ArrayList<>();
		Firestation firefire = new Firestation();
		firefire.setAddress("505 Orleans St");
		firefire.setStationNumber(10);
		fireList.add(firefire);
		FirestationServiceImpl firestationRsult = new FirestationServiceImpl();
		firestationRsult.addFirestation(firefire);
		
		assertNotNull(firestationRsult);
	}
	
	@Test
	public void testUpdateFirestation() {
		FirestationServiceImplTest testFire = new FirestationServiceImplTest();
		testFire.testAddFirestation();
		List<Firestation> fireList = new ArrayList<>();
		Firestation firefire = new Firestation();
		firefire.setAddress("15055 Orleans St");
		FirestationServiceImpl firestationRsult = new FirestationServiceImpl();
		firestationRsult.updateFirestation(firefire);
		
		assertEquals("15055 Orleans St",firefire.getAddress());
		assertNotNull(firestationRsult);
	}
	
	@Test
	public void testDeleteFirestation() {
		//String station_number="10";
		List<Firestation> fireListed = new ArrayList<>();
		Firestation firefire = new Firestation();
		firefire.setAddress("505 Orleans St");
		firefire.setStationNumber(10);
		fireListed.add(firefire);
		FirestationServiceImpl testFire = new FirestationServiceImpl();
		testFire.deleteFirestation(firefire.getId());

		assertNotNull(firefire.getAddress());
	}
}
