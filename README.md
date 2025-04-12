# Distributed Online Enrollment System

This project is a distributed online enrollment system designed to manage student enrollment and grades in a distributed environment. The system includes multiple services running on different nodes to ensure fault tolerance. The application uses OAuth2/JWT for authentication, allowing students and faculty to manage their courses and grades. The services are structured in a microservices architecture, with each service running on a separate node.

## Features

- **Login/Logout**: Users can log in and log out with their credentials. Session management is handled across different nodes using OAuth2/JWT.
- **View Available Courses**: Students can view a list of available courses.
- **Student Enrollment**: Students can enroll in open courses.
- **View Grades**: Students can view their grades for enrolled courses.
- **Faculty Upload Grades**: Faculty members can upload grades for the students.

## System Architecture

The application follows the **Model-View-Controller (MVC)** architecture. The **view** layer (frontend) is hosted on its own node, while the **controller** and **API** layers (auth-service, course-service, grade-service, enrollment-service) run on separate nodes.

### Fault Tolerance:

When a node goes down, the features provided by that node will stop working. However, the rest of the application will remain functional.

## Prerequisites

Before running this project, make sure you have the following installed:

- Java 11 or higher
- Maven

## Setting Up and Running the Project

### 1. Clone the repository

```bash
git clone

cd STDISCM-P4
```

### 2. Build and Run the Services

Each service (auth-service, course-service, grade-service, enrollment-service) can be run independently.

1. Run the **auth-service**

```bash
cd auth-service
mvn clean install
mvn spring-boot:run
```

2. Run the **course-service**

```bash
cd course-service
mvn clean install
mvn spring-boot:run
```

3.  Run the **grade-service**

```bash
cd grade-service
mvn clean install
mvn spring-boot:run
```

4. Run the **enrollment-service**

```bash
cd enrollment-service
mvn clean install
mvn spring-boot:run
```

### Accessing the Application

- The frontend will be available at http://localhost:8000.

- You can access the services through their respective APIs, with each service running on its own port.
