# Session Quick Reference Guide

Quick reference for jumping into any session during the Windows port migration.

---

## Session Overview

| Session | Focus Area | Est. Duration | Status |
|---------|------------|---------------|--------|
| 1 | Foundation & Strategy | 2-3 hours | ✅ Complete |
| 2 | Core Infrastructure & Build | 2-3 hours | ⏳ Next |
| 3 | Domain Layer Migration | 2-3 hours | 📋 Planned |
| 4 | Data Layer & Database | 2-3 hours | 📋 Planned |
| 5 | Source API & Extensions | 2-3 hours | 📋 Planned |
| 6 | Video Player Integration | 3-4 hours | 📋 Planned |
| 7 | UI - Core Screens (Part 1) | 2-3 hours | 📋 Planned |
| 8 | UI - Core Screens (Part 2) | 2-3 hours | 📋 Planned |
| 9 | Download Manager & Files | 2-3 hours | 📋 Planned |
| 10 | Tracking & Sync Services | 2-3 hours | 📋 Planned |
| 11 | Windows Integration & Polish | 2-3 hours | 📋 Planned |
| 12 | Testing & Documentation | 2-3 hours | 📋 Planned |

---

## Quick Commands

### Build & Run

```bash
# Run desktop app
./gradlew :desktop:run

# Build Android app (verify compatibility)
./gradlew :app:assembleDebug

# Run all tests
./gradlew test

# Build desktop installer
./gradlew :desktop:packageMsi
```

### Project Structure

```
anikku/
├── desktop/              # Desktop application (Session 1+)
├── core/common/          # Shared utilities (Session 2+)
├── domain/               # Business logic (Session 3)
├── data/                 # Data layer (Session 4)
├── source-api/           # Extension API (Session 5)
├── presentation-core/    # Shared UI (Session 7-8)
└── docs/                 # Documentation
```

### Key Files

| File | Purpose | Session |
|------|---------|---------|
| `WINDOWS_PORT_MIGRATION_PLAN.md` | Overall migration strategy | 1 |
| `TECHNOLOGY_DECISIONS.md` | Tech choices & rationale | 1 |
| `SESSION_N_HANDOFF.md` | Session transition docs | Each |
| `docs/ARCHITECTURE_WINDOWS.md` | Architecture details | 1 |
| `docs/BUILD_WINDOWS.md` | Build instructions | 1 |
| `desktop/build.gradle.kts` | Desktop build config | 1 |
| `gradle/desktop.versions.toml` | Desktop dependencies | 1 |

---

## Before Starting Any Session

1. **Read the handoff document**: `SESSION_N_HANDOFF.md`
2. **Review the migration plan**: `WINDOWS_PORT_MIGRATION_PLAN.md`
3. **Check current status**: Look for `✅`, `⏳`, `📋` markers
4. **Pull latest changes**: `git pull`
5. **Verify Android build**: `./gradlew :app:assembleDebug`

---

## After Completing Any Session

1. **Update handoff document**: Create `SESSION_N_HANDOFF.md`
2. **Update migration plan**: Mark completed tasks
3. **Test builds**: Both desktop and Android
4. **Document decisions**: Update relevant docs
5. **Commit changes**: Clear commit messages with `[Session N]` prefix

---

## Common Tasks

### Adding a New Desktop Dependency

1. Add to `gradle/desktop.versions.toml`
2. Reference in `desktop/build.gradle.kts`
3. Sync Gradle

### Creating Platform Abstraction

```kotlin
// commonMain
expect class MyClass {
    fun doSomething(): String
}

// desktopMain
actual class MyClass {
    actual fun doSomething(): String = "Desktop implementation"
}

// androidMain
actual class MyClass {
    actual fun doSomething(): String = "Android implementation"
}
```

### Converting Module to KMP

1. Update `build.gradle.kts` to use KMP plugin
2. Restructure `src/` to `commonMain`, `androidMain`, `desktopMain`
3. Move shared code to `commonMain`
4. Extract platform-specific code to respective source sets
5. Add desktop-specific dependencies

---

## Key Technologies

| Technology | Purpose | Documentation |
|------------|---------|---------------|
| Compose Desktop | UI Framework | [Link](https://www.jetbrains.com/lp/compose-multiplatform/) |
| VLCJ | Video Player | [Link](https://github.com/caprica/vlcj) |
| SQLDelight | Database | [Link](https://cashapp.github.io/sqldelight/) |
| Koin | Dependency Injection | [Link](https://insert-koin.io/) |
| Ktor Client | HTTP Client | [Link](https://ktor.io/docs/client.html) |
| Coil 3 | Image Loading | [Link](https://coil-kt.github.io/coil/) |

---

## Troubleshooting

### Build Failures

```bash
# Clean build
./gradlew clean

# Build with stacktrace
./gradlew :desktop:run --stacktrace

# Invalidate caches (IntelliJ)
File -> Invalidate Caches / Restart
```

### Android Build Broken

1. Check what changed in shared modules
2. Verify `androidMain` source sets exist
3. Test with `./gradlew :app:assembleDebug`
4. Review recent commits

### VLC Not Found

```bash
# Windows
set VLCJ_LIBRARY_PATH=C:\Program Files\VideoLAN\VLC

# Install VLC
choco install vlc  # Windows
brew install vlc   # macOS
apt install vlc    # Linux
```

---

## Testing Strategy

### Unit Tests
```bash
./gradlew :desktop:test
./gradlew :core:common:test
```

### Integration Tests
```bash
./gradlew :desktop:integrationTest
```

### Manual Testing Checklist
- [ ] Desktop app launches
- [ ] UI displays correctly
- [ ] Video playback works
- [ ] Database operations succeed
- [ ] Settings persist
- [ ] Extensions load
- [ ] Downloads work
- [ ] Android app still functions

---

## Session-Specific Notes

### Session 1 (Complete)
- ✅ Foundation laid
- ✅ Desktop module created
- ✅ Documentation complete
- ✅ Platform abstractions defined

### Session 2 (Next)
- Focus: KMP setup for `core/common`
- Goal: Platform abstractions working
- Test: Desktop app runs with core utilities

### Session 6 (Critical)
- VLCJ integration is complex
- Test thoroughly on multiple Windows versions
- Consider fallback video player options

### Session 11 (Polish)
- Windows installer creation
- File associations
- System tray integration
- Update mechanism

---

## Git Workflow

### Branch Strategy
```bash
# Work on desktop port
git checkout -b session-N-feature-name

# Commit with session prefix
git commit -m "[Session N] Description of changes"

# Keep sessions in separate PRs for easier review
```

### Commit Message Format
```
[Session N] Short description

- Detailed change 1
- Detailed change 2
- Files modified: path/to/file.kt

Related: #issue-number
```

---

## Performance Targets

| Metric | Target | Current |
|--------|--------|---------|
| **Startup Time** | < 3 seconds | TBD |
| **Memory Usage** | < 500 MB idle | TBD |
| **Video Playback** | 60 FPS smooth | TBD |
| **UI Responsiveness** | < 16ms frame time | TBD |
| **Installer Size** | < 250 MB | TBD |

---

## Resources

### Documentation
- [Migration Plan](../WINDOWS_PORT_MIGRATION_PLAN.md)
- [Architecture](./ARCHITECTURE_WINDOWS.md)
- [Build Guide](./BUILD_WINDOWS.md)
- [Technology Decisions](../TECHNOLOGY_DECISIONS.md)

### External Links
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Desktop Docs](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-desktop.html)
- [VLCJ Examples](https://github.com/caprica/vlcj-examples)

### Community
- Discord: [Join Server](https://discord.gg/85jB7V5AJR)
- GitHub Discussions: [Link](https://github.com/komikku-app/anikku/discussions)

---

## Contact & Support

For questions about the migration:
- Open a GitHub discussion
- Ask in Discord `#desktop-port` channel
- Tag `@desktop-team` in issues

---

*Last Updated*: Session 1  
*Next Review*: Session 6 (after video player)
