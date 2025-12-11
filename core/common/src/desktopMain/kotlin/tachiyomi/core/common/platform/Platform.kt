package tachiyomi.core.common.platform

actual fun getPlatformName(): String {
    val osName = System.getProperty("os.name")
    val osVersion = System.getProperty("os.version")
    return "$osName $osVersion"
}

actual fun getPlatformType(): PlatformType {
    val osName = System.getProperty("os.name").lowercase()
    return when {
        osName.contains("windows") -> PlatformType.WINDOWS
        osName.contains("linux") -> PlatformType.LINUX
        osName.contains("mac") || osName.contains("darwin") -> PlatformType.MACOS
        else -> PlatformType.UNKNOWN
    }
}

actual fun getPlatformCapabilities(): PlatformCapabilities {
    return DesktopPlatformCapabilities()
}

private class DesktopPlatformCapabilities : PlatformCapabilities {
    override val supportsPictureInPicture: Boolean = true // Via floating window
    override val supportsSystemTray: Boolean = true
    override val supportsFilePicker: Boolean = true
    override val supportsBackgroundDownloads: Boolean = true
    override val supportsCast: Boolean = false // TODO: DLNA support in future
}
