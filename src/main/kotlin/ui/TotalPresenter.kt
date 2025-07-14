package ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import domain.SettingsUseCases
import domain.model.Theme
import domain.model.WindowAlignment
import kotlinx.coroutines.*
import ui.model.*
import java.io.File

class TotalPresenter(private val settingUseCases: SettingsUseCases) {

    var isLoadingFiles by mutableStateOf(false)
    var progressState by mutableStateOf(0f)
    var totalProgressState by mutableStateOf(0f)
    var allCopiedBytes by mutableStateOf(0L)
    var totalBytes by mutableStateOf(0L)
    var totalCountFiles by mutableStateOf(0)
    var countFilesCopied by mutableStateOf(0)
    var currentFileCoping by mutableStateOf("")
    var titleOperation by mutableStateOf("")
    var currentTheme by mutableStateOf(getTheme())
    var currentWindowAlignment by mutableStateOf(getWindowsAlignment())

    var dialogTotalWindow by mutableStateOf(DialogTotalWindow.NONE)
    var dialogTotalData: DialogTotalData by mutableStateOf(DialogTotalData.NoneTotalData)

    fun getSettings(): SettingsApplication  = SettingsApplication(theme = getTheme(), windowAlignment = getWindowsAlignment())

    fun getWindowsAlignment(): WindowAlignment = settingUseCases.getWindowAlignmentUseCase.invoke()

    fun setWindowsAlignment(windowAlignment: WindowAlignment) {
        settingUseCases.setWindowAlignmentUseCase.invoke(windowAlignment)
        currentWindowAlignment = getWindowsAlignment()
    }

    fun getTheme(): Theme = settingUseCases.getThemeUseCase.invoke()

    fun setTheme(theme: Theme) {

        settingUseCases.setThemeUseCase.invoke(theme)
        Utils.currentTheme = getTheme()
        currentTheme = getTheme()
    }

    var currentListProgram by mutableStateOf(getPrograms())


    fun deleteFiles(listFiles: List<FileUI>) {
        listFiles.forEach { file ->
            val path = file.path
            val component = Component.buildComponent(path)
            val listPathForDelete = component.getListPathForDeleteFile()

            listPathForDelete.forEach { path ->
                settingUseCases.deleteFileUseCase.invoke(path)
            }
        }
    }

    fun copyFiles(listFiles: List<FileUI>, toPath: String, afterDelete: Boolean = false, updateMethod: () -> Unit = {}){

        val listOperation = mutableListOf<FileOperation>()

        listFiles.forEach { file ->
            val fromPath = file.path
            val component = Component.buildComponent(fromPath)
            component.createTreeFolderInFileSystem(toPath)
            listOperation += component.getListFileOperation(toPath).reversed()
            totalBytes += component.getSizeComponent()
            totalCountFiles += component.countFile()

        }
        CoroutineScope(Dispatchers.IO).launch{
            titleOperation = if(afterDelete) "Перемещение" else "Копирование"
            isLoadingFiles = true
            listOperation.forEach {
                currentFileCoping = it.fileName
                copyFileWithProgress(it)
            }
            if(afterDelete) deleteFiles(listFiles)
            resetLoading()
            updateMethod()
        }
    }

    private fun resetLoading() {
        isLoadingFiles = false
        progressState = 0f
        totalProgressState = 0f
        allCopiedBytes = 0L
        totalBytes = 0L
        currentFileCoping = ""
        countFilesCopied = 0
        titleOperation = ""
    }

    fun copyFileWithProgress(fileOperation: FileOperation) {

        val sourceFile = File(fileOperation.fromPath)
        val bufferSize = 1024 // 1 MB buffer size
        val fileSize = fileOperation.sizeFile
        val inputStream = sourceFile.inputStream()
        val outputStream = File(fileOperation.toPath).outputStream()
        var bytesCopied = 0L
        val buffer = ByteArray(bufferSize)
        while (true) {
            val bytesRead = inputStream.read(buffer)
            if (bytesRead == -1) break
            outputStream.write(buffer, 0, bytesRead)
            bytesCopied += bytesRead
            progressState = (bytesCopied.toFloat() / fileSize.toFloat())
            totalProgressState = ((allCopiedBytes + bytesCopied.toFloat()) / totalBytes.toFloat())
        }
        allCopiedBytes += fileSize
        countFilesCopied++

        inputStream.close()
        outputStream.close()
    }

    private fun getPrograms(): List<ProgramUI> {

        val listDefaultProgram = listOf(
            "Проводник*explorer.exe*${Utils.PainterResources.listProgramPainterSmall[4]}*default",
            "Диспетчер задач*ManagerTask.exe*${Utils.PainterResources.listProgramPainterSmall[3]}*default",
            "Калькулятор*explorer.exe*${Utils.PainterResources.PROGRAM_CALCULATOR}*default",
            "Параметры Windows*explorer.exe*${Utils.PainterResources.PROGRAM_SETTINGS}*default",
        )

        val listStringProgram = listDefaultProgram + settingUseCases.getProgramsUseCase.invoke()
        return listStringProgram.mapNotNull { ProgramUI.toProgramUI(it) }
    }

    fun deleteProgram(program: String) {
        settingUseCases.deleteProgramUseCase.invoke(program)
        updateListProgram()
    }
    fun addProgram(program: String) {
        settingUseCases.addProgramUseCase.invoke(program)
        updateListProgram()
    }
    fun updateListProgram(){
        currentListProgram = getPrograms()
    }
    fun openProgram(program: String): Boolean = settingUseCases.openFileUseCase.invoke(program.split('*')[1])

    fun openExplorer() = settingUseCases.openExplorerUseCase.invoke()
    fun openManagerTask(): Boolean = settingUseCases.openManagerTaskUseCase.invoke()
    fun openCalculator() = settingUseCases.openCalculatorUseCase.invoke()
    fun openSettings() = settingUseCases.openSettingsUseCase.invoke()
}