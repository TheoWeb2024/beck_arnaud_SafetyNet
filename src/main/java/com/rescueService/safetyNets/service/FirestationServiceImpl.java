package com.rescueService.safetyNets.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rescueService.safetyNets.SafetyNetsApplication;
import com.rescueService.safetyNets.model.Firestation;
import com.rescueService.safetyNets.model.Person;

import lombok.Data;

@Data
@Service
public class FirestationServiceImpl implements FirestationService{
	
	private static final Logger logger = LogManager.getLogger(SafetyNetsApplication.class);

	@Autowired
	private PersonServiceImpl personServiceImpl;
	
		List<Firestation> firestations = new ArrayList<>();

		public List<Firestation> readJsonFileForFirestations() {

			logger.info("Read existing Json file for firestation");
			File file = new File("src/main/resources/templates/data.json");
			try {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode jsonNode=mapper.readValue(file, JsonNode.class);
				JsonNode fire = jsonNode.get("firestations");
				
				Iterator iterator = fire.iterator(); 
			
				while (iterator.hasNext()) { 
					JsonNode objInner = (JsonNode) iterator.next();
					
					int stat = (objInner.get("station")).asInt();
			
					String address = (objInner.get("address").toString()).replace("\"","");
				
					Firestation firestation = new Firestation();
					int i =0;
					for( i=0;i<firestations.size();i++) {
						firestations.get(i);
					}
					firestation.setId(i);
					firestation.setStationNumber(stat);
					firestation.setAddress(address);
					
					firestations.add(firestation);
									    
				}

			} catch (Exception e) {
				e.printStackTrace();
				
			}
			return firestations ;
		}

	public  List<Firestation> writeJSONData(List<Firestation> firestationData)  {
		
		logger.info("Write data in new Json file for firestation");
			
		try {
		File file = new File("src/main/resources/templates/data2.json");
			ObjectMapper om = new ObjectMapper();
			om.writeValue(file, firestationData);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Can't write data to new Json file");
		}
		return firestationData;
	}
		
	@Override
	public boolean addFirestation(Firestation firestation) {
		
		List<Firestation> updatedFirestation = new ArrayList<>();
		List<Firestation> firesData = readJsonFileForFirestations();
		boolean isPresent = false;
		for(Firestation fireS : firesData) {
			fireS.getStationNumber();
			if(firestation.getStationNumber() == fireS.getStationNumber()) {
				
				logger.error("Firestation doesn't exist");
				isPresent= true;
			}
		}
		
		
		if(isPresent) {
			writeJSONData(firesData);
		
		
		Firestation firestationPresent = null ;
		if(firesData != null) {
			Predicate <Firestation> condition1 = fire -> fire.getAddress().equalsIgnoreCase(firestation.getAddress()); 
			Predicate<Firestation> condition2 = fire -> fire.getStationNumber()==(firestation.getStationNumber());
			firesData.removeIf(condition1.and(condition2));
		}
		else  {
			firesData = new ArrayList<>();
		}
		if(firestationPresent == null) {
			
			int index=0;
			for( int i=0;i<firestations.size();i++) {
				if (firestations.get(i).getId() > index) {
					index = firestations.get(i).getId();
				}
			
			}
			firestation.setId(index+1);
			firesData.add(firestation);
		}
		writeJSONData(firesData);
		logger.info("add data in new Json file for firestation");
			
		return true;
		}else {
			return false;
		}
	}
	
	@Override
	public List<Firestation> updateFirestation(Firestation firestation) {
		logger.info("update data in new Json file for firestation");
		List<Firestation> updatedFirestation = new ArrayList<>();
		List<Firestation> firesData = readJsonFileForFirestations();
		
		Firestation firestationPresent = null ;
		
		if(firesData != null) {
			Predicate <Firestation> condition1 = fire -> fire.getAddress().equalsIgnoreCase(firestation.getAddress()); 
			//Predicate<Firestation> condition2 = fire -> fire.getStationNumber()==(firestation.getStationNumber());
			Predicate<Firestation> condition3 = fire -> fire.getId()==(firestation.getId());
			firesData.removeIf(condition1.and(condition3));
		}
		else 
			firesData = new ArrayList<>();
	
		int i =0;
		for( i=0;i<firestations.size();i++) {
			firestations.get(i);
		}
		firestation.setId(i);
		
			firestation.setStationNumber(firestation.getStationNumber());;
		
			firesData.add(firestation);
			
		writeJSONData(firesData);
		
		return updatedFirestation;
		
	}
	
	@Override
	public List<Firestation> deleteFirestation(int id) {
		logger.info("delete data in new Json file for firestation");
		List<Firestation> deletedFirestation = new ArrayList<>();
		FirestationServiceImpl firestationDatas = new FirestationServiceImpl();
		List<Firestation> firesData = firestationDatas.readJsonFileForFirestations();
		firesData.removeIf(fire -> fire.getId()==(id));
		
		writeJSONData(firesData);
		
		return deletedFirestation;
	}

	@Override
	public List<String> checkStationFromNumber(int stationNumber) {
		int nbPersonnes = 0;
		PersonServiceImpl personDatas = new PersonServiceImpl();
		List<Person> psi = personDatas.readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		FirestationServiceImpl firestationsDatas = new FirestationServiceImpl();
		List<Firestation> fireData = firestationsDatas.readJsonFileForFirestations();
		List<String> returnValue = new ArrayList<>();
		String firstName=new String();
		String lastName=new String();
		String address=new String();
		String phone=new String();
		
		for(Firestation firest : fireData)  {
			if(firest.getStationNumber()==(stationNumber)) {
				for(Person pers : psi) {
					if(	pers.getAddress().equals(firest.getAddress())) {
						firstName = pers.getFirstName();
						lastName = pers.getLastName();
						address = pers.getAddress();
						phone = pers.getPhone();
						returnValue.add(firstName + " " + lastName + " " + address + " " + phone);
					}
				}
			
			}	
		}	
		return returnValue ;
	}
	
	@Override
	public List<String> checkPhoneFromAroundFirestation(int stationNumber) {
		PersonServiceImpl personDatas = new PersonServiceImpl();
		FirestationServiceImpl firestationsDatas = new FirestationServiceImpl();
		List<Person> psi = personDatas.readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		List<Firestation> fireData = firestationsDatas.readJsonFileForFirestations();
		List<String> returnValue = new ArrayList<>();
		String phone = new String();
		
		for(Firestation firest : fireData)  {
			if(firest.getStationNumber()==(stationNumber)) {
				for(Person pers : psi) {
					if(	pers.getAddress().equals(firest.getAddress())) {
						phone = pers.getPhone();
						returnValue.add(phone);
						
					}
				}
			
			}	
		}	
		return returnValue ;
	}
}




