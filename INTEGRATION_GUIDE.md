# 🔗 Complete Integration Guide - Frontend ↔️ Backend

This guide shows how the React frontend and Spring Boot backend work together.

---

## System Architecture

```
┌─────────────────────┐         ┌─────────────────────┐
│   React Frontend    │         │  Spring Boot API    │
│   (VS Code)         │◄───────►│   (IntelliJ)        │
│   Port: 5173        │  HTTP   │   Port: 8080        │
└─────────────────────┘         └─────────────────────┘
                                          │
                                          ▼
                                ┌─────────────────────┐
                                │   MySQL Database    │
                                │   (XAMPP)           │
                                │   Port: 3306        │
                                └─────────────────────┘
```

---

## Complete Flow Example: User Registration

### Step 1: User Fills Registration Form (Frontend)

**File:** `web/src/pages/Register.jsx`

```javascript
// User types in form:
firstName: "John"
lastName: "Doe"
email: "john@example.com"
password: "password123"
```

### Step 2: Frontend Validates Input (Client-Side)

**File:** `web/src/pages/Register.jsx`

```javascript
const validateForm = () => {
    if (!formData.firstName || !formData.lastName || !formData.email || 
        !formData.password || !formData.confirmPassword) {
        setError('Please fill in all fields');
        return false;
    }
    // More validation...
};
```

### Step 3: Frontend Calls API

**File:** `web/src/services/authService.js`

```javascript
register: async (userData) => {
    const response = await apiClient.post('/auth/register', {
        first_name: userData.firstName,  // Maps to backend format
        last_name: userData.lastName,
        email: userData.email,
        password: userData.password
    });
    return response.data;
}
```

**Actual HTTP Request:**
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

### Step 4: Backend Receives Request

**File:** `mini-app/src/main/java/com/example/mini_app/controller/AuthController.java`

```java
@PostMapping("/register")
public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
    // Validates input using @Valid annotation
    MessageResponse response = authService.register(request);
    return ResponseEntity.ok(response);
}
```

### Step 5: Service Layer Processes

**File:** `mini-app/src/main/java/com/example/mini_app/service/AuthService.java`

```java
public MessageResponse register(RegisterRequest request) {
    // Check if email exists
    if (userRepository.existsByEmail(request.getEmail())) {
        throw new RuntimeException("Email already exists");
    }
    
    // Create user
    User user = new User();
    user.setFirstName(request.getFirst_name());
    user.setLastName(request.getLast_name());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword())); // Hash password!
    user.setStatus("active");
    
    // Save to database
    userRepository.save(user);
    
    return new MessageResponse("User registered successfully");
}
```

### Step 6: Database Stores User

**Database Operation:**
```sql
INSERT INTO users (first_name, last_name, email, password, status, created_at, updated_at)
VALUES ('John', 'Doe', 'john@example.com', '$2a$10$...', 'active', NOW(), NOW());
```

### Step 7: Backend Sends Response

**HTTP Response:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
    "message": "User registered successfully"
}
```

### Step 8: Frontend Handles Response

**File:** `web/src/pages/Register.jsx`

```javascript
const result = await register({
    firstName: formData.firstName,
    lastName: formData.lastName,
    email: formData.email,
    password: formData.password
});

if (result.success) {
    alert('Registration successful! Please login.');
    navigate('/login');  // Redirects to login page
}
```

---

## Complete Flow Example: User Login

### Step 1: User Enters Credentials (Frontend)

**File:** `web/src/pages/Login.jsx`

```javascript
email: "john@example.com"
password: "password123"
```

### Step 2: Frontend Sends Login Request

**File:** `web/src/services/authService.js`

```javascript
login: async (email, password) => {
    const response = await apiClient.post('/auth/login', {
        email,
        password
    });
    return response.data;  // { token, user }
}
```

**HTTP Request:**
```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
    "email": "john@example.com",
    "password": "password123"
}
```

### Step 3: Backend Validates Credentials

**File:** `mini-app/src/main/java/com/example/mini_app/service/AuthService.java`

```java
public AuthResponse login(LoginRequest request) {
    // Find user
    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    
    // Check password
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new RuntimeException("Invalid email or password");
    }
    
    // Generate JWT token
    String token = tokenProvider.generateToken(user.getEmail());
    
    // Create response
    UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), ...);
    return new AuthResponse(token, userDTO);
}
```

### Step 4: Backend Generates JWT Token

**File:** `mini-app/src/main/java/com/example/mini_app/security/JwtTokenProvider.java`

```java
public String generateToken(String email) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + 86400000); // 24 hours
    
    return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(getSigningKey(), SignatureAlgorithm.HS512)
            .compact();
}
```

### Step 5: Backend Returns Token + User Data

**HTTP Response:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huQGV4YW1wbGUuY29tIiwiaWF0IjoxNzA2OTU5MjAwLCJleHAiOjE3MDcwNDU2MDB9.xxx",
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

### Step 6: Frontend Stores Token

**File:** `web/src/context/AuthContext.jsx`

```javascript
const login = async (email, password) => {
    const response = await authService.login(email, password);
    const { token, user } = response;
    
    // Store in localStorage (persists across page reloads)
    localStorage.setItem('token', token);
    localStorage.setItem('user', JSON.stringify(user));
    
    // Update state
    setToken(token);
    setUser(user);
    
    return { success: true };
};
```

### Step 7: Frontend Redirects to Dashboard

```javascript
if (result.success) {
    navigate('/dashboard');  // User sees dashboard
}
```

---

## Protected Route Example: View Profile

### Step 1: User Clicks "View Profile"

**File:** `web/src/pages/Dashboard.jsx`

```javascript
const handleViewProfile = () => {
    navigate('/profile');  // Protected route
};
```

### Step 2: Protected Route Checks Authentication

**File:** `web/src/components/ProtectedRoute.jsx`

```javascript
const ProtectedRoute = ({ children }) => {
    const { isAuthenticated } = useAuth();
    
    if (!isAuthenticated) {
        return <Navigate to="/login" replace />;  // Redirect if no token
    }
    
    return children;  // Show profile if authenticated
};
```

### Step 3: Frontend Requests Profile Data

**File:** `web/src/services/apiClient.js`

```javascript
// Interceptor automatically adds token to ALL requests
apiClient.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;  // Add token!
    }
    return config;
});
```

**HTTP Request:**
```http
GET http://localhost:8080/api/auth/profile
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

### Step 4: Backend Validates JWT Token

**File:** `mini-app/src/main/java/com/example/mini_app/security/JwtAuthenticationFilter.java`

```java
protected void doFilterInternal(HttpServletRequest request, ...) {
    // Extract token from "Authorization: Bearer xxx" header
    String jwt = getJwtFromRequest(request);
    
    // Validate token
    if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
        // Get email from token
        String email = tokenProvider.getEmailFromToken(jwt);
        
        // Load user and set authentication
        UserDetails userDetails = userRepository.findByEmail(email)...
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
```

### Step 5: Controller Gets Authenticated User

**File:** `mini-app/src/main/java/com/example/mini_app/controller/AuthController.java`

```java
@GetMapping("/profile")
public ResponseEntity<?> getProfile() {
    // Get authenticated user from security context
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    
    UserResponse response = authService.getProfile(email);
    return ResponseEntity.ok(response);
}
```

### Step 6: Backend Sends Profile Data

**HTTP Response:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

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

### Step 7: Frontend Displays Profile

**File:** `web/src/pages/Profile.jsx`

```javascript
const Profile = () => {
    const { user } = useAuth();  // Gets user from context
    
    return (
        <div>
            <h1>{user?.firstName} {user?.lastName}</h1>
            <p>{user?.email}</p>
        </div>
    );
};
```

---

## Logout Flow

### Frontend (Client-Side)

**File:** `web/src/context/AuthContext.jsx`

```javascript
const logout = () => {
    // Remove token and user data
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    
    // Clear state
    setToken(null);
    setUser(null);
    
    // Optional: Call backend logout endpoint
    authService.logout();
};
```

### Backend (Optional - Token Blacklisting)

**File:** `mini-app/src/main/java/com/example/mini_app/controller/AuthController.java`

```java
@PostMapping("/logout")
public ResponseEntity<?> logout() {
    // With JWT, logout is mainly client-side
    // Server can maintain a blacklist of invalidated tokens (advanced)
    return ResponseEntity.ok(new MessageResponse("Logged out successfully"));
}
```

---

## Error Handling

### Frontend Error Handling

**File:** `web/src/services/apiClient.js`

```javascript
// Interceptor handles 401 (Unauthorized) responses
apiClient.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            // Token expired or invalid
            localStorage.removeItem('token');
            localStorage.removeItem('user');
            window.location.href = '/login';  // Force logout
        }
        return Promise.reject(error);
    }
);
```

### Backend Error Handling

**File:** `mini-app/src/main/java/com/example/mini_app/controller/AuthController.java`

```java
@PostMapping("/register")
public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
    try {
        MessageResponse response = authService.register(request);
        return ResponseEntity.ok(response);  // 200 OK
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)  // 400 Bad Request
                .body(new MessageResponse(e.getMessage()));
    }
}
```

---

## CORS Configuration

### Why CORS is Needed

Frontend runs on: `http://localhost:5173`  
Backend runs on: `http://localhost:8080`

**Different ports = Different origins = CORS needed!**

### Backend CORS Setup

**File:** `mini-app/src/main/java/com/example/mini_app/config/CorsConfig.java`

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    
    // Allow frontend origin
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
    
    // Allow all methods (GET, POST, etc.)
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    
    // Allow all headers (including Authorization)
    configuration.setAllowedHeaders(Arrays.asList("*"));
    
    // Allow cookies/auth headers
    configuration.setAllowCredentials(true);
    
    return source;
}
```

---

## Security Summary

### Password Security
- ✅ Passwords are **hashed with BCrypt** before storing
- ✅ Never store plain text passwords
- ✅ BCrypt uses salt automatically

### Token Security
- ✅ JWT tokens are **signed** with secret key
- ✅ Tokens expire after 24 hours
- ✅ Token contains user email (not password!)
- ✅ Tampering with token makes it invalid

### HTTPS (Production)
- ⚠️ Current setup uses HTTP (development only)
- 🔒 Production should use HTTPS for encryption

---

## Testing the Integration

### Test 1: Complete Registration Flow
```bash
1. Frontend: http://localhost:5173
2. Click "Sign up"
3. Fill form and submit
4. Check browser DevTools → Network tab → See API call
5. Check phpMyAdmin → auth_db → users table → New user added
6. Frontend redirects to login
```

### Test 2: Complete Login Flow
```bash
1. Frontend: http://localhost:5173/login
2. Enter credentials
3. DevTools → Network → See login API call
4. DevTools → Application → Local Storage → See token stored
5. Frontend redirects to dashboard
```

### Test 3: Protected Routes
```bash
1. Logged in → Can access /dashboard and /profile
2. Not logged in → Redirected to /login automatically
```

### Test 4: Token Expiration
```bash
1. Login successfully
2. Wait 24 hours (or manually expire token)
3. Try to access protected route
4. Should be automatically logged out
```

---

## Debugging Tips

### Frontend Debugging
```javascript
// Check if token exists
console.log('Token:', localStorage.getItem('token'));

// Check API calls
// DevTools → Network tab → Filter: XHR → See all API requests
```

### Backend Debugging
```java
// Add logging in service
@Service
public class AuthService {
    public AuthResponse login(LoginRequest request) {
        System.out.println("Login attempt for: " + request.getEmail());
        // ...
    }
}

// Check IntelliJ console for logs
```

### Database Debugging
```sql
-- Check if user was created
SELECT * FROM users;

-- Check password hash
SELECT email, password FROM users WHERE email = 'john@example.com';
```

---

## File Mapping Reference

| Frontend File | Backend File | Purpose |
|--------------|--------------|---------|
| `authService.js` | `AuthController.java` | API calls |
| `AuthContext.jsx` | `AuthService.java` | Business logic |
| `apiClient.js` | `JwtAuthenticationFilter.java` | Token handling |
| `Login.jsx` | `/api/auth/login` | Login UI/endpoint |
| `Register.jsx` | `/api/auth/register` | Register UI/endpoint |
| `Dashboard.jsx` | `/api/auth/profile` | Protected route |

---

**🎉 Your frontend and backend are now fully integrated!**
