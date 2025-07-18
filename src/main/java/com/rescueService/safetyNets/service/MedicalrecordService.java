package com.rescueService.safetyNets.service;

import java.util.List;
import java.util.stream.Stream;

import com.rescueService.safetyNets.dto.MedicalrecordDto;
import com.rescueService.safetyNets.model.Medicalrecord;

public interface MedicalrecordService {
	
	boolean addMedicalrecord (Medicalrecord  medicalrecord) ;
	
	List<Medicalrecord> updateMedicalrecord (Medicalrecord medicalrecord);
	
	List<Medicalrecord> deleteMedicalrecord (String lastName, String firstName);

	Stream<MedicalrecordDto> getInfoFromMedicalrecord(String lastName);

}



