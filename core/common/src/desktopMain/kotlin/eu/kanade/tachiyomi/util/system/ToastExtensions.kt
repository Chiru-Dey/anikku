package eu.kanade.tachiyomi.util.system

import co.touchlab.kermit.Logger

/**
 * Desktop stub for Toast notifications.
 * 
 * On Android, these show toast messages. On desktop, we log them for now.
 * In future sessions, we'll implement proper desktop notifications.
 */

private val logger = Logger.withTag("Toast")

/**
 * Show a short toast message (desktop: log as info).
 */
fun toast(message: String, duration: Int = 0) {
    logger.i { "Toast: $message" }
    // TODO: Implement desktop notification in Session 11
}

/**
 * Show a long toast message (desktop: log as info).
 */
fun toastLong(message: String) {
    logger.i { "Toast (long): $message" }
    // TODO: Implement desktop notification in Session 11
}
