package tachiyomi.core.common.storage

import tachiyomi.core.common.platform.createFileSystem
import java.io.File

class DesktopStorageFolderProvider : FolderProvider {
    
    private val fileSystem = createFileSystem()
    
    override fun directory(): File {
        val dir = File(fileSystem.getAppDataDir())
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }
    
    override fun path(): String {
        return directory().toURI().toString()
    }
}
