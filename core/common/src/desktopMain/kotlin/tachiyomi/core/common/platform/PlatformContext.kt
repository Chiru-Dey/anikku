package tachiyomi.core.common.platform

import java.util.ResourceBundle

actual class PlatformContext {
    // TODO: Integrate with moko-resources in future sessions
    private val bundle: ResourceBundle? = try {
        ResourceBundle.getBundle("strings")
    } catch (e: Exception) {
        null
    }
    
    actual fun getString(key: String): String {
        return bundle?.getString(key) ?: key
    }
    
    actual fun getString(key: String, vararg args: Any): String {
        val template = getString(key)
        return String.format(template, *args)
    }
}

actual fun createPlatformContext(): PlatformContext {
    return PlatformContext()
}
