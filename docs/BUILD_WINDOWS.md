# Building Anikku for Windows

This guide explains how to build and run the Anikku Windows desktop application.

---

## Prerequisites

### Required Software

1. **JDK 17 or higher**
   - Download from [Adoptium](https://adoptium.net/) or [Oracle](https://www.oracle.com/java/technologies/downloads/)
   - Verify installation: `java -version`

2. **VLC Media Player** (for video playback)
   - Download from [videolan.org](https://www.videolan.org/vlc/)
   - Install to default location: `C:\Program Files\VideoLAN\VLC\`
   - Or set `VLCJ_LIBRARY_PATH` environment variable to VLC installation directory

3. **Git**
   - Download from [git-scm.com](https://git-scm.com/download/win)

### Optional

- **IntelliJ IDEA** or **Android Studio** (recommended for development)
- **Visual Studio Code** with Kotlin plugin

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/aniyomiorg/aniyomi.git
cd aniyomi
```

### 2. Build the Desktop Application

#### Using Gradle Wrapper (Recommended)

```bash
# Windows Command Prompt
.\gradlew :desktop:run

# PowerShell
./gradlew :desktop:run

# Git Bash
./gradlew :desktop:run
```

This will:
- Download all dependencies
- Compile the desktop application
- Launch the application window

#### Using IDE

1. Open the project in IntelliJ IDEA
2. Wait for Gradle sync to complete
3. Navigate to `desktop/src/main/kotlin/Main.kt`
4. Click the green play button next to `fun main()`

---

## Build Tasks

### Run Application (Development)

```bash
./gradlew :desktop:run
```

Launches the application in development mode with hot reload support.

### Create Distributable Package

```bash
./gradlew :desktop:createDistributable
```

Creates a runnable distribution in:
- `desktop/build/compose/binaries/main/app/`

Contains:
- Application JAR
- All dependencies
- Launch scripts (`.bat` for Windows)

### Package as Native Installer

#### Windows MSI Installer

```bash
./gradlew :desktop:packageMsi
```

Creates MSI installer in:
- `desktop/build/compose/binaries/main/msi/`

Requirements:
- WiX Toolset must be installed
- Download from [wixtoolset.org](https://wixtoolset.org/)

#### Windows EXE Installer

```bash
./gradlew :desktop:packageExe
```

Creates EXE installer using Inno Setup.

#### All Formats

```bash
./gradlew :desktop:packageDistributionForCurrentOS
```

Creates all available formats for Windows (MSI + EXE + Distributable ZIP).

---

## Project Structure

```
aniyomi/
├── desktop/                    # Desktop application module
│   ├── src/
│   │   └── main/
│   │       ├── kotlin/
│   │       │   ├── Main.kt     # Entry point
│   │       │   └── DesktopApp.kt  # Main UI
│   │       └── resources/
│   │           └── icon.png    # App icon
│   ├── build.gradle.kts        # Desktop build configuration
│   └── proguard-rules.pro      # ProGuard rules for release
├── core/                       # Shared core modules
├── domain/                     # Business logic
├── data/                       # Data layer
├── presentation-core/          # Shared UI components
├── gradle/
│   └── desktop.versions.toml   # Desktop dependencies
└── settings.gradle.kts         # Project settings
```

---

## Configuration

### Application Data Directory

Windows: `%APPDATA%\Anikku\`

Contains:
- `config/` - Application configuration
- `anikku.db` - SQLite database
- `extensions/` - Installed extensions

### Cache Directory

Windows: `%LOCALAPPDATA%\Anikku\cache\`

Contains:
- `image_cache/` - Cached cover images
- `http_cache/` - HTTP cache

### Download Directory

Default: `%USERPROFILE%\Downloads\Anikku\`

Configurable in settings.

---

## Development

### Running Tests

```bash
# Run all tests
./gradlew :desktop:test

# Run specific test
./gradlew :desktop:test --tests "DesktopPlayerTest"
```

### Code Style

```bash
# Check code style
./gradlew :desktop:ktlintCheck

# Auto-format code
./gradlew :desktop:ktlintFormat
```

### Debugging

#### Enable Debug Logging

Add to `Main.kt`:

```kotlin
System.setProperty("compose.verbose", "true")
System.setProperty("vlcj.log", "DEBUG")
```

#### VLCJ Debugging

Set environment variable:
```bash
set VLCJ_LOG_LEVEL=DEBUG
```

---

## Troubleshooting

### Issue: "VLC not found"

**Solution**:
1. Install VLC from [videolan.org](https://www.videolan.org/)
2. Set environment variable:
   ```bash
   set VLCJ_LIBRARY_PATH=C:\Program Files\VideoLAN\VLC
   ```

### Issue: "Java version mismatch"

**Solution**:
```bash
# Check Java version
java -version

# Set JAVA_HOME
set JAVA_HOME=C:\Program Files\Java\jdk-17
```

### Issue: "Gradle build failed"

**Solution**:
```bash
# Clean build
./gradlew clean

# Build with stacktrace
./gradlew :desktop:run --stacktrace
```

### Issue: "Application won't start"

**Solution**:
1. Check logs in: `%LOCALAPPDATA%\Anikku\logs\`
2. Delete cache: `%LOCALAPPDATA%\Anikku\cache\`
3. Reset config: `%APPDATA%\Anikku\config\`

### Issue: "Out of memory"

**Solution**:

Edit `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx4g -XX:MaxMetaspaceSize=512m
```

---

## Building for Release

### 1. Update Version

Edit `desktop/build.gradle.kts`:
```kotlin
version = "1.0.0"
```

### 2. Build Release Package

```bash
# Create optimized release build
./gradlew :desktop:packageReleaseDistributionForCurrentOS

# With ProGuard obfuscation
./gradlew :desktop:packageReleaseUberJarForCurrentOS
```

### 3. Sign the Installer (Optional)

For MSI signing:
```bash
signtool sign /f certificate.pfx /p password /t http://timestamp.server.com installer.msi
```

### 4. Test Installation

1. Install from MSI
2. Launch application
3. Verify all features work
4. Check for memory leaks
5. Test update mechanism

---

## CI/CD

### GitHub Actions Workflow

Create `.github/workflows/desktop-build.yml`:

```yaml
name: Desktop Build

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: windows-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
    
    - name: Install VLC
      run: |
        choco install vlc
    
    - name: Build Desktop Application
      run: ./gradlew :desktop:packageMsi
    
    - name: Upload Artifact
      uses: actions/upload-artifact@v3
      with:
        name: anikku-windows
        path: desktop/build/compose/binaries/main/msi/*.msi
```

---

## Performance Optimization

### JVM Options

Add to launch script or IDE configuration:

```bash
-Xms512m                    # Initial heap
-Xmx2g                      # Maximum heap
-XX:+UseG1GC                # Use G1 garbage collector
-XX:MaxGCPauseMillis=200    # Target max GC pause
-Dcompose.layers.enabled=true  # Enable Compose layers
```

### Compose Performance

```kotlin
// Use remember for expensive operations
@Composable
fun MyScreen() {
    val expensiveValue = remember(key) {
        computeExpensiveValue()
    }
}

// Use LazyColumn for large lists
LazyColumn {
    items(animeList) { anime ->
        AnimeListItem(anime)
    }
}
```

---

## Multi-Platform Builds

### Build for Linux

On Linux machine:
```bash
./gradlew :desktop:packageDeb
./gradlew :desktop:packageRpm
```

### Build for macOS

On macOS machine:
```bash
./gradlew :desktop:packageDmg
./gradlew :desktop:packagePkg
```

---

## Additional Resources

### Documentation
- [Compose Desktop Documentation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-desktop.html)
- [VLCJ Documentation](https://github.com/caprica/vlcj)
- [Gradle Documentation](https://docs.gradle.org/)

### Community
- [Discord Server](https://discord.gg/aniyomi)
- [GitHub Discussions](https://github.com/aniyomiorg/aniyomi/discussions)
- [Issue Tracker](https://github.com/aniyomiorg/aniyomi/issues)

### Contributing
- See [CONTRIBUTING.md](../CONTRIBUTING.md)
- Read [ARCHITECTURE_WINDOWS.md](ARCHITECTURE_WINDOWS.md)
- Follow [Code of Conduct](../CODE_OF_CONDUCT.md)

---

## Next Steps

After building successfully:
1. Read the [Architecture Documentation](ARCHITECTURE_WINDOWS.md)
2. Explore the codebase
3. Run tests to verify everything works
4. Start contributing!

---

*Last Updated*: Session 1  
*For Issues*: [GitHub Issues](https://github.com/aniyomiorg/aniyomi/issues)
