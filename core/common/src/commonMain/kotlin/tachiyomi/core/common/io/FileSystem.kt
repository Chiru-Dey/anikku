package tachiyomi.core.common.io

/**
 * Cross-platform file system abstraction.
 * 
 * Provides access to app-specific directories and file operations
 * that work consistently across Android and desktop platforms.
 */
expect class FileSystem {
    /**
     * Get the application data directory.
     * 
     * Android: /data/data/eu.kanade.tachiyomi/files/
     * Windows: %APPDATA%\Anikku\
     * Linux: ~/.local/share/anikku/
     * macOS: ~/Library/Application Support/Anikku/
     */
    fun getAppDataDir(): String
    
    /**
     * Get the cache directory.
     * 
     * Android: /data/data/eu.kanade.tachiyomi/cache/
     * Windows: %LOCALAPPDATA%\Anikku\cache\
     * Linux: ~/.cache/anikku/
     * macOS: ~/Library/Caches/Anikku/
     */
    fun getCacheDir(): String
    
    /**
     * Get the downloads directory.
     * 
     * Android: User-selected via SAF
     * Desktop: User-configurable, default to ~/Downloads/Anikku/
     */
    fun getDownloadsDir(): String
    
    /**
     * Get the config directory.
     * 
     * Android: Same as app data dir
     * Windows: %APPDATA%\Anikku\config\
     * Linux: ~/.config/anikku/
     * macOS: ~/Library/Application Support/Anikku/config/
     */
    fun getConfigDir(): String
    
    /**
     * Get the logs directory.
     */
    fun getLogsDir(): String
    
    /**
     * Create a directory if it doesn't exist.
     */
    fun createDirectory(path: String): Boolean
    
    /**
     * Check if a file or directory exists.
     */
    fun exists(path: String): Boolean
    
    /**
     * Delete a file or directory.
     */
    fun delete(path: String): Boolean
    
    /**
     * List files in a directory.
     */
    fun listFiles(path: String): List<String>
}

/**
 * Create a file system instance for the current platform.
 */
expect fun createFileSystem(): FileSystem
