package ui.model

import domain.model.ListMode
import domain.model.DateMode
import domain.model.Order
import domain.model.OrderField
import domain.model.OrderType
import domain.model.ViewMode
import java.awt.image.BufferedImage

data class Content(
    val column: Column,
    val viewMode: ViewMode = ViewMode.LIST_ITEMS,
    val listMode: ListMode = ListMode.COLUMN,
    val dateMode: DateMode = DateMode.NUMBER,
    val currentList: List<FileUI> = emptyList(),
    val currentListFavorite: List<FileUI> = emptyList(),
    val currentObserveFile: FileUI? = null,
    val path: String = DEFAULT_PATH,
    val order: Order = Order(OrderField.NAME, OrderType.ASС),
    val textFieldPath: String = DEFAULT_PATH,
    val isHidden: Boolean = false,
    val drives: List<DriveUI> = emptyList(),
    val isSelected: Boolean = false,
    val lastSelectedFileName: String = "",
    val currentBufferedImage: BufferedImage? = null,
    val currentBufferedText: String? = null,
    val isActiveSaveTextButton: Boolean = false
){

    companion object {
        const val DEFAULT_PATH = "C:\\"
    }

    val selectedListFiles: List<FileUI>
        get() = if(viewMode == ViewMode.LIST_ITEMS) listFilesByViewMode.filter { it.isBlue } else emptyList()

    val isActiveCopyListButton: Boolean
        get() = selectedListFiles.isNotEmpty()

    val isActiveCutListButton: Boolean
        get() = selectedListFiles.isNotEmpty()

    val isActiveDeleteListButton: Boolean
        get() = selectedListFiles.isNotEmpty()

    val selectedFile: FileUI?
        get() {
            val selectedFile = listFiles.firstOrNull { it.isSelected }
            selectedFile?.let {
                val index = listFiles.indexOf(it)
                return listFiles[index]
            }
            return null
        }

    val indexSelectedFile: Int?
        get() {
            if(listFiles.isEmpty()) return null
            val selectedFile = listFiles.firstOrNull { it.isSelected }
            selectedFile?.let { return listFiles.indexOf(it) }
            return null
        }

    fun setBackgroundToFileUI(file: FileUI){
        if(!listFiles.contains(file)) return
        val index = listFiles.indexOf(file)
        listFiles[index].isBlue = !listFiles[index].isBlue

    }

    fun setSelectedFileUI(file: FileUI){

        indexSelectedFile?.let { listFiles[it].isSelected = false }

        val newSelectedFile = listFiles.firstOrNull { it == file }
        newSelectedFile?.let {
            val index = listFiles.indexOf(it)
            listFiles[index].isSelected = true
        }
    }

    fun setSelectedFileUI(index: Int){
        indexSelectedFile?.let { listFiles[it].isSelected = false }
        listFiles[index].isSelected = true
    }

    val isSelectedBackAction : Boolean
        get() = (levelPath != 0 && viewMode == ViewMode.LIST_ITEMS) ||
                viewMode == ViewMode.PATH_NOT_FOUND ||
                viewMode == ViewMode.PREVIEW_IMAGE ||
                viewMode == ViewMode.PREVIEW_TEXT

    private val levelPath: Int
        get() {
            val sizeArray = path.split("\\").filter{ it.isNotEmpty() }.size
            return sizeArray - 1
        }

    val currentDrive : DriveUI?
        get() = if(viewMode == ViewMode.LIST_ITEMS) drives.firstOrNull { it.name == path.take(3) } else null

    val countDirectoryAndFilesText: String
        get() {
            if (viewMode != ViewMode.LIST_ITEMS && viewMode != ViewMode.FAVORITE) return ""
            val countFolder = listFiles.count { it.isDirectory }
            val countFiles = listFiles.count { !it.isDirectory }
            return "Папок: $countFolder   Файлов: $countFiles"
        }

    private val listFilesByViewMode: List<FileUI>
        get() = if (viewMode == ViewMode.LIST_ITEMS) currentList else currentListFavorite

    val listFiles: List<FileUI>
        get() {
            var listDirectory = listFilesByViewMode.filter { it.isDirectory }.sortedBy {

                when(order.field) {
                    OrderField.NAME -> it.nameWithExtension.lowercase()
                    OrderField.EXTENSION -> it.fileType.lowercase()
                    OrderField.SIZE -> it.nameWithExtension.lowercase()
                    OrderField.DATE -> it.nameWithExtension.lowercase()
                }
            }

            if(order.field == OrderField.DATE)
                listDirectory = listDirectory.sortedBy { it.dateLong }


            if(order.type == OrderType.DESС) listDirectory = listDirectory.reversed()

            var listFiles = listFilesByViewMode.filter { !it.isDirectory }.sortedBy {
                when(order.field) {
                    OrderField.NAME -> it.nameWithExtension.lowercase()
                    OrderField.EXTENSION -> it.fileType.lowercase()
                    else -> it.nameWithExtension.lowercase()
                }
            }

            if(order.field == OrderField.SIZE)
                listFiles = listFiles.sortedBy { it.sizeLong }
            if(order.field == OrderField.DATE)
                listFiles = listFiles.sortedBy { it.dateLong }


            if(order.type == OrderType.DESС) listFiles = listFiles.reversed()

            val list = listDirectory + listFiles

            if(!isHidden) return list.filter { !it.isHidden }

            return list
        }
}