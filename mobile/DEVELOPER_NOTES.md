# Developer Notes - Mobile Registration Feature

## 🎯 Quick Navigation

### Want to...

**Change the API endpoint?**
```kotlin
// File: RetrofitClient.kt
private const val BASE_URL = "http://10.0.2.2:8080/api/"
// Change to your backend URL
```

**Modify validation rules?**
```kotlin
// File: RegisterViewModel.kt
fun register() {
    // Add your custom validation here
    if (passwordValue.length < 6) {  // Change minimum password length
        _registerState.value = RegisterState.Error("Password must be at least 6 characters")
        return
    }
}
```

**Change the color scheme?**
```xml
<!-- File: res/values/colors.xml -->
<color name="accent_strong">#5B75F7</color>  <!-- Change primary button color -->
<color name="background_dark">#0F1A33</color>  <!-- Change background -->
```

**Update the form layout?**
```xml
<!-- File: res/layout/activity_register.xml -->
<!-- All UI components are here -->
```

**Add a new API endpoint?**
```kotlin
// File: AuthApiService.kt
interface AuthApiService {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<MessageResponse>
    
    // Add new endpoints here:
    // @POST("auth/your-endpoint")
    // suspend fun yourMethod(...): Response<YourResponse>
}
```

**Implement login screen?**
1. Copy `RegisterActivity.kt` → `LoginActivity.kt`
2. Copy `RegisterViewModel.kt` → `LoginViewModel.kt`
3. Copy `activity_register.xml` → `activity_login.xml`
4. Modify for login fields (email + password only)
5. Add to AndroidManifest.xml

---

## 🔍 Key Code Locations

### API Configuration
- **Base URL**: `data/api/RetrofitClient.kt:16`
- **Timeout**: `data/api/RetrofitClient.kt:23-25`
- **Logging**: `data/api/RetrofitClient.kt:19-21`

### Validation Logic
- **All validation**: `ui/auth/RegisterViewModel.kt:58-94`
- **Email check**: Line 73
- **Password length**: Line 78
- **Password match**: Line 83

### UI Components
- **Form layout**: `res/layout/activity_register.xml`
- **Error display**: Lines 126-141
- **Loading indicator**: Line 342
- **Success feedback**: `ui/auth/RegisterActivity.kt:116`

### State Management
- **State definition**: `ui/auth/RegisterViewModel.kt:126-131`
- **State observation**: `ui/auth/RegisterActivity.kt:67-84`

---

## 🐛 Common Modifications

### 1. Change Password Requirements

```kotlin
// RegisterViewModel.kt - Line 78
if (passwordValue.length < 8) {  // Changed from 6 to 8
    _registerState.value = RegisterState.Error("Password must be at least 8 characters")
    return
}

// Add complexity requirement:
if (!passwordValue.any { it.isUpperCase() }) {
    _registerState.value = RegisterState.Error("Password must contain uppercase letter")
    return
}
```

### 2. Add Phone Number Field

**Step 1: Update Model**
```kotlin
// RegisterRequest.kt
data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phoneNumber: String  // Add this
)
```

**Step 2: Update ViewModel**
```kotlin
// RegisterViewModel.kt
private val _phoneNumber = MutableLiveData("")
val phoneNumber: LiveData<String> = _phoneNumber

fun updatePhoneNumber(value: String) {
    _phoneNumber.value = value
}

// In register() function, add validation:
val phoneValue = _phoneNumber.value?.trim() ?: ""
if (phoneValue.isEmpty()) {
    _registerState.value = RegisterState.Error("Phone number is required")
    return
}

// Update API call:
val result = repository.register(
    firstNameValue,
    lastNameValue,
    emailValue,
    passwordValue,
    phoneValue  // Add this
)
```

**Step 3: Update Layout**
```xml
<!-- activity_register.xml - Add after email field -->
<com.google.android.material.textfield.TextInputLayout
    android:id="@+id/phoneLayout"
    style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="16dp"
    android:hint="Phone Number"
    app:boxBackgroundColor="@color/background_darker">
    
    <com.google.android.material.textfield.TextInputEditText
        android:id="@+id/phoneInput"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="phone"
        android:textColor="@color/text_primary" />
</com.google.android.material.textfield.TextInputLayout>
```

**Step 4: Update Activity**
```kotlin
// RegisterActivity.kt - In setupUI()
binding.phoneInput.doAfterTextChanged { text ->
    viewModel.updatePhoneNumber(text.toString())
}
```

### 3. Add Remember Me Checkbox

```xml
<!-- activity_register.xml - Add before button -->
<com.google.android.material.checkbox.MaterialCheckBox
    android:id="@+id/rememberMeCheckbox"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="16dp"
    android:text="Remember me"
    android:textColor="@color/text_secondary"
    app:buttonTint="@color/accent_primary" />
```

```kotlin
// RegisterActivity.kt
private var rememberMe = false

binding.rememberMeCheckbox.setOnCheckedChangeListener { _, isChecked ->
    rememberMe = isChecked
}

// Save to SharedPreferences after success:
if (rememberMe) {
    getSharedPreferences("auth", MODE_PRIVATE)
        .edit()
        .putString("email", email)
        .apply()
}
```

### 4. Add Loading Timeout

```kotlin
// RegisterViewModel.kt
import kotlinx.coroutines.withTimeout

fun register() {
    // ... validation code ...
    
    viewModelScope.launch {
        try {
            withTimeout(30000L) {  // 30 second timeout
                val result = repository.register(...)
                // ... handle result ...
            }
        } catch (e: TimeoutCancellationException) {
            _registerState.value = RegisterState.Error("Request timeout. Please try again.")
        }
    }
}
```

### 5. Add Network Check Before API Call

```kotlin
// Create NetworkUtils.kt
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
           capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
}

// RegisterActivity.kt - Before calling viewModel.register()
if (!isNetworkAvailable()) {
    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
    return
}
```

---

## 🎨 UI Customization Tips

### Change Button Style
```xml
<!-- activity_register.xml -->
<com.google.android.material.button.MaterialButton
    android:id="@+id/registerButton"
    app:cornerRadius="20dp"              <!-- More rounded -->
    android:textSize="16sp"              <!-- Larger text -->
    app:backgroundTint="@color/success"  <!-- Green button -->
    app:elevation="4dp" />               <!-- Add shadow -->
```

### Customize Input Fields
```xml
<com.google.android.material.textfield.TextInputLayout
    app:boxStrokeWidth="2dp"                    <!-- Thicker border -->
    app:boxStrokeColor="@color/accent_light"    <!-- Blue border -->
    app:hintTextColor="@color/accent_primary"   <!-- Blue hint -->
    app:errorEnabled="true"                     <!-- Enable error text -->
    app:errorTextColor="@color/error">          <!-- Red error -->
```

### Add Icons to Fields
```xml
<com.google.android.material.textfield.TextInputLayout
    app:startIconDrawable="@android:drawable/ic_menu_myplaces"  <!-- Email icon -->
    app:startIconTint="@color/accent_primary">
```

---

## 🔒 Security Enhancements

### 1. Add Token Storage (for future login)

```kotlin
// Create TokenManager.kt
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class TokenManager(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    
    private val prefs = EncryptedSharedPreferences.create(
        context,
        "auth_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    fun saveToken(token: String) {
        prefs.edit().putString("token", token).apply()
    }
    
    fun getToken(): String? = prefs.getString("token", null)
    
    fun clearToken() {
        prefs.edit().remove("token").apply()
    }
}
```

### 2. Add Request Authentication

```kotlin
// RetrofitClient.kt - Add interceptor
private val authInterceptor = Interceptor { chain ->
    val token = getToken() // Implement token retrieval
    val request = chain.request().newBuilder()
        .addHeader("Authorization", "Bearer $token")
        .build()
    chain.proceed(request)
}

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(authInterceptor)  // Add this
    .addInterceptor(loggingInterceptor)
    // ... rest of config
    .build()
```

---

## 📊 Analytics Integration (Optional)

```kotlin
// Add to RegisterActivity.kt

import com.google.firebase.analytics.FirebaseAnalytics

class RegisterActivity : AppCompatActivity() {
    private lateinit var analytics: FirebaseAnalytics
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = FirebaseAnalytics.getInstance(this)
        // ... rest of code
    }
    
    private fun showSuccess(message: String) {
        // ... existing code ...
        
        // Log registration event
        val bundle = Bundle().apply {
            putString("method", "email")
        }
        analytics.logEvent("sign_up", bundle)
    }
}
```

---

## 🧪 Testing Helpers

### Mock Successful Registration

```kotlin
// For testing without backend
// In AuthRepository.kt, add a mock mode:

companion object {
    var MOCK_MODE = false  // Set to true for testing
}

suspend fun register(...): Result<MessageResponse> {
    if (MOCK_MODE) {
        delay(2000)  // Simulate network delay
        return Result.success(MessageResponse("User registered successfully"))
    }
    // ... normal API call
}
```

### Test Different Error States

```kotlin
// In RegisterViewModel.kt, add test functions:

fun testLoadingState() {
    _registerState.value = RegisterState.Loading
}

fun testErrorState() {
    _registerState.value = RegisterState.Error("Test error message")
}

fun testSuccessState() {
    _registerState.value = RegisterState.Success("Test success message")
}
```

---

## 📱 Device-Specific Notes

### For Emulator Testing
- ✅ Use: `http://10.0.2.2:8080/api/`
- ✅ No special network setup needed
- ✅ Can use Android Studio Logcat

### For Physical Device Testing
- ✅ Find computer IP: `ipconfig` (Windows) or `ifconfig` (Mac/Linux)
- ✅ Update `BASE_URL` in `RetrofitClient.kt`
- ✅ Ensure both on same WiFi network
- ✅ Disable firewall temporarily if needed
- ✅ Use Chrome DevTools for backend debugging: `chrome://inspect`

---

## 🚀 Performance Tips

1. **Debounce Input Validation**
```kotlin
// Add to RegisterViewModel.kt
private val validationJob = MutableStateFlow("")

init {
    validationJob
        .debounce(300)  // Wait 300ms after last keystroke
        .onEach { validateEmail(it) }
        .launchIn(viewModelScope)
}
```

2. **Cache API Responses**
```kotlin
// In AuthRepository.kt
private var cachedUser: User? = null

fun getCachedUser(): User? = cachedUser
```

3. **Optimize Layout**
```xml
<!-- Use ConstraintLayout instead of nested LinearLayouts -->
<!-- Avoid overdraw with proper background settings -->
<!-- Use vector drawables instead of PNGs -->
```

---

## 📚 Useful Resources

- [Material Design 3 Components](https://m3.material.io/components)
- [Retrofit Documentation](https://square.github.io/retrofit/)
- [Kotlin Coroutines Guide](https://kotlinlang.org/docs/coroutines-guide.html)
- [Android Architecture Components](https://developer.android.com/topic/architecture)
- [ViewBinding Guide](https://developer.android.com/topic/libraries/view-binding)

---

**Happy Coding! 🚀**

For more details, see:
- `README.md` - Full documentation
- `QUICKSTART.md` - Setup guide
- `STRUCTURE_GUIDE.md` - Visual structure
- `IMPLEMENTATION_SUMMARY.md` - Implementation details
