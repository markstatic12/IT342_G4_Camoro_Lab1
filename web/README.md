# Frontend Web Application

This is the React frontend application for user authentication and management.

## Folder Structure

```
web/
├── public/                 # Static assets
├── src/
│   ├── components/        # Reusable UI components
│   │   └── ProtectedRoute.jsx
│   ├── context/          # React context providers
│   │   └── AuthContext.jsx
│   ├── pages/            # Page components
│   │   ├── Login.jsx
│   │   ├── Register.jsx
│   │   ├── Dashboard.jsx
│   │   └── Profile.jsx
│   ├── services/         # API service layer
│   │   ├── apiClient.js
│   │   └── authService.js
│   ├── styles/           # CSS stylesheets
│   │   ├── Auth.css
│   │   ├── Dashboard.css
│   │   └── Profile.css
│   ├── assets/           # Images, fonts, etc.
│   ├── App.jsx           # Main app component
│   ├── App.css           # Global styles
│   ├── main.jsx          # Entry point
│   └── index.css         # Base styles
├── .env.local            # Environment variables (local)
├── .env.example          # Environment variables template
├── package.json
└── vite.config.js
```

## Features

- **User Registration** - New users can create an account
- **User Login** - Existing users can authenticate
- **Dashboard** - Authenticated users see their dashboard
- **Profile** - Users can view their profile information
- **Protected Routes** - Routes that require authentication
- **Logout** - Users can securely log out

## Prerequisites

- Node.js (v18 or higher)
- npm or yarn
- Backend API running (default: http://localhost:8080/api)

## Installation

1. Install dependencies:
```bash
npm install
```

2. Configure environment variables:
```bash
# Copy the example file
cp .env.example .env.local

# Update .env.local with your backend API URL
VITE_API_URL=http://localhost:8080/api
```

## Development

Run the development server:
```bash
npm run dev
```

The application will be available at `http://localhost:5173`

## Build

Create a production build:
```bash
npm run build
```

Preview the production build:
```bash
npm run preview
```

## API Integration

The frontend communicates with the backend through the following endpoints:

- `POST /auth/register` - Register new user
- `POST /auth/login` - Authenticate user
- `POST /auth/logout` - Logout user
- `GET /auth/profile` - Get user profile

All API calls are configured in `src/services/apiClient.js` and `src/services/authService.js`.

## Authentication Flow

1. **Registration**: User fills form → validates data → calls backend → redirects to login
2. **Login**: User enters credentials → backend authenticates → receives token → stores token → redirects to dashboard
3. **Protected Access**: App checks token → validates → allows access or redirects to login
4. **Logout**: User clicks logout → token removed → redirects to login

## Technologies Used

- **React 19** - UI library
- **React Router v7** - Client-side routing
- **Axios** - HTTP client for API calls
- **Vite** - Build tool and dev server
- **CSS3** - Styling with modern gradients and animations

## Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run lint` - Run ESLint

## Notes for Backend Integration

Make sure your backend:
1. Is running on the URL specified in `.env.local`
2. Accepts CORS requests from your frontend origin
3. Returns JWT tokens on successful login
4. Accepts `Bearer` token in Authorization header
5. Has the following response format:
   - Login: `{ token: string, user: object }`
   - Register: `{ message: string }`
   - Profile: `{ user: object }`

