package domain.repository

import domain.model.FileDomain
import java.awt.image.BufferedImage

interface FileSourceRepository {

    fun isExistDirectory(path: String): Boolean
    fun getFilesByPath(path: String) : List<FileDomain>
    fun getFileByPath(path: String): FileDomain?
    fun openFile(path: String): Boolean
    fun deleteFile(path: String)
    fun showFileInExplorer(path: String)
    fun addFolder(path: String, name: String)
    fun addFileTxt(path: String, name: String): Boolean
    fun getDesktopDirForWindows(): String
    fun writeDataToFile(filePath: String, data: String)
    fun moveFile(sourcePath: String, destinationPath: String)
    fun copyFile(sourcePath: String, destinationPath: String)
    fun renameFile(sourcePath: String, destinationPath: String): Boolean
    fun loadImageFromFile(filePath: String): BufferedImage?
    fun loadTextFromFile(filePath: String): String?
    fun saveTextToFile(filePath: String, text: String): Boolean
}