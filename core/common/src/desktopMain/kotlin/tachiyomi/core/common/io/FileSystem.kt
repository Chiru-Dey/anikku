package tachiyomi.core.common.io

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

actual class FileSystem {
    actual fun getAppDataDir(): String {
        val appData = when (val osName = System.getProperty("os.name").lowercase()) {
            "windows", "windows 10", "windows 11" -> {
                System.getenv("APPDATA") ?: System.getProperty("user.home")
            }
            "linux" -> {
                System.getenv("XDG_DATA_HOME") 
                    ?: "${System.getProperty("user.home")}/.local/share"
            }
            "mac os x", "macos" -> {
                "${System.getProperty("user.home")}/Library/Application Support"
            }
            else -> System.getProperty("user.home")
        }
        return "$appData/Anikku"
    }
    
    actual fun getCacheDir(): String {
        val cacheDir = when (System.getProperty("os.name").lowercase()) {
            "windows", "windows 10", "windows 11" -> {
                System.getenv("LOCALAPPDATA") ?: System.getProperty("user.home")
            }
            "linux" -> {
                System.getenv("XDG_CACHE_HOME") 
                    ?: "${System.getProperty("user.home")}/.cache"
            }
            "mac os x", "macos" -> {
                "${System.getProperty("user.home")}/Library/Caches"
            }
            else -> System.getProperty("user.home")
        }
        return "$cacheDir/Anikku"
    }
    
    actual fun getDownloadsDir(): String {
        val userHome = System.getProperty("user.home")
        return "$userHome/Downloads/Anikku"
    }
    
    actual fun getConfigDir(): String {
        val configDir = when (System.getProperty("os.name").lowercase()) {
            "windows", "windows 10", "windows 11" -> {
                "${getAppDataDir()}/config"
            }
            "linux" -> {
                System.getenv("XDG_CONFIG_HOME") 
                    ?: "${System.getProperty("user.home")}/.config/anikku"
            }
            "mac os x", "macos" -> {
                "${getAppDataDir()}/config"
            }
            else -> "${getAppDataDir()}/config"
        }
        return configDir
    }
    
    actual fun getLogsDir(): String {
        return "${getCacheDir()}/logs"
    }
    
    actual fun createDirectory(path: String): Boolean {
        return try {
            val file = File(path)
            file.mkdirs()
            true
        } catch (e: Exception) {
            false
        }
    }
    
    actual fun exists(path: String): Boolean {
        return File(path).exists()
    }
    
    actual fun delete(path: String): Boolean {
        return try {
            val file = File(path)
            if (file.isDirectory) {
                file.deleteRecursively()
            } else {
                file.delete()
            }
        } catch (e: Exception) {
            false
        }
    }
    
    actual fun listFiles(path: String): List<String> {
        return try {
            File(path).listFiles()?.map { it.absolutePath } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}

actual fun createFileSystem(): FileSystem {
    return FileSystem()
}
