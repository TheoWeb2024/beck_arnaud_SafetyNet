package com.rescueService.safetyNets.service;

import java.util.List;

import com.rescueService.safetyNets.model.Medicalrecord;
import com.rescueService.safetyNets.model.Person;

public interface MedicalrecordService {
	
	boolean addMedicalrecord (Medicalrecord  medicalrecord) ;
	
	List<Medicalrecord> updateMedicalrecord (Medicalrecord medicalrecord);
	
	List<Medicalrecord> deleteMedicalrecord (String lastName, String firstName);

	List<Medicalrecord> getInfoFromMedicalrecord(String lastName);

}



