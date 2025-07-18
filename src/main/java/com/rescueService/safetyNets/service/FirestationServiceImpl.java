package com.rescueService.safetyNets.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rescueService.safetyNets.SafetyNetsApplication;
import com.rescueService.safetyNets.dto.CheckStationFromNumberDto;
import com.rescueService.safetyNets.dto.CheckStationFromNumberServiceMapperDto;
import com.rescueService.safetyNets.dto.PhoneFromAroundFirestationDto;
import com.rescueService.safetyNets.dto.PhoneFromAroundFirestationServiceMapperDTO;
import com.rescueService.safetyNets.model.Firestation;
import com.rescueService.safetyNets.model.Person;

import lombok.Data;
 
@Data
@Service
public class FirestationServiceImpl implements FirestationService{
	
	private static final Logger logger = LogManager.getLogger(SafetyNetsApplication.class);

	@Autowired
	private PersonServiceImpl personServiceImpl;
	
	private final CheckStationFromNumberServiceMapperDto checkStationFromNumberServiceMapperDto;
	private final PhoneFromAroundFirestationServiceMapperDTO phoneFromAroundFirestationServiceMapperDTO;
	

	public List<Firestation> readJsonFileForFirestations() {
		List<Firestation> firestations = new ArrayList<>();
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
			logger.error("Can't read data from Json file");
				
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
		logger.info("Adding firestation regarding address");
		List<Firestation> firesData = readJsonFileForFirestations();
		boolean isPresent = false;
		for(Firestation fireS : firesData) {
			fireS.getStationNumber();
			if(firestation.getStationNumber() == fireS.getStationNumber()) {
				
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
						 
				int index = 0;
				for( int i=0;i<firesData.size();i++) {
					if (firesData.get(i).getId() > index) {
						index = firesData.get(i).getId();
					}
				 
				}
				firestation.setId(index+1);
				firesData.add(firestation);
			
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
		List<Firestation> firestations = new ArrayList<>();
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
		for( i=0;i<firesData.size();i++) {
			firesData.get(i);
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
	public List<CheckStationFromNumberDto> checkStationFromNumber(int stationNumber) {
		logger.info("Checking station from number");
		List<Firestation> result = new ArrayList<>();
		//Stream<ChildrenAndFamilyDto> psi = null;
		PersonServiceImpl personDatas = new PersonServiceImpl();
		List<Person> psi = personDatas.readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		List<Firestation> fireData = readJsonFileForFirestations();
		List<CheckStationFromNumberDto> returnValue = new ArrayList<>();
		//Stream<Object> phoneAroundFirestation=null;
	
		for(Firestation firest : fireData)  {
			if(firest.getStationNumber()==(stationNumber)) {
			result.add(firest);
				returnValue.addAll(psi
						.stream()
						.filter(pers-> pers.getAddress().equalsIgnoreCase(firest.getAddress()))
						.map(checkStationFromNumberServiceMapperDto)
						.collect(Collectors.toList()));
			}
		}
		return returnValue ;
	}
	
	@Override
	public List<PhoneFromAroundFirestationDto> checkPhoneFromAroundFirestation(int stationNumber) {
		logger.info("Checking phone number from around firestation");
		PersonServiceImpl personDatas = new PersonServiceImpl();
		List<Person> psi = personDatas.readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		List<Firestation> fireData = readJsonFileForFirestations();
		List<PhoneFromAroundFirestationDto> returnValue = new ArrayList<>();
		Stream<Object> phoneAroundFirestation=null;
	
		for(Firestation firest : fireData)  {
			if(firest.getStationNumber()==(stationNumber)) {
				returnValue.addAll(psi
						.stream()
						.filter(pers-> pers.getAddress().equalsIgnoreCase(firest.getAddress()))
						.map(phoneFromAroundFirestationServiceMapperDTO)
						.collect(Collectors.toList()));
			}
		}
		return returnValue ;
	}

	
	
	public FirestationServiceImpl() {
		this.checkStationFromNumberServiceMapperDto = new CheckStationFromNumberServiceMapperDto();
		this.phoneFromAroundFirestationServiceMapperDTO = new PhoneFromAroundFirestationServiceMapperDTO();
		
	}
}



