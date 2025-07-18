package com.rescueService.safetyNets.dto;

import java.util.function.Function;

import com.rescueService.safetyNets.model.Medicalrecord;


public class MedicalrecordMapperServiceDTO implements Function<Medicalrecord, MedicalrecordDto> {
	
	

	@Override
	public MedicalrecordDto apply(Medicalrecord medicalrecord) {
		return new MedicalrecordDto(medicalrecord.getId(), medicalrecord.getFirstName(), medicalrecord.getLastName(), medicalrecord.getBirthDate(), 
				medicalrecord.getMedications(), medicalrecord.getAllergies());
	}

}
