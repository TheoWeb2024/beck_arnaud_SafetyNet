package com.rescueService.safetyNets.service;

import java.util.List;
import java.util.stream.Stream;

import com.rescueService.safetyNets.dto.CheckStationFromNumberDto;
import com.rescueService.safetyNets.dto.ChildrenAndFamilyDto;
import com.rescueService.safetyNets.dto.PhoneFromAroundFirestationDto;
import com.rescueService.safetyNets.model.Firestation;

public interface FirestationService {

	
	boolean addFirestation(Firestation firestation);
	
	List<Firestation> updateFirestation (Firestation firestation);
	
	List<Firestation> deleteFirestation (int id);
	
	List<CheckStationFromNumberDto> checkStationFromNumber(int stationNumber);

	List<PhoneFromAroundFirestationDto> checkPhoneFromAroundFirestation(int stationNumber);

	}


