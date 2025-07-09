package com.rescueService.safetyNets.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import com.rescueService.safetyNets.dto.ChildrenAndFamilyDto;
import com.rescueService.safetyNets.dto.EmailMapperServiceDTO;
import com.rescueService.safetyNets.dto.EmailPersonDto;
import com.rescueService.safetyNets.dto.FireDto;
import com.rescueService.safetyNets.dto.FloodStationsDto;
import com.rescueService.safetyNets.dto.FloodStationsMapperServiceDTO;
import com.rescueService.safetyNets.dto.ParentsDto;
import com.rescueService.safetyNets.dto.PersonDto;
import com.rescueService.safetyNets.dto.PersonMapperServiceDTO;
import com.rescueService.safetyNets.model.Firestation;
import com.rescueService.safetyNets.model.Person;

import lombok.Data;


@Data
@Service
public class PersonServiceImpl implements PersonService {
	
	private final PersonMapperServiceDTO personMapperServiceDTO;
	private final EmailMapperServiceDTO emailMapperServiceDTO;
	private final FloodStationsMapperServiceDTO floodStationsMapperServiceDTO;
	
	private static final Logger logger = LogManager.getLogger(SafetyNetsApplication.class);
	
	@Override
	public boolean addPerson(Person person) {
		logger.info("Add one person in new Json file");
		//boolean addedPerson = true;
		
		List<Person> personData = readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		
		logger.info("Adding one person in PersonServiceImpl");
		
		int currentDate = LocalDate.now().getYear();
		String dateToConvert = person.getBirthdate();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM/dd/yyyy",Locale.FRANCE);
	    LocalDate formattedDate = LocalDate.parse(dateToConvert,myFormatObj);
	    int dateConverted = formattedDate.getYear();
		if(dateConverted > currentDate) {
			return false;
		}else {
			Predicate <Person> condition1 = pers -> pers.getLastName().equalsIgnoreCase(person.getLastName()); 
			Predicate<Person> condition2 = pers -> pers.getFirstName().equalsIgnoreCase(person.getFirstName());
			Predicate<Person> condition3 = pers -> pers.getEmail().equals(person.getEmail());
			personData.removeIf(condition1.and(condition2).and(condition3));
			personData.add(person);
		
			writeJSONData(personData);
		}
		
		return true;
	}
	
	public List<Person> readJsonFileForPersonsWithBirthDateAndMedAndAllergies() {
						
			List<Person>persons = new ArrayList<>();
			File file = new File("src/main/resources/templates/data.json");
			try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode=mapper.readValue(file, JsonNode.class);
			//String jsonString = mapper.writeValueAsString(jsonNode);
			JsonNode persone = jsonNode.get("persons");
			JsonNode medic = jsonNode.get("medicalrecords");
		
				Iterator iterPersons = persone.iterator(); 
				
				while (iterPersons.hasNext()) {
					JsonNode objInnerPersons = (JsonNode) iterPersons.next();
				
					Person person = new Person();
					int i =0;
					for( i=0;i<persons.size();i++) {
						persons.get(i);
					}
					person.setId(i);
					person.setFirstName( objInnerPersons.get("firstName").toString().replace("\"",""));
					person.setLastName( objInnerPersons.get("lastName").toString().replace("\"",""));
					person.setAddress(objInnerPersons.get("address").toString().replace("\"",""));
					person.setCity(objInnerPersons.get("city").toString().replace("\"",""));
					person.setEmail(objInnerPersons.get("email").toString().replace("\"",""));
					person.setPhone(objInnerPersons.get("phone").toString().replace("\"",""));
					person.setZip(objInnerPersons.get("zip").toString().replace("\"",""));
	
					persons.add(person);
					
					Iterator iterMedicalRecords = medic.iterator();
					while (iterMedicalRecords.hasNext()) {
						JsonNode objInnerMedicat = (JsonNode) iterMedicalRecords.next();
						String firstNameMed =  objInnerMedicat.get("firstName").toString().replace("\"","");
						String lastNameMed = objInnerMedicat.get("lastName").toString().replace("\"","");
						String birthdateMed = objInnerMedicat.get("birthdate").toString().replace("\"","");
						if (person.getFirstName().equals(firstNameMed)&& person.getLastName().equals(lastNameMed)) {
							person.setBirthdate(objInnerMedicat.get("birthdate").toString().replace("\"",""));
							person.setMedications(objInnerMedicat.get("medications"));
							person.setAllergies(objInnerMedicat.get("allergies"));
						}
					}
			
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
				logger.info("Read existing Json file for persons and medications");
		return persons ;
	}
	
	public  List<Person> writeJSONData(List<Person> personData)  {
	
		try {
			File file = new File("src/main/resources/templates/data2.json");
				ObjectMapper om = new ObjectMapper();
				om.writeValue(file, personData);
				logger.info("Write one person in new Json file");
		
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Exception when writing in a new Json file data coming from user");
		}
		return personData ;
	}

	@Override
	public Stream<PersonDto> getInfoFromOnePerson (String lastName) {
		logger.info("Getting infos from person");
		
		Stream<PersonDto> personData = readJsonFileForPersonsWithBirthDateAndMedAndAllergies()
				.stream()
				.filter(pers -> pers.getLastName().equalsIgnoreCase(lastName))
				.map(personMapperServiceDTO);
		return personData;
	}

	@Override
	public List<Person> updatePerson(Person person) {
		logger.info("Updating a person");
		List<Person> updatedPerson = new ArrayList<>();
		List<Person> personData = readJsonFileForPersonsWithBirthDateAndMedAndAllergies();

		logger.info("Updating person in PersonServiceImpl");
		Person personPresent = null;
				
		if(personData != null)  {
		
		Predicate <Person> condition1 = pers -> pers.getLastName().equalsIgnoreCase(person.getLastName());
		Predicate<Person> condition2 = pers -> pers.getFirstName().equalsIgnoreCase(person.getFirstName());
		Predicate<Person> condition3 = pers -> pers.getBirthdate().equals(person.getBirthdate());
		personData.removeIf(condition1.and(condition2).and(condition3));
		}
		else 
			personData = new ArrayList<>();
	
		if(personPresent == null) {
			person.setAddress(person.getAddress());
			person.setEmail(person.getEmail());
			person.setZip(person.getZip());
			person.setCity(person.getCity());
			person.setPhone(person.getPhone());
			person.setBirthdate(person.getBirthdate());
			person.setMedications(person.getMedications());
			person.setAllergies(person.getAllergies());
			personData.add(person);
			
		writeJSONData(personData);
		}
		return updatedPerson;
	}

	@Override
	public List<Person>  deletePerson(String email) {
		logger.info("Deleting a person");
		List<Person>  deletedPerson = new ArrayList<>();
		List<Person> personData = readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		personData.removeIf(pers -> pers.getEmail().equals(email));
		writeJSONData(personData);
		
		return deletedPerson;
	}

	@Override
	public List<EmailPersonDto> getEmailFromAllPersonsOfCity(String city) {
		logger.info("Getting email from persons");
		List<PersonDto> infoFromOnePerson = new ArrayList<>();
		String lastName1 = (city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase()) ;
		List<Person> persone = readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		List<EmailPersonDto> personData = persone
					.stream()
					.map(emailMapperServiceDTO)
					.distinct()
					.collect(Collectors.toList());
			
		return personData;
	}

	@Override
	public Stream<Object> getChildrenAndFamilyLeavingAtOneAddress(String address) {
		logger.info("Getting family infos in PersonServiceImpl");
		List<Person> listOfChildrenAndAdult = readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		Stream<Object> childrenAndAdult = null;
		List<ParentsDto> parentName = getAdultsLivingAtSameAddress(address);
	
		for(Person personaFamily : listOfChildrenAndAdult) {
			if(!personaFamily.isAdult()) {
				childrenAndAdult =  listOfChildrenAndAdult
						.stream()
						.filter(pers -> pers.ageCalculated()<18)
						.filter(pers -> pers.getAddress().equalsIgnoreCase(address))
						.map(person -> new ChildrenAndFamilyDto( person.getFirstName(),person.getLastName(),
								person.ageCalculated(), parentName));
			}
		}
		return childrenAndAdult;
	}
	
	@Override
	public Stream<Object> getPersonAroundFirestationWithMedicalrecords(String address) {
		logger.info("Getting person around firestation with medicals");
		FirestationServiceImpl readFire = new FirestationServiceImpl();
		List<Person> personData = readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		List<Firestation> fireData = readFire.readJsonFileForFirestations();
		Stream<Object>returnValue = null;
		
		for(Firestation firest : fireData)  {
			firest.getAddress();
			int numerota = firest.getStationNumber();
			if(firest.getAddress().equalsIgnoreCase(address)) {
				returnValue = personData
				.stream()
				.filter(pers -> pers.getAddress().equalsIgnoreCase(address))
				.map(floodStationsMapperServiceDTO);
			}
		}
			return  returnValue ;
		}
		
	@Override
	public List<FloodStationsDto> getFamilyAroundFirestationWithMedicalrecords(int stationNumber) {
		logger.info("Getting family around firestation with medicals");
		FirestationServiceImpl readFire = new FirestationServiceImpl();
		List<Person> personData = readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		List<Firestation> fireData = readFire.readJsonFileForFirestations();
		List<FloodStationsDto> returnValue = new ArrayList<>();
		
		List<String> fired = new ArrayList<>();
		for(Firestation firest : fireData)  {
			//firest.getAddress();
			if(firest.getStationNumber()==(stationNumber)) {
				firest.getAddress();
				fired.add(firest.getAddress());
				
				returnValue.addAll( personData
								.stream()
								.filter(perse ->perse.getAddress().equalsIgnoreCase(firest.getAddress()))
								.map(floodStationsMapperServiceDTO)
								 .collect(Collectors.toList()));
					}
				}	
		return returnValue ;
	}
	
    public List<ParentsDto> getAdultsLivingAtSameAddress(String address) {
		List<ParentsDto> adultsLivingAtSameAddress = new ArrayList<>();
		logger.info("Getting parent name method");
		List<Person> persone = readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		for(Person personaFamily : persone) {
			if(personaFamily.getAddress().equalsIgnoreCase(address) && personaFamily.isAdult()) {
				adultsLivingAtSameAddress = persone
						.stream()
						.filter(person -> person.getAddress().equalsIgnoreCase(address))
						.filter(person -> person.ageCalculated()>18)
						.map(person -> new ParentsDto(person.getId(), person.getFirstName(), person.getLastName()))
				
						.collect(Collectors.toList());
			   }
		
	    }
		return adultsLivingAtSameAddress;
    }

	public PersonServiceImpl() {
		this.personMapperServiceDTO = new PersonMapperServiceDTO();
		this.emailMapperServiceDTO = new EmailMapperServiceDTO();
		this.floodStationsMapperServiceDTO = new FloodStationsMapperServiceDTO();
	
	}



}

