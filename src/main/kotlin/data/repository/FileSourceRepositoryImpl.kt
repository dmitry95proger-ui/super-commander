package data.repository

import domain.model.FileDomain
import domain.repository.FileSourceRepository
import java.io.File
import java.io.PrintWriter
import kotlin.String
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.awt.image.BufferedImage
import java.nio.charset.StandardCharsets
import javax.imageio.ImageIO
import java.nio.charset.Charset.forName

class FileSourceRepositoryImpl: FileSourceRepository {

    override fun isExistDirectory(path: String): Boolean = File(path).exists()

    override fun getFilesByPath(path: String): List<FileDomain> {
        val rootDir = File(path)
        if(!rootDir.exists()) return emptyList()
        val filesAndDirs = rootDir.listFiles()?.sortedBy { it.isDirectory } ?: emptyList()
        return filesAndDirs.mapNotNull { FileDomain.toFileDomain(it) }
    }

    override fun getFileByPath(path: String): FileDomain? {
        val file = File(path)
        if(!file.exists()) return null
        return FileDomain.toFileDomain(file)
    }

    override fun openFile(path: String): Boolean {
        val file = File(path)
        try {
            if (file.exists()) {
                // Используем команду 'start' для открытия файла
                Runtime.getRuntime().exec(arrayOf("cmd", "/c", "start", "\"\"", file.absolutePath))
                return true
            } else {
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    override fun deleteFile(path: String) {
        try {
            // Создаем полный путь до новой директории
            val file = File(path)

            file.walkTopDown().forEach { file ->
                if (file.isFile) {
                    file.delete()
                } else if (file.isDirectory) {
                    file.delete()
                }
            }
            // Удаление самой директории
            file.delete()

        }catch(e: Exception) {
            println("Не удается удалить файл")
        }
    }

    override fun showFileInExplorer(path: String) {
        val file = File(path)
        Runtime.getRuntime().exec("explorer.exe /select,\"${file.absolutePath}\"")
    }

    override fun addFolder(parentPath: String, newDirectoryName: String) {

        try {
            // Создаем полный путь до новой директории
            val fullPath = File(parentPath, newDirectoryName)
            // Проверяем, существует ли уже директория
            if (!fullPath.exists()) {
                // Создаем директорию
                val created = fullPath.mkdir()

                if (created) {
                    println("Директория успешно создана: $fullPath")
                } else {
                    println("Не удалось создать директорию: $fullPath")
                }
            } else {
                println("Директория уже существует: $fullPath")
            }
        } catch (e: Exception) {
            // Обработка исключений
            when (e) {
                is SecurityException -> println("Недостаточно прав доступа для создания директории.")
                else -> println("Произошла ошибка при создании директории: ${e.message}")
            }
        }

    }

    override fun addFileTxt(path: String, name: String): Boolean {
        val file = File("$path\\$name")
        // Создаем файл, если он еще не существует
        try{
            if (!file.exists()) {
                file.createNewFile()
            }
            return true
        }
        catch(e: Exception){
            return false
        }
    }

    override fun getDesktopDirForWindows(): String = System.getenv("USERPROFILE") + "\\Desktop"

    override fun writeDataToFile(path: String, data: String) {
        val file = File(path)
        if(!file.exists()) return
        // Создаем PrintWriter для записи данных в файл
        PrintWriter(file).use { writer ->
            writer.println(data)
        }
    }

    override fun moveFile(sourcePath: String, destinationPath: String) {
        val sourceDir = File(sourcePath)
        val targetDir = File(destinationPath)
        try {
            sourceDir.walkTopDown().forEach { file ->
                val relativePath = file.relativeTo(sourceDir)
                val targetFile = File(targetDir, relativePath.path)
                if (file.isFile) {
                    Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
                } else if (file.isDirectory && !targetFile.exists()) {
                    targetFile.mkdir()
                }
            }
            // Удаление исходной директории после успешного копирования
            sourceDir.deleteRecursively()
        }
        catch (e: Exception) {

        }
    }

    override fun copyFile(sourcePath: String, destinationPath: String) {
        try {
            val sourceDir = File(sourcePath)
            val targetDir = File(destinationPath)
            sourceDir.walkTopDown().forEach { file ->
                val relativePath = file.relativeTo(sourceDir)
                val targetFile = File(targetDir, relativePath.path)

                if (file.isFile) {
                    Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
                } else if (file.isDirectory && !targetFile.exists()) {
                    targetFile.mkdir()
                }
            }
        }
        catch (e: Exception) {

        }
    }

    override fun renameFile(oldName: String, newName: String): Boolean {
        val oldFile = File(oldName)

        // Проверяем, существует ли файл
        if (!oldFile.exists()) {
            println("Файл $oldName не существует.")
            return false
        }
        // Создаем объект File для нового имени файла
        val newFile = File(newName)

        // Переименовываем файл
        val isRenamed = oldFile.renameTo(newFile)

        if (isRenamed) {
            println("Файл успешно переименован из $oldName в $newName")
        } else {
            println("Не удалось переименовать файл из $oldName в $newName")
        }
        return isRenamed
    }

    override fun loadImageFromFile(filePath: String): BufferedImage? {
        val file = File(filePath)
        return if (file.exists()) {
            ImageIO.read(file)
        } else {
            null
        }
    }

    override fun loadTextFromFile(filePath: String): String? {

        val file = File(filePath)
        return if (file.exists() && file.canRead()) {
            //file.readText()
            val charset = forName("Windows-1251")
            //String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8)
            Files.readString(Paths.get(filePath), charset)
        } else {
            null
        }
    }

    override fun saveTextToFile(filePath: String, text: String): Boolean {
        try {
            Files.write(Paths.get(filePath), text.toByteArray(StandardCharsets.UTF_8))
            return true
        }
        catch (e: Exception) {
            println(e.message)
            return false
        }
    }
}












