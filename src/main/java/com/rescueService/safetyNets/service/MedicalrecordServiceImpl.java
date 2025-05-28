package com.rescueService.safetyNets.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rescueService.safetyNets.model.Medicalrecord;
import com.rescueService.safetyNets.model.Person;

import lombok.Data;

@Data
@Service
public class MedicalrecordServiceImpl implements MedicalrecordService {
	
	private static final Logger logger = LogManager.getLogger("Medicalrecord ServiceImpl");
	
	@Autowired
	PersonServiceImpl personServiceImpl;
	
	List<Medicalrecord> medicalrecords = new ArrayList<>();

	public List<Medicalrecord> readJsonFileForMedicalrecords() {
		logger.info("read Json file in medical record");
		List<Medicalrecord>medicalrecords = new ArrayList<>();
		File file = new File("src/main/resources/templates/data.json");
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode=mapper.readValue(file, JsonNode.class);
			JsonNode medic = jsonNode.get("medicalrecords");
		
		Iterator iterator = medic.iterator(); 
			while (iterator.hasNext()) {
				
				Medicalrecord medicalrecord = new Medicalrecord();
				JsonNode objInner = (JsonNode) iterator.next();

			// System.out.println(iterator.next());
			String firstName = (objInner.get("firstName").toString()).replace("\"","");
			String lastName = (objInner.get("lastName").toString()).replace("\"","");
			String birthDate = (objInner.get("birthdate").toString()).replace("\"","");
						
			JsonNode medications = objInner.get("medications");
			JsonNode allergies = objInner.get("allergies");
		
			medicalrecord.setFirstName(firstName);
			medicalrecord.setLastName(lastName);
			medicalrecord.setBirthDate(birthDate);
			medicalrecord.setMedications(medications);
			medicalrecord.setAllergies(allergies);
			medicalrecords.add(medicalrecord);
			}
		} catch (Exception e) {
		e.printStackTrace();
		logger.error("Exception when read Json file in medical record");
		}
		return medicalrecords ;
	}
		
	public  List<Medicalrecord> writeJSONData(List<Medicalrecord> medicalData)  {
			
		try {
		File file = new File("src/main/resources/templates/data2.json");
			ObjectMapper om = new ObjectMapper();
			om.writeValue(file, medicalData);		
		} catch (IOException e) {
		e.printStackTrace();
		logger.error("write data in new Json file in medical record");
		}
		return medicalData;
	}

	@Override
	public List<Medicalrecord> getInfoFromMedicalrecord (String lastName) {
		List<Medicalrecord> listMedical = new ArrayList<>();
		
		List<Medicalrecord> medicalData = readJsonFileForMedicalrecords();
		Predicate <Medicalrecord> condition1 = medi -> medi.getLastName().equalsIgnoreCase(lastName); 
		for(Medicalrecord medica:medicalData) {
			if(condition1 != null) {
				medica.getFirstName();
				medica.getLastName();
				medica.getAllergies();
				medica.getMedications();
				medica.getAllergies();
				listMedical.add(medica);
			}
		}	
		return listMedical;	
	}


	@Override
	public  boolean addMedicalrecord(Medicalrecord  medicalrecord) {
		logger.info("add medical in medical record");
		//List<Medicalrecord> addedMedical = new ArrayList<>();
		boolean addedMedical = true;
		List<Medicalrecord> medicalData = readJsonFileForMedicalrecords();
		int currentDate = LocalDate.now().getYear();
		String dateToConvert = medicalrecord.getBirthDate();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM/dd/yyyy",Locale.FRANCE);
	    LocalDate formattedDate = LocalDate.parse(dateToConvert,myFormatObj);
	    int dateConverted = formattedDate.getYear();
		if(dateConverted > currentDate) {
			return false;
		}else {
			Predicate <Medicalrecord> condition1 = medi -> medi.getLastName().equalsIgnoreCase(medicalrecord.getLastName()); 
			Predicate<Medicalrecord> condition2 = medi -> medi.getFirstName().equalsIgnoreCase(medicalrecord.getFirstName());
			Predicate<Medicalrecord> condition3 = medi -> medi.getBirthDate().equals(medicalrecord.getBirthDate());
			medicalData.removeIf(condition1.and(condition2).and(condition3));
			medicalData.add(medicalrecord);	
			writeJSONData(medicalData);
		}
		return addedMedical;
	}
	
	@Override
	public List<Medicalrecord>  updateMedicalrecord(Medicalrecord medicalrecord) {
		List<Medicalrecord> addedMedical = new ArrayList<>();
		
		List<Medicalrecord> medicalData = readJsonFileForMedicalrecords();
		Medicalrecord medicalPresent = null;
		
		if(medicalData != null) {
			Predicate <Medicalrecord> condition1 = medi -> medi.getLastName().equalsIgnoreCase(medicalrecord.getLastName()); 
			Predicate<Medicalrecord> condition2 = medi -> medi.getFirstName().equalsIgnoreCase(medicalrecord.getFirstName());
			Predicate<Medicalrecord> condition3 = medi -> medi.getBirthDate().equals(medicalrecord.getBirthDate());
			medicalData.removeIf(condition1.and(condition2).and(condition3));
		}
		else 
			medicalData = new ArrayList<>();
	
		if(medicalPresent == null) {
			medicalrecord.setBirthDate(medicalrecord.getBirthDate());
			medicalrecord.setAllergies(medicalrecord.getAllergies());
			medicalrecord.setMedications(medicalrecord.getMedications());
			medicalData.add(medicalrecord);
			
		writeJSONData(medicalData);
		logger.info("update medical in medical record");
		}
		return addedMedical;
	}
	
	@Override
	public List<Medicalrecord>  deleteMedicalrecord(String lastName, String firstName) {
		//String lastName1 = (lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase()) ;
		//String firstName1 = (lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase()) ;
		logger.info("delete data in Json file in medical record");
		List<Medicalrecord>  deletedMedical = new ArrayList<>();
		List<Medicalrecord> medicalData = readJsonFileForMedicalrecords();
		//medicalData.removeIf((pers -> pers.getLastName().equals(lastName1)) && ((pers.getLastName().equals(firstName1)))) ;
		if(medicalData != null) {
		Predicate <Medicalrecord> condition1 = medi -> medi.getLastName().equalsIgnoreCase(lastName); 
		Predicate<Medicalrecord> condition2 = medi -> medi.getFirstName().equalsIgnoreCase(firstName);
		medicalData.removeIf(condition1.and(condition2));
		}
		writeJSONData(medicalData);
		logger.info("delete medical in medical record");
		return deletedMedical;
		}

	@Override
	public List<Person> getAllergies(String birthdate) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
	
	



