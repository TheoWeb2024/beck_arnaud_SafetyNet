package com.rescueService.safetyNets.model;



import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;


@Data
public class Medicalrecord {
	
	public Medicalrecord() {
			
	}
	
	private int id;
	
	private String firstName;
	
	private String lastName;
	
	private String birthDate;
	
	private JsonNode  medications;
	
	private JsonNode allergies;
	
	
	public int getId() {
		return id;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getFirstName() {
		return firstName;
	}
	
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	public String getLastName() {
		return lastName;
	}
	
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	
	public String getBirthDate() {
		return birthDate;
	}
	
	
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	
	
	public JsonNode getMedications() {
		return medications;
	}
	
	
	public void setMedications(JsonNode medications) {
		this.medications = medications;
	}
	
	
	public JsonNode getAllergies() {
		return allergies;
	}
	
	
	public void setAllergies(JsonNode allergies) {
		this.allergies = allergies;
	}
	
}
