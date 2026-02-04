# 🗄️ Database Setup Guide for phpMyAdmin (XAMPP)

## Complete Step-by-Step Instructions

---

## ✅ Pre-Check: Verify XAMPP is Running

### Step 1: Open XAMPP Control Panel
1. Search for "XAMPP Control Panel" in Windows
2. Open XAMPP Control Panel
3. You should see a list of modules (Apache, MySQL, FileZilla, etc.)

### Step 2: Start MySQL
1. Find the **MySQL** row in XAMPP Control Panel
2. Click the **"Start"** button next to MySQL
3. Wait for MySQL to show **green highlight** and "Running" status
4. Port should show: **3306**

**✅ Screenshot check:** MySQL row should be highlighted in green

---

## 🌐 Method 1: Using phpMyAdmin Web Interface (Recommended)

### Step 1: Open phpMyAdmin
```
Option A: Click "Admin" button next to MySQL in XAMPP Control Panel
Option B: Open browser and go to: http://localhost/phpmyadmin
```

**You should see:** phpMyAdmin homepage with databases on the left sidebar

### Step 2: Create the Database

#### Option A: Using SQL Tab (Fastest)
1. Click the **"SQL"** tab at the top of phpMyAdmin
2. You'll see a large text area for SQL queries
3. **Copy and paste this entire SQL code:**

```sql
-- Create database
CREATE DATABASE IF NOT EXISTS auth_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE auth_db;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_email (email),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

4. Click the **"Go"** button at the bottom right
5. You should see: **"Your SQL query has been executed successfully"**

**✅ Verify:**
- Left sidebar should now show **"auth_db"** database
- Click on **"auth_db"** → You should see **"users"** table

#### Option B: Using Database Tab (Manual)
1. Click **"Databases"** tab at the top
2. Under "Create database":
   - Database name: `auth_db`
   - Collation: `utf8mb4_unicode_ci`
3. Click **"Create"**
4. Click on **"auth_db"** in the left sidebar
5. Click **"SQL"** tab
6. Paste the CREATE TABLE query (from above, just the CREATE TABLE part)
7. Click **"Go"**

---

## 📊 Step 3: Verify Database Structure

### Check Database
1. In left sidebar, click on **"auth_db"**
2. You should see the **"users"** table listed

### Check Table Structure
1. Click on **"users"** table
2. Click **"Structure"** tab at the top
3. **You should see these columns:**

| Column | Type | Null | Key | Default | Extra |
|--------|------|------|-----|---------|-------|
| user_id | BIGINT | No | PRIMARY | None | AUTO_INCREMENT |
| first_name | VARCHAR(255) | No | | None | |
| last_name | VARCHAR(255) | No | | None | |
| email | VARCHAR(255) | No | UNIQUE | None | |
| password | VARCHAR(255) | No | | None | |
| status | VARCHAR(50) | No | | active | |
| created_at | TIMESTAMP | No | | CURRENT_TIMESTAMP | |
| updated_at | TIMESTAMP | No | | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |

**✅ If you see all 8 columns, you're good to go!**

---

## 👤 Step 4: Add Test User (Optional but Recommended)

### Option A: Using Application (Recommended)
**Best way:** Use the frontend registration form
1. Start backend (IntelliJ)
2. Start frontend (VS Code)
3. Go to: http://localhost:5173
4. Click "Sign up"
5. Fill the form and register
6. Check phpMyAdmin → users table → You'll see the new user!

### Option B: Manual Insert via SQL (For Testing)
1. In phpMyAdmin, select **"auth_db"** database
2. Click **"SQL"** tab
3. Paste this query:

```sql
INSERT INTO users (first_name, last_name, email, password, status) 
VALUES ('Test', 'User', 'test@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'active');
```

4. Click **"Go"**
5. This creates a test user:
   - Email: `test@example.com`
   - Password: `password123` (already BCrypt hashed)

**✅ Verify:**
1. Click **"users"** table
2. Click **"Browse"** tab
3. You should see the test user in the table

---

## 🔍 View Database Data

### View All Users
1. Select **"auth_db"** database from left sidebar
2. Click **"users"** table
3. Click **"Browse"** tab
4. You'll see all registered users

**Columns you'll see:**
- `user_id` - Auto-generated ID
- `first_name` - User's first name
- `last_name` - User's last name
- `email` - User's email (unique)
- `password` - BCrypt hashed password (will look like: $2a$10$...)
- `status` - Account status (active)
- `created_at` - When user registered
- `updated_at` - Last update time

### Search for Specific User
1. In **"users"** table → **"Search"** tab
2. Enter email in the email field
3. Click **"Go"**

### Edit User Manually (if needed)
1. In **"Browse"** tab
2. Click **"Edit"** (pencil icon) next to the user
3. Modify fields
4. Click **"Go"** to save

### Delete User
1. In **"Browse"** tab
2. Click **"Delete"** (trash icon) next to the user
3. Confirm deletion

---

## 🔐 Important Security Notes

### ⚠️ About Passwords
- **NEVER** store plain text passwords!
- Passwords in database look like: `$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG`
- This is **BCrypt hashed** - cannot be reversed
- Backend automatically hashes passwords during registration

### ⚠️ About Database Credentials
Current setup (XAMPP default):
- Username: `root`
- Password: `(empty)`

**For production:**
- Change MySQL root password in XAMPP
- Update `application.properties` with new password

---

## 🧪 Test Database Connection

### Test 1: From phpMyAdmin
1. In phpMyAdmin, select **"auth_db"**
2. Click **"SQL"** tab
3. Run: `SELECT * FROM users;`
4. Should execute without errors

### Test 2: From Backend Application
1. Start backend in IntelliJ
2. Check console for:
```
HikariPool-1 - Start completed.
Hibernate: 
    select ...
```
3. No errors = Database connected successfully!

### Test 3: Full Integration Test
```bash
1. Database: Running in XAMPP (green status)
2. Backend: Running in IntelliJ (port 8080)
3. Frontend: Running in VS Code (port 5173)
4. Browser: http://localhost:5173
5. Register a new user
6. Check phpMyAdmin → users table → New user appears!
```

---

## 🔧 Troubleshooting

### ❌ Error: "Cannot create database"
**Solution:**
- Ensure MySQL is running (green in XAMPP)
- Check if database already exists (delete old one if needed)

### ❌ Error: "Access denied for user 'root'@'localhost'"
**Solution:**
- Check username/password in `application.properties`
- Default XAMPP: username=`root`, password=`(empty)`

### ❌ Error: "Table 'users' doesn't exist"
**Solution:**
1. Go to phpMyAdmin → auth_db
2. Run the CREATE TABLE SQL again
3. Or check if table was created with different name

### ❌ Database exists but empty
**Solution:**
- Run the CREATE TABLE SQL in phpMyAdmin
- Or restart backend (it will auto-create table if `ddl-auto=update`)

### ❌ Cannot connect to phpMyAdmin
**Solution:**
- Ensure Apache is running in XAMPP (needed for phpMyAdmin)
- Try: http://localhost/phpmyadmin
- Try: http://127.0.0.1/phpmyadmin

### ❌ Port 3306 in use
**Solution:**
- Another MySQL service is running
- Stop other MySQL services
- Or change MySQL port in XAMPP config

---

## 📋 Quick Reference Commands

### Useful SQL Queries

#### View all users
```sql
SELECT * FROM users;
```

#### View specific user by email
```sql
SELECT * FROM users WHERE email = 'john@example.com';
```

#### Count total users
```sql
SELECT COUNT(*) FROM users;
```

#### Delete all users (CAREFUL!)
```sql
DELETE FROM users;
```

#### Delete specific user
```sql
DELETE FROM users WHERE email = 'john@example.com';
```

#### Update user status
```sql
UPDATE users SET status = 'inactive' WHERE email = 'john@example.com';
```

#### View recent registrations
```sql
SELECT * FROM users ORDER BY created_at DESC LIMIT 10;
```

---

## ✅ Final Verification Checklist

Before running your application:

- [ ] XAMPP is open
- [ ] MySQL is running (green status in XAMPP)
- [ ] phpMyAdmin is accessible (http://localhost/phpmyadmin)
- [ ] Database `auth_db` exists
- [ ] Table `users` exists with correct structure (8 columns)
- [ ] Can run SQL queries without errors
- [ ] Backend `application.properties` has:
  - Database: `auth_db`
  - Username: `root`
  - Password: `(empty)`
  - Port: `3306`

---

## 🎯 Next Steps After Database Setup

1. ✅ Database is ready
2. ▶️ Start backend in IntelliJ
3. ▶️ Start frontend in VS Code
4. 🧪 Test registration from http://localhost:5173
5. 👀 Check phpMyAdmin to see new users appear!

---

## 📸 Visual Guide

### What you should see in phpMyAdmin:

**After creating database:**
```
Left Sidebar:
  └─ auth_db
     └─ users (8 columns)
```

**In users table structure:**
```
user_id         BIGINT          PRIMARY KEY, AUTO_INCREMENT
first_name      VARCHAR(255)    NOT NULL
last_name       VARCHAR(255)    NOT NULL
email           VARCHAR(255)    NOT NULL, UNIQUE
password        VARCHAR(255)    NOT NULL
status          VARCHAR(50)     NOT NULL, DEFAULT 'active'
created_at      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
updated_at      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP ON UPDATE
```

**After registering a user:**
```
user_id | first_name | last_name | email              | password (hashed)    | status | created_at          | updated_at
1       | John       | Doe       | john@example.com   | $2a$10$dXJ3SW...  | active | 2026-02-04 10:00:00 | 2026-02-04 10:00:00
```

---

## 🆘 Need Help?

**If database setup fails:**
1. Restart MySQL in XAMPP
2. Restart XAMPP completely
3. Check Windows Firewall isn't blocking MySQL
4. Try running XAMPP as Administrator

**If backend cannot connect:**
1. Verify database name is exactly: `auth_db`
2. Verify username is: `root`
3. Verify password is empty in `application.properties`
4. Check MySQL is running on port 3306

---

**🎉 Once you see `auth_db` with `users` table in phpMyAdmin, you're ready to test your application!**
