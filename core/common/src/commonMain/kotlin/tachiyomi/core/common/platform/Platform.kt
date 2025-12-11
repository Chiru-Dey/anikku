package tachiyomi.core.common.platform

/**
 * Returns the name of the current platform.
 * 
 * Examples:
 * - Android: "Android 13"
 * - Windows: "Windows 11"
 * - Linux: "Linux"
 * - macOS: "macOS 13.0"
 */
expect fun getPlatformName(): String

/**
 * Returns the platform type.
 */
expect fun getPlatformType(): PlatformType

/**
 * Platform types supported by Anikku.
 */
enum class PlatformType {
    ANDROID,
    WINDOWS,
    LINUX,
    MACOS,
    UNKNOWN
}

/**
 * Platform capabilities that may vary between platforms.
 */
interface PlatformCapabilities {
    /**
     * Whether the platform supports picture-in-picture mode.
     * Android: Native PiP support
     * Desktop: Floating window as alternative
     */
    val supportsPictureInPicture: Boolean
    
    /**
     * Whether the platform supports system tray/notification area.
     * Android: false
     * Desktop: true
     */
    val supportsSystemTray: Boolean
    
    /**
     * Whether the platform supports native file picker.
     */
    val supportsFilePicker: Boolean
    
    /**
     * Whether the platform supports background downloads without app being active.
     */
    val supportsBackgroundDownloads: Boolean
    
    /**
     * Whether the platform supports casting to external devices.
     */
    val supportsCast: Boolean
}

/**
 * Get platform capabilities for the current platform.
 */
expect fun getPlatformCapabilities(): PlatformCapabilities
