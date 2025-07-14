package ui.model

import domain.model.DateMode
import domain.model.ListMode
import domain.model.Order
import domain.model.ViewMode

sealed class UserUpButtonEvent {
    data object F2 : UserUpButtonEvent()
    data object F3 : UserUpButtonEvent()
    data object F4 : UserUpButtonEvent()
    data object F5 : UserUpButtonEvent()
    data object F6 : UserUpButtonEvent()
    data object F7 : UserUpButtonEvent()
}

sealed class UserTotalEvent {

    class AddProgram(val programUI: ProgramUI) : UserTotalEvent()
    class DeleteProgram(val programUI: ProgramUI) : UserTotalEvent()
    class OpenProgram(val programUI: ProgramUI) : UserTotalEvent()

    class ShowDialog(val dialogTotalWindow: DialogTotalWindow, val dialogTotalData: DialogTotalData) : UserTotalEvent()
    class ApplySettings(val settings: SettingsApplication): UserTotalEvent()

    class Copy(val listFiles: List<FileUI>, val toPath: String): UserTotalEvent()
    class Cut(val listFiles: List<FileUI>, val toPath: String): UserTotalEvent()
    class Delete(val listFiles: List<FileUI>): UserTotalEvent()

    data object CloseDialog : UserTotalEvent()

    //default programs
    data object OpenExplorer : UserTotalEvent()
    data object OpenManagerTask : UserTotalEvent()
    data object OpenCalculator : UserTotalEvent()
    data object OpenSettings : UserTotalEvent()
}

sealed class UserColumnEvent {

    data object Visibility : UserColumnEvent()
    data object ToDesktop : UserColumnEvent()
    data object Empty : UserColumnEvent()
    data object ArrowMoveDown : UserColumnEvent()
    data object ArrowMoveUp : UserColumnEvent()
    data object ArrowMoveLeft : UserColumnEvent()
    data object ArrowMoveRight : UserColumnEvent()
    data object BackFromPathNotFoundScreen : UserColumnEvent()
    data object BackFromObserveScreen : UserColumnEvent()
    data object CloseDialog : UserColumnEvent()
    data object AddSelectedListFiles : UserColumnEvent()
    data object OnEqualsWindow : UserColumnEvent()

    class AddProgramFromColumn(val programUI: ProgramUI): UserColumnEvent()
    class AddFolder(val name: String) : UserColumnEvent()
    class AddFileTxt(val name: String) : UserColumnEvent()
    class ShowDialog(val dialog: DialogWindow): UserColumnEvent()
    class NavigateToWithKeyBoard(val file: FileUI?): UserColumnEvent()
    class NavigateToByTextField(val value: String): UserColumnEvent()
    class NavigateToByDrive(val drive: DriveUI): UserColumnEvent()
    class OnClickFile(val file: FileUI): UserColumnEvent()
    class OnDoubleClickFile(val file: FileUI): UserColumnEvent()
    class ObserveFile(val file: FileUI): UserColumnEvent()
    class Rename(val file: FileUI, val toName: String): UserColumnEvent()
    class ShowInExplorer(val file: FileUI): UserColumnEvent()
    class SetItemToListFavorite(val file: FileUI): UserColumnEvent()
    class SetCurrentBufferedText(val text: String): UserColumnEvent()
    class SaveTextToFile(val filePath: String, val text: String): UserColumnEvent()
    class UpdateTextFieldPath(val path: String): UserColumnEvent()
    class UpdateTextFieldSearch(val path: String): UserColumnEvent()
    class OnFocusTextFieldPath(val isFocus: Boolean): UserColumnEvent()
    class SetViewMode(val viewMode: ViewMode) : UserColumnEvent()
    class SetOrder(val order: Order) : UserColumnEvent()
    class SetDateMode(val dateMode: DateMode): UserColumnEvent()
    class SetListMode(val listMode: ListMode): UserColumnEvent()
    class Back(val isAbleBackAction: Boolean) : UserColumnEvent()
}