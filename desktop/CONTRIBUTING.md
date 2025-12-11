# Contributing to Anikku Desktop

Thank you for your interest in contributing to the Anikku desktop port!

## Getting Started

1. **Read the documentation**:
   - [Windows Port Migration Plan](../WINDOWS_PORT_MIGRATION_PLAN.md)
   - [Architecture Documentation](../docs/ARCHITECTURE_WINDOWS.md)
   - [Build Instructions](../docs/BUILD_WINDOWS.md)

2. **Setup your development environment**:
   - JDK 17 or higher
   - VLC Media Player
   - IntelliJ IDEA (recommended)

3. **Verify your setup**:
   ```bash
   ./gradlew :desktop:build
   ./gradlew :desktop:run
   ```

## Development Workflow

### Making Changes

1. **Create a branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes**:
   - Follow Kotlin coding conventions
   - Add tests for new functionality
   - Update documentation as needed

3. **Test thoroughly**:
   ```bash
   # Run unit tests
   ./gradlew :desktop:test
   
   # Verify Android still works
   ./gradlew :app:assembleDebug
   ```

4. **Commit with clear messages**:
   ```bash
   git commit -m "[Desktop] Add video player controls"
   ```

### Code Style

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Add KDoc comments for public APIs
- Keep functions focused and small

### Platform Abstractions

When adding platform-specific code:

```kotlin
// commonMain - Define interface
expect class VideoPlayer {
    fun play()
    fun pause()
}

// desktopMain - Desktop implementation
actual class VideoPlayer {
    actual fun play() { /* Desktop implementation */ }
    actual fun pause() { /* Desktop implementation */ }
}

// androidMain - Android implementation  
actual class VideoPlayer {
    actual fun play() { /* Android implementation */ }
    actual fun pause() { /* Android implementation */ }
}
```

### Testing

- Write unit tests in `src/test/kotlin/`
- Test on multiple platforms when possible
- Ensure Android build remains functional

### Documentation

Update relevant documentation:
- Code comments for complex logic
- KDoc for public APIs
- Update architecture docs for major changes
- Add entries to migration plan if applicable

## Pull Requests

1. **Before submitting**:
   - All tests pass
   - Android build works
   - Code is formatted
   - Documentation updated

2. **PR Description should include**:
   - What changed
   - Why it changed
   - How to test
   - Screenshots (if UI changes)

3. **PR Title format**:
   ```
   [Desktop] Short description
   ```

## Current Focus Areas

See [WINDOWS_PORT_MIGRATION_PLAN.md](../WINDOWS_PORT_MIGRATION_PLAN.md) for:
- Current session priorities
- What needs implementation
- Known issues and blockers

## Questions?

- Open a discussion on GitHub
- Ask in Discord `#desktop-port` channel
- Check [Quick Reference Guide](../docs/SESSION_QUICK_REFERENCE.md)

## Code of Conduct

Please follow the project's [Code of Conduct](../CODE_OF_CONDUCT.md).

---

Thank you for contributing to Anikku Desktop! 🎉
