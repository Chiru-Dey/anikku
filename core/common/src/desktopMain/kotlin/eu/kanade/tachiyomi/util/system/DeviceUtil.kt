package eu.kanade.tachiyomi.util.system

/**
 * Desktop implementation of DeviceUtil.
 * 
 * Provides platform information for desktop systems.
 */
object DeviceUtil {
    
    /**
     * MIUI-specific properties don't exist on desktop.
     */
    val isMiui: Boolean = false
    val miuiMajorVersion: Int? = null
    
    fun isMiuiOptimizationDisabled(): Boolean = false
    
    /**
     * Samsung-specific properties don't exist on desktop.
     */
    val isSamsung: Boolean = false
    val oneUiVersion: Double? = null
    
    /**
     * Desktop doesn't have browser invalidation issues.
     */
    val invalidDefaultBrowsers = emptyList<String>()
    
    /**
     * Check if desktop system has low memory.
     * We consider less than 4GB as low memory for desktop systems.
     */
    fun isLowRamDevice(): Boolean {
        val runtime = Runtime.getRuntime()
        val maxMemory = runtime.maxMemory()
        val totalMemBytes = if (maxMemory == Long.MAX_VALUE) {
            // If max is unlimited, use total memory
            runtime.totalMemory()
        } else {
            maxMemory
        }
        // Consider less than 4GB as low memory on desktop
        return totalMemBytes < 4L * 1024 * 1024 * 1024
    }
    
    /**
     * Get OS name for desktop.
     */
    val osName: String = System.getProperty("os.name")
    
    /**
     * Get OS version for desktop.
     */
    val osVersion: String = System.getProperty("os.version")
    
    /**
     * Get OS architecture.
     */
    val osArch: String = System.getProperty("os.arch")
    
    /**
     * Check if running on Windows.
     */
    val isWindows: Boolean = osName.lowercase().contains("windows")
    
    /**
     * Check if running on Linux.
     */
    val isLinux: Boolean = osName.lowercase().contains("linux")
    
    /**
     * Check if running on macOS.
     */
    val isMacOS: Boolean = osName.lowercase().contains("mac") || osName.lowercase().contains("darwin")
}
