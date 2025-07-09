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
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rescueService.safetyNets.SafetyNetsApplication;
import com.rescueService.safetyNets.model.Firestation;
import com.rescueService.safetyNets.model.Person;

import lombok.Data;


@Data
@Service
public class PersonServiceImpl implements PersonService {
	
	
	private static final Logger logger = LogManager.getLogger(SafetyNetsApplication.class);
	
	@Override
	public boolean addPerson(Person person) {
		logger.info("Add one person in new Json file");
		boolean addedPerson = true;
		
		List<Person> personData = readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		
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
		
		return addedPerson;
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
					//person.setId(objInnerPersons.get("id").toString());
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
	public Stream<Person> getInfoFromOnePerson (String lastName) {
		List<Person> infoFromOnePerson = new ArrayList<>();
		String lastName1 = (lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase()) ;
		List<Person> personData = readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		Stream<Person> person = personData.parallelStream().filter(pers -> pers.getLastName().equals(lastName1));
		
		return person;	
	}

	@Override
	public List<Person> updatePerson(Person person) {
		logger.info("Updating a person");
		List<Person> updatedPerson = new ArrayList<>();
		List<Person> personData = readJsonFileForPersonsWithBirthDateAndMedAndAllergies();

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
	public List<String> getEmailFromAllPersonsOfCity(String city)  {
		
		String city1 = (city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase()) ;
	
		List<String> result = new ArrayList<>();
		List<Person> personsData = readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		for(Person perse :personsData) {
			String emails= perse.getEmail();
			String cityCheck= perse.getCity();
			
			if (cityCheck.equals(city1) ) {
				result.add(emails);
			}
		}
		List<String> listWithoutDuplicates = new ArrayList<>(new HashSet(result));
		listWithoutDuplicates.sort(null);
		return listWithoutDuplicates;
	}

	@Override
	public List<String> getChildrenAndFamilyLeavingAtOneAddress(String address) {
		
		List<String> returnValue = new ArrayList<>();
		List<Person> listOfChildrenAndAdult = readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		String firstNameChildren = new String();
		String lastNameChildren = new String();
		String addressChildren = new String();
		String firstNameAdult = new String();
		String lastNameAdult = new String();
		boolean children = false;
		int ageChildren=0;
		
		for(Person personaFamily : listOfChildrenAndAdult) {
			
			addressChildren = personaFamily.getAddress();
			children = personaFamily.isAdult();
			firstNameChildren = personaFamily.getFirstName();
			lastNameChildren = personaFamily.getLastName() ;
			ageChildren = personaFamily.ageCalculated();
			if(addressChildren.equalsIgnoreCase(address)) {
				if(children) {
					firstNameAdult = personaFamily.getFirstName();
					lastNameAdult = personaFamily.getLastName();
				}
			if(!children) {
				returnValue.add(firstNameChildren +" " + lastNameChildren + " " + ageChildren + " " + firstNameAdult + " " + lastNameAdult);
			}
			}
		}
		return returnValue;
	}

	@Override
	public List<String> getPersonAroundFirestationWithMedicalrecords(String address) {
		
		FirestationServiceImpl readFire = new FirestationServiceImpl();
		List<Person> personData = readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		List<Firestation> fireData = readFire.readJsonFileForFirestations();
		List<String> returnValue = new ArrayList<>();
		
		
		for(Firestation firest : fireData)  {
			firest.getAddress();
			int numerota = firest.getStationNumber();
			if(firest.getAddress().equalsIgnoreCase(address)) {
				for(Person pers : personData) {
					pers.getAddress();
					if(	pers.getAddress().equalsIgnoreCase(address)) {
						String firsta= pers.getFirstName();
						 String lasta = pers.getLastName();
						 String phona = pers.getPhone();
						 int age = pers.ageCalculated();
						JsonNode medica = pers.getMedications();
						JsonNode allergia =  pers.getAllergies();
						returnValue.add(numerota + " " + lasta + " " + age + " " + phona  + " " + medica + " " + allergia);	
					}
				}
			}
		}
			return returnValue ;
	}

	@Override
	public List<String> getFamilyAroundFirestationWithMedicalrecords(int stationNumber) {

		FirestationServiceImpl readFire = new FirestationServiceImpl();
		List<Person> personData = readJsonFileForPersonsWithBirthDateAndMedAndAllergies();
		List<Firestation> fireData = readFire.readJsonFileForFirestations();
		List<String> returnValue = new ArrayList<>();
		int age = 0;
		
		for(Firestation firest : fireData)  {
			 firest.getAddress();
			if(firest.getStationNumber()==(stationNumber)) {
				for(Person pers : personData) {
					
					if(	pers.getAddress().equalsIgnoreCase(firest.getAddress())) {
						age = pers.ageCalculated();
						String address = pers.getAddress();
						String lastName = pers.getLastName();
						String phone = pers.getPhone();
						JsonNode medications =  pers.getMedications();
						JsonNode allergies =  pers.getAllergies();
						returnValue.add(address + " " + lastName + " " + age + " " + phone + " " + medications + " " + allergies);
					}
				}
			}
		}
		return returnValue ;
	}
}
