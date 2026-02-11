# 🚀 User Authentication System

A complete full-stack authentication application built for **IT342 – Lab Activity 1**.

---

## 📌 Project Description

This application is a secure user authentication system that allows users to register, login, view their profile, and logout. It demonstrates:

- User registration with validation
- Secure login with JWT (JSON Web Token) authentication
- Protected routes requiring authentication
- User profile management
- Token-based session handling
- Password encryption using BCrypt
- RESTful API architecture
- Frontend-backend integration

The system uses Spring Boot for the backend API, React for the frontend interface, and MySQL for data persistence.

---

## 🛠 Tech Stack

### Frontend
- **React 19** - UI library
- **React Router v7** - Client-side routing
- **Axios** - HTTP client for API calls
- **Vite** - Build tool and dev server
- **CSS3** - Styling

### Backend
- **Spring Boot 3.2.2** - Application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database access layer
- **Hibernate** - ORM
- **JWT (jjwt 0.11.5)** - Token generation and validation
- **Maven** - Build tool
- **Lombok** - Reduce boilerplate code

### Database
- **MySQL 8.0** - Production database (via XAMPP)
- **H2** - In-memory database for testing

---

## 🚀 How to Run Backend

### Prerequisites
- **JDK 17 or higher** installed (JDK 19 works)
- **MySQL** running (via XAMPP or standalone)
- **Maven** (included via Maven Wrapper)

### Steps

1. **Set up Java environment** (if using JDK 19):
   ```cmd
   set JAVA_HOME=C:\Program Files\Java\jdk-19
   set PATH=%JAVA_HOME%\bin;%PATH%
   java -version
   ```

2. **Navigate to backend folder**:
   ```cmd
   cd backend\mini-app
   ```

3. **Build and run**:
   ```cmd
   .\mvnw.cmd spring-boot:run
   ```

   Or build JAR and run:
   ```cmd
   .\mvnw.cmd clean package
   java -jar target\mini-app-0.0.1-SNAPSHOT.jar
   ```

4. **Verify backend is running**:
   - Backend runs at: `http://localhost:8080`
   - Check logs for "Started MiniAppApplication"

---

## 🌐 How to Run Web (Frontend)

### Prerequisites
- **Node.js** (v16 or higher)
- **npm** or **yarn**

### Steps

1. **Navigate to web folder**:
   ```cmd
   cd web
   ```

2. **Install dependencies**:
   ```cmd
   npm install
   ```

3. **Start development server**:
   ```cmd
   npm run dev
   ```

4. **Access the application**:
   - Frontend runs at: `http://localhost:5173`
   - Open in browser to use the app

### Build for production
```cmd
npm run build
```
Production files will be in `dist/` folder.

---

## 📱 How to Run Mobile

The `mobile` folder contains the mobile application.

### Steps

1. **Navigate to mobile folder**:
   ```cmd
   cd mobile
   ```

2. **Install dependencies**:
   ```cmd
   npm install
   ```

3. **Run the app** (example for React Native/Expo):
   ```cmd
   npm start
   ```
   Or:
   ```cmd
   npm run android
   npm run ios
   ```

> **Note:** Refer to `mobile/README.md` for platform-specific setup instructions.

---

## 🗄 Database Setup Instructions

### Method 1: Using phpMyAdmin (Recommended for beginners)

1. **Start MySQL**:
   - Open **XAMPP Control Panel**
   - Click **Start** on MySQL module

2. **Open phpMyAdmin**:
   - Go to `http://localhost/phpmyadmin` in browser

3. **Import schema**:
   - Click **Import** tab
   - Choose file: `backend/mini-app/database/schema.sql`
   - Click **Go** button
   - Database `miniapp_db` will be created with tables `users` and `token_blacklist`

### Method 2: Using MySQL Command Line

```bash
mysql -u root -p < backend/mini-app/database/schema.sql
```

### Database Schema

**Table: users**
- `user_id` (BIGINT, Primary Key)
- `first_name` (VARCHAR 255)
- `last_name` (VARCHAR 255)
- `email` (VARCHAR 255, Unique)
- `password` (VARCHAR 255, BCrypt hashed)
- `status` (VARCHAR 50)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

**Table: token_blacklist**
- `token_id` (BIGINT, Primary Key)
- `token` (VARCHAR 512, Unique)
- `expires_at` (DATETIME)
- `created_at` (DATETIME)

### Verify Setup
```sql
USE miniapp_db;
SHOW TABLES;
DESCRIBE users;
```

---

## ⚙️ Environment Variables / Configuration

### Backend Configuration

**File:** `backend/mini-app/src/main/resources/application.properties`

**Key configurations to verify/modify:**

```properties
# Database Connection
spring.datasource.url=jdbc:mysql://localhost:3306/miniapp_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server Port
server.port=8080

# JWT Configuration
jwt.secret=Y29tcGxleHNlY3JldGtleWZvcmp3dHRva2VuZ2VuZXJhdGlvbmFuZHZhbGlkYXRpb24=
jwt.expiration=86400000

# Logging
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate=INFO
```

**Configuration Notes:**
- **Database URL**: Change `localhost:3306` if MySQL runs on different host/port
- **Database Credentials**: Update `username` and `password` if different from defaults
- **JWT Secret**: Keep this secret in production (use environment variable)
- **JWT Expiration**: Currently set to 24 hours (86400000 ms)
- **Port**: Change `server.port` if 8080 is in use

### Test Configuration

**File:** `backend/mini-app/src/test/resources/application.properties`

Uses H2 in-memory database for testing (no MySQL needed for tests):
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
```

### Frontend Configuration

**File:** `web/src/services/apiClient.js`

**API Base URL:**
```javascript
const API_BASE_URL = 'http://localhost:8080';
```

Change this if backend runs on different host/port.

---

## 🔌 API Endpoints

**Base URL:** `http://localhost:8080/api/auth`

### 1. Register User
- **Endpoint:** `POST /api/auth/register`
- **Description:** Create a new user account
- **Request Body:**
  ```json
  {
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "password": "password123"
  }
  ```
- **Response (200 OK):**
  ```json
  {
    "message": "User registered successfully"
  }
  ```
- **Error (400):** Email already exists or validation failed

### 2. Login
- **Endpoint:** `POST /api/auth/login`
- **Description:** Authenticate user and receive JWT token
- **Request Body:**
  ```json
  {
    "email": "john@example.com",
    "password": "password123"
  }
  ```
- **Response (200 OK):**
  ```json
  {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer"
  }
  ```
- **Error (401):** Invalid credentials

### 3. Get User Profile
- **Endpoint:** `GET /api/auth/profile`
- **Description:** Retrieve current user's profile information
- **Headers:** `Authorization: Bearer <token>`
- **Response (200 OK):**
  ```json
  {
    "userId": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "status": "active",
    "createdAt": "2026-02-11T10:30:00"
  }
  ```
- **Error (401):** Missing or invalid token
- **Error (404):** User not found

### 4. Logout
- **Endpoint:** `POST /api/auth/logout`
- **Description:** Invalidate current JWT token
- **Headers:** `Authorization: Bearer <token>`
- **Response (200 OK):**
  ```json
  {
    "message": "Logged out successfully"
  }
  ```
- **Error (401):** Missing or invalid token

---

## 🧪 Testing the API with Postman

1. **Start backend** (`mvnw.cmd spring-boot:run`)
2. **Register a user** (POST to `/api/auth/register`)
3. **Login** (POST to `/api/auth/login`) and copy the `accessToken`
4. **Get profile** (GET to `/api/auth/profile`) with header:
   - Key: `Authorization`
   - Value: `Bearer <paste-token-here>`
5. **Logout** (POST to `/api/auth/logout`) with same header

---

## 🐞 Troubleshooting

| Issue | Solution |
|-------|----------|
| **Java compilation error: release version 17 not supported** | Set JAVA_HOME to JDK 17+: `set JAVA_HOME=C:\Program Files\Java\jdk-19` |
| **Cannot connect to database** | Ensure MySQL is running in XAMPP and `miniapp_db` database exists |
| **Port 8080 already in use** | Change `server.port` in `application.properties` or stop other service |
| **Test failures** | MySQL not needed for tests (uses H2). Run `mvnw.cmd test` |
| **CORS errors** | Backend has CORS configured for `http://localhost:5173` |
| **Frontend connection refused** | Verify backend is running on port 8080 |
| **npm install errors** | Delete `node_modules` and `package-lock.json`, run `npm install` again |

---

## ✅ Quick Start Checklist

- [ ] JDK 17+ installed and `JAVA_HOME` set
- [ ] MySQL running (XAMPP started)
- [ ] Database created using `schema.sql`
- [ ] Backend running on `http://localhost:8080`
- [ ] Frontend dependencies installed (`npm install`)
- [ ] Frontend running on `http://localhost:5173`
- [ ] Test registration and login in browser

---

## 📂 Project Structure

```
IT342_G4_Camoro_Lab1/
├── backend/mini-app/          # Spring Boot backend
│   ├── src/main/java/         # Java source files
│   ├── src/main/resources/    # Configuration files
│   ├── src/test/              # Test files
│   ├── database/schema.sql    # MySQL schema
│   └── pom.xml                # Maven dependencies
├── web/                       # React frontend
│   ├── src/                   # React components
│   ├── public/                # Static assets
│   └── package.json           # npm dependencies
├── mobile/                    # Mobile app
└── README.md                  # This file
```

---

**Course:** IT342  
**Group:** G4 Camoro  
**Lab Activity:** 1

🎉 **Ready to use!** Open `http://localhost:5173` in your browser after starting both backend and frontend.