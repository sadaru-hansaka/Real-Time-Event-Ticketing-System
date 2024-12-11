# Real Time Event Ticketing System

This project simulates a real-time event ticketing system using the Producer-Consumer pattern and multithreading. It incorporates Object-Oriented Programming principles 
and aims to handle ticket releases and purchases concurrently. Vendors act as producers, adding tickets to the system, while customers act as consumers, purchasing 
tickets.

## Tech Stack

**CLI:** Core Java

**Backend:** Spring Boot

**Frontend:** React JS

## Features

- Multithreading: Simultaneous ticket release and purchase by multiple vendors and customers.
- Synchronization and Concurrency: Ensures data consistency and thread-safe operations.
- System Initialization and Configuration: Customize parameters such as:
    - Total tickets available
    - Ticket release rate
    - Customer retrieval rate
    - Maximum ticket capacity
- Logging and Error Handling: Comprehensive logs for ticketing activities and robust error management.
- Frontend Interface
    - Configuration Form
    - Vendor Form
    - Customer Form
    - TicketPool page

## Installation

### Prerequisites

- Java Development Kit(JDK)
- Maven
- Node.js
- Git

#### Step 1 : Clone the repository using Git :
```bash
    git clone https://github.com/sadaru-hansaka/Real-Time-Event-Ticketing-System.git
```

#### Step 2 : Navigate to the Project directory :
```bash
    cd Real-Time-Event-Ticketing-System
```

#### Step 3 : Setting up the Backend
```bash
    cd Backend
```
```bash
    mvn clean install
```
```bash
    mvn spring-boot:run
```
The backend will start running on localhost:8080

#### Step 4 : Setting up the Frontend
```bash
    cd Frontend
```
```bash
    npm install
```
```bash
    npm run dev 
```
The frontend will start running on localhost:5173

#### Step 4 : Setting up the CLI
```bash
    cd CLI
```
```bash
    javac *.java
    java Main
```

## API Documentation
The Real Time Event Ticketing System includes API documentation generated using Springdoc OpenAPI.

### Accessing Swagger UI
Once the application is running, you can access the API documentation via swagger ui :
- URL: http://localhost:8080/swagger-ui/index.html

### OpenAPI Specifications
The OpenAPI JSON specification is available at :
-URL: http://localhost:8080/v3/api-docs