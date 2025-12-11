# Anikku Windows Port - Migration Checklist

Track overall progress across all 12 sessions.

---

## Session Progress

- [x] **Session 1**: Foundation & Strategy (100%)
- [x] **Session 2**: Core Infrastructure & Build System (95% - awaiting build verification)
- [ ] **Session 3**: Domain Layer Migration (0%)
- [ ] **Session 4**: Data Layer & Database (0%)
- [ ] **Session 5**: Source API & Extensions (0%)
- [ ] **Session 6**: Video Player Integration (0%)
- [ ] **Session 7**: UI - Core Screens (Part 1) (0%)
- [ ] **Session 8**: UI - Core Screens (Part 2) (0%)
- [ ] **Session 9**: Download Manager & File System (0%)
- [ ] **Session 10**: Tracking & Sync Services (0%)
- [ ] **Session 11**: Windows Integration & Polish (0%)
- [ ] **Session 12**: Testing & Documentation (0%)

**Overall Progress**: 8% (1/12 sessions)

---

## Module Migration Status

### Core Modules
- [x] `desktop/` - Desktop application (Session 1)
- [x] `core/common` - Shared utilities (Session 2) ✅
- [ ] `core/archive` - Archive handling (Session 2)
- [ ] `domain/` - Business logic (Session 3)
- [ ] `data/` - Data layer (Session 4)
- [ ] `core-metadata/` - Metadata (Session 4)

### Features
- [ ] `source-api/` - Extension API (Session 5)
- [ ] `source-local/` - Local source (Session 5)
- [ ] Video Player - VLCJ integration (Session 6)
- [ ] `presentation-core/` - Shared UI (Session 7-8)
- [ ] Library Screen (Session 7)
- [ ] Browse Screen (Session 7)
- [ ] Updates Screen (Session 7)
- [ ] Anime Detail Screen (Session 8)
- [ ] Settings Screen (Session 8)
- [ ] History Screen (Session 8)
- [ ] Download Manager (Session 9)
- [ ] Tracking Services (Session 10)
- [ ] Sync Services (Session 10)

### Platform Integration
- [ ] Windows Installer (Session 11)
- [ ] System Tray (Session 11)
- [ ] File Associations (Session 11)
- [ ] Auto-Updater (Session 11)
- [ ] Notifications (Session 9-11)

---

## Feature Parity Checklist

### Core Features
- [ ] Video playback (all formats)
- [ ] Library management
- [ ] Episode tracking
- [ ] Download episodes
- [ ] Search anime
- [ ] Browse sources
- [ ] Install extensions

### Tracking Services
- [ ] MyAnimeList
- [ ] AniList
- [ ] Kitsu
- [ ] Simkl
- [ ] Shikimori
- [ ] Bangumi

### Player Features
- [ ] Subtitle support
- [ ] Audio track selection
- [ ] Playback speed control
- [ ] Picture-in-Picture (floating window)
- [ ] Hardware acceleration
- [ ] Gesture controls adaptation

### Library Features
- [ ] Categories
- [ ] Filters
- [ ] Sorting
- [ ] Search
- [ ] Bulk operations
- [ ] Cover management
- [ ] Tags

### Additional Features
- [ ] Backup/Restore
- [ ] Cloud sync (Google Drive)
- [ ] SyncYomi support
- [ ] Discord RPC
- [ ] Theme customization
- [ ] Keyboard shortcuts

---

## Technical Tasks

### Build System
- [x] Desktop module setup
- [x] Gradle configuration
- [x] Version catalogs
- [x] Convention plugins
- [ ] KMP plugin configuration
- [ ] Source sets structure
- [ ] Dependency management

### Platform Abstractions
- [x] Platform detection
- [x] File system abstraction (defined)
- [x] Platform context (defined)
- [ ] Platform abstractions (implemented)
- [ ] Image loader abstraction
- [ ] Video player abstraction
- [ ] Notification abstraction
- [ ] Network abstraction

### Database
- [ ] SQLDelight desktop driver
- [ ] Database migrations
- [ ] Query adapters
- [ ] Backup/restore

### UI
- [ ] Compose Desktop setup
- [ ] Navigation implementation
- [ ] Theme system
- [ ] Custom components
- [ ] Dialogs
- [ ] Context menus
- [ ] Keyboard shortcuts

### Testing
- [ ] Unit test framework
- [ ] Integration tests
- [ ] UI tests
- [ ] Platform-specific tests
- [ ] CI/CD pipeline

---

## Documentation Status

### Completed
- [x] Migration Plan (WINDOWS_PORT_MIGRATION_PLAN.md)
- [x] Technology Decisions (TECHNOLOGY_DECISIONS.md)
- [x] Architecture Guide (docs/ARCHITECTURE_WINDOWS.md)
- [x] Build Instructions (docs/BUILD_WINDOWS.md)
- [x] Quick Reference (docs/SESSION_QUICK_REFERENCE.md)
- [x] Session 1 Handoff (SESSION_1_HANDOFF.md)
- [x] Desktop README (desktop/README.md)

### Needed
- [ ] API documentation
- [ ] User guide (Windows)
- [ ] Troubleshooting guide
- [ ] Performance guide
- [ ] Contributing guide (desktop-specific) - ✅ Created
- [ ] Release notes template

---

## Quality Metrics

### Performance Targets
- [ ] Startup time < 3 seconds
- [ ] Memory usage < 500 MB idle
- [ ] Video playback 60 FPS
- [ ] UI frame time < 16ms
- [ ] Database queries < 100ms

### Code Quality
- [ ] Unit test coverage > 70%
- [ ] No critical bugs
- [ ] All features working
- [ ] Documentation complete
- [ ] Code reviewed

### User Experience
- [ ] Installation < 5 minutes
- [ ] First-time setup < 2 minutes
- [ ] Responsive UI
- [ ] No crashes in common workflows
- [ ] Helpful error messages

---

## Blockers & Issues

### Known Issues
None currently (Session 1)

### Potential Blockers
- [ ] VLCJ compatibility issues
- [ ] SQLDelight migration problems
- [ ] Performance issues
- [ ] Android build breakage
- [ ] Extension system complexity

---

## Release Criteria

### MVP (Minimum Viable Product)
- [ ] Desktop app launches
- [ ] Video playback works
- [ ] Library management functional
- [ ] At least 1 source/extension works
- [ ] Settings persist
- [ ] Basic tracking works

### Beta Release
- [ ] All core features working
- [ ] Multiple sources working
- [ ] Download manager functional
- [ ] No major bugs
- [ ] Documentation complete
- [ ] Installer works

### Stable Release
- [ ] Feature parity with Android
- [ ] All tracking services work
- [ ] Cloud sync working
- [ ] Thoroughly tested
- [ ] Performance optimized
- [ ] Auto-update working

---

## Session Completion Criteria

Each session is complete when:
- [x] All planned tasks finished
- [x] Handoff document created
- [x] Documentation updated
- [x] Android build verified
- [x] Changes committed

---

## Next Milestone

**Session 2: Core Infrastructure & Build System**

Target Tasks:
- [ ] Convert `core/common` to KMP
- [ ] Implement platform abstractions
- [ ] Setup SQLDelight desktop driver
- [ ] Test desktop build runs

Success Criteria:
- [ ] `./gradlew :desktop:run` launches app
- [ ] Platform abstractions work
- [ ] Android build still works
- [ ] Core utilities accessible from desktop

---

## Notes

- Keep Android build working at all times
- Test on multiple Windows versions (10, 11)
- Document all architectural decisions
- Create session handoff docs
- Update this checklist regularly

---

*Last Updated*: Session 1  
*Next Update*: Session 2  
*Overall Progress*: 8%
