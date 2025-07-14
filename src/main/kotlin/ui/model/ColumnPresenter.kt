package ui.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import domain.UseCases
import domain.model.ArrowDirection
import domain.model.DateMode
import domain.model.ListMode
import domain.model.Order
import domain.model.ResponseMove
import domain.model.Settings
import domain.model.ViewMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ColumnPresenter(private val useCases: UseCases) {

    fun getDesktopPath() = useCases.getDesktopDirForWindowsUseCase.invoke()

    private val columnString: String
        get() = if(content.column == Column.FIRST) "first" else "second"

    var dialogWindow by mutableStateOf(DialogWindow.NONE)
    var dialogColumnData: DialogColumnData by mutableStateOf(DialogColumnData.NoneColumnData)

    var isFocused by mutableStateOf(false)
    var isLoadingIcon by mutableStateOf(false)

    var content by mutableStateOf(
        Content(
            column = Column.FIRST,
            path = "C:\\",
            textFieldPath = "C:\\"
        )
    )

    fun addProgram(program: String) = useCases.addProgramUseCase.invoke(program)

    fun editCurrentBufferedText(text: String?) {
        content = content.copy(currentBufferedText = text, isActiveSaveTextButton = true)
    }

    fun saveTextToFile(filePath: String, text: String): Boolean {
        if(useCases.saveTextToFileUseCase.invoke(filePath, text)){
            content = content.copy(isActiveSaveTextButton = false)
            return true
        }
        else
            return false
    }

    fun initContent(){

        if(!isExistDirectory(getPath())) {
            useCases.setPathUseCase.invoke(columnString, Content.DEFAULT_PATH)
            content = content.copy(path = getPath())
        }

        content = content.copy(
            path = getPath(),
            order = getOrder(),
            listMode = getListMode(),
            dateMode = getDateMode(),
            currentListFavorite = getListFavorite().mapNotNull { convertPathToFileUI(it) }
        )
        updateListFiles(content.path)
    }

    fun addListFavorite(star: String) {
        useCases.addFavoriteUseCase.invoke(star)
        updateFavoriteList()
    }

    fun deleteFavorite(star: String) {
        useCases.deleteFavoriteUseCase.invoke(star)
        updateFavoriteList()
    }

    private fun convertPathToFileUI(path: String): FileUI? {
        val fileDomain = useCases.getOneFileUseCase.invoke(path)
        return fileDomain?.let { FileUI.toFileUI(it).copy(isFavorite = true) }
    }

    fun updateFavoriteList() {
        val listStar = getListFavorite().mapNotNull { convertPathToFileUI(it) }
        content = content.copy(currentListFavorite = listStar)

        val currentListFavorite = content.currentListFavorite
        content.currentList.forEach { it.isFavorite = currentListFavorite.map { item -> item.path }.contains(it.path) }
    }

    private fun updateContent() {
        content = content.copy(isSelected = !content.isSelected)
        content = content.copy(isSelected = !content.isSelected)
    }

    fun addListSelectedItem(file: FileUI) {
        content.setBackgroundToFileUI(file)
        updateContent()
    }

    fun selectFile(file: FileUI) {
        content.setSelectedFileUI(file)
        updateContent()
    }

    private fun selectFile(index: Int){
        content.setSelectedFileUI(index)
        updateContent()
    }

    fun setHidden(){
        content = content.copy(isHidden = !content.isHidden)
    }

    fun setTextFieldPath(value: String) {
        content = content.copy(textFieldPath = value.replace("\n", ""))
    }

    fun setOrder(order: Order) {
        useCases.setOrderUseCase.invoke(columnString, order)
        content = content.copy(order = getOrder())
    }

    fun setListMode(listMode: ListMode) {
        useCases.setListModeUseCase.invoke(columnString, listMode)
        content = content.copy(listMode = getListMode())
    }

    fun setDateMode(dateMode: DateMode) {
        useCases.setDateModeUseCase.invoke(columnString, dateMode)
        content = content.copy(dateMode = getDateMode())
    }

    fun showImage(path: String) {

        content = content.copy(currentBufferedImage = null)
        isLoadingIcon = true
        content = content.copy(viewMode = ViewMode.PREVIEW_IMAGE)

        val context = Dispatchers.IO
        CoroutineScope(context).launch {

            val bufferedImage = withContext(context) {
                useCases.loadImageFromFileUseCase.invoke(path)
            }
            content = content.copy(currentBufferedImage = bufferedImage)
            isLoadingIcon = false
        }

    }

    fun showText(path: String) {

        content = content.copy(currentBufferedText = null, currentObserveFile = content.selectedFile)
        isLoadingIcon = true
        content = content.copy(viewMode = ViewMode.PREVIEW_TEXT)

        val context = Dispatchers.IO
        CoroutineScope(context).launch {

            val bufferedText = withContext(context) {
                useCases.loadTextFromFileUseCase.invoke(path)
            }
            content = content.copy(currentBufferedText = bufferedText, isActiveSaveTextButton = false)
            isLoadingIcon = false
        }

    }

    fun updateListFiles(newPath: String, lastFilePath: String = ""): Boolean {

        if(!(useCases.isExistDirectoryUseCase.invoke(newPath))) return false

        val context = Dispatchers.IO
        var result: Boolean? = null

        CoroutineScope(context).launch {
            result = update(context, newPath, lastFilePath)
        }
        return result == true
    }

    private suspend fun update(context: CoroutineContext, newPath: String, lastFilePath: String): Boolean {

        val settings = Settings(
            path = newPath,
            viewMode = content.viewMode
        )
        isLoadingIcon = true

        val listFileDomain = withContext(context) {

            try {
                useCases.getFileUseCase.invoke(settings)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
        isLoadingIcon  = false

        // Обновление текущего списка файлов

        if(newPath.isNotEmpty() && newPath != content.path){
            useCases.setPathUseCase.invoke(columnString, newPath)
            content = content.copy(path = getPath())
        }

        val newCurrentList = listFileDomain.map { FileUI.toFileUI(it) }

        content = content.copy(
            currentList = newCurrentList,
            textFieldPath = newPath,
            drives = getDrives()
        )

        // Проверка, если список файлов пустой, вернуть false
        if (content.listFiles.isEmpty()) return false

        // Обновление других полей контента
        updateFavoriteList()
        content = content.copy(viewMode = ViewMode.LIST_ITEMS)

        try{
            val selectedFile = if(lastFilePath.isEmpty()) content.listFiles.first() else content.listFiles.first {
                it.absolutePath.lowercase() == lastFilePath.lowercase()
            }
            selectFile(selectedFile)
        }
        catch (ex: Exception) {
            println("ERROR SELECTED FILE PATH exception ${ex.message}")
        }

        return true
    }

    fun back() : Pair<String, String> {
        val oldPath = content.path
        val listPath = oldPath.split("\\")
        val lastSize = listPath.last().length + if (listPath.size == 2) 0 else 1
        val newLength: Int = oldPath.length - lastSize
        val newPath = oldPath.take(newLength)

        return Pair(oldPath, newPath)
    }

    fun move(arrowDirection: ArrowDirection) {

        val responseMove = with(content) {
            useCases.moveUseCase.invoke(
                currentIndex = indexSelectedFile,
                listCount = listFiles.size,
                arrowDirection = arrowDirection,
                listMode = listMode,
            )
        }
        when(responseMove){
            is ResponseMove.Failure -> {}
            is ResponseMove.Success -> { selectFile(responseMove.index) }
        }
    }

    fun setViewMode(viewMode: ViewMode) {
        content = content.copy(viewMode = viewMode, currentListFavorite = getListFavorite().mapNotNull { convertPathToFileUI(it) })
    }

    fun openFile(file: FileUI) = useCases.openFileUseCase.invoke(file.path)
    fun showFileInExplorer(file: FileUI) = useCases.showFileInExplorerUseCase.invoke(file.path)
    fun addFolder(name: String) = useCases.addFolderUseCase.invoke(path = content.path, name = name)
    fun addFileTxt(name: String): Boolean = useCases.addFileTxtUseCase.invoke(path = content.path, name = name)
    fun renameFile(fromName: String, toName: String): Boolean = useCases.renameFileUseCase.invoke(fromName, toName)
    private fun getListFavorite(): List<String> = useCases.getFavoriteUseCase.invoke()
    fun isExistDirectory(path: String) = useCases.isExistDirectoryUseCase.invoke(path)

    private fun getDrives() = useCases.getDriveUseCase.invoke().map { DriveUI.toDriverUI(it) }
    private fun getOrder(): Order = useCases.getOrderUseCase.invoke(columnString)
    private fun getPath(): String = useCases.getPathUseCase.invoke(columnString)

    private fun getListMode(): ListMode = useCases.getListModeUseCase.invoke(columnString)
    private fun getDateMode(): DateMode = useCases.getDateModeUseCase.invoke(columnString)
}