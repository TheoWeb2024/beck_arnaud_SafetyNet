package com.rescueService.safetyNets.controller;

import java.util.List;
import java.util.stream.Stream;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rescueService.safetyNets.dto.MedicalrecordDto;
import com.rescueService.safetyNets.dto.PersonDto;
import com.rescueService.safetyNets.model.Medicalrecord;
import com.rescueService.safetyNets.service.MedicalrecordService;


@RestController
@RequestMapping("/medicalRecord")
public class MedicalrecordController {

	private static final Logger logger = LogManager.getLogger("MedicalrecordController");
	
	@Autowired
	private MedicalrecordService medicalrecordService;
	
	@PutMapping("/update/{lastName}/{firstName}/{birthdate}")
	public List<Medicalrecord> updateMedicalrecord(@RequestBody Medicalrecord medicalrecord) {
		return medicalrecordService.updateMedicalrecord(medicalrecord);
	}
 
	@PostMapping("/create")
	public boolean addMedicalrecord(@RequestBody Medicalrecord  medicalrecord) {
		logger.info ("MedicalrecordController create");
		return medicalrecordService.addMedicalrecord(medicalrecord);	
	}
	
	@DeleteMapping("/delete/{lastName}/{firstName}")
	public List<Medicalrecord> deleteMedicalrecord(@PathVariable String lastName, String firstName) {
		logger.info ("Medicalrecorddelete birthdate");
		return medicalrecordService.deleteMedicalrecord(lastName, firstName);
	}
	
	@GetMapping("/personMedicalrecords")
	public Stream<MedicalrecordDto> getInfoFromMedicalrecord (@RequestParam String lastName) {
		logger.info("MedicalRecordController getting info regard lastName");
		return medicalrecordService.getInfoFromMedicalrecord(lastName);
	}
}
