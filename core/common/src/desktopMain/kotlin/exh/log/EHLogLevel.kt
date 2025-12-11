package exh.log

/**
 * Desktop stub for EHLogLevel.
 * 
 * This is Android-specific logging configuration.
 * On desktop, we use Kermit for logging which has its own configuration.
 */
object EHLogLevel {
    // Desktop doesn't need Android preference-based log level
    // Kermit handles log levels via its own configuration
    
    fun shouldLog(level: Int): Boolean {
        // On desktop, always allow logging (can be configured via Kermit)
        return true
    }
}
