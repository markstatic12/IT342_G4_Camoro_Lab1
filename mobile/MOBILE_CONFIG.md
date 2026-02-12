# Mobile App Configuration Guide

## Backend API Configuration

The mobile app connects to the backend API through the `RetrofitClient.kt` file located at:
`mobile/app/src/main/java/com/example/miniapp_mobile/data/api/RetrofitClient.kt`

### Current Configuration

```kotlin
private const val BASE_URL = "http://10.0.2.2:8080/api/"
```

### Environment-Specific URLs

#### For Android Emulator:
- Use: `http://10.0.2.2:8080/api/`
- This IP address maps to `localhost` on your development machine

#### For Physical Android Device:
- Use: `http://YOUR_COMPUTER_IP:8080/api/`
- Replace `YOUR_COMPUTER_IP` with your computer's actual IP address on the network
- Example: `http://192.168.1.100:8080/api/`

#### For Production:
- Use: `https://your-production-domain.com/api/`
- Make sure to use HTTPS in production
- Remove or set `android:usesCleartextTraffic="false"` in AndroidManifest.xml for production

### How to Find Your Computer's IP Address

**Windows:**
```powershell
ipconfig
```
Look for "IPv4 Address" under your active network adapter.

**Mac/Linux:**
```bash
ifconfig
```
Look for "inet" address under your active network interface.

## Network Security

The app currently allows cleartext traffic for local development:
```xml
android:usesCleartextTraffic="true"
```

**Important:** For production builds, ensure you:
1. Use HTTPS for all API endpoints
2. Set `android:usesCleartextTraffic="false"` or remove this attribute
3. Implement proper SSL certificate pinning if needed

## Testing the Connection

1. Make sure your backend server is running on port 8080
2. Verify the backend is accessible from your device/emulator
3. Check the Logcat for network requests and responses
4. The app uses OkHttp logging interceptor to log all API calls

## Features Implemented

- ✅ User Registration
- ✅ Form validation (email, password matching, required fields)
- ✅ Loading states
- ✅ Error handling
- ✅ Success feedback
- ✅ Material Design 3 components
- ✅ Consistent color scheme with web app

## Dependencies Added

- Retrofit 2.9.0 (HTTP client)
- Gson Converter (JSON parsing)
- OkHttp Logging Interceptor (debugging)
- Kotlin Coroutines (async operations)
- AndroidX Lifecycle components (ViewModel, LiveData)
- Material Components (UI)

## Next Steps

To extend the app, you can:
1. Implement Login screen
2. Add Dashboard after successful registration/login
3. Implement token storage using SharedPreferences or DataStore
4. Add profile management
5. Implement logout functionality
