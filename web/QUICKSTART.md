# 🚀 Quick Start Guide

## What Was Created?

A complete **React frontend** for user authentication with:
- ✅ Login page
- ✅ Registration page  
- ✅ Dashboard (protected)
- ✅ Profile page (protected)
- ✅ Professional folder structure
- ✅ Beautiful UI with gradients and animations
- ✅ Complete authentication flow

## Folder Structure

```
web/src/
├── components/        → Reusable components (ProtectedRoute)
├── context/          → Authentication state management
├── pages/            → Login, Register, Dashboard, Profile
├── services/         → API calls to backend
└── styles/           → CSS for each page
```

## Run the Application

```bash
# From the web folder
npm run dev
```

Then open: **http://localhost:5173**

## Pages You Can Visit

| Route | Page | Access |
|-------|------|--------|
| `/` | Redirects to login | Public |
| `/login` | Login form | Public |
| `/register` | Registration form | Public |
| `/dashboard` | User dashboard | Protected ⚠️ |
| `/profile` | User profile | Protected ⚠️ |

**Protected** = Requires login. Will redirect to `/login` if not authenticated.

## Configuration

Edit `.env.local` to set your backend URL:

```env
VITE_API_URL=http://localhost:8080/api
```

**Default:** `http://localhost:8080/api`

## Testing Without Backend

The app will show errors if backend is not running. To test:

1. **Option 1:** Start your backend server first
2. **Option 2:** Modify `authService.js` to use mock data temporarily

## Backend Requirements

Your backend needs these endpoints:

```
POST /api/auth/register    → Register new user
POST /api/auth/login       → Login (returns JWT token)
POST /api/auth/logout      → Logout
GET  /api/auth/profile     → Get user info
```

## Authentication Flow

1. **Register** → Fill form → Save to database → Redirect to login
2. **Login** → Enter credentials → Get JWT token → Save token → Go to dashboard
3. **Protected Pages** → Check token → Allow/Deny access
4. **Logout** → Clear token → Redirect to login

## Key Files to Know

| File | Purpose |
|------|---------|
| `App.jsx` | Main routing configuration |
| `AuthContext.jsx` | Authentication state & methods |
| `apiClient.js` | Axios config + token injection |
| `authService.js` | API calls for auth |
| `Login.jsx` | Login page component |
| `Register.jsx` | Registration page component |

## Common Commands

```bash
# Install dependencies
npm install

# Run development server
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview

# Lint code
npm run lint
```

## Troubleshooting

### "Cannot connect to backend"
- Check if backend is running
- Check `.env.local` has correct URL
- Check CORS is enabled on backend

### "Token expired" or redirects to login
- Token is invalid or expired
- Backend needs to accept the token format
- Check Authorization header is sent

### "Module not found"
- Run `npm install`
- Restart dev server

## What's Next?

### Frontend Tasks:
- [ ] Test all pages
- [ ] Customize colors/branding
- [ ] Add more features (edit profile, change password)
- [ ] Add loading spinners
- [ ] Improve error messages

### Backend Tasks (IntelliJ):
- [ ] Create AuthController
- [ ] Implement JWT token generation
- [ ] Set up User entity & repository
- [ ] Configure Spring Security
- [ ] Enable CORS
- [ ] Hash passwords (BCrypt)

## File Structure Overview

```
web/
├── src/
│   ├── components/
│   │   └── ProtectedRoute.jsx         ← Guards protected pages
│   ├── context/
│   │   └── AuthContext.jsx            ← Auth state management
│   ├── pages/
│   │   ├── Login.jsx                  ← Login UI
│   │   ├── Register.jsx               ← Registration UI
│   │   ├── Dashboard.jsx              ← Main dashboard
│   │   └── Profile.jsx                ← User profile
│   ├── services/
│   │   ├── apiClient.js               ← Axios instance
│   │   └── authService.js             ← Auth API calls
│   ├── styles/
│   │   ├── Auth.css                   ← Login/Register styles
│   │   ├── Dashboard.css              ← Dashboard styles
│   │   └── Profile.css                ← Profile styles
│   ├── App.jsx                        ← Main app + routing
│   └── main.jsx                       ← Entry point
├── .env.local                         ← Config (backend URL)
├── package.json                       ← Dependencies
└── README.md                          ← Full documentation
```

## Need Help?

- Check `README.md` for full documentation
- Check `ARCHITECTURE.md` for technical details
- Review the diagrams you provided for backend implementation

---

**Remember:** The backend folder is empty on purpose. You'll implement the Java backend in IntelliJ separately! 🎯
