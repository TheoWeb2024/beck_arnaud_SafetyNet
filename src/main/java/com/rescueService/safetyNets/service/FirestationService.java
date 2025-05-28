package com.rescueService.safetyNets.service;

import java.util.List;

import com.rescueService.safetyNets.model.Firestation;
import com.rescueService.safetyNets.model.Person;




public interface FirestationService {

	
	boolean addFirestation(Firestation firestation);
	
	List<Firestation> updateFirestation (Firestation firestation);
	
	List<Firestation> deleteFirestation (int id);
	
	List<String> checkStationFromNumber(int stationNumber);

	List<String> checkPhoneFromAroundFirestation(int stationNumber);

	}


