# 🚀 Complete Backend Setup Guide

## Prerequisites

### Required Software
- ✅ **IntelliJ IDEA** (Community or Ultimate)
- ✅ **Java JDK 17** or higher
- ✅ **XAMPP** (for MySQL database)
- ✅ **Maven** (usually bundled with IntelliJ)

---

## Step 1: Start XAMPP MySQL

### 1.1 Start MySQL Server
```bash
1. Open XAMPP Control Panel
2. Click "Start" for MySQL module
3. MySQL should show as running (green highlight)
```

### 1.2 Create Database
```bash
1. Click "Admin" button next to MySQL in XAMPP
2. This opens phpMyAdmin in your browser
3. Click "SQL" tab at the top
4. Copy and paste the contents of: backend/mini-app/database/schema.sql
5. Click "Go" to execute
6. You should see "auth_db" database created with "users" table
```

**Alternative:** Create manually:
- Click "New" in phpMyAdmin
- Database name: `auth_db`
- Collation: `utf8mb4_unicode_ci`
- Click "Create"
- Then run the SQL from `schema.sql`

---

## Step 2: Open Project in IntelliJ

### 2.1 Import Project
```bash
1. Open IntelliJ IDEA
2. File → Open
3. Navigate to: backend/mini-app
4. Select the folder and click OK
5. IntelliJ will detect it's a Maven project and start importing
6. Wait for dependencies to download (check bottom right progress bar)
```

### 2.2 Configure JDK
```bash
1. File → Project Structure → Project
2. SDK: Select Java 17 or higher
3. Language level: 17 or higher
4. Click OK
```

### 2.3 Enable Lombok (Required)
```bash
1. File → Settings → Plugins
2. Search for "Lombok"
3. Install the Lombok plugin
4. Restart IntelliJ
5. File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors
6. Check "Enable annotation processing"
7. Click OK
```

---

## Step 3: Verify Database Connection

### 3.1 Check application.properties
File: `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/auth_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=
```

**Important Notes:**
- Default XAMPP MySQL username is `root`
- Default XAMPP MySQL password is empty (blank)
- Port 3306 is default MySQL port
- If you changed MySQL password in XAMPP, update it here

### 3.2 Test Database Connection (Optional)
In IntelliJ:
```bash
1. View → Tool Windows → Database
2. Click "+" → Data Source → MySQL
3. Host: localhost
4. Port: 3306
5. Database: auth_db
6. User: root
7. Password: (leave empty for XAMPP default)
8. Click "Test Connection"
9. Should show "Successful"
```

---

## Step 4: Build the Project

### 4.1 Maven Clean and Install
```bash
Option A: Using IntelliJ Maven Tool
1. View → Tool Windows → Maven
2. Expand mini-app → Lifecycle
3. Double-click "clean"
4. Double-click "install"

Option B: Using Terminal
1. View → Tool Windows → Terminal
2. Run: mvn clean install
```

### 4.2 Verify Build Success
Look for:
```
[INFO] BUILD SUCCESS
[INFO] Total time: XX.XXX s
```

If you see errors:
- Check internet connection (Maven downloads dependencies)
- Ensure Java 17+ is configured
- Check pom.xml for any red underlines

---

## Step 5: Run the Application

### 5.1 Run Spring Boot App
```bash
Option A: Using IntelliJ Run Button
1. Open: src/main/java/com/example/mini_app/MiniAppApplication.java
2. Right-click on the file
3. Select "Run 'MiniAppApplication'"

Option B: Using Maven
1. Maven → mini-app → Plugins → spring-boot → spring-boot:run
2. Double-click to run
```

### 5.2 Verify Application Started
Check console output for:
```
Started MiniAppApplication in X.XXX seconds
Tomcat started on port 8080
```

**Common Issues:**
- **Port 8080 already in use:** Another app is using port 8080
  - Solution: Change port in application.properties: `server.port=8081`
- **Database connection error:** MySQL not running in XAMPP
  - Solution: Start MySQL in XAMPP Control Panel
- **Cannot find database:** Database not created
  - Solution: Run schema.sql in phpMyAdmin

---

## Step 6: Test API Endpoints

### 6.1 Using Browser (Quick Test)
Visit: `http://localhost:8080/api/auth/register`

You should see: JSON response (might be error, but server is responding)

### 6.2 Using Postman (Recommended)

#### Test 1: Register User
```http
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
    "first_name": "John",
    "last_name": "Doe",
    "email": "john@example.com",
    "password": "password123"
}
```

Expected Response (200 OK):
```json
{
    "message": "User registered successfully"
}
```

#### Test 2: Login
```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
    "email": "john@example.com",
    "password": "password123"
}
```

Expected Response (200 OK):
```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "user": {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john@example.com",
        "status": "active",
        "createdAt": "2026-02-04T10:00:00",
        "updatedAt": "2026-02-04T10:00:00"
    }
}
```

#### Test 3: Get Profile (Protected)
```http
GET http://localhost:8080/api/auth/profile
Authorization: Bearer YOUR_TOKEN_FROM_LOGIN_HERE
```

Expected Response (200 OK):
```json
{
    "user": {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john@example.com",
        "status": "active",
        "createdAt": "2026-02-04T10:00:00",
        "updatedAt": "2026-02-04T10:00:00"
    }
}
```

---

## Step 7: Connect to Frontend

### 7.1 Verify Backend is Running
- Backend URL: `http://localhost:8080`
- Test endpoint: `http://localhost:8080/api/auth/register`

### 7.2 Start Frontend (In VS Code)
```bash
1. Open VS Code
2. Open terminal in 'web' folder
3. Run: npm run dev
4. Frontend should start at: http://localhost:5173
```

### 7.3 Test Full Integration
```bash
1. Open browser: http://localhost:5173
2. Click "Sign up" to register
3. Fill in registration form
4. Submit - should succeed
5. Login with registered credentials
6. You should see the dashboard
```

---

## Troubleshooting

### Issue: "Cannot connect to database"
**Solution:**
1. Start MySQL in XAMPP
2. Verify database `auth_db` exists in phpMyAdmin
3. Check credentials in application.properties

### Issue: "Port 8080 already in use"
**Solution:**
1. Change port in application.properties:
   ```properties
   server.port=8081
   ```
2. Update frontend .env.local:
   ```
   VITE_API_URL=http://localhost:8081/api
   ```

### Issue: "CORS error" in frontend
**Solution:**
- CorsConfig.java should already handle this
- Verify frontend URL is `http://localhost:5173`
- If using different port, update CorsConfig.java:
  ```java
  configuration.setAllowedOrigins(Arrays.asList("http://localhost:YOUR_PORT"));
  ```

### Issue: "Lombok not working"
**Solution:**
1. Install Lombok plugin in IntelliJ
2. Enable annotation processing
3. Rebuild project

### Issue: "JWT token errors"
**Solution:**
- Check jwt.secret in application.properties (must be 512 bits)
- Current secret is already configured correctly

---

## Project Structure

```
mini-app/
├── src/main/java/com/example/mini_app/
│   ├── MiniAppApplication.java          # Main Spring Boot class
│   ├── entity/
│   │   └── User.java                    # User entity (JPA)
│   ├── repository/
│   │   └── UserRepository.java          # Database operations
│   ├── dto/
│   │   ├── RegisterRequest.java         # Register input
│   │   ├── LoginRequest.java            # Login input
│   │   ├── AuthResponse.java            # Login output
│   │   ├── UserDTO.java                 # User data
│   │   ├── MessageResponse.java         # Simple messages
│   │   └── UserResponse.java            # Profile output
│   ├── security/
│   │   ├── JwtTokenProvider.java        # JWT generation/validation
│   │   └── JwtAuthenticationFilter.java # JWT filter
│   ├── config/
│   │   ├── SecurityConfig.java          # Spring Security setup
│   │   └── CorsConfig.java              # CORS configuration
│   ├── service/
│   │   └── AuthService.java             # Business logic
│   └── controller/
│       └── AuthController.java          # REST API endpoints
├── src/main/resources/
│   └── application.properties           # Configuration
└── database/
    └── schema.sql                       # Database schema
```

---

## Next Steps

1. ✅ Backend is running on `http://localhost:8080`
2. ✅ Frontend is running on `http://localhost:5173`
3. ✅ They can communicate with each other
4. 🎯 Test the complete registration and login flow
5. 🎯 Check phpMyAdmin to see users being created

---

## Quick Commands Reference

### Start Backend (IntelliJ)
```
Run → Run 'MiniAppApplication'
```

### Start Frontend (VS Code Terminal)
```bash
cd web
npm run dev
```

### Start XAMPP MySQL
```
XAMPP Control Panel → MySQL → Start
```

### View Database
```
http://localhost/phpmyadmin
```

### Test Backend API
```
http://localhost:8080/api/auth/register
```

### Test Frontend App
```
http://localhost:5173
```

---

**🎉 Your backend is ready! Now test it with your React frontend!**
