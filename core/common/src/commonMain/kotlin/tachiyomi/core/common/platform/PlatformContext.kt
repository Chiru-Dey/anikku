package tachiyomi.core.common.platform

/**
 * Platform context abstraction to replace Android's Context.
 * 
 * This provides access to platform-specific resources and services
 * in a cross-platform way.
 */
expect class PlatformContext {
    /**
     * Get a string resource by key.
     * Uses Android string resources on Android, and resource bundles on desktop.
     */
    fun getString(key: String): String
    
    /**
     * Get a string resource with format arguments.
     */
    fun getString(key: String, vararg args: Any): String
}

/**
 * Creates a platform context for the current platform.
 */
expect fun createPlatformContext(): PlatformContext
