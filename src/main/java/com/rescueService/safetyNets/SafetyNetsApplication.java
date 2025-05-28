package com.rescueService.safetyNets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rescueService.safetyNets.model.Firestation;
import com.rescueService.safetyNets.model.Person;
import com.rescueService.safetyNets.service.FirestationServiceImpl;
import com.rescueService.safetyNets.service.PersonServiceImpl;

import lombok.Data;



@Data
@SpringBootApplication
public class SafetyNetsApplication implements CommandLineRunner {
	
	
	
	private static final Logger logger = LogManager.getLogger(SafetyNetsApplication.class);

	
	
public static void main(String[] args) throws IOException, java.text.ParseException  {
		SpringApplication.run(SafetyNetsApplication.class, args);
		logger.info("Initializing safetyNets Application");
}

		@Override
			public void run(String... args) throws Exception {
				// TODO Auto-generated method stub		
		}
}
 