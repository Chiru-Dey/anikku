package eu.kanade.tachiyomi.util.system

/**
 * Desktop stub for WebView utilities.
 * 
 * On desktop, we don't use Android WebView.
 * These functions are stubs that may need proper implementation in future sessions.
 */
object WebViewUtil {
    
    /**
     * Check if WebView is available.
     * On desktop, we don't have Android WebView, but we could use JavaFX WebView or similar.
     */
    fun isWebViewAvailable(): Boolean {
        // TODO: Implement desktop WebView detection if needed
        return false
    }
    
    /**
     * Get WebView user agent.
     * Returns a generic desktop user agent string.
     */
    fun getWebViewUserAgent(): String {
        val osName = System.getProperty("os.name")
        val osVersion = System.getProperty("os.version")
        return "Mozilla/5.0 ($osName $osVersion) Anikku/Desktop"
    }
}

/**
 * Cookie manager stub for desktop.
 */
object CookieManager {
    fun getInstance(): CookieManager = this
    
    fun acceptCookie(): Boolean = true
    fun setAcceptCookie(accept: Boolean) {}
    fun removeAllCookies(callback: ((Boolean) -> Unit)?) {
        callback?.invoke(true)
    }
}
