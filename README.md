# bank-account

## Overview
The overall purpose of this application is to track the balance of a bank account by processing credit and debit transactions in real-time. It supports the creation of transactions, tracks the resulting account balance, and meets an audit requirement by sending the last 1,000 transactions in batches to a downstream audit system. The system is optimised to minimise the number of audit batches sent, reducing associated costs.

Key Components of the application

There are 3 main components of this application. These are - 

1. Producer
2. Balance Tracker
3. Auditing system

### Producer

- Responsible for generating transactions at a rate of 50 per second, split evenly between credits and debits.
- Each transaction has a randomly generated ID and an amount between £20,000 and £500,000.
- The producer operates on two separate threads: one for credits and one for debits.


### Balance Tracker

- Processes transactions to update the account balance.
- Tracks the overall balance by aggregating all processed transactions.
- Publishes batches of transactions to an audit system, ensuring the batch size and value constraints are respected.
- Exposes the current account balance via a REST API.

### Audit System:

- Receives transaction batches where each batch is limited to a total value of £1,000,000 and a maximum of 1,000 transactions.
- The system prints batch details to the console, including batch number, total value, and the count of transactions.
- Optimized to minimize the number of batches sent to reduce costs.


# How to run application
## Step 1: Install Prerequisites
1.1 Install Java Development Kit (JDK)
- Ensure that the JDK (Java Development Kit) is installed on your machine.
- Open a terminal or command prompt and run following command to ensure Java is installed:
  - `java -version`
- Please ensure it's Java 8 or later.

1.2 Download maven
-  Go to the Maven official [website](https://maven.apache.org/download.cgi) and download the latest version.
-  Follow the instructions on the Maven website for installation.
-  Once completed installation, run following command in terminal or command prompt:
  - `mvn -version`
    
1.3 Install IDE (Optional but would recommended) 
- IntelliJ IDEA: [Download IntelliJ IDEA](https://www.jetbrains.com/idea/download)
- Eclipse: [Download Eclipse](https://www.eclipse.org/downloads/)

## Step 2: Download or Clonse spring boot project
2.1 Obtain the Bank-Account project
- Download the Project: If the project is available as a .zip file, download it and unzip it to a directory on your computer.
- Clone from GitHub, running the following command: 
  `git clone git@github.com:cravencooper/bank-account.git`
## Step 3: Import project in IDE
3.1 Open in IntelliJ 
- Open IntelliJ
- Import project:
  - Go to File -> Open
  - Navigate to location of where you downloaded the file to.
  - IntelliJ should recognise it as a Maven project and import all dependencies.
3.2 Open in Eclipse
- Open Eclipse
- Import Project:
  - Go to 'File -> Import'
  - Choose existing maven project
  - Browse to root directory of your Maven project and select.
  - Eclipse, similar to IntelliJ will know that it is a maven project and sort depencies.
## Step 4: Building the project
Step 4.1
- Run the following command to build the project and download all necessary dependencies in terminal:
- `mvn clean install`
## Step 5: Running the project
5.1 Run from the Terminal
- Navigate to the root directory of the project and run the following command: 
- `mvn spring-boot:run`
5.2 Run Inside the IDE
- Locate the main class annotated with @SpringBootApplication.
- Click the green play button.
## Step 6: Access the Application
6.1 The producer will be generating transactions at the rate in which was previously outlined.
6.2 Retrieve account balance:
- Open web browser and paste in following URL - `http://localhost:8080/`


## Future enhancements




