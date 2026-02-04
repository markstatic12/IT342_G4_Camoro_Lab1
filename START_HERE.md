# ⚡ Quick Reference - Start Here!

## 🎯 Your Mission
Get the full-stack authentication system running with React frontend + Spring Boot backend + MySQL database.

---

## 📝 Step-by-Step Instructions

### STEP 1: Start Database (2 minutes)
```
1. Open XAMPP Control Panel
2. Click "Start" next to MySQL
3. Click "Admin" to open phpMyAdmin
4. Click "SQL" tab
5. Open file: backend/mini-app/database/schema.sql
6. Copy ALL the SQL code
7. Paste in phpMyAdmin SQL tab
8. Click "Go"
9. You should see "auth_db" database in left sidebar
```

### STEP 2: Start Backend (5 minutes)
```
1. Open IntelliJ IDEA
2. File → Open → Select folder: backend/mini-app
3. Wait for Maven to download dependencies (watch bottom right)
4. Install Lombok:
   - File → Settings → Plugins
   - Search "Lombok" → Install → Restart
5. Enable annotation processing:
   - File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors
   - Check "Enable annotation processing" → OK
6. Find file: src/main/java/com/example/mini_app/MiniAppApplication.java
7. Right-click → Run 'MiniAppApplication'
8. Wait for console to show: "Started MiniAppApplication"
9. Backend is ready at: http://localhost:8080
```

### STEP 3: Start Frontend (2 minutes)
```
1. Open VS Code
2. File → Open Folder → Select folder: web
3. Terminal → New Terminal (Ctrl + `)
4. Type: npm install
5. Press Enter, wait for install to complete
6. Type: npm run dev
7. Press Enter
8. Frontend is ready at: http://localhost:5173
```

### STEP 4: Test Everything (2 minutes)
```
1. Open browser: http://localhost:5173
2. Click "Sign up"
3. Fill in registration form:
   - First Name: John
   - Last Name: Doe
   - Email: john@test.com
   - Password: password123
   - Confirm Password: password123
4. Click "Create Account"
5. Should redirect to login page
6. Login with the same email/password
7. Should see Dashboard
8. Click "View Profile" - should work
9. Click "Logout" - should return to login

✅ If all steps work, you're done!
```

---

## 🔍 Verify Each Step

### ✓ Database Running?
```
Visit: http://localhost/phpmyadmin
Should see: phpMyAdmin interface
Database: auth_db should exist in left sidebar
```

### ✓ Backend Running?
```
Check IntelliJ console for: "Started MiniAppApplication"
Visit: http://localhost:8080/api/auth/login
Should see: JSON error (this is OK - just means server is responding)
```

### ✓ Frontend Running?
```
Check VS Code terminal for: "Local: http://localhost:5173"
Visit: http://localhost:5173
Should see: Login page with purple gradient
```

---

## ❌ Common Problems & Solutions

### Problem: "Cannot connect to database"
```
Solution:
1. Open XAMPP Control Panel
2. Make sure MySQL shows green (running)
3. If not, click "Start"
4. Restart backend in IntelliJ
```

### Problem: "Port 8080 already in use"
```
Solution:
1. Open: backend/mini-app/src/main/resources/application.properties
2. Change: server.port=8080
3. To: server.port=8081
4. Open: web/.env.local
5. Change: VITE_API_URL=http://localhost:8080/api
6. To: VITE_API_URL=http://localhost:8081/api
7. Restart both backend and frontend
```

### Problem: "Lombok not working in IntelliJ"
```
Solution:
1. File → Settings → Plugins
2. Install "Lombok" plugin
3. Restart IntelliJ
4. File → Settings → Build → Compiler → Annotation Processors
5. Check "Enable annotation processing"
6. Click OK
7. Build → Rebuild Project
```

### Problem: "npm install fails"
```
Solution:
1. Make sure Node.js is installed: node --version
2. Delete node_modules folder in web/
3. Delete package-lock.json in web/
4. Run: npm install again
```

### Problem: "CORS error in browser"
```
Solution:
1. Check backend is running on port 8080
2. Check frontend is running on port 5173
3. Both must be running at the same time
4. If you changed ports, update CorsConfig.java
```

---

## 📋 Checklist Before Asking for Help

Before seeking help, verify:

- [ ] XAMPP Control Panel shows MySQL as "Running" (green)
- [ ] phpMyAdmin shows "auth_db" database exists
- [ ] IntelliJ console shows "Started MiniAppApplication"
- [ ] VS Code terminal shows "Local: http://localhost:5173"
- [ ] Browser DevTools (F12) → Console shows no red errors
- [ ] Both backend and frontend are running SIMULTANEOUSLY

---

## 🆘 Still Need Help?

### Check These Files:

1. **Detailed Backend Setup:**
   - File: `backend/mini-app/SETUP_GUIDE.md`
   - Has complete step-by-step with screenshots

2. **Frontend Setup:**
   - File: `web/QUICKSTART.md`
   - Has troubleshooting tips

3. **How They Work Together:**
   - File: `INTEGRATION_GUIDE.md`
   - Explains the complete flow

### Check Console Logs:

**Backend (IntelliJ):**
- Look at the Run console (bottom)
- Check for red error messages
- Look for "Started" message

**Frontend (VS Code):**
- Look at terminal output
- Check for red errors
- npm errors will show clearly

**Browser:**
- Press F12
- Go to Console tab
- Look for red errors
- Go to Network tab
- Look at failed requests

---

## 🎯 Success Criteria

Your system is working correctly when:

✅ Can register new user  
✅ New user appears in phpMyAdmin → auth_db → users table  
✅ Can login with registered credentials  
✅ Dashboard shows user's name  
✅ Can view profile  
✅ Can logout and login again  

---

## 🚀 What Each Component Does

```
┌─────────────────────┐
│   React Frontend    │  ← What user sees (http://localhost:5173)
│   Pretty UI forms   │  ← Sends data to backend
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│  Spring Boot API    │  ← Processes requests (http://localhost:8080)
│  Business logic     │  ← Validates, hashes passwords, generates tokens
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│   MySQL Database    │  ← Stores data (XAMPP)
│   Users table       │  ← Saves registered users
└─────────────────────┘
```

---

## ⏱️ Time Estimate

- Database setup: 2 minutes
- Backend setup: 5 minutes (first time)
- Frontend setup: 2 minutes (first time)
- Testing: 2 minutes
- **Total: ~11 minutes**

After first setup, starting everything takes only ~1 minute!

---

## 💡 Pro Tips

1. **Always start in this order:**
   - XAMPP MySQL first
   - Backend second
   - Frontend last

2. **Keep all three running together:**
   - Don't close XAMPP
   - Don't stop backend
   - Don't stop frontend

3. **Check for errors immediately:**
   - If backend shows error, fix before starting frontend
   - If frontend shows error, check backend is running

4. **Use browser DevTools:**
   - Network tab shows API calls
   - Console tab shows frontend errors
   - Great for debugging!

---

**Ready? Let's go! Start with STEP 1 above! 🚀**
