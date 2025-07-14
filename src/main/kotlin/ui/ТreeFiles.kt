package ui

import java.io.File
import java.util.LinkedList

data class FileOperation(val fileName: String, val fromPath: String, val toPath: String, val sizeFile: Long)

interface Component {

    val name: String

    fun getListChildComponent(): List<Component>
    fun getSizeComponent() : Long
    fun printAllComponent(): String
    fun createTreeFolderInFileSystem(path: String)
    fun countFolder(): Int
    fun countFile(): Int
    fun getListFileOperation(toPath: String): List<FileOperation>
    fun getListPathForDeleteFile(): List<String>

    companion object {
        fun mapToComponent(file: File): Component {
            val nameWithoutExtension = file.nameWithoutExtension
            val name = file.name
            val path = file.path
            val size = file.length()

            return if (file.isDirectory) FolderComponent(nameWithoutExtension, path, size)
            else FileComponent(name, path, size)
        }

        fun buildComponent(path: String): Component {
            return mapToComponent(File(path))
        }
    }
}

// Класс Leaf представляет листовые элементы дерева
class FileComponent(
    override val name: String,
    val path: String,
    val size: Long,
) : Component {

    override fun getListChildComponent(): List<Component>  = emptyList()
    override fun getSizeComponent(): Long = size
    override fun printAllComponent(): String = "FILE $name"
    override fun createTreeFolderInFileSystem(path: String) {  }
    override fun countFolder(): Int = 0
    override fun countFile(): Int = 0

    override fun getListPathForDeleteFile(): List<String>{
        return listOf(path)
    }

    override fun getListFileOperation(toPath: String): List<FileOperation>{

        val newToPath = "${toPath}\\${name}"

        return listOf(FileOperation(
            fileName = name,
            fromPath = path,
            toPath = newToPath,
            sizeFile = size)
        )

    }
}

// Класс Composite представляет составной элемент дерева
class FolderComponent(
    override val name: String,
    val path: String,
    val size: Long,
    private val children: MutableList<Component> = mutableListOf(),

) : Component {

    init {
        children += initListChildComponent()
    }

    private fun getSortChildren(): List<Component>  {
        return children.sortedBy { it is FolderComponent }
    }

    override fun getListChildComponent(): List<Component> {

        val list = mutableListOf<Component>()
        children.forEach {
            list += it.getListChildComponent()
        }
        return children + list
    }

    override fun getSizeComponent(): Long {
        var allSize = 0L
        getSortChildren().forEach {
            allSize += it.getSizeComponent()
        }
        return allSize
    }

    override fun printAllComponent(): String {
        return buildString {
            append("FOLDER $name\n")
            children.forEach {
                append("    -${it.printAllComponent()}\n")
            }
        }
    }

    override fun createTreeFolderInFileSystem(path: String) {
        createFolder(path)

        getSortChildren().forEach {
            it.createTreeFolderInFileSystem(path = "$path\\$name")
        }

    }

    override fun countFolder(): Int {
        var count = getSortChildren().count { it is FolderComponent }
        getSortChildren().forEach {
            count += it.countFolder()
        }
        return count
    }

    override fun countFile(): Int {
        var count = getSortChildren().count { it is FileComponent }
        getSortChildren().forEach {
            count += it.countFile()
        }
        return count
    }

    override fun getListFileOperation(toPath: String): List<FileOperation> {

        val list = mutableListOf<FileOperation>()

        getSortChildren().forEach {
            list += it.getListFileOperation(toPath = "$toPath\\$name")
        }

        return list
    }

    override fun getListPathForDeleteFile(): List<String> {

        val list = mutableListOf<String>()

        getSortChildren().forEach {
            list += it.getListPathForDeleteFile()
        }
        return list + path
    }

    private fun createFolder(path: String){
        try {
            val fullPath = File(path, name)

            if (!fullPath.exists()) {
                // Создаем директорию
                val created = fullPath.mkdir()

                if (created) {
                    //println("Директория успешно создана: $fullPath")
                } else {
                    //println("Не удалось создать директорию: $fullPath")
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

    private fun initListChildComponent(): List<Component> {
        val rootFolder = File(path)
        val listComponent =  rootFolder.listFiles()?.mapNotNull { Component.mapToComponent(it) }

        return listComponent ?: emptyList()
    }
}