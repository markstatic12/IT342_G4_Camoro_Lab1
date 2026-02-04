"# 🚀 User Authentication System - Full Stack Application

Complete authentication system with **React Frontend** + **Spring Boot Backend** + **MySQL Database**

---

## 📋 Project Overview

Full-stack web application with user registration and authentication capabilities for IT342 Lab 1.

### Features
- ✅ User Registration with validation
- ✅ User Login with JWT authentication
- ✅ Protected Dashboard and Profile pages
- ✅ Secure password hashing (BCrypt)
- ✅ JWT token-based authentication
- ✅ CORS enabled for frontend-backend communication
- ✅ Responsive UI design

---

## 🛠️ Technology Stack

### Frontend (React)
- React 19 + React Router v7 + Axios + Vite

### Backend (Spring Boot)
- Spring Boot 4.0.2 + Spring Security + JWT + JPA + MySQL

### Database
- MySQL 8.0 (XAMPP)

---

## 🚀 Quick Start

### 1️⃣ Setup Database (XAMPP)
1. Start XAMPP → Start MySQL
2. Open phpMyAdmin → Create database using `backend/mini-app/database/schema.sql`

### 2️⃣ Setup Backend (IntelliJ)
1. Open `backend/mini-app` in IntelliJ
2. Install Lombok plugin & enable annotation processing
3. Run MiniAppApplication.java
4. Backend: http://localhost:8080

📖 **Detailed guide:** [backend/mini-app/SETUP_GUIDE.md](backend/mini-app/SETUP_GUIDE.md)

### 3️⃣ Setup Frontend (VS Code)
1. Open `web` folder in VS Code
2. Run: `npm install` then `npm run dev`
3. Frontend: http://localhost:5173

📖 **Detailed guide:** [web/QUICKSTART.md](web/QUICKSTART.md)

---

## 📚 Documentation

- **[INTEGRATION_GUIDE.md](INTEGRATION_GUIDE.md)** - How frontend & backend work together
- **[backend/mini-app/SETUP_GUIDE.md](backend/mini-app/SETUP_GUIDE.md)** - Complete backend setup
- **[web/QUICKSTART.md](web/QUICKSTART.md)** - Frontend quick start
- **[web/ARCHITECTURE.md](web/ARCHITECTURE.md)** - Frontend architecture
- **[web/BACKEND_CONTRACT.md](web/BACKEND_CONTRACT.md)** - API specifications

---

## 🔌 API Endpoints

- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user (returns JWT)
- `GET /api/auth/profile` - Get user profile (protected)
- `POST /api/auth/logout` - Logout user

---

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| Database connection error | Start MySQL in XAMPP |
| Port 8080 in use | Change `server.port` in application.properties |
| CORS errors | Check CorsConfig.java allows http://localhost:5173 |
| Lombok errors | Install plugin & enable annotation processing |

---

## ✅ Pre-Test Checklist

- [ ] XAMPP MySQL running
- [ ] Database `auth_db` created
- [ ] Backend running (http://localhost:8080)
- [ ] Frontend running (http://localhost:5173)
- [ ] No console errors

---

**Course:** IT342 | **Group:** G4 Camoro | **Lab:** 1

🎉 **All set! Visit http://localhost:5173 to start!**
" 
