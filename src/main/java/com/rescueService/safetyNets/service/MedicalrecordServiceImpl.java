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
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rescueService.safetyNets.dto.MedicalrecordDto;
import com.rescueService.safetyNets.dto.MedicalrecordMapperServiceDTO;
import com.rescueService.safetyNets.dto.PersonMapperServiceDTO;
import com.rescueService.safetyNets.model.Medicalrecord;

import lombok.Data;

@Data
@Service
public class MedicalrecordServiceImpl implements MedicalrecordService {
	
	private static final Logger logger = LogManager.getLogger("Medicalrecord ServiceImpl");
	
	@Autowired
	PersonServiceImpl personServiceImpl;
	
	private final PersonMapperServiceDTO personMapperServiceDTO;
	private final MedicalrecordMapperServiceDTO medicalrecordMapperServiceDTO;
	
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
				
				//Medicalrecord medicalrecord = new Medicalrecord();
				JsonNode objInner = (JsonNode) iterator.next();
				Medicalrecord medicalrecord = new Medicalrecord();
				//Medicalrecord medicRecord = new Medicalrecord();
				int i =0;
				for( i=0;i<medicalrecords.size();i++) {
					medicalrecords.get(i);
				}

			// System.out.println(iterator.next());
			String firstName = (objInner.get("firstName").toString()).replace("\"","");
			String lastName = (objInner.get("lastName").toString()).replace("\"","");
			String birthDate = (objInner.get("birthdate").toString()).replace("\"","");
						
			JsonNode medications = objInner.get("medications");
			JsonNode allergies = objInner.get("allergies");
		
			medicalrecord.setId(i);
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
	public Stream<MedicalrecordDto> getInfoFromMedicalrecord (String lastName) {
		logger.info("Getting infos from person with Medicalrecords");
		
		Stream<MedicalrecordDto> personData = readJsonFileForMedicalrecords()
				.stream()
				.filter(pers -> pers.getLastName().equalsIgnoreCase(lastName))
				.map(medicalrecordMapperServiceDTO);
		return personData;
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
			Medicalrecord medicalPresent = null;
			if(medicalData!= null){
			Predicate <Medicalrecord> condition1 = medi -> medi.getLastName().equalsIgnoreCase(medicalrecord.getLastName()); 
			Predicate<Medicalrecord> condition2 = medi -> medi.getFirstName().equalsIgnoreCase(medicalrecord.getFirstName());
			Predicate<Medicalrecord> condition3 = medi -> medi.getBirthDate().equals(medicalrecord.getBirthDate());
			medicalData.removeIf(condition1.and(condition2).and(condition3));
			}
			else {
				medicalData = new ArrayList<>();
			}
			int index=0;
			for( int i=0;i<medicalData.size();i++) {
				if (medicalData.get(i).getId() > index) {
					index = medicalData.get(i).getId();
				}
			}
			medicalrecord.setId(index+1);
			medicalData.add(medicalrecord);	
		}
			writeJSONData(medicalData);
	
		return true;
	}
	
	@Override
	public List<Medicalrecord>  updateMedicalrecord(Medicalrecord medicalrecord) {
		List<Medicalrecord> addedMedical = new ArrayList<>();
		logger.info("update medical in medical record");
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
	
		int i =0;
		for( i=0;i<medicalData.size();i++) {
			medicalData.get(i);
		}
			medicalrecord.setId(i);
			medicalrecord.setBirthDate(medicalrecord.getBirthDate());
			medicalrecord.setAllergies(medicalrecord.getAllergies());
			medicalrecord.setMedications(medicalrecord.getMedications());
			medicalData.add(medicalrecord);
			
		writeJSONData(medicalData);
	
		return addedMedical;
	}
	
	@Override
	public List<Medicalrecord>  deleteMedicalrecord(String lastName, String firstName) {
		List<Medicalrecord>  deletedMedical = new ArrayList<>();
		List<Medicalrecord> medicalData = readJsonFileForMedicalrecords();
		if(medicalData != null) {
		Predicate <Medicalrecord> condition1 = medi -> medi.getLastName().equalsIgnoreCase(lastName); 
		Predicate<Medicalrecord> condition2 = medi -> medi.getFirstName().equalsIgnoreCase(firstName);
		medicalData.removeIf(condition1.and(condition2));
		}
		writeJSONData(medicalData);
		logger.info("delete medical in medical record");
		return deletedMedical;
		}

	public MedicalrecordServiceImpl() {
		this.personMapperServiceDTO = new PersonMapperServiceDTO();
		this.medicalrecordMapperServiceDTO = new MedicalrecordMapperServiceDTO();
	}

}
	
	



