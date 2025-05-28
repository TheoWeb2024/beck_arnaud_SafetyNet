package com.rescueService.safetyNets.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rescueService.safetyNets.model.Person;
import com.rescueService.safetyNets.service.PersonService;
import com.rescueService.safetyNets.service.PersonServiceImpl;


@RestController
@RequestMapping("/person")
public class PersonController {


	private static final Logger logger = LogManager.getLogger("PersonController");
	
	@Autowired
	 private PersonService personService;
	@Autowired
	private PersonServiceImpl personServiceImpl;
	

	@PutMapping("/update/{lastName}/{firstName}/{birthdate}")
	public List<Person> updatePerson(@RequestBody Person person) {
		return personService.updatePerson(person);
	}
	
	@PostMapping("/create")
	public boolean addPerson(@RequestBody Person person) {
		logger.info ("PersonController create");
		return personService.addPerson(person);	
	}
	
	@DeleteMapping("/delete/{email}")
	public List<Person> delete(@PathVariable String email) {
		logger.info ("Persondelete email");
		return personService.deletePerson(email);
	}

	}




