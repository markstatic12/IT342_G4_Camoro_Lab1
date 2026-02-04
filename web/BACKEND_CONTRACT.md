# Backend API Contract

This document specifies the exact API endpoints your backend needs to implement for the frontend to work correctly.

## Base URL
```
http://localhost:8080/api
```
*Configurable in frontend's `.env.local` file*

---

## 1️⃣ User Registration

### Endpoint
```http
POST /api/auth/register
```

### Request Headers
```
Content-Type: application/json
```

### Request Body
```json
{
  "first_name": "John",
  "last_name": "Doe",
  "email": "john.doe@example.com",
  "password": "securePassword123"
}
```

### Success Response (200 OK)
```json
{
  "message": "User registered successfully"
}
```

### Error Responses

**400 Bad Request** - Validation error
```json
{
  "message": "Email already exists"
}
```

**400 Bad Request** - Invalid input
```json
{
  "message": "Invalid email format"
}
```

---

## 2️⃣ User Login

### Endpoint
```http
POST /api/auth/login
```

### Request Headers
```
Content-Type: application/json
```

### Request Body
```json
{
  "email": "john.doe@example.com",
  "password": "securePassword123"
}
```

### Success Response (200 OK)
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "status": "active",
    "createdAt": "2026-02-04T10:00:00Z",
    "updatedAt": "2026-02-04T10:00:00Z"
  }
}
```

### Error Responses

**401 Unauthorized** - Invalid credentials
```json
{
  "message": "Invalid email or password"
}
```

**404 Not Found** - User doesn't exist
```json
{
  "message": "User not found"
}
```

---

## 3️⃣ User Logout

### Endpoint
```http
POST /api/auth/logout
```

### Request Headers
```
Content-Type: application/json
Authorization: Bearer <jwt_token>
```

### Request Body
```json
{}
```

### Success Response (200 OK)
```json
{
  "message": "Logged out successfully"
}
```

### Error Responses

**401 Unauthorized** - Invalid/expired token
```json
{
  "message": "Invalid token"
}
```

---

## 4️⃣ Get User Profile

### Endpoint
```http
GET /api/auth/profile
```

### Request Headers
```
Authorization: Bearer <jwt_token>
```

### Success Response (200 OK)
```json
{
  "user": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "status": "active",
    "createdAt": "2026-02-04T10:00:00Z",
    "updatedAt": "2026-02-04T10:00:00Z"
  }
}
```

### Error Responses

**401 Unauthorized** - Invalid/expired token
```json
{
  "message": "Unauthorized"
}
```

---

## 🔐 JWT Token Format

### Token Structure
The frontend expects a standard JWT token:
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.payload.signature
```

### Token Payload Example
```json
{
  "sub": "john.doe@example.com",
  "userId": 1,
  "iat": 1706959200,
  "exp": 1706962800
}
```

### Token Usage
- Frontend stores token in `localStorage`
- Frontend sends token in **Authorization** header: `Bearer <token>`
- Backend validates token on protected endpoints

---

## 🔧 CORS Configuration

Your backend **MUST** enable CORS for the frontend origin:

```
Access-Control-Allow-Origin: http://localhost:5173
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
Access-Control-Allow-Headers: Content-Type, Authorization
Access-Control-Allow-Credentials: true
```

### Spring Boot Example
```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
```

---

## 🗄️ Database Schema

Based on your USERS table diagram:

### Users Table
```sql
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,  -- Hashed with BCrypt
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'active'
);
```

### Password Hashing
- **Algorithm:** BCrypt
- **Strength:** 10-12 rounds
- Store hashed password, **NEVER** plain text

---

## 🏗️ Backend Architecture (From Your Diagrams)

### Controller Layer
```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request)
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request)
    
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String token)
    
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token)
}
```

### Service Layer
```java
@Service
public class AuthService {
    
    public void register(RegisterRequest request)
    
    public LoginResponse authenticate(String email, String password)
    
    public void logout(String token)
    
    public User getProfile(String token)
}
```

### Repository Layer
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
}
```

### Utility Classes
```java
@Component
public class PasswordEncoder {
    public String encodePassword(String password);
    public boolean matchPassword(String raw, String encoded);
}

@Component
public class TokenProvider {
    public String generateToken(String email);
    public boolean validateToken(String token);
    public String getUserEmailFromToken(String token);
}
```

---

## ✅ Implementation Checklist

### Phase 1: Setup
- [ ] Create Spring Boot project
- [ ] Add dependencies (Spring Security, JWT, JPA, MySQL)
- [ ] Configure database connection
- [ ] Enable CORS

### Phase 2: Database
- [ ] Create User entity
- [ ] Create UserRepository interface
- [ ] Test database connection

### Phase 3: Security
- [ ] Implement JWT TokenProvider
- [ ] Implement BCrypt PasswordEncoder
- [ ] Configure Spring Security

### Phase 4: Business Logic
- [ ] Create AuthService
- [ ] Implement registration logic
- [ ] Implement login logic
- [ ] Implement logout logic
- [ ] Implement profile retrieval

### Phase 5: API Layer
- [ ] Create AuthController
- [ ] Implement all endpoints
- [ ] Add validation
- [ ] Add error handling

### Phase 6: Testing
- [ ] Test registration endpoint
- [ ] Test login endpoint
- [ ] Test token generation
- [ ] Test protected endpoints
- [ ] Test with frontend

---

## 🧪 Testing with Postman/cURL

### Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "first_name": "John",
    "last_name": "Doe",
    "email": "john@example.com",
    "password": "password123"
  }'
```

### Login User
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

### Get Profile (with token)
```bash
curl -X GET http://localhost:8080/api/auth/profile \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

---

## 📝 Notes

1. **Token Expiration:** Set reasonable expiration (e.g., 24 hours)
2. **Error Messages:** Be consistent with error format
3. **Status Codes:** Use appropriate HTTP status codes
4. **Validation:** Validate all inputs server-side
5. **Security:** Always hash passwords, never store plain text
6. **CORS:** Don't forget to enable for frontend origin

---

## 🔗 Frontend Integration

The frontend automatically:
- Adds `Authorization: Bearer <token>` header to protected requests
- Handles 401 responses by redirecting to login
- Stores token and user data in localStorage
- Includes token in all API calls after login

**Your backend just needs to match this API contract!** 🎯
