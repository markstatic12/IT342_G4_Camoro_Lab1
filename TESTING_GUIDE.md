# ✅ Complete Testing & Verification Guide

## Step-by-Step Testing to Ensure Backend & Frontend Work Together

---

## 🚦 PHASE 1: Individual Component Checks

### ✅ Step 1: Verify XAMPP MySQL

**What to do:**
1. Open XAMPP Control Panel
2. Check MySQL status

**Expected Result:**
- MySQL row is **GREEN**
- Status shows: **Running**
- Port shows: **3306**

**If NOT working:**
- Click "Start" button next to MySQL
- Wait 5-10 seconds
- Should turn green

---

### ✅ Step 2: Verify Database Exists

**What to do:**
1. Open browser: http://localhost/phpmyadmin
2. Look at left sidebar

**Expected Result:**
- You see **"auth_db"** in the database list
- Click on "auth_db"
- You see **"users"** table

**If NOT working:**
- Follow [DATABASE_SETUP.md](DATABASE_SETUP.md)
- Run the schema.sql in phpMyAdmin SQL tab

---

### ✅ Step 3: Verify Database Structure

**What to do:**
1. In phpMyAdmin, click **"auth_db"** database
2. Click **"users"** table
3. Click **"Structure"** tab

**Expected Result:**
You should see exactly these 8 columns:

| # | Column Name | Type | Attributes |
|---|-------------|------|------------|
| 1 | user_id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| 2 | first_name | VARCHAR(255) | NOT NULL |
| 3 | last_name | VARCHAR(255) | NOT NULL |
| 4 | email | VARCHAR(255) | NOT NULL, UNIQUE |
| 5 | password | VARCHAR(255) | NOT NULL |
| 6 | status | VARCHAR(50) | NOT NULL, DEFAULT 'active' |
| 7 | created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
| 8 | updated_at | TIMESTAMP | ON UPDATE CURRENT_TIMESTAMP |

**✅ If you see all 8 columns with correct types → Database is correct!**

---

### ✅ Step 4: Verify Backend Configuration

**What to do:**
Open: `backend/mini-app/src/main/resources/application.properties`

**Check these settings:**
```properties
✅ server.port=8080
✅ spring.datasource.url=jdbc:mysql://localhost:3306/auth_db
✅ spring.datasource.username=root
✅ spring.datasource.password=
✅ spring.jpa.hibernate.ddl-auto=update
```

**Expected Result:**
- Port: 8080
- Database: auth_db
- Username: root
- Password: empty (no value after =)

---

### ✅ Step 5: Start Backend (IntelliJ)

**What to do:**
1. Open IntelliJ
2. Open project: `backend/mini-app`
3. Find: `src/main/java/com/example/mini_app/MiniAppApplication.java`
4. Right-click → **Run 'MiniAppApplication'**
5. Watch the console output

**Expected Result - You should see:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

Started MiniAppApplication in X.XXX seconds
Tomcat started on port 8080
```

**✅ Key things to look for:**
- ✅ No red error messages
- ✅ "Started MiniAppApplication"
- ✅ "Tomcat started on port 8080"
- ✅ No database connection errors

**❌ Common Errors:**

**Error: "Cannot create PoolableConnectionFactory"**
- **Cause:** MySQL not running or wrong credentials
- **Fix:** Start MySQL in XAMPP, check application.properties

**Error: "Port 8080 already in use"**
- **Cause:** Another app using port 8080
- **Fix:** Change to `server.port=8081` in application.properties

**Error: "Table 'auth_db.users' doesn't exist"**
- **Cause:** Database or table not created
- **Fix:** Run schema.sql in phpMyAdmin

---

### ✅ Step 6: Test Backend API Directly

**What to do:**
Open browser and visit: http://localhost:8080/api/auth/register

**Expected Result:**
You'll see a JSON error (this is GOOD!):
```json
{
  "message": "Required request body is missing"
}
```

**✅ This means:**
- Backend is running ✓
- API endpoint exists ✓
- It's waiting for registration data ✓

**❌ If you see:**
- "This site can't be reached" → Backend not running
- "Connection refused" → Wrong port or backend crashed

---

### ✅ Step 7: Verify Frontend Configuration

**What to do:**
Open: `web/.env.local`

**Check this setting:**
```env
✅ VITE_API_URL=http://localhost:8080/api
```

**Expected Result:**
- URL points to: http://localhost:8080/api
- Port matches backend (8080)
- Path ends with /api

---

### ✅ Step 8: Start Frontend (VS Code)

**What to do:**
1. Open VS Code
2. Open folder: `web`
3. Open Terminal (Ctrl + `)
4. Run: `npm run dev`
5. Watch the terminal output

**Expected Result:**
```
  VITE v7.2.4  ready in XXX ms

  ➜  Local:   http://localhost:5173/
  ➜  Network: use --host to expose
  ➜  press h + enter to show help
```

**✅ Key things to look for:**
- ✅ No errors
- ✅ Shows "Local: http://localhost:5173/"
- ✅ Port is 5173

**❌ Common Errors:**

**Error: "Cannot find package"**
- **Fix:** Run `npm install` first

**Error: "Port 5173 already in use"**
- **Fix:** Stop other Vite instances or use different port

---

### ✅ Step 9: Access Frontend in Browser

**What to do:**
Open browser: http://localhost:5173

**Expected Result:**
You should see:
- ✅ Login page with email and password fields
- ✅ "Welcome Back" heading
- ✅ "Sign up" link at bottom
- ✅ Beautiful purple gradient design
- ✅ No error messages in browser console

**Check Browser Console (F12):**
- Press F12 to open Developer Tools
- Click "Console" tab
- Should be mostly empty (no red errors)

**❌ If you see errors:**
- "Failed to fetch" → Backend not running
- "CORS error" → Backend CORS misconfigured
- "Network error" → Wrong backend URL in .env.local

---

## 🧪 PHASE 2: Integration Tests

### 🧪 Test 1: User Registration (Full Flow)

**Step 1: Open Registration Page**
1. Go to: http://localhost:5173
2. Click **"Sign up"** link at bottom
3. You should see registration form

**Step 2: Fill Registration Form**
Fill in the form:
- First Name: `Test`
- Last Name: `User`
- Email: `testuser@example.com`
- Password: `password123`
- Confirm Password: `password123`

**Step 3: Submit Form**
1. Click **"Create Account"** button
2. Button should change to "Creating account..."

**Expected Result:**
- ✅ Success alert: "Registration successful! Please login."
- ✅ Automatically redirects to login page
- ✅ No errors in browser console

**Step 4: Verify in Database**
1. Open phpMyAdmin: http://localhost/phpmyadmin
2. Click **"auth_db"** database
3. Click **"users"** table
4. Click **"Browse"** tab

**Expected in Database:**
```
user_id: 1
first_name: Test
last_name: User
email: testuser@example.com
password: $2a$10$... (BCrypt hashed - NOT "password123")
status: active
created_at: 2026-02-04 XX:XX:XX
updated_at: 2026-02-04 XX:XX:XX
```

**✅ SUCCESS CRITERIA:**
- User appears in database
- Password is hashed (starts with $2a$10$)
- All fields filled correctly

**Step 5: Check Backend Logs (IntelliJ Console)**
You should see SQL logs:
```sql
Hibernate: insert into users (created_at, email, first_name, last_name, password, status, updated_at) 
values (?, ?, ?, ?, ?, ?, ?)
```

**✅ Test 1 PASSED if:**
- ✅ Form submits without errors
- ✅ Redirects to login
- ✅ User appears in database
- ✅ Password is hashed
- ✅ Backend logs show INSERT query

---

### 🧪 Test 2: User Login (Full Flow)

**Step 1: Go to Login Page**
1. Navigate to: http://localhost:5173/login
2. You should see login form

**Step 2: Enter Credentials**
Use the user you just created:
- Email: `testuser@example.com`
- Password: `password123`

**Step 3: Click "Sign In"**
1. Button changes to "Signing in..."
2. Wait for response

**Expected Result:**
- ✅ Successfully logs in
- ✅ Redirects to: http://localhost:5173/dashboard
- ✅ Dashboard shows: "Welcome back, Test!"
- ✅ No errors

**Step 4: Verify Token Storage**
1. Press F12 (Developer Tools)
2. Go to **Application** tab (Chrome) or **Storage** tab (Firefox)
3. Expand **Local Storage**
4. Click on **http://localhost:5173**

**Expected in Local Storage:**
```
token: eyJhbGciOiJIUzUxMiJ9... (long JWT token)
user: {"id":1,"firstName":"Test","lastName":"User",...}
```

**Step 5: Check Backend Logs**
You should see:
```sql
Hibernate: select ... from users where email=?
```

**✅ Test 2 PASSED if:**
- ✅ Login succeeds
- ✅ Redirects to dashboard
- ✅ Token stored in localStorage
- ✅ User data stored in localStorage
- ✅ Dashboard shows correct user name

---

### 🧪 Test 3: Protected Route Access

**Step 1: View Dashboard**
You should already be on: http://localhost:5173/dashboard

**Expected Result:**
- ✅ Shows "Welcome back, Test!"
- ✅ Shows user info card with:
  - Name: Test User
  - Email: testuser@example.com
  - Status: Active
- ✅ Has "View Profile" and "Logout" buttons

**Step 2: View Profile**
1. Click **"View Profile"** button
2. Should navigate to: http://localhost:5173/profile

**Expected Result:**
- ✅ Shows profile page
- ✅ Avatar circle with initials "TU"
- ✅ Full name: Test User
- ✅ Email: testuser@example.com
- ✅ Created date
- ✅ Updated date

**Step 3: Check Network Request**
1. Open Developer Tools (F12)
2. Go to **Network** tab
3. Refresh the page
4. Look for request to: `profile`

**Expected in Request Headers:**
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

**✅ Token is automatically added to request!**

**Step 4: Check Backend Logs**
You should see:
```
GET /api/auth/profile
Hibernate: select ... from users where email=?
```

**✅ Test 3 PASSED if:**
- ✅ Dashboard loads correctly
- ✅ Profile loads correctly
- ✅ Authorization header sent with token
- ✅ Backend validates token
- ✅ User data displayed correctly

---

### 🧪 Test 4: Logout Flow

**Step 1: Click Logout**
From dashboard or profile, click **"Logout"** button

**Expected Result:**
- ✅ Immediately redirects to login page
- ✅ Can no longer access /dashboard or /profile

**Step 2: Verify Token Removed**
1. Open Developer Tools (F12)
2. Application → Local Storage → http://localhost:5173
3. Should be empty (no token, no user)

**Step 3: Try Accessing Protected Route**
Manually go to: http://localhost:5173/dashboard

**Expected Result:**
- ✅ Immediately redirects back to login
- ✅ Cannot access dashboard without token

**✅ Test 4 PASSED if:**
- ✅ Logout succeeds
- ✅ Token removed from localStorage
- ✅ Protected routes are blocked
- ✅ Redirects to login

---

### 🧪 Test 5: Validation Tests

**Test 5A: Email Already Exists**
1. Go to registration page
2. Try registering with `testuser@example.com` again
3. **Expected:** Error: "Email already exists"

**Test 5B: Password Mismatch**
1. Go to registration page
2. Password: `password123`
3. Confirm: `password456`
4. **Expected:** Error: "Passwords do not match"

**Test 5C: Invalid Email**
1. Go to registration page
2. Email: `notemail`
3. **Expected:** Error: "Please enter a valid email address"

**Test 5D: Short Password**
1. Go to registration page
2. Password: `123`
3. **Expected:** Error: "Password must be at least 6 characters"

**Test 5E: Invalid Login**
1. Go to login page
2. Email: `wrong@example.com`
3. Password: `wrongpassword`
4. **Expected:** Error: "Invalid email or password"

**✅ Test 5 PASSED if:**
- ✅ All validation errors work correctly
- ✅ Cannot register duplicate email
- ✅ Cannot login with wrong credentials

---

## 📊 PHASE 3: Database Verification

### ✅ Verify Data Integrity

**Check 1: User Count**
In phpMyAdmin, run:
```sql
SELECT COUNT(*) as total_users FROM users;
```
**Expected:** Should match number of registrations

**Check 2: Password Hashing**
```sql
SELECT email, password FROM users;
```
**Expected:** 
- All passwords start with `$2a$10$` or `$2a$12$`
- NO plain text passwords visible

**Check 3: Timestamps**
```sql
SELECT email, created_at, updated_at FROM users;
```
**Expected:**
- created_at has value
- updated_at has value
- Both are recent timestamps

**Check 4: Email Uniqueness**
Try to insert duplicate:
```sql
INSERT INTO users (first_name, last_name, email, password, status)
VALUES ('Another', 'User', 'testuser@example.com', 'test', 'active');
```
**Expected:** Error - "Duplicate entry for key 'email'"

---

## 🔧 PHASE 4: Troubleshooting Matrix

### Issue → Diagnosis → Solution

| Symptom | Diagnosis | Solution |
|---------|-----------|----------|
| "Connection refused" | Backend not running | Start backend in IntelliJ |
| "CORS error" | CORS misconfigured | Check CorsConfig.java has localhost:5173 |
| "Cannot connect to database" | MySQL not running | Start MySQL in XAMPP |
| "Table doesn't exist" | Database not created | Run schema.sql in phpMyAdmin |
| "Invalid credentials" | Wrong password or user doesn't exist | Check database, re-register user |
| "Token expired" | JWT expired (24h+) | Login again to get new token |
| "Port already in use" | Another app on same port | Change port or stop other app |
| Blank page | Frontend not built | Run `npm run dev` |
| 404 error on API | Wrong URL or backend route | Check .env.local and backend routes |
| Registration succeeds but no user | Database permissions | Check MySQL user has INSERT rights |

---

## ✅ Final System Health Check

Run this checklist:

### Infrastructure
- [ ] XAMPP MySQL: **RUNNING** (green)
- [ ] Database `auth_db`: **EXISTS**
- [ ] Table `users`: **EXISTS** with 8 columns
- [ ] phpMyAdmin: **ACCESSIBLE** at http://localhost/phpmyadmin

### Backend
- [ ] IntelliJ project: **OPENED**
- [ ] MiniAppApplication: **RUNNING**
- [ ] Console shows: **"Started MiniAppApplication"**
- [ ] No red errors in console
- [ ] Can access: http://localhost:8080/api/auth/register

### Frontend
- [ ] VS Code project: **OPENED**
- [ ] npm run dev: **RUNNING**
- [ ] Terminal shows: **"Local: http://localhost:5173"**
- [ ] Can access: http://localhost:5173
- [ ] No errors in browser console

### Integration
- [ ] Can register new user
- [ ] User appears in database
- [ ] Password is hashed
- [ ] Can login with credentials
- [ ] Token stored in localStorage
- [ ] Dashboard displays correctly
- [ ] Profile displays correctly
- [ ] Logout works
- [ ] Cannot access protected routes when logged out

---

## 🎯 Success Criteria Summary

**✅ YOUR SYSTEM IS WORKING PERFECTLY IF:**

1. ✅ Can register a new user → User appears in database with hashed password
2. ✅ Can login → Redirects to dashboard with user info
3. ✅ Token stored in localStorage
4. ✅ Dashboard and Profile pages load correctly
5. ✅ Logout works → Token removed, cannot access protected routes
6. ✅ Validation works for all error cases
7. ✅ No errors in any console (Backend, Frontend, Browser)
8. ✅ Database structure matches specification

---

## 📝 Test Results Tracker

Use this to track your testing:

```
DATE: _______________

[ ] Test 1: Registration Flow - PASSED / FAILED
    Notes: _________________________________

[ ] Test 2: Login Flow - PASSED / FAILED
    Notes: _________________________________

[ ] Test 3: Protected Routes - PASSED / FAILED
    Notes: _________________________________

[ ] Test 4: Logout Flow - PASSED / FAILED
    Notes: _________________________________

[ ] Test 5: Validation - PASSED / FAILED
    Notes: _________________________________

DATABASE RECORDS CREATED: _______
ERRORS ENCOUNTERED: _____________
OVERALL STATUS: PASS / FAIL
```

---

**🎉 If all tests pass, your Full Stack Authentication System is fully operational!**
