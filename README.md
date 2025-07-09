SAFETYNET 

An API REST which send specific data to rescue services. This app uses Java to run.

Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

Prerequisites
What things you need to install the software and how to install them
Java 17 
Maven 4.0.0 

Installing
Some examples that tell you how to get a development env running:
1.Install Java:
https://www.oracle.com/fr/java/technologies/downloads/
2.Install Maven:
https://maven.apache.org/install.html

In order to use to the API, use the port 8080 in your browser or Postman. Then, you'll access to different data with URLs or Endpoints for persons, firestations and medical records.

Running App
After running the app, you'll check data with the urls or endpoints. 

Testing
The app has unit tests written. In folders, you can get the jacoco report and the surefire report.
To run the tests from maven, execute the below command.
mvn verify
