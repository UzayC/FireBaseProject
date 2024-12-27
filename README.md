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
- **Environment Variables**: Sensitive data is securely managed through `.env` files.
- **Responsiveness**: The frontend is fully responsive and adapts to different screen sizes.

---

## Technologies Used

### Frontend
- **Vue.js**: For the user interface.
- **Firebase Authentication**: For secure user login.
- **CSS**: Custom styling for a modern look.
- **Axios**: For API communication.

### Backend
- **Java**: Modular architecture with handlers for endpoints.
- **Firebase Firestore**: Real-time database for storing configurations.
- **HTTP Server**: Custom API server with robust error handling.
- **Dotenv**: For managing environment variables.

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
- **Heroku CLI** (for deployment) or any preferred cloud provider CLI.

### Backend Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/config-management-system.git
   cd config-management-system/backend
   ```

2. Add the `firebase-service-account.json` file in `src/main/resources/` (this file should not be included in the repository).

3. Update the `.env` file with the following variables:
   ```env
   FIREBASE_API_KEY=your-firebase-api-key
   FIREBASE_AUTH_DOMAIN=your-auth-domain
   FIREBASE_PROJECT_ID=your-project-id
   FIREBASE_STORAGE_BUCKET=your-storage-bucket
   FIREBASE_MESSAGING_SENDER_ID=your-sender-id
   FIREBASE_APP_ID=your-app-id
   SERVER_PORT=4567
   ```

4. Build the project:
   ```bash
   mvn clean install
   ```

5. Run the backend locally:
   ```bash
   java -jar target/backend-1.0-SNAPSHOT.jar
   ```

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd ../frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Add a `.env` file in the frontend directory with the following content:
   ```env
   VUE_APP_BACKEND_URL=http://localhost:4567
   VUE_APP_FIREBASE_API_KEY=your-firebase-api-key
   VUE_APP_FIREBASE_AUTH_DOMAIN=your-auth-domain
   VUE_APP_FIREBASE_PROJECT_ID=your-project-id
   VUE_APP_FIREBASE_STORAGE_BUCKET=your-storage-bucket
   VUE_APP_FIREBASE_MESSAGING_SENDER_ID=your-sender-id
   VUE_APP_FIREBASE_APP_ID=your-app-id
   ```

4. Run the frontend locally:
   ```bash
   npm run serve
   ```

5. Access the application at `http://localhost:8080/`.

### Deployment

#### Backend

1. Deploy the backend using Heroku or another preferred cloud platform.
   ```bash
   heroku create
   git push heroku main
   ```

2. Add environment variables to the cloud platform’s configuration.

#### Frontend

1. Build the frontend for production:
   ```bash
   npm run build
   ```

2. Deploy the `dist` folder to a cloud hosting platform like Vercel, Netlify, or AWS S3.

---

## Testing

1. Ensure all environment variables are correctly set up locally and on the deployed environments.
2. Verify the authentication flow using Firebase.
3. Test CRUD operations for configurations.
4. Check the responsiveness of the UI on different devices.
5. Verify error handling by simulating backend and API errors.

---



