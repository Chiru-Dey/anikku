package tachiyomi.core.common.i18n

import tachiyomi.core.common.platform.PlatformContext
import tachiyomi.core.common.platform.createPlatformContext

/**
 * Desktop implementation for localization.
 * 
 * Uses the platform context to get localized strings.
 */
object Localize {
    private val context = createPlatformContext()
    
    fun getString(key: String): String {
        return context.getString(key)
    }
    
    fun getString(key: String, vararg args: Any): String {
        return context.getString(key, *args)
    }
}

/**
 * Extension function for getting localized strings on desktop.
 */
fun PlatformContext.stringResource(stringRes: Any): String {
    // For desktop, we'll use the string resource key directly
    // In future sessions, this will integrate with moko-resources
    return when (stringRes) {
        is String -> getString(stringRes)
        else -> stringRes.toString()
    }
}
