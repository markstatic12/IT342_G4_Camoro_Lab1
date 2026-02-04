# 🎯 QUICK START - 10 Minute Setup

## Complete Setup in 4 Simple Steps

---

## ✅ STEP 1: Database (2 min)

**Start MySQL:**
```
XAMPP Control Panel → MySQL → Start (wait for GREEN)
```

**Create Database:**
```
1. Open: http://localhost/phpmyadmin
2. Click "SQL" tab
3. Paste this code:
```
```sql
CREATE DATABASE IF NOT EXISTS auth_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE auth_db;
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email)
) ENGINE=InnoDB;
```
```
4. Click "Go"
✅ Left sidebar shows "auth_db" database
```

---

## ✅ STEP 2: Backend (3 min)

**In IntelliJ:**
```
1. Open: backend/mini-app
2. Install Lombok plugin (Settings → Plugins → "Lombok")
3. Enable annotation processing (Settings → Compiler → Annotation Processors)
4. Right-click: MiniAppApplication.java → Run
✅ Console shows "Started MiniAppApplication"
```

**Test:** http://localhost:8080/api/auth/register (should show JSON error - that's good!)

---

## ✅ STEP 3: Frontend (2 min)

**In VS Code:**
```
1. Open: web folder
2. Terminal: npm install
3. Terminal: npm run dev
✅ Shows "Local: http://localhost:5173"
```

---

## ✅ STEP 4: Test (3 min)

```
1. Browser: http://localhost:5173
2. Click "Sign up"
3. Register a test user
4. Login
✅ See dashboard with user info
```

**Verify in database:**
```
phpMyAdmin → auth_db → users → Browse
✅ Your user appears with hashed password
```

---

## 🎉 DONE!

**All services running:**
- ✅ MySQL (port 3306)
- ✅ Backend (port 8080)  
- ✅ Frontend (port 5173)

**What works:**
- ✅ User registration
- ✅ User login with JWT
- ✅ Protected dashboard
- ✅ User profile
- ✅ Logout

---

## 🆘 Quick Fixes

| Problem | Solution |
|---------|----------|
| Backend won't start | Start MySQL in XAMPP, install Lombok |
| Port 8080 in use | Change `server.port=8081` in application.properties |
| CORS error | Check backend is running |
| Database error | Run schema.sql in phpMyAdmin |

---

## 📚 Full Documentation

- **[DATABASE_SETUP.md](DATABASE_SETUP.md)** - Detailed database guide
- **[TESTING_GUIDE.md](TESTING_GUIDE.md)** - Complete testing
- **[INTEGRATION_GUIDE.md](INTEGRATION_GUIDE.md)** - How it works

---

**Ready to test! Visit: http://localhost:5173** 🚀
