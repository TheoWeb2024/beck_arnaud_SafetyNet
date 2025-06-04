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

In order to use to the API, use the port 8080 in your browser or Postman. Then, you'll access to different data with URLs such as : 
http://localhost:8080/firestation?stationNumber=<station_number>
http://localhost:8080/childAlert?address=<address>
http://localhost:8080/phoneAlert?firestation=<firestation_number>
http://localhost:8080/fire?address=<address>
http://localhost:8080/flood/stations?stations=<station_number>
http://localhost:8080/personInfolastName=<lastName>
http://localhost:8080/communityEmail?city=<city>
or Endpoints :
http://localhost:8080/person
http://localhost:8080/firestation
http://localhost:8080/medicalRecord


Running App
After running the app, you'll check data with the above urls or endpoints. 

Testing
The app has unit tests written. In folders, you can get the jacoco report and the surefire report.
To run the tests from maven, execute the below command.
mvn verify
