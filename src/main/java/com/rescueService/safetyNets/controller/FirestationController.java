package com.rescueService.safetyNets.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rescueService.safetyNets.model.Firestation;
import com.rescueService.safetyNets.service.FirestationService;
import com.rescueService.safetyNets.service.FirestationServiceImpl;

 
@RestController
@RequestMapping("/firestation")
public class FirestationController {
	
	private static final Logger logger = LogManager.getLogger("FirestationController");
	
	@Autowired
	private FirestationService firestationService;

	@GetMapping("/read")
	public  List<Firestation> getFirestations(){
		FirestationServiceImpl fireSI = new FirestationServiceImpl();
		List<Firestation> firestationa =  fireSI.readJsonFileForFirestations();
		logger.info ("FirestationController read");
		return  firestationa;
	}
	
	@PutMapping("/update/{address}")
	public List<Firestation> updateFirestation(@RequestBody Firestation firestation) {
		logger.info("FirestationController update");
		return firestationService.updateFirestation(firestation);
	}
	
	@PostMapping("/create")
	public boolean addFirestation(@RequestBody Firestation firestation) {
		logger.info ("FirestationController create");
		return firestationService.addFirestation(firestation);	
	}

	@DeleteMapping("/delete/{id}")
	public List<Firestation> delete(@PathVariable int id) {
		logger.info ("FirestationController delete");
		return firestationService.deleteFirestation(id);
	}
}