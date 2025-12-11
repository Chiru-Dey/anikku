# Anikku Desktop

Windows, Linux, and macOS port of Anikku using Compose Multiplatform.

## Quick Start

### Prerequisites

- **JDK 17+**: [Download Adoptium](https://adoptium.net/)
- **VLC Media Player**: [Download VLC](https://www.videolan.org/vlc/)

### Run Application

```bash
./gradlew :desktop:run
```

### Build Installer

```bash
# Windows MSI
./gradlew :desktop:packageMsi

# All formats for current OS
./gradlew :desktop:packageDistributionForCurrentOS
```

## Documentation

- **[Build Instructions](../docs/BUILD_WINDOWS.md)** - Complete build guide
- **[Architecture](../docs/ARCHITECTURE_WINDOWS.md)** - Technical architecture
- **[Migration Plan](../WINDOWS_PORT_MIGRATION_PLAN.md)** - Multi-session migration strategy
- **[Technology Decisions](../TECHNOLOGY_DECISIONS.md)** - Technology choices and rationale

## Project Structure

```
desktop/
├── src/main/kotlin/
│   ├── Main.kt              # Application entry point
│   ├── DesktopApp.kt        # Main UI composable
│   ├── ui/                  # UI screens (to be added)
│   ├── player/              # Video player integration (to be added)
│   └── platform/            # Platform-specific implementations (to be added)
├── src/main/resources/
│   └── icon.png             # Application icon
├── build.gradle.kts         # Build configuration
└── proguard-rules.pro       # ProGuard rules for release
```

## Features Status

### ✅ Implemented (Session 1)
- [x] Compose Desktop setup
- [x] Window management
- [x] Basic UI structure
- [x] Platform abstractions (foundation)

### ⏳ In Progress
- [ ] Core modules KMP migration (Session 2)
- [ ] SQLDelight desktop setup (Session 2)

### 📋 Planned
- [ ] Domain layer migration (Session 3)
- [ ] Data layer migration (Session 4)
- [ ] Extension system (Session 5)
- [ ] Video player (VLCJ) (Session 6)
- [ ] Library screen (Session 7)
- [ ] Browse & Updates screens (Session 7)
- [ ] Anime detail & Settings (Session 8)
- [ ] Download manager (Session 9)
- [ ] Tracking services (Session 10)
- [ ] Windows integration (Session 11)
- [ ] Testing & polish (Session 12)

## Technology Stack

| Component | Technology | Status |
|-----------|------------|--------|
| **UI Framework** | Compose Multiplatform | ✅ Setup |
| **Video Player** | VLCJ | 📋 Planned |
| **Database** | SQLDelight (JDBC) | 📋 Planned |
| **HTTP Client** | Ktor Client | ✅ Configured |
| **DI** | Koin | ✅ Configured |
| **Image Loading** | Coil 3 | ✅ Configured |
| **Navigation** | Voyager | 📋 Planned |
| **Logging** | Kermit | ✅ Configured |
| **Packaging** | JPackage | ✅ Configured |

## Development

### Run with Debug Logging

```bash
./gradlew :desktop:run -Dcompose.verbose=true
```

### Run Tests

```bash
./gradlew :desktop:test
```

### Build Distribution

```bash
./gradlew :desktop:createDistributable
```

Output: `desktop/build/compose/binaries/main/app/`

## Platform Support

| Platform | Status | Package Format |
|----------|--------|----------------|
| **Windows** | 🚧 In Development | MSI, EXE |
| **Linux** | 📋 Planned | DEB, RPM, AppImage |
| **macOS** | 📋 Planned | DMG, PKG |

## Configuration

### Application Data

| Platform | Location |
|----------|----------|
| Windows | `%APPDATA%\Anikku\` |
| Linux | `~/.local/share/anikku/` |
| macOS | `~/Library/Application Support/Anikku/` |

### Cache

| Platform | Location |
|----------|----------|
| Windows | `%LOCALAPPDATA%\Anikku\cache\` |
| Linux | `~/.cache/anikku/` |
| macOS | `~/Library/Caches/Anikku/` |

## Troubleshooting

### VLC Not Found

Install VLC and set environment variable:

```bash
# Windows
set VLCJ_LIBRARY_PATH=C:\Program Files\VideoLAN\VLC

# Linux
export VLCJ_LIBRARY_PATH=/usr/lib/vlc

# macOS
export VLCJ_LIBRARY_PATH=/Applications/VLC.app/Contents/MacOS/lib
```

### Out of Memory

Edit `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx4g
```

### Application Won't Start

1. Check logs: `%LOCALAPPDATA%\Anikku\logs\`
2. Clear cache: `%LOCALAPPDATA%\Anikku\cache\`
3. Reset config: `%APPDATA%\Anikku\config\`

## Contributing

See the main [CONTRIBUTING.md](../CONTRIBUTING.md) for guidelines.

### Desktop-Specific Guidelines

1. **Platform Abstractions**: Use `expect`/`actual` for platform-specific code
2. **Testing**: Write tests for both common and desktop-specific code
3. **Documentation**: Update docs when making architectural changes
4. **Android Compatibility**: Ensure Android build still works after changes

## Migration Progress

**Current Session**: 1 of 12  
**Overall Progress**: ~8%

See [WINDOWS_PORT_MIGRATION_PLAN.md](../WINDOWS_PORT_MIGRATION_PLAN.md) for detailed progress.

## License

See [LICENSE](../LICENSE)

## Credits

- Original Android app: [Aniyomi](https://github.com/aniyomiorg/aniyomi)
- Compose Multiplatform: [JetBrains](https://www.jetbrains.com/lp/compose-multiplatform/)
- VLCJ: [caprica](https://github.com/caprica/vlcj)

---

**Status**: 🚧 Active Development - Session 1 Complete  
**Last Updated**: Session 1  
**Next Milestone**: Session 2 - Core Infrastructure & Build System
