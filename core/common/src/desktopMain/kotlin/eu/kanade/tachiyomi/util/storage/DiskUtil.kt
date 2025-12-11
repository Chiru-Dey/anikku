package eu.kanade.tachiyomi.util.storage

import java.io.File
import java.nio.file.FileStore
import java.nio.file.Files

/**
 * Desktop implementation of disk utilities.
 */
object DiskUtil {
    
    /**
     * Get available space on disk in bytes.
     */
    fun getAvailableStorageSpace(path: File): Long {
        return try {
            val fileStore: FileStore = Files.getFileStore(path.toPath())
            fileStore.usableSpace
        } catch (e: Exception) {
            -1L
        }
    }
    
    /**
     * Get total space on disk in bytes.
     */
    fun getTotalStorageSpace(path: File): Long {
        return try {
            val fileStore: FileStore = Files.getFileStore(path.toPath())
            fileStore.totalSpace
        } catch (e: Exception) {
            -1L
        }
    }
    
    /**
     * Check if external storage is available (always true on desktop).
     */
    fun isExternalStorageAvailable(): Boolean = true
    
    /**
     * Scan media file (no-op on desktop, Android MediaScanner not needed).
     */
    fun scanMedia(file: File, onScanCompleted: ((String?, Any?) -> Unit)? = null) {
        // Desktop doesn't need media scanning like Android
        onScanCompleted?.invoke(file.absolutePath, null)
    }
}
