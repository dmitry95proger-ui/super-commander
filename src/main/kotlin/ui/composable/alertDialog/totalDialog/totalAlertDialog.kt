package ui.composable.alertDialog.totalDialog

import androidx.compose.runtime.*
import ui.TotalPresenter
import ui.model.DialogTotalData

import ui.model.DialogTotalWindow
import ui.model.SettingsDialog
import ui.model.UserTotalEvent

@Composable
fun showTotalDialog(
    totalPresenter: TotalPresenter,
    onEvent: (UserTotalEvent) -> Unit,
){
    val isVisibleState = remember { mutableStateOf(false) }
    var isVisible by isVisibleState

    val dialogData = totalPresenter.dialogTotalData

    when(totalPresenter.dialogTotalWindow){

        DialogTotalWindow.ERROR_ACCESS -> {
            if(dialogData is DialogTotalData.ErrorTotalData){
                showDialogError(
                    onEvent = { onEvent(it) },
                    textError = dialogData.message,
                    settings = SettingsDialog.getInstance(),
                    isVisible = isVisibleState
                )
                isVisible = true
            }
        }
        DialogTotalWindow.ERROR_FOUND -> {
            if(dialogData is DialogTotalData.ErrorTotalData){
                showDialogError(
                    onEvent = { onEvent(it) },
                    textError = dialogData.message,
                    settings = SettingsDialog.getInstance(),
                    isVisible = isVisibleState
                )
                isVisible = true
            }
        }
        DialogTotalWindow.ADD_PROGRAM -> {
            showDialogAddProgram(
                onEvent = { onEvent(it) },
                settings = SettingsDialog.getInstance(),
                isVisible = isVisibleState
            )
            isVisible = true
        }
        DialogTotalWindow.DELETE_PROGRAM -> {
            showDialogDeleteProgram(
                onEvent = { onEvent(it) },
                dialogTotalData = totalPresenter.dialogTotalData,
                settings = SettingsDialog.getInstance(),
                isVisible = isVisibleState
            )
            isVisible = true
        }
        DialogTotalWindow.SETTINGS -> {
            if(dialogData is DialogTotalData.SettingTotalData) {
                showDialogSettings(
                    onEvent = { onEvent(it) },
                    dialogTotalData = dialogData,
                    settings = SettingsDialog.getInstance(),
                    isVisible = isVisibleState
                )
                isVisible = true
            }
        }
        DialogTotalWindow.NONE -> {}
    }
}