package eu.kanade.tachiyomi.util.system

/**
 * Desktop stubs for Android density conversions.
 * 
 * On desktop, we don't have density-dependent pixels.
 * These extensions return the value as-is or provide reasonable defaults.
 */

/**
 * Convert dp to pixels (on desktop, return as-is since we use pixels directly).
 */
fun Int.dpToPx(): Int = this

/**
 * Convert pixels to dp (on desktop, return as-is).
 */
fun Int.pxToDp(): Int = this

/**
 * Convert sp to pixels (for text, return as-is).
 */
fun Float.spToPx(): Float = this
