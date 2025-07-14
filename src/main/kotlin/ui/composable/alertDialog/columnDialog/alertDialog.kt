package ui.composable.alertDialog.columnDialog

import androidx.compose.runtime.*
import ui.model.*

@Composable
fun showColumnDialog(
    content: Content,
    dialogWindow: DialogWindow,
    onEvent: (UserColumnEvent) -> Unit,
    dialogColumnData: DialogColumnData,
    onTotalEvent: (UserTotalEvent) -> Unit
){
    val isVisibleState = remember { mutableStateOf(false) }
    var isVisible by isVisibleState

    when(dialogWindow){
        DialogWindow.ERROR_SELECTED_DIRECTORY -> {
            if(dialogColumnData is DialogColumnData.ErrorColumnData){
                showDialogError(
                    onEvent = { onEvent(it) },
                    textError = dialogColumnData.message,
                    settings = SettingsDialog.getInstance(),
                    isVisible = isVisibleState
                )
                isVisible = true
            }
        }
        DialogWindow.ERROR_SELECTED_FILE -> {
            if(dialogColumnData is DialogColumnData.ErrorColumnData){
                showDialogError(
                    onEvent = { onEvent(it) },
                    textError = dialogColumnData.message,
                    settings = SettingsDialog.getInstance(),
                    isVisible = isVisibleState
                )
                isVisible = true
            }
        }
        DialogWindow.ERROR_ACCESS -> {
            if(dialogColumnData is DialogColumnData.ErrorColumnData){
                showDialogError(
                    onEvent = { onEvent(it) },
                    textError = dialogColumnData.message,
                    settings = SettingsDialog.getInstance(),
                    isVisible = isVisibleState
                )
                isVisible = true
            }
        }
        DialogWindow.ADD_PROGRAM -> {
            if(dialogColumnData is DialogColumnData.AddProgramColumnData){
                showDialogColumnAddProgram(
                    onEvent = { onEvent(it) },
                    dialogColumnData = dialogColumnData,
                    settings = SettingsDialog.getInstance(),
                    isVisible = isVisibleState
                )
                isVisible = true
            }
        }
        DialogWindow.EDIT -> {
            showDialogRename(
                onEvent = { onEvent(it) },
                file = content.selectedFile,
                settings = SettingsDialog.getInstance(),
                isVisible = isVisibleState
            )
            isVisible = true
        }
        DialogWindow.ADD_FILE -> {
            showDialogAddFile (
                onEvent = { onEvent(it) },
                settings = SettingsDialog.getInstance(),
                isVisible = isVisibleState
            )
            isVisible = true
        }
        DialogWindow.ADD_FOLDER -> {
            showDialogAddFolder (
                onEvent = { onEvent(it) },
                settings = SettingsDialog.getInstance(),
                isVisible = isVisibleState
            )
            isVisible = true
        }
        DialogWindow.COPY -> {
            if(dialogColumnData is DialogColumnData.CopyColumnData) {
                showDialogCopy(
                    file = content.selectedFile,
                    onEvent = { onEvent(it) },
                    dialogColumnData = dialogColumnData,
                    settings = SettingsDialog.getInstance(),
                    isVisible = isVisibleState,
                    onTotalEvent = { onTotalEvent(it) }
                )
                isVisible = true
            }
        }
        DialogWindow.COPY_LIST -> {
            if(dialogColumnData is DialogColumnData.CopyListColumnData) {
                showDialogListCopy(
                    listFiles = content.selectedListFiles,
                    onEvent = { onEvent(it) },
                    dialogColumnData = dialogColumnData,
                    settings = SettingsDialog.getInstance(),
                    isVisible = isVisibleState,
                    onTotalEvent = { onTotalEvent(it) }
                )
                isVisible = true
            }
        }
        DialogWindow.CUT -> {
            if(dialogColumnData is DialogColumnData.CutColumnData) {
                showDialogCut(
                    file = content.selectedFile,
                    onEvent = { onEvent(it) },
                    dialogColumnData = dialogColumnData,
                    settings = SettingsDialog.getInstance(),
                    isVisible = isVisibleState,
                    onTotalEvent = { onTotalEvent(it) }
                )
                isVisible = true
            }
        }
        DialogWindow.CUT_LIST -> {
            if(dialogColumnData is DialogColumnData.CutListColumnData) {
                showDialogListCut(
                    listFiles = content.selectedListFiles,
                    onEvent = { onEvent(it) },
                    dialogColumnData = dialogColumnData,
                    settings = SettingsDialog.getInstance(),
                    isVisible = isVisibleState,
                    onTotalEvent = { onTotalEvent(it) }
                )
                isVisible = true
            }
        }
        DialogWindow.DELETE -> {
            showDialogDelete(
                onEvent = { onEvent(it) },
                file = content.selectedFile,
                settings = SettingsDialog.getInstance(),
                isVisible = isVisibleState,
                onTotalEvent = { onTotalEvent(it) }
            )
            isVisible = true
        }
        DialogWindow.DELETE_LIST -> {
            if(dialogColumnData is DialogColumnData.DeleteListColumnData) {
                showDialogListDelete(
                    listFiles = content.selectedListFiles,
                    onEvent = { onEvent(it) },
                    settings = SettingsDialog.getInstance(),
                    isVisible = isVisibleState,
                    onTotalEvent = { onTotalEvent(it) }
                )
                isVisible = true
            }
        }
        DialogWindow.NONE -> { return }
    }
}