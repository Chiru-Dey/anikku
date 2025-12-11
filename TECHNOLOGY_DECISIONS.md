# Technology Decisions for Anikku Windows Port

## Overview

This document records all major technology decisions made during the Windows port of Anikku, including rationale, alternatives considered, and implementation notes.

---

## 1. UI Framework

### Decision: **Compose Multiplatform Desktop**

**Rationale**:
- Anikku already uses Jetpack Compose for Android
- Code reuse: ~70-80% of UI code can be shared
- Modern declarative UI paradigm
- Active development by JetBrains
- Material3 support for desktop
- Good performance on desktop platforms

**Alternatives Considered**:
- **JavaFX**: Mature but different paradigm, requires full UI rewrite
- **Swing**: Legacy technology, not modern
- **Electron + Web**: Requires rewriting everything in web technologies

**Implementation Notes**:
- Use Compose Desktop 1.6.0+
- Leverage existing Compose components from `presentation-core/`
- Create desktop-specific adaptations for window management

---

## 2. Video Player

### Decision: **VLCJ (VLC Java Bindings)**

**Rationale**:
- Excellent Java/Kotlin integration via JNA
- Mature and actively maintained
- Supports all video formats that VLC supports (extensive)
- Good subtitle support
- Hardware acceleration support
- Cross-platform (Windows, Linux, macOS)
- Easy to embed in Swing/Compose panels

**Alternatives Considered**:

| Library | Pros | Cons | Decision |
|---------|------|------|----------|
| **libmpv + JNA** | Direct equivalent to mpv-android | Requires manual JNA bindings, more setup | Rejected |
| **JavaFX MediaPlayer** | Built-in, no dependencies | Limited format support, less control | Rejected |
| **GStreamer Java** | Very flexible | Complex setup, larger footprint | Rejected |
| **VLCJ** | ✅ Best balance | Requires VLC installation | **Selected** |

**Implementation Notes**:
```kotlin
// Dependencies
implementation("uk.co.caprica:vlcj:4.8.2")
implementation("uk.co.caprica:vlcj-natives:4.8.0")
```

**VLC Distribution Strategy**:
- Bundle VLC runtime with installer (increases size by ~80MB)
- Alternative: Detect system VLC installation and use that
- Recommendation: Bundle for best user experience

---

## 3. Database

### Decision: **SQLDelight with SQLite JDBC Driver**

**Rationale**:
- Already used in the Android app
- Official multiplatform support
- Type-safe SQL queries
- Minimal migration effort
- SQLite is cross-platform

**Implementation**:
```kotlin
// Desktop driver
val driver: SqlDriver = JdbcSqliteDriver(
    url = "jdbc:sqlite:${appDataDir}/anikku.db",
    schema = Database.Schema
)
```

**Alternatives Considered**:
- **Room**: Android-specific, doesn't support desktop
- **Exposed**: Kotlin SQL library, would require full rewrite

**Migration Path**:
- Keep existing SQLDelight schemas
- Add desktop source set with JDBC driver
- Handle Windows file paths for database location

---

## 4. Dependency Injection

### Decision: **Koin**

**Rationale**:
- Full Kotlin Multiplatform support
- Simpler than Dagger/Hilt
- Good Compose integration via `koin-compose`
- Service locator pattern works well for desktop
- Active development and community

**Current State**: Anikku uses **Injekt** (custom DI)

**Migration Strategy**:
- Gradually migrate from Injekt to Koin
- Start with shared modules (domain, data)
- Keep Android app on Injekt initially
- Full migration to Koin for both platforms eventually

**Alternatives Considered**:
- **Keep Injekt**: Would need to port to KMP, less tested
- **Kodein**: Similar to Koin but less popular
- **Dagger/Hilt**: No multiplatform support

---

## 5. Build System

### Decision: **Gradle with Kotlin Multiplatform Plugin**

**Rationale**:
- Already using Gradle
- Official KMP support
- Can share build logic via convention plugins
- Supports multiple targets (Android, JVM Desktop)

**Project Structure**:
```
anikku/
├── android/           # Android app (existing)
├── desktop/           # Desktop app (new)
├── core/
│   └── common/        # Shared code (KMP)
├── domain/            # Business logic (KMP)
├── data/              # Data layer (KMP)
└── presentation-core/ # Shared UI (KMP)
```

**Source Sets**:
- `commonMain`: Shared code
- `androidMain`: Android-specific
- `desktopMain`: Desktop-specific

---

## 6. Networking

### Decision: **Keep OkHttp for Android, Add Ktor Client for Desktop**

**Rationale**:
- OkHttp is Android-focused but can work on JVM
- Ktor Client has better multiplatform support
- Both provide similar HTTP functionality

**Strategy**:
- Create `HttpClient` abstraction
- Android: Use OkHttp (existing)
- Desktop: Use Ktor Client CIO engine

**Implementation**:
```kotlin
// Common interface
expect class HttpClient {
    suspend fun get(url: String): HttpResponse
    suspend fun post(url: String, body: String): HttpResponse
}

// Desktop implementation with Ktor
actual class HttpClient {
    private val client = HttpClient(CIO) { /* config */ }
    // Implementation
}
```

**Alternative**: Keep OkHttp for both (simpler but less idiomatic)

---

## 7. File System

### Decision: **Java NIO (java.nio.file) with Windows-specific Extensions**

**Rationale**:
- Cross-platform by default
- Modern API (better than java.io.File)
- Supports symbolic links, file attributes
- Can use JNA for Windows-specific features

**File Locations**:
```kotlin
// Windows paths
val appData = System.getenv("APPDATA") + "/Anikku"
val cache = System.getenv("LOCALAPPDATA") + "/Anikku/cache"
val downloads = System.getenv("USERPROFILE") + "/Downloads/Anikku"
```

**Storage Strategy**:
- Config: `%APPDATA%/Anikku/config/`
- Database: `%APPDATA%/Anikku/anikku.db`
- Cache: `%LOCALAPPDATA%/Anikku/cache/`
- Downloads: User-configurable, default to `~/Downloads/Anikku`

---

## 8. Extension System

### Decision: **JAR-based Extensions with URLClassLoader**

**Rationale**:
- JVM has built-in class loading
- JAR files are standard Java/Kotlin packaging
- Can reuse existing extension source code
- Sandboxing possible via SecurityManager

**Migration from APK**:
```kotlin
// Android: APK loading via PackageManager
// Desktop: JAR loading via URLClassLoader

class DesktopExtensionLoader {
    fun loadExtension(jarFile: File): List<AnimeSource> {
        val classLoader = URLClassLoader(
            arrayOf(jarFile.toURI().toURL()),
            this::class.java.classLoader
        )
        
        // Load via ServiceLoader or scan for implementations
        val sources = ServiceLoader.load(
            AnimeSource::class.java,
            classLoader
        )
        
        return sources.toList()
    }
}
```

**Extension Distribution**:
- Host JAR extensions on extension repository
- Same API as Android extensions
- Compile Kotlin to JVM bytecode (no Android dependencies)

---

## 9. Background Tasks

### Decision: **Kotlin Coroutines with Desktop-specific Dispatchers**

**Rationale**:
- Already using coroutines
- Cross-platform by design
- Can create desktop-specific dispatchers for I/O
- No need for Android WorkManager equivalent

**Implementation**:
```kotlin
// Desktop background task manager
class DesktopTaskManager {
    private val scope = CoroutineScope(
        Dispatchers.IO + SupervisorJob()
    )
    
    fun schedulePeriodicTask(interval: Duration, task: suspend () -> Unit) {
        scope.launch {
            while (isActive) {
                task()
                delay(interval)
            }
        }
    }
}
```

**Persistent Tasks**:
- Use Windows Task Scheduler for system-level tasks
- JNA integration for scheduling

---

## 10. Notifications

### Decision: **Windows Toast Notifications via JNA**

**Rationale**:
- Native Windows 10/11 look and feel
- Supports actions and images
- Integrates with Windows notification center

**Implementation Options**:
1. **JNA + Windows API**: Direct calls to Windows notification API
2. **jpowershell**: Use PowerShell to trigger toasts
3. **electron-windows-notifications** (requires native module)

**Selected**: Option 2 (jpowershell) for simplicity

```kotlin
class WindowsNotificationManager {
    fun showToast(title: String, message: String) {
        val ps = PowerShell.openSession()
        val command = """
            [Windows.UI.Notifications.ToastNotificationManager, Windows.UI.Notifications, ContentType = WindowsRuntime]
            # Create toast
        """.trimIndent()
        ps.executeCommand(command)
        ps.close()
    }
}
```

---

## 11. Navigation

### Decision: **Keep Voyager (KMP-compatible)**

**Rationale**:
- Anikku already uses Voyager for navigation
- Voyager supports Kotlin Multiplatform
- Compose-based navigation
- Minimal changes needed

**Desktop Adaptations**:
- Handle desktop back button (keyboard/mouse)
- Multi-window support if needed
- Desktop-specific transitions

---

## 12. Image Loading

### Decision: **Coil 3 (Multiplatform support)**

**Rationale**:
- Anikku uses Coil on Android
- Coil 3 has multiplatform support
- Compose integration
- Familiar API

**Alternative**: Kamel (multiplatform image loading library)

**Configuration**:
```kotlin
// Desktop image loader
val imageLoader = ImageLoader.Builder(context)
    .components {
        add(OkHttpFetcher.Factory())
    }
    .diskCache {
        DiskCache.Builder()
            .directory(cacheDir.resolve("image_cache"))
            .maxSizeBytes(512L * 1024 * 1024) // 512 MB
            .build()
    }
    .build()
```

---

## 13. Localization

### Decision: **Keep moko-resources (KMP i18n library)**

**Rationale**:
- Already using moko-resources
- Full multiplatform support
- All translations already in place
- Minimal migration effort

**Implementation**:
```kotlin
// Works on both Android and Desktop
Text(text = MR.strings.app_name.getString())
```

---

## 14. Packaging & Distribution

### Decision: **JPackage for Windows Installer**

**Rationale**:
- Built into JDK 14+
- Creates native installers (MSI, EXE)
- Can bundle JRE
- Professional-looking installers

**Configuration**:
```bash
jpackage \
  --input build/libs \
  --name Anikku \
  --main-jar anikku-desktop.jar \
  --main-class MainKt \
  --type msi \
  --app-version 1.0.0 \
  --icon resources/icon.ico \
  --win-dir-chooser \
  --win-menu \
  --win-shortcut \
  --win-per-user-install
```

**Alternatives Considered**:
- **Conveyor**: Commercial tool, easier but costs money
- **Install4j**: Commercial, powerful but expensive
- **Inno Setup**: Free but requires scripting

**Update Mechanism**:
- Custom updater or use Sparkle framework port
- Check GitHub releases API for updates
- Download and install MSI programmatically

---

## 15. Testing

### Decision: **Kotlin Test (Multiplatform) + Compose UI Testing**

**Rationale**:
- Works across platforms
- Can share test code
- Compose has good testing support

**Test Structure**:
```kotlin
// Common tests (run on both platforms)
class AnimeRepositoryTest {
    @Test
    fun testGetAnime() = runTest {
        // Test implementation
    }
}

// Desktop-specific tests
class DesktopPlayerTest {
    @Test
    fun testVLCJPlayer() {
        // Desktop-only test
    }
}
```

---

## 16. Logging

### Decision: **Kermit (Multiplatform logging)**

**Rationale**:
- Multiplatform logging library
- Simple API
- Can output to console/file on desktop

**Configuration**:
```kotlin
val logger = Logger(
    config = StaticConfig(
        logWriterList = listOf(
            ConsoleWriter(),
            FileWriter(logFile)
        )
    )
)
```

---

## 17. Preferences/Settings Storage

### Decision: **Multiplatform Settings library**

**Rationale**:
- Cross-platform key-value storage
- Uses SharedPreferences on Android
- Uses Java Preferences API on desktop

**Implementation**:
```kotlin
val settings: Settings = Settings() // Desktop uses Preferences API
settings.putString("theme", "dark")
```

---

## Summary Table

| Component | Android | Windows Desktop | Migration Effort |
|-----------|---------|-----------------|------------------|
| UI | Jetpack Compose | Compose Desktop | Low (code reuse) |
| Video | mpv-android | VLCJ | Medium (API differences) |
| Database | SQLDelight + Android | SQLDelight + JDBC | Low (same library) |
| DI | Injekt | Koin | Medium (full migration) |
| HTTP | OkHttp | Ktor Client | Medium (abstraction layer) |
| Images | Coil 2 | Coil 3 | Low (upgrade + config) |
| Navigation | Voyager | Voyager | Low (already KMP) |
| Extensions | APK loading | JAR loading | Medium (loader rewrite) |
| Notifications | Android API | Windows Toasts | Medium (new implementation) |
| File I/O | Android SAF | java.nio.file | Medium (abstraction layer) |
| Background Tasks | WorkManager | Coroutines | Low (already using coroutines) |
| i18n | moko-resources | moko-resources | None (already KMP) |
| Packaging | APK/AAB | MSI/EXE | Medium (new tooling) |

---

## Risk Assessment

| Risk | Severity | Mitigation |
|------|----------|------------|
| VLCJ performance issues | Medium | Extensive testing, hardware acceleration |
| Extension loading security | High | Implement sandboxing, code signing |
| Windows-specific bugs | Medium | Test on Win 10/11, multiple configurations |
| Large installer size | Low | Acceptable for desktop (200-300 MB) |
| Update mechanism reliability | Medium | Robust error handling, rollback capability |

---

## Next Steps (Session 1)

1. ✅ Create this document
2. ⏳ Create desktop version catalog (`gradle/desktop.versions.toml`)
3. ⏳ Create desktop module (`desktop/`)
4. ⏳ Setup initial Compose Desktop window
5. ⏳ Create platform abstraction interfaces
6. ⏳ Document architecture decisions

---

*Document Version*: 1.0  
*Last Updated*: Session 1  
*Next Review*: Session 6 (after video player implementation)
