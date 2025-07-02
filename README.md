# Vet-Clinic-Management-System
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1-blue.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.java.com/)

A comprehensive veterinary farm management system built with Spring Boot, providing RESTful APIs for animal health tracking, medical examinations, and vaccination management.


## Key Features

### 🐾 Animal Management
- CRUD operations for animals
- Search by type, health status, or both
- Animal health status tracking
- Advanced filtering and counting

### 🩺 Medical Examinations
- Create and manage medical exams
- Automatic health status updates
- Search exams by animal type, result, and date
- Exam history tracking

### 💉 Vaccination System
- Vaccine administration tracking
- Check vaccination status
- Filter by date and animal type
- Automatic reminders (future enhancement)

## Technology Stack

| Component | Technology |
|-----------|------------|
| **Backend** | Spring Boot 3.1 |
| **Database** | H2 (Embedded) |
| **Persistence** | Spring Data JPA |
| **API Documentation** | Swagger/OpenAPI |
| **Testing** | JUnit, Mockito |
| **Build Tool** | Maven |

## API Endpoints

### Animals (`/api/animals`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/` | Add new animal |
| `GET` | `/{id}` | Get animal by ID |
| `GET` | `/type/{type}` | Get animals by type |
| `GET` | `/search` | Search animals by criteria |
| `PUT` | `/{id}` | Update animal |
| `DELETE` | `/{id}` | Delete animal |

### Exams (`/api/examens`)
