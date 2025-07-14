package ui.model

import ui.TotalPresenter
import ui.Utils

class TotalController(
    private var currentColumnPresenter: ColumnPresenter? = null,
    private var opColumnPresenter: ColumnPresenter? = null,
    private var columnController: ColumnController? = null
) {

    fun setPresenters(currentColumnPresenter: ColumnPresenter, opColumnPresenter: ColumnPresenter){
        this.currentColumnPresenter = currentColumnPresenter
        this.opColumnPresenter = opColumnPresenter
    }
    fun setColumnController(columnController: ColumnController?){
        this.columnController = columnController
    }

    fun onEvent(event: UserTotalEvent, totalPresenter: TotalPresenter) {


        when (event) {

            is UserTotalEvent.ApplySettings -> {
                totalPresenter.setTheme(event.settings.theme)
                totalPresenter.setWindowsAlignment(event.settings.windowAlignment)
            }

            is UserTotalEvent.Copy -> {
                totalPresenter.copyFiles(event.listFiles, event.toPath){
                    opColumnPresenter?.let { it.updateListFiles(it.content.path) }
                }

            }

            is UserTotalEvent.Cut -> {
                totalPresenter.copyFiles(event.listFiles, event.toPath, true){
                    opColumnPresenter?.let { it.updateListFiles(it.content.path) }
                    currentColumnPresenter?.let { it.updateListFiles(it.content.path) }
                }

            }

            is UserTotalEvent.Delete -> {
                totalPresenter.deleteFiles(event.listFiles)
                println("delete completed")
                val currentPath = currentColumnPresenter?.content?.path ?: ""
                val opCurrentPath = opColumnPresenter?.content?.path ?: ""
                if(currentPath == opCurrentPath){
                    opColumnPresenter?.let { it.updateListFiles(it.content.path) }
                }
                currentColumnPresenter?.let { it.updateListFiles(it.content.path) }
            }

            is UserTotalEvent.AddProgram -> {
                totalPresenter.addProgram(event.programUI.toString())
            }
            is UserTotalEvent.DeleteProgram -> { totalPresenter.deleteProgram(event.programUI.toString()) }
            is UserTotalEvent.OpenProgram -> {
                if(!totalPresenter.openProgram(event.programUI.toString()))
                     onEvent(
                         event = UserTotalEvent.ShowDialog(
                            dialogTotalWindow = DialogTotalWindow.ERROR_FOUND,
                            dialogTotalData = DialogTotalData.ErrorTotalData(Utils.TextResources.ERROR_FOUND_FILE)),
                         totalPresenter = totalPresenter)

            }
            is UserTotalEvent.ShowDialog -> {
                totalPresenter.dialogTotalWindow = event.dialogTotalWindow
                totalPresenter.dialogTotalData = event.dialogTotalData
            }
            is UserTotalEvent.CloseDialog -> { totalPresenter.dialogTotalWindow = DialogTotalWindow.NONE }

            //default programs
            is UserTotalEvent.OpenExplorer -> { totalPresenter.openExplorer() }
            is UserTotalEvent.OpenManagerTask -> {
                if(!totalPresenter.openManagerTask())
                    onEvent(
                        event = UserTotalEvent.ShowDialog(
                            dialogTotalWindow = DialogTotalWindow.ERROR_ACCESS,
                            dialogTotalData = DialogTotalData.ErrorTotalData(Utils.TextResources.ERROR_ACCESS)
                        ),
                        totalPresenter = totalPresenter
                    )
            }
            is UserTotalEvent.OpenCalculator -> { totalPresenter.openCalculator() }
            is UserTotalEvent.OpenSettings -> { totalPresenter.openSettings() }
        }
    }
}