# 🚀 User Authentication System

A full-stack authentication system built with **React, Spring Boot, and MySQL**  
Developed for **IT342 – Lab Activity 1**

---

## 📌 Overview

This project is a secure web application that implements user registration and authentication using JWT. It demonstrates frontend-backend integration with protected routes and database persistence.

### Key Features
- User registration and login  
- JWT-based authentication  
- Protected profile and dashboard  
- Secure password hashing (BCrypt)  
- RESTful API backend  
- Responsive React UI  

---

## 🛠 Technology Stack

**Frontend:** React 19, React Router v7, Axios, Vite  
**Backend:** Spring Boot 4.0.2, Spring Security, JWT, JPA  
**Database:** MySQL 8.0 (XAMPP)

---

# ⚙️ Setup and Run Instructions

Follow these steps to run the system locally.

---

## 1️⃣ Database Setup

1. Open **XAMPP Control Panel** and start **MySQL**
2. Open **phpMyAdmin**
3. Create the database using:

backend/mini-app/database/schema.sql


---

## 2️⃣ Backend Setup

1. Open the project in **IntelliJ IDEA**:

backend/mini-app


2. Install and enable:
- Lombok Plugin  
- Annotation Processing  

3. Run:

MiniAppApplication.java


Backend will run at:  
**http://localhost:8080**

📘 Full guide: `backend/mini-app/SETUP_GUIDE.md`

---

## 3️⃣ Frontend Setup

1. Open the `web` folder in **VS Code**
2. Run the following commands:

npm install
npm run dev


Frontend will run at:  
**http://localhost:5173**

📘 Full guide: `web/QUICKSTART.md`

---

## 🔌 API Endpoints

| Method | Endpoint | Description |
|------|----------------------|----------------|
| POST | /api/auth/register | Register user |
| POST | /api/auth/login | Login user |
| GET  | /api/auth/profile | Get profile |
| POST | /api/auth/logout | Logout |

---

## 🐞 Troubleshooting

| Issue | Solution |
|------|----------|
| Cannot connect to DB | Start MySQL in XAMPP |
| Port 8080 in use | Change port in `application.properties` |
| Lombok errors | Enable annotation processing |
| CORS issues | Verify CorsConfig settings |
| Frontend errors | Run `npm install` again |

---

## ✅ Pre-Run Checklist

- [ ] MySQL running in XAMPP  
- [ ] Database created  
- [ ] Backend running on port 8080  
- [ ] Frontend running on port 5173  

---

**Course:** IT342  
**Group:** G4 Camoro  
**Lab Activity:** 1  

🎉 Open **http://localhost:5173** to start using the application!