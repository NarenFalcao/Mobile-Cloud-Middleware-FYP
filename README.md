# Mobile-Cloud-Middleware-FYP
An Interoperable Framework 
To create a Client application directly:
	Use the following URL in the Client application using the HTTPClient class of Apache.
	http://www.middleware.narendrakumar619.cloudbees.net/rest/cloud/
	Upload to cloud service
	http://www.middleware.narendrakumar619.cloudbees.net/rest/cloud/upload/
	The input should be of html form type with 3 inputs (File filename, Text cloud, Text code).
	Download from cloud service
	http://www.middleware.narendrakumar619.cloudbees.net/rest/cloud/download/
	Input should consist of a set of 2 texts (Text filename, Text cloud, Text code).
	List files on cloud
	http://www.middleware.narendrakumar619.cloudbees.net/rest/cloud/list/
The input parameter consists of (Text cloud, Text Code) and output is a text which consist of a list of all files in that cloud.
Search files on cloud 
The service is requested through the following URL, the input parameters like (FileName, CloudName, Code) are obtained from the user and the file is searched in the cloud storage and the result is displayed. 
http://www.middleware.narendrakumar619.cloudbees.net/rest/cloud/search/
Transfer files between cloud services 
The service is requested through the following URL, the input parameters like (FileName, FromCloudName, ToCloudName, FromCode, ToCode) are obtained from the user. The subsequent file is transferred to the required cloud. 
http://www.middleware.narendrakumar619.cloudbees.net/rest/cloud/transfer/

To run the source code of the project:
	Software Requirements:
•	Java 1.6
•	Maven 3.1.1
•	Apache tomcat 7.
Location of the code:
•	The code for REST services are in package src/main/java/com/mcm/rest.
•	The code to communicate to cloud is in package src/main/java/mcm (separate folders for each cloud service).
Compilation of Code:
•	Copy the package to any location in the system. 
•	Navigate to that location in the Command prompt.
•	Run the command mvn compile to complile the File.
Execution of Code:
•	Run the command mvn clean package in the command prompt.
•	Copy the War file from the folder /target 
•	Paste it in the webapps folder of Apache tomcat.
•	Start the Localhost by running the startup.bat in bin folder of apache tomcat.
•	The project then can be seen by using the following URL
http://localhost:8080/RESTfulExample.

 

	
	

