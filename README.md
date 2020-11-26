# Help Queue Application 

 

## Description 

The Help Queue Application is a web-hosted application, intended for Atos employees to receive IT assistance directly from IT support staff, in the form of tickets. 

Employees can post a ticket to the help queue, containing a description of their problem and its level of urgency, to which IT support staff then can post a solution. Employees can update their tickets whilst in the queue, to change the description or level or urgency. 

A list of _completed_ tickets is publicly accessible within the application, so employees can search for and retrieve the solution they require, before posting a ticket. Completed tickets can be easily filtered via keywords, enabling efficient and quick access to solutions, minimising downtime.  

IT support staff work through the queue of tickets, which are ordered based on waiting time (_time created_, earliest to latest) and the urgency of each ticket. Once an employee’s ticket has been resolved, they can mark it as _completed_ and becomes publicly accessible. Employees can also access the _My Tickets_ page, where they can view their current, open tickets and completed, closed tickets. 

This Spring Boot application utilises Jenkins (CI server), a GCP SQL database and Docker. 

 

## Table of Contents 

1. Project Development 

2. Architecture 
   - CI pipeline 

   - Flowchart 

   - UMLs? 

3. Risk Assessment 

4. Installation 

5. Usage 

 

## Project Development 

To begin, we assigned roles within the Scrum Team; back-end and front-end developers, DevOps developers and documentation leads.  

We then collectively created a product backlog containing 3 main tasks, with subtasks within each; Create documentation, Create homepage/template for general front-end pages and Work on DevOps operations. Other issues in the backlog comprised user stories. Once completed, tasks within the backlog were assigned to each team member to begin working on, either individually or in pairs/groups.  After work on our own tasks began, we had daily stand ups, as well as frequent discussions during the day to get help/advice/feedback from each other. 

Midway through the project, changes were made to the project specification; the application would be intended for internal use at Atos, rather than a classroom training tool. As this change was only superficial, alterations were quickly made to the business case and business model canvas to meet the new requirements. No changes were necessary to the back-end as the application would function the same. The product backlog items’ titles and descriptions were not updated, as the actual tasks remained the same, just with superficial changes to certain words. Essentially, the change to the specification did not significantly delay project progression. 

 


 

The initial tasks completed were DevOps operations: setting up Jenkins within GCP, with a webhook, ensuring Spring Boot ran from the GCP console and applying Docker for containerisation. After these were completed, joining the back and front-end could begin. 

 

Documentation of the project began with developing a business case (what, why, who, how and future vision) and producing a Business Model Canvas. 

A list of technical requirements was put together, describing the user story or back-end setup, target user (employee/IT support/developer), task description and goal, and acceptance criteria.  Once the technical specification had been compiled, full system and component diagrams were created. 

- Full system diagram 

- Component diagram 

 

## Architecture 
### Back-end
The back-end of the app comprises a REST API with full CRUD functionality, created with SpringBoot. Integration tests were written and performed also using SpringBoot, and  Postman was used to test the API functionality. When testing, a H2 database was used - as configured by our test profile. In production, Cloud SQL was used, as provided by Google, as linked to by our production profile. 
The hosting virtual machine is provided by GCP Console, and specifically using aubuntu-1804-bionic-v20201116 image, with n1-standard-1 (1 vCPU, 3.75 GB memory) machine type. The API itself is run from within a Docker container, which will be elaborated on further when discussing the CI/CD pipeline.  
In order to build the API, Maven was used to generate a jar file, which was run from within a Docker container. The  instructions for this were specified in a Dockerfile, and automatically triggered by Jenkins build instructions.  

### DevOps: CI/CD Pipeline
In order to create an effective CI/CD pipeline, a number of technologies were used. Specifically, Git was used for version control, with GitHub hosting our remote repository. A feature-branch model of working was utilised: new branches were created for each new feature and pushed to GitHub, then merged into the development branch, and finally into the main branch. A webhook was set up within GitHub, whereby new pushes to main trigger a build in our Jenkins instance running on the VM.  

With Jenkins set up on the VM, this enabled coordination of the automated process of generating new builds, spinning up fresh Docker containers with the new features as a result. This was set up as a freestyle project within Jenkins; this simply calls Docker build and run commands, prompting the instructions within the Dockerfile to be run. The Dockerfile details a two-stage build process, whereby one container is spun up to run the Maven build command, and another container receives the generated jar file and runs this from within it, using a Java image. This was found to be much more streamlined than running the API directly from the VM, allowing for a lightweight CI/CD process.  

## Risk Assessment 

 

## Installation 

- List of prerequisites? (e.g., Git, etc.) 

 

## Usage 

- Screenshots of platform in use 

 

## (Contributing) 

 

 

## Credits 

 

 

### License 

 

 

 

 
