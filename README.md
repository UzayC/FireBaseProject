# Configuration Management System

## Overview

This project provides a user-friendly interface and robust backend for managing application configurations. It is designed to be scalable and easy to deploy, ensuring reliable configuration updates and management.

---

## Features

- **Authentication**: Secure Firebase authentication.
- **Configuration Management**: Add, update, delete, and view configurations.
- **Country Overrides**: Support for country-specific configurations.
- **Real-Time Updates**: Instant propagation of configuration changes.
- **Error Handling**: User-friendly error notifications.

---

## Technologies Used

### Frontend
- **Vue.js**: For the user interface.
- **Firebase Authentication**: For secure user login.
- **CSS**: Custom styling for a modern look.

### Backend
- **Java (Spring Boot)**: Modular architecture with handlers for endpoints.
- **Firebase Firestore**: Real-time database for storing configurations.
- **HTTP Server**: Custom API server with robust error handling.

---

## System Architecture

### Frontend
The frontend communicates with the backend via RESTful APIs to fetch, update, and delete configurations.

### Backend
The backend serves as a bridge between the frontend and Firebase Firestore, handling:
- Authentication
- Configuration CRUD operations
- Country-specific overrides

---

## Deployment Steps

### Prerequisites
- **Java JDK 17+**
- **Node.js and npm**
- **Firebase Project** with a configured Firestore database and service account JSON file.

### Backend Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/config-management-system.git
   cd config-management-system/backend
