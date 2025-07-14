package ui.model

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import domain.model.ArrowDirection
import domain.model.ListMode
import domain.model.ViewMode
import ui.Utils

class ColumnController {

    fun onEvent(event: UserColumnEvent, columnPresenter: ColumnPresenter, opColumnPresenter: ColumnPresenter){

        if (event !is UserColumnEvent.UpdateTextFieldPath)
            columnPresenter.isFocused = true

        opColumnPresenter.isFocused = false

        when(event){

            is UserColumnEvent.AddProgramFromColumn -> { columnPresenter.addProgram(event.programUI.toString()) }

            is UserColumnEvent.SetCurrentBufferedText -> { columnPresenter.editCurrentBufferedText(event.text) }
            is UserColumnEvent.SaveTextToFile -> {
                if(!columnPresenter.saveTextToFile(event.filePath, event.text)){
                    onEvent(UserColumnEvent.ShowDialog(dialog = DialogWindow.ERROR_ACCESS), columnPresenter, opColumnPresenter)
                }
            }

            is UserColumnEvent.Empty -> {}

            is UserColumnEvent.SetItemToListFavorite -> {
                when(columnPresenter.content.viewMode) {
                    ViewMode.LIST_ITEMS -> {
                        if(event.file.isFavorite)
                            columnPresenter.deleteFavorite(event.file.path)
                        else
                            columnPresenter.addListFavorite(event.file.path)
                    }
                    ViewMode.FAVORITE -> {
                        columnPresenter.deleteFavorite(event.file.path)

                    }
                    else -> {}
                }
                opColumnPresenter.updateFavoriteList()

            }
            is UserColumnEvent.UpdateTextFieldSearch -> {}

            is UserColumnEvent.BackFromObserveScreen -> {
                columnPresenter.setViewMode(ViewMode.LIST_ITEMS)
            }
            is UserColumnEvent.SetListMode -> { columnPresenter.setListMode(event.listMode) }
            is UserColumnEvent.AddSelectedListFiles -> {
                val currentFile = columnPresenter.content.selectedFile!!
                columnPresenter.addListSelectedItem(currentFile)
            }
            is UserColumnEvent.OnEqualsWindow -> {
                if(columnPresenter.content.viewMode != ViewMode.LIST_ITEMS) return
                opColumnPresenter.updateListFiles(columnPresenter.content.path)
            }
            is UserColumnEvent.ShowDialog -> {

                when(event.dialog) {
                    DialogWindow.COPY_LIST -> if(!columnPresenter.content.isActiveCopyListButton) return
                    DialogWindow.CUT_LIST -> if(!columnPresenter.content.isActiveCutListButton) return
                    DialogWindow.DELETE_LIST -> if(!columnPresenter.content.isActiveDeleteListButton) return
                    else -> {}
                }

                if(event.dialog == DialogWindow.COPY || event.dialog == DialogWindow.CUT || event.dialog == DialogWindow.DELETE){
                    if(columnPresenter.content.listFiles.isEmpty()){
                        onEvent(
                            event = UserColumnEvent.ShowDialog(dialog = DialogWindow.ERROR_SELECTED_FILE),
                            columnPresenter = columnPresenter,
                            opColumnPresenter = opColumnPresenter
                        )
                        return
                    }
                }

                columnPresenter.dialogWindow = event.dialog

                columnPresenter.dialogColumnData = when(columnPresenter.dialogWindow) {
                    DialogWindow.COPY -> { DialogColumnData.CopyColumnData(toPath = opColumnPresenter.content.path) }
                    DialogWindow.CUT -> { DialogColumnData.CutColumnData(toPath = opColumnPresenter.content.path) }
                    DialogWindow.COPY_LIST -> {
                        val listFromFile = columnPresenter.content.selectedListFiles.map { it.nameWithExtension }
                        val toPath = opColumnPresenter.content.path
                        DialogColumnData.CopyListColumnData(listFromFile = listFromFile, toPath = toPath)
                    }
                    DialogWindow.CUT_LIST -> {
                        val listFromFile = columnPresenter.content.selectedListFiles.map { it.nameWithExtension }
                        val toPath = opColumnPresenter.content.path
                        DialogColumnData.CutListColumnData(listFromFile = listFromFile, toPath = toPath)
                    }
                    //переделать
                    DialogWindow.DELETE_LIST -> {
                        val listFromFile = columnPresenter.content.selectedListFiles.map { it.nameWithExtension }
                        DialogColumnData.DeleteListColumnData
                    }
                    DialogWindow.ADD_PROGRAM -> {
                        val path = columnPresenter.content.selectedFile?.path ?: ""
                        val name = columnPresenter.content.selectedFile?.name ?: ""
                        DialogColumnData.AddProgramColumnData(path = path, name = name)
                    }
                    DialogWindow.ERROR_ACCESS -> {
                        DialogColumnData.ErrorColumnData(Utils.TextResources.ERROR_ACCESS)
                    }
                    DialogWindow.ERROR_SELECTED_FILE -> {
                        DialogColumnData.ErrorColumnData(Utils.TextResources.ERROR_SELECTED_FILE)
                    }
                    DialogWindow.ERROR_SELECTED_DIRECTORY -> {
                        DialogColumnData.ErrorColumnData(Utils.TextResources.ERROR_SELECTED_DIRECTORY)
                    }
                    else -> DialogColumnData.NoneColumnData
                }
            }
            is UserColumnEvent.CloseDialog -> { columnPresenter.dialogWindow = DialogWindow.NONE }

            is UserColumnEvent.AddFolder -> {
                columnPresenter.addFolder(event.name)
                columnPresenter.updateListFiles(columnPresenter.content.path)
                if(columnPresenter.content.path == opColumnPresenter.content.path) opColumnPresenter.updateListFiles(opColumnPresenter.content.path)
            }
            is UserColumnEvent.Back -> {
                if(!event.isAbleBackAction) return
                val currentViewMode = columnPresenter.content.viewMode
                if(
                    currentViewMode == ViewMode.PREVIEW_IMAGE  ||
                    currentViewMode == ViewMode.PREVIEW_TEXT  ||
                    currentViewMode == ViewMode.PATH_NOT_FOUND) {

                    columnPresenter.setViewMode(ViewMode.LIST_ITEMS)
                    columnPresenter.updateListFiles(columnPresenter.content.path)
                    return
                }

                val lastFilePathPair = columnPresenter.back()
                columnPresenter.updateListFiles(lastFilePathPair.second, lastFilePathPair.first)
            }
            is UserColumnEvent.BackFromPathNotFoundScreen -> {
                columnPresenter.setViewMode(ViewMode.LIST_ITEMS)
            }

            is UserColumnEvent.ObserveFile -> {
                if(event.file.isImage) columnPresenter.showImage(event.file.path)
                if(event.file.isText) columnPresenter.showText(event.file.path)
            }

            is UserColumnEvent.Rename -> {

                val fromName = event.file.absolutePath
                val toName = "${columnPresenter.content.path}\\${event.toName}"

                columnPresenter.renameFile(fromName, toName)
                columnPresenter.updateListFiles(columnPresenter.content.path)
                if(columnPresenter.content.path == opColumnPresenter.content.path) opColumnPresenter.updateListFiles(opColumnPresenter.content.path)
            }
            is UserColumnEvent.Visibility -> { columnPresenter.setHidden() }
            is UserColumnEvent.ToDesktop -> { columnPresenter.updateListFiles(columnPresenter.getDesktopPath()) }
            is UserColumnEvent.AddFileTxt -> {
                if(columnPresenter.addFileTxt(event.name)){
                    columnPresenter.updateListFiles(columnPresenter.content.path)
                    if(columnPresenter.content.path == opColumnPresenter.content.path) opColumnPresenter.updateListFiles(opColumnPresenter.content.path)
                }
                else {
                    onEvent(UserColumnEvent.ShowDialog(DialogWindow.ERROR_ACCESS), columnPresenter, opColumnPresenter)
                }
            }
            is UserColumnEvent.SetViewMode -> { columnPresenter.setViewMode(event.viewMode) }
            is UserColumnEvent.SetDateMode -> { columnPresenter.setDateMode(event.dateMode) }

            is UserColumnEvent.ShowInExplorer -> {
                columnPresenter.showFileInExplorer(event.file)
            }
            is UserColumnEvent.SetOrder -> { columnPresenter.setOrder(event.order) }
            is UserColumnEvent.UpdateTextFieldPath -> {
                columnPresenter.setTextFieldPath(event.path)
            }
            is UserColumnEvent.ArrowMoveUp -> { columnPresenter.move(ArrowDirection.UP) }
            is UserColumnEvent.ArrowMoveDown -> { columnPresenter.move(ArrowDirection.DOWN) }
            is UserColumnEvent.ArrowMoveLeft -> { columnPresenter.move(ArrowDirection.LEFT) }
            is UserColumnEvent.ArrowMoveRight -> { columnPresenter.move(ArrowDirection.RIGHT) }
            is UserColumnEvent.OnFocusTextFieldPath -> {
                columnPresenter.setTextFieldPath(columnPresenter.content.path)
                columnPresenter.isFocused = !event.isFocus
            }
            is UserColumnEvent.NavigateToByTextField -> {
                if(event.value == columnPresenter.content.path) return
                if(columnPresenter.isExistDirectory(event.value)) columnPresenter.updateListFiles(event.value)
                else { columnPresenter.setViewMode(ViewMode.PATH_NOT_FOUND) }
            }
            is UserColumnEvent.NavigateToWithKeyBoard -> {
                event.file?.let {
                    if(!it.isDirectory){
                        columnPresenter.openFile(it)
                        return
                    }
                    else{
                        columnPresenter.updateListFiles(it.path)
                    }
                }
            }
            is UserColumnEvent.NavigateToByDrive -> {
                if(columnPresenter.content.path == event.drive.name && columnPresenter.content.viewMode == ViewMode.LIST_ITEMS) return
                if(columnPresenter.isExistDirectory(event.drive.name)) columnPresenter.updateListFiles(event.drive.name)
                else(columnPresenter.updateListFiles(columnPresenter.content.path))
            }

            //click mouse
            is UserColumnEvent.OnClickFile -> { columnPresenter.selectFile(event.file) }
            is UserColumnEvent.OnDoubleClickFile -> {
                if(event.file.isDirectory)
                    columnPresenter.updateListFiles(event.file.path)
                else
                    columnPresenter.openFile(event.file)
            }
        }
    }

    fun onKeyEvent(event: KeyEvent, columnPresenter: ColumnPresenter, opColumnPresenter: ColumnPresenter): Boolean {
        if (event.type != KeyEventType.KeyDown) return false
        when (event.key) {

            Key.DirectionDown -> onEvent(UserColumnEvent.ArrowMoveDown, columnPresenter, opColumnPresenter)
            Key.DirectionUp -> onEvent(UserColumnEvent.ArrowMoveUp, columnPresenter, opColumnPresenter)
            Key.DirectionRight -> {
                if(columnPresenter.content.listMode == ListMode.COLUMN) onEvent(UserColumnEvent.NavigateToWithKeyBoard(columnPresenter.content.selectedFile), columnPresenter, opColumnPresenter)
                else onEvent(UserColumnEvent.ArrowMoveRight, columnPresenter, opColumnPresenter)
            }
            Key.DirectionLeft -> {
                if(columnPresenter.content.listMode == ListMode.COLUMN) onEvent(UserColumnEvent.Back(columnPresenter.content.isSelectedBackAction), columnPresenter, opColumnPresenter)
                else onEvent(UserColumnEvent.ArrowMoveLeft, columnPresenter, opColumnPresenter)
            }
            Key.Spacebar -> {
                if(columnPresenter.content.viewMode == ViewMode.LIST_ITEMS)
                    onEvent(UserColumnEvent.AddSelectedListFiles, columnPresenter, opColumnPresenter)
            }
            Key.F2 -> {
                if(columnPresenter.content.viewMode != ViewMode.LIST_ITEMS) return true
                onEvent(UserColumnEvent.ShowDialog(dialog = DialogWindow.EDIT), columnPresenter, opColumnPresenter)
            }
            Key.F3 -> {
                if(columnPresenter.content.viewMode != ViewMode.LIST_ITEMS) return true
                val dialog = if(columnPresenter.content.selectedListFiles.isEmpty()) DialogWindow.CUT else DialogWindow.CUT_LIST
                onEvent(UserColumnEvent.ShowDialog(dialog = dialog), columnPresenter, opColumnPresenter)
            }
            Key.F4 -> {
                if(columnPresenter.content.viewMode != ViewMode.LIST_ITEMS) return true
                val dialog = if(columnPresenter.content.selectedListFiles.isEmpty()) DialogWindow.COPY else DialogWindow.COPY_LIST
                onEvent(UserColumnEvent.ShowDialog(dialog = dialog), columnPresenter, opColumnPresenter)
            }
            Key.F5 -> {
                if(columnPresenter.content.viewMode != ViewMode.LIST_ITEMS) return true
                val dialog = if(columnPresenter.content.selectedListFiles.isEmpty()) DialogWindow.DELETE else DialogWindow.DELETE_LIST
                onEvent(UserColumnEvent.ShowDialog(dialog = dialog), columnPresenter, opColumnPresenter)
            }
            Key.F6 -> {
                if(columnPresenter.content.viewMode != ViewMode.LIST_ITEMS) return true
                onEvent(UserColumnEvent.ShowDialog(dialog = DialogWindow.ADD_FILE), columnPresenter, opColumnPresenter)
            }
            Key.F7 -> {
                if(columnPresenter.content.viewMode != ViewMode.LIST_ITEMS) return true
                onEvent(UserColumnEvent.ShowDialog(dialog = DialogWindow.ADD_FOLDER), columnPresenter, opColumnPresenter)
            }
            Key.Delete -> {
                if(columnPresenter.content.viewMode != ViewMode.LIST_ITEMS) return true
                val dialog = if(columnPresenter.content.selectedListFiles.isEmpty()) DialogWindow.DELETE else DialogWindow.DELETE_LIST
                onEvent(UserColumnEvent.ShowDialog(dialog = dialog), columnPresenter, opColumnPresenter)
            }
        }
        return true
    }
}