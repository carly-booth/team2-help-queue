# Help Queue Application 

## Description 
The Help Queue Application is a web-hosted application, intended for Atos employees to receive IT assistance directly from IT support staff, in the form of tickets. 

Employees can post a ticket to the help queue, containing a description of their problem and its level of urgency, to which IT support staff then can post a solution. Employees can update their tickets whilst in the queue, to change the description or level of urgency. 

A list of _completed_ tickets is publicly accessible within the application, so employees can search for and retrieve the solution they require, before posting a ticket. Completed tickets can be easily filtered via keywords, enabling efficient and quick access to solutions and so minimising downtime.  

IT support staff work through the queue of tickets, which are ordered based on waiting time (_time created_, earliest to latest) and the urgency of each ticket. Once an employee’s ticket has been resolved, they can mark it as _completed_ and becomes publicly accessible. Employees can also access the _My Tickets_ page, where they can view their current, open tickets and completed, closed tickets. 

![FlowchartHorizontal](/flowchart-horizontal.jpg)

## Table of Contents 

1. Project Development 

2. Architecture 
   - Front-end
  
   - Back-end
   
   - DevOps: CI/CD Pipeline

   - UML Diagrams

3. Risk Assessment 

4. Installation 

5. Usage 

### Project Development
To begin, we assigned roles within the Scrum Team; back-end and front-end developers, DevOps developers and documentation leads.  

We then collectively created a product backlog containing 3 main tasks, with subtasks within each; Create documentation, Create homepage/template for general front-end pages and Work on DevOps operations. Other issues in the backlog comprised user stories. Once completed, tasks within the backlog were assigned to each team member to begin working on, either individually or in pairs/groups.  After work on our own tasks began, we had daily stand ups, as well as frequent discussions during the day to get help/advice/feedback from each other. 

Midway through the project, changes were made to the project specification; the application would be intended for internal use at Atos, rather than a classroom training tool. As this change was only superficial, alterations were quickly made to the business case and business model canvas to meet the new requirements. No changes were necessary to the back-end as the application would function the same. The product backlog items’ titles and descriptions were not updated, as the actual tasks remained the same, just with superficial changes to certain words. Essentially, the change to the specification did not significantly delay project progression. 

![Sprint Backlog](/sprint-backlog.png)
*<sub>The sprint backlog.<sub>*

![Kanban-board-in-progress](/kanban-board-in-progress.png)
*<sub>Example of the sprint in progress, showing tasks to do, in progess and done.<sub>*

The initial tasks completed were DevOps operations: setting up Jenkins within GCP, with a webhook, ensuring Spring Boot ran from the GCP console and applying Docker for containerisation. After these were completed, joining the back and front-end could begin. 

Documentation of the project began with developing a business case (what, why, who, how and future vision) and producing a Business Model Canvas. A list of technical requirements was put together, describing the user story or back-end setup, target user (employee/IT support/developer), task description and goal, and acceptance criteria.  Once the technical specification had been compiled, full system and component diagrams were created. 

![CI-pipeline](/CI-pipeline.jpg)

<sub>*CI/CD Pipeline.*<sub>

![service-diagram](/service-diagram.jpg)

<sub>*Service Diagram.*<sub>

 
## Architecture 
### Front-end


### Back-end
The back-end of the app comprises a REST API with full CRUD functionality, created with SpringBoot. Integration tests were written and performed also using SpringBoot, and  Postman was used to test the API functionality. When testing, a H2 database was used - as configured by our test profile. In production, Cloud SQL was used, as provided by Google, as linked to by our production profile. 
The hosting virtual machine is provided by GCP Console, and specifically using aubuntu-1804-bionic-v20201116 image, with n1-standard-1 (1 vCPU, 3.75 GB memory) machine type. The API itself is run from within a Docker container, which will be elaborated on further when discussing the CI/CD pipeline.  
In order to build the API, Maven was used to generate a jar file, which was run from within a Docker container. The  instructions for this were specified in a Dockerfile, and automatically triggered by Jenkins build instructions.  

### DevOps: CI/CD Pipeline
In order to create an effective CI/CD pipeline, a number of technologies were used. Specifically, Git was used for version control, with GitHub hosting our remote repository. A feature-branch model of working was utilised: new branches were created for each new feature and pushed to GitHub, then merged into the development branch, and finally into the main branch. A webhook was set up within GitHub, whereby new pushes to main trigger a build in our Jenkins instance running on the VM.  

With Jenkins set up on the VM, this enabled coordination of the automated process of generating new builds, spinning up fresh Docker containers with the new features as a result. This was set up as a freestyle project within Jenkins; this simply calls Docker build and run commands, prompting the instructions within the Dockerfile to be run. The Dockerfile details a two-stage build process, whereby one container is spun up to run the Maven build command, and another container receives the generated jar file and runs this from within it, using a Java image. This was found to be much more streamlined than running the API directly from the VM, allowing for a lightweight CI/CD process.  

## Risk Assessment 

    
    

## Installation 
### Prerequisites 
- Installed:
  - JRE
  - Maven

Ensure that firewall rules are set and that HTTP is allowed. In the case of GCP; sett firewall rules to allow TCP access on the specified ports, and assign the tag names that you create to the VM that is running your app.

If you are using Jenkins, you will need to set Jenkins as a sudoer; enter ‘sudo visudo’ on the shell, and setting the rule “jenkins ALL=(ALL:ALL) NOPASSWD:ALL”, giving Jenkins root access. 

To run the Docker commands from a Jenkins build, you will need to preface the commands with ‘sudo’, otherwise it will not have sufficient permission 

Jenkins and the app cannot share a port number, and the port number of the app was deliberately set to 8081 - as Jenkins’ port number is 8080 by default.

There are a number of ways to run the app, but these instructions will assume that the reader is running the app as the intended user would - from the cloud and using a cloud database. In order to make this platform independent, the instructions will just explain how to run it from the VM, or from a Docker container.  

### Running from a VM
To run from a VM, first ensure that JRE and Maven are installed on the VM instance (mvn -version and java -version from the CLI should show you if they are installed). 
If not, calling sudo apt install maven/java, for example. -----------

Again, ensure that git is installed. Once installed, call:
````
git clone https://github.com/nbBernard/team2-help-queue.git cd
````
into the team2-help-queue directory. 
Once in this directory, call:
````
mvn clean install
````
then navigate to the newly-created target directory. 
You will find a jar file in this folder called Help-Queue-0.0.1-SNAPSHOT.jar, and lastly call:
````
java -jar Help-Queue-0.0.1-SNAPSHOT.jar
````
which should run the API from port 8081 of your cloud IP address. 
You can test this by navigating to the URL:
````
http://<your ip address>:8081/read
````
which should present you with “[]” on the page, signifying that you are connecting to the API, and receiving an empty object array. Alternatively, if you are using Jenkins, you can add these commands to a freestyle build project in the build actions (Execute Shell), which will do this for you in its own environment when you build.  

Please note that thi sapp is currently configured to link to a GCP SQL database, as specified in the application.properties file - there is the option of running from an H2 database if you change the profile to ‘test’. 
It may be worth forking this project, and either cloning it down to your machine locally to change the database location, or editing the database path in VIM on the VM so that your data goes to where you want it to. 
If you want to use your own database rather than H2, you will need to create a database, and create tables as per the schema detailed on:
````
https://github.com/nbBernard/team2-help-queue/blob/main/src/main/resources/ticket-schema.sql
````

### Running from Docker
To run from Docker is even simpler. Ensuring that Docker is installed - if in doubt, call:
````
docker -version
````
Also, ensure you have cloned down the remote repository onto your VM, navigate to the team2-help-queue directory and enter the commands:  

````
docker rm -f help-queue 
````

````
docker build -t help-queue
````

````
docker run -d -p 8081:8081 --name help-queue help-queue 
````

This should run the commands as per the Dockerfile in that directory, first spinning up a container in which the jar file is built, and then running the jar file as per the second container that is spun up. As with running directly on the VM, the API should run on port 8081, at the VM’s IP address.  

### Setup of CI/CD Pipeline
To perform this setup, you need = to have Jenkins installed on your VM. Once = installed, you can create a freestyle project build, and enter the docker commands as per the section above.
In the _build triggers_ section, tick _“GitHub hook trigger for GITScm polling”_, and you will need to set a webhook to:
````
http://<ip of your VM>/webhook-github
````
in the webhook section of GitHub. 
This can be tested and should show a success message below. From then on, any changes that you make to the code (pushes to main) will trigger a new Jenkins build.  

## Usage 
- Screenshots of platform in use 
