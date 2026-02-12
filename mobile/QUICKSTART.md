# Mobile App - Quick Start Guide

## 🚀 5-Minute Setup

### Step 1: Open Project
```
1. Open Android Studio
2. File -> Open
3. Navigate to: IT342_G4_Camoro_Lab1/mobile
4. Click OK
```

### Step 2: Wait for Gradle Sync
- Android Studio will automatically sync dependencies
- This may take 2-5 minutes on first launch

### Step 3: Configure Backend URL

**For Android Emulator (Default - No Changes Needed):**
- Already configured to use: `http://10.0.2.2:8080/api/`

**For Physical Device:**
1. Find your computer's IP address:
   ```powershell
   ipconfig
   ```
   Look for "IPv4 Address" (e.g., 192.168.1.100)

2. Open: `app/src/main/java/com/example/miniapp_mobile/data/api/RetrofitClient.kt`

3. Change this line:
   ```kotlin
   private const val BASE_URL = "http://10.0.2.2:8080/api/"
   ```
   To:
   ```kotlin
   private const val BASE_URL = "http://YOUR_IP_HERE:8080/api/"
   ```
   Example: `http://192.168.1.100:8080/api/`

### Step 4: Start Backend Server
Make sure your backend is running on port 8080:
```powershell
cd backend/mini-app
./mvnw spring-boot:run
```

### Step 5: Run the App
1. Connect Android device OR start emulator
2. Click the green "Run" button in Android Studio
3. Wait for installation
4. App will launch automatically

## ✅ Test Registration

1. Fill in the form:
   - First Name: John
   - Last Name: Doe
   - Email: john.doe@test.com
   - Password: password123
   - Confirm Password: password123

2. Click "Create account"

3. You should see: "User registered successfully"

## 🐛 Common Issues

### "Unable to connect to backend"
- ✅ Verify backend is running: `http://localhost:8080`
- ✅ Check firewall settings
- ✅ For physical device: Use correct IP address

### "Cleartext HTTP traffic not permitted"
- ✅ Already fixed in AndroidManifest.xml
- ✅ If issue persists, clean and rebuild project

### Build errors
- ✅ File -> Invalidate Caches / Restart
- ✅ Build -> Clean Project
- ✅ Build -> Rebuild Project

## 📱 Emulator vs Physical Device

| Feature | Emulator | Physical Device |
|---------|----------|-----------------|
| Setup | Easy | Requires IP config |
| Speed | Slower | Faster |
| Backend URL | `10.0.2.2:8080` | `YOUR_IP:8080` |
| Best for | Quick testing | Real-world testing |

## 📂 Key Files

```
mobile/
├── app/src/main/
│   ├── java/com/example/miniapp_mobile/
│   │   ├── data/
│   │   │   ├── api/RetrofitClient.kt          ← Configure backend URL here
│   │   │   └── repository/AuthRepository.kt
│   │   └── ui/auth/
│   │       ├── RegisterActivity.kt
│   │       └── RegisterViewModel.kt
│   ├── res/
│   │   ├── layout/activity_register.xml       ← UI layout
│   │   ├── values/colors.xml                  ← Color palette
│   │   └── drawable/                          ← Backgrounds
│   └── AndroidManifest.xml                    ← Permissions
└── build.gradle.kts                           ← Dependencies
```

## 🎨 UI Preview

The app features:
- Dark gradient background (navy blue)
- Material Design 3 components
- Blue accent colors
- Smooth animations
- Professional form layout
- Error handling
- Loading states

## 🔗 Integration

The mobile app connects to the same backend as the web app:
- **Web**: `http://localhost:8080/api/`
- **Mobile Emulator**: `http://10.0.2.2:8080/api/`
- **Mobile Device**: `http://YOUR_IP:8080/api/`

Both use the same endpoints:
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login (to be implemented)

## 📖 Need More Help?

- **Full Documentation**: See `README.md`
- **Configuration Guide**: See `MOBILE_CONFIG.md`
- **Backend Setup**: See `../backend/mini-app/SETUP_GUIDE.md`

---

**You're all set! 🎉**

Run the app and start testing the registration feature!
