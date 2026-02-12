# MiniApp Mobile - Android Application

A modern Android mobile application for user registration and authentication, built with Kotlin and Material Design 3.

## 🎨 Design System

The mobile app follows the same design system as the web application, featuring:

- **Dark Theme**: Deep navy blue backgrounds (#0F1A33, #0B1428)
- **Accent Colors**: Blue gradient (#4E6BF2, #5B75F7, #6F88FF)
- **Typography**: Clean, readable fonts with proper hierarchy
- **Consistent UX**: Matches web app's user experience patterns

## 📱 Features

### Registration Screen
- ✅ Full name input (first name and last name)
- ✅ Email validation
- ✅ Password with visibility toggle
- ✅ Confirm password matching
- ✅ Real-time form validation
- ✅ Loading states during API calls
- ✅ Error messages with clear feedback
- ✅ Success notifications
- ✅ Responsive layout with ScrollView

## 🏗️ Architecture

The app follows **MVVM (Model-View-ViewModel)** architecture pattern:

```
mobile/app/src/main/java/com/example/miniapp_mobile/
├── data/
│   ├── api/
│   │   ├── AuthApiService.kt          # Retrofit API interface
│   │   └── RetrofitClient.kt          # HTTP client configuration
│   ├── model/
│   │   ├── RegisterRequest.kt         # Registration data model
│   │   ├── AuthResponse.kt            # Authentication response model
│   │   └── MessageResponse.kt         # Generic message model
│   └── repository/
│       └── AuthRepository.kt          # Data layer abstraction
├── ui/
│   └── auth/
│       ├── RegisterActivity.kt        # Registration screen
│       └── RegisterViewModel.kt       # Business logic and state
└── MainActivity.kt                     # App entry point
```

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI**: Material Design 3, ViewBinding
- **Architecture**: MVVM
- **Networking**: Retrofit, OkHttp
- **Async**: Kotlin Coroutines
- **Lifecycle**: AndroidX Lifecycle (ViewModel, LiveData)
- **Dependency Injection**: Manual (can be upgraded to Hilt/Koin)

## 📦 Dependencies

```kotlin
// Retrofit for API calls
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

// Coroutines for async operations
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

// ViewModel and LiveData
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

// Fragment KTX
implementation("androidx.fragment:fragment-ktx:1.6.2")
```

## 🚀 Getting Started

### Prerequisites

- Android Studio (latest version recommended)
- JDK 8 or higher
- Android SDK (API 24+)
- Backend server running on port 8080

### Setup Instructions

1. **Open the project in Android Studio**
   ```
   File -> Open -> Select the mobile folder
   ```

2. **Sync Gradle**
   - Android Studio will automatically sync Gradle
   - Wait for dependencies to download

3. **Configure Backend URL**
   - Open `RetrofitClient.kt`
   - Update `BASE_URL` based on your environment:
     - Emulator: `http://10.0.2.2:8080/api/`
     - Physical device: `http://YOUR_COMPUTER_IP:8080/api/`

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press Shift+F10

### Finding Your Computer's IP

**Windows (PowerShell):**
```powershell
ipconfig
```

**Mac/Linux:**
```bash
ifconfig
```

## 📱 Screenshots & UI Components

### Registration Screen Components

1. **Brand Header**
   - App logo with bordered icon
   - App name display

2. **Registration Card**
   - Material Design 3 card with elevation
   - Dark theme background
   - Rounded corners (24dp)

3. **Form Fields**
   - First Name & Last Name (side by side)
   - Email with validation
   - Password with visibility toggle
   - Confirm Password with visibility toggle

4. **Interactive Elements**
   - Primary action button (Create account)
   - Loading indicator
   - Error message card
   - Success Snackbar
   - Login link

## 🎯 API Integration

### Registration Endpoint

**POST** `/api/auth/register`

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "password123"
}
```

**Success Response (200):**
```json
{
  "message": "User registered successfully"
}
```

**Error Response (4xx/5xx):**
```json
{
  "message": "Error description"
}
```

## 🔒 Security Considerations

### Current Setup (Development)
- Cleartext traffic enabled for local testing
- HTTP connections allowed

### Production Recommendations
1. Use HTTPS for all API endpoints
2. Disable cleartext traffic in AndroidManifest.xml
3. Implement SSL certificate pinning
4. Store tokens securely (use EncryptedSharedPreferences)
5. Add ProGuard rules for obfuscation
6. Enable minification in release builds

## 🧪 Testing

### Manual Testing Checklist

- [ ] All fields show validation errors when empty
- [ ] Email validation works correctly
- [ ] Password must be at least 6 characters
- [ ] Passwords must match
- [ ] Loading indicator shows during API call
- [ ] Success message displays after registration
- [ ] Form clears after successful registration
- [ ] Error messages display correctly
- [ ] Network errors are handled gracefully

### Testing on Emulator

1. Start your backend server
2. Launch the Android emulator
3. The app will automatically connect to `http://10.0.2.2:8080/api/`
4. Test the registration flow

### Testing on Physical Device

1. Ensure your device and computer are on the same network
2. Find your computer's IP address
3. Update `BASE_URL` in `RetrofitClient.kt`
4. Build and install the app on your device
5. Test the registration flow

## 🐛 Troubleshooting

### Connection Issues

**Problem**: Cannot connect to backend
- ✅ Verify backend is running on port 8080
- ✅ Check firewall settings
- ✅ Ensure `usesCleartextTraffic="true"` in AndroidManifest.xml
- ✅ Verify correct IP address for physical devices
- ✅ Check Logcat for network errors

**Problem**: SSL/TLS errors
- ✅ Use HTTP for local development
- ✅ Ensure cleartext traffic is enabled

### Build Issues

**Problem**: Gradle sync failed
- ✅ Check internet connection
- ✅ Invalidate caches: File -> Invalidate Caches / Restart
- ✅ Update Gradle wrapper: `./gradlew wrapper --gradle-version=8.4`

**Problem**: ViewBinding not found
- ✅ Ensure `buildFeatures { viewBinding = true }` in build.gradle.kts
- ✅ Clean and rebuild project

## 📝 Color Palette Reference

```xml
<!-- Primary Colors -->
background_dark: #0F1A33
background_darker: #0B1428
surface_dark: #1A2844

<!-- Accent Colors -->
accent_primary: #4E6BF2
accent_strong: #5B75F7
accent_light: #6F88FF

<!-- Text Colors -->
text_primary: #EEF3FF
text_secondary: #C3CBDE
text_muted: #7A8499
text_hint: #5A6579

<!-- UI Colors -->
border_subtle: #1F2D4A
border_light: #2A3A58
error: #E85D75
success: #4CAF50
```

## 🔄 Future Enhancements

### Planned Features
- [ ] Login screen
- [ ] Dashboard after authentication
- [ ] Token persistence (SharedPreferences/DataStore)
- [ ] Profile management
- [ ] Logout functionality
- [ ] Password reset flow
- [ ] Remember me functionality
- [ ] Biometric authentication
- [ ] Dark/Light theme toggle
- [ ] Multi-language support

### Technical Improvements
- [ ] Add Hilt for dependency injection
- [ ] Implement Navigation Component
- [ ] Add unit tests (JUnit, MockK)
- [ ] Add UI tests (Espresso)
- [ ] Implement offline support
- [ ] Add Crashlytics for error tracking
- [ ] Implement analytics
- [ ] Add CI/CD pipeline

## 📚 Additional Resources

- [Android Developer Guide](https://developer.android.com/)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Material Design 3](https://m3.material.io/)
- [Retrofit Documentation](https://square.github.io/retrofit/)
- [Coroutines Guide](https://kotlinlang.org/docs/coroutines-guide.html)

## 📄 License

This project is part of the IT342_G4_Camoro_Lab1 mini application.

## 👥 Contributors

Group 4 - IT342

---

**Note**: This mobile app is designed to work seamlessly with the backend API. Ensure the backend server is running before testing the mobile app.
