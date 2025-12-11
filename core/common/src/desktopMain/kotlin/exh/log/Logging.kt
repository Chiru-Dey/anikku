package exh.log

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity

/**
 * Desktop implementation of logging using Kermit.
 */
object Logging {
    private val logger = Logger.withTag("Anikku")
    
    fun d(tag: String, message: String) {
        logger.d { "[$tag] $message" }
    }
    
    fun i(tag: String, message: String) {
        logger.i { "[$tag] $message" }
    }
    
    fun w(tag: String, message: String) {
        logger.w { "[$tag] $message" }
    }
    
    fun e(tag: String, message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            logger.e(throwable) { "[$tag] $message" }
        } else {
            logger.e { "[$tag] $message" }
        }
    }
    
    fun v(tag: String, message: String) {
        logger.v { "[$tag] $message" }
    }
}

// Desktop doesn't have Android Log class, so provide stubs
object Log {
    const val VERBOSE = 2
    const val DEBUG = 3
    const val INFO = 4
    const val WARN = 5
    const val ERROR = 6
    
    fun d(tag: String, message: String): Int {
        Logging.d(tag, message)
        return 0
    }
    
    fun i(tag: String, message: String): Int {
        Logging.i(tag, message)
        return 0
    }
    
    fun w(tag: String, message: String): Int {
        Logging.w(tag, message)
        return 0
    }
    
    fun e(tag: String, message: String, throwable: Throwable? = null): Int {
        Logging.e(tag, message, throwable)
        return 0
    }
    
    fun v(tag: String, message: String): Int {
        Logging.v(tag, message)
        return 0
    }
}
