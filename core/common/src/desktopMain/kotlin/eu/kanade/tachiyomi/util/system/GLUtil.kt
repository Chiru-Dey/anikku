package eu.kanade.tachiyomi.util.system

/**
 * Desktop implementation of GL utilities.
 * 
 * On desktop, OpenGL capabilities are queried differently than on Android.
 */
object GLUtil {
    /**
     * Get max texture size supported by the GPU.
     * 
     * On desktop, we return a safe default value.
     * Actual GL queries would require LWJGL or similar libraries.
     */
    fun getMaxTextureSize(): Int {
        // Safe default that works on most desktop GPUs
        return 8192
    }
}
