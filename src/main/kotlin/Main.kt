import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import data.Repositories
import domain.SettingsUseCases
import domain.UseCases
import domain.model.WindowAlignment
import ui.composable.alertDialog.columnDialog.showColumnDialog
import ui.composable.content.showMainContent
import ui.composable.showMainStatusBar
import kotlinx.coroutines.launch
import ui.TotalPresenter
import ui.Utils
import ui.composable.alertDialog.totalDialog.showTotalDialog
import ui.composable.showAddProgramBar
import ui.composable.showButtonBar
import ui.composable.showProgramsBar
import ui.composable.showLoadingFilesAndTimeBar
import ui.model.*

val repositories = Repositories()
val useCases = UseCases(
    fileRepository = repositories.fileRepository,
    driveRepository = repositories.driveRepository,
    databaseRepository = repositories.databaseRepository,
)
val settingsUseCases = SettingsUseCases(
    databaseRepository = repositories.databaseRepository,
    fileRepository = repositories.fileRepository,
    systemUtilRepository = repositories.systemUtilRepository
)

private val presentersMap = mapOf(
    Column.FIRST to ColumnPresenter(useCases = useCases).apply { content = Content(column = Column.FIRST) },
    Column.SECOND to ColumnPresenter(useCases = useCases).apply { content = Content(column = Column.SECOND) }
)

private val totalPresenter = TotalPresenter(settingsUseCases)

private val upButtonController = UpButtonController()
private val totalController = TotalController()

fun main() = application {

    var alignmentWindow by remember { mutableStateOf(WindowAlignment.toAlignment(totalPresenter.currentWindowAlignment) ) }

    val state = rememberWindowState()
    state.position = WindowPosition(alignmentWindow)
    state.size = DpSize(1500.dp, 900.dp)

    Window(
        onCloseRequest = ::exitApplication,
        undecorated = true,
        state = state,
        resizable = false,
        title = "Commander"
    ) {
        mainScreen(
            onAlignmentClick = { windowAlignment ->
                alignmentWindow = WindowAlignment.toAlignment(windowAlignment)
            },
            onCloseClick = { state.isMinimized = !state.isMinimized },
            onExitClick = { exitApplication() }
        )
    }
}

@Composable
private fun mainScreen(
    onCloseClick: () -> Unit,
    onExitClick: () -> Unit,
    onAlignmentClick: (WindowAlignment) -> Unit
) {

    Utils.currentTheme = totalPresenter.getTheme()

    val actions: List<() -> Unit> = listOf(
        onCloseClick,
        { totalController.onEvent(
            event = UserTotalEvent.ShowDialog(
                dialogTotalWindow = DialogTotalWindow.SETTINGS,
                dialogTotalData = DialogTotalData.SettingTotalData(settings = totalPresenter.getSettings())
            ),
            totalPresenter = totalPresenter) },
        onExitClick
    )

    Box(modifier = Modifier.fillMaxSize().background(Utils.ColorResources.getColor01)){
        Column(modifier = Modifier.padding(3.dp).background(Utils.ColorResources.getColor015)) {
            Box(modifier = Modifier.fillMaxWidth().height(165.dp)
            ){
                showTotalDialog(
                    totalPresenter = totalPresenter,
                    onEvent = {
                        totalController.onEvent(it, totalPresenter)
                        onAlignmentClick(totalPresenter.currentWindowAlignment)
                    }
                )
                Column {
                    showMainStatusBar(Utils.getMainListIconButtonUI(actions))
                    showButtonBar(Utils.getListUpTextButtonUI()){ upButtonController.onEvent(it) }
                    showLoadingFilesAndTimeBar(
                        totalPresenter = totalPresenter,
                        currentTheme = totalPresenter.currentTheme
                    )
                    Spacer(Modifier.height(5.dp))
                    showProgramsBar(
                        onEvent = { totalController.onEvent(it, totalPresenter) },
                        listProgram = totalPresenter.currentListProgram
                    )
                    showAddProgramBar {
                        totalController.onEvent(it, totalPresenter)
                    }
                }
            }
            Row {
                val columns = listOf(Column.FIRST, Column.SECOND)
                columns.forEach { column ->
                    val opColumn = if(column == Column.FIRST) Column.SECOND else Column.FIRST
                    val presenter = presentersMap[column]!!
                    val opPresenter = presentersMap[opColumn]!!
                    val focusRequester = FocusRequester()
                    val controller = ColumnController()
                    totalController.setColumnController(controller)
                    upButtonController.setColumnController(controller)

                    val dialogWindow = presenter.dialogWindow

                    showColumnDialog(
                        content = presenter.content,
                        dialogWindow = dialogWindow,
                        onEvent = {
                            controller.onEvent(it, presenter, opPresenter)
                            if(it is UserColumnEvent.AddProgramFromColumn) totalPresenter.updateListProgram()
                        },
                        onTotalEvent = { totalController.onEvent(it, totalPresenter) },
                        dialogColumnData = presenter.dialogColumnData
                    )
                    LaunchedEffect(Unit) {
                        presenter.initContent()
                        launch {
                            if(column == Column.FIRST) {
                                focusRequester.requestFocus()
                                controller.onEvent(UserColumnEvent.Empty, presenter, opPresenter)
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(3.dp)
                            .weight(1f)
                            .clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                            .clickable {
                                focusRequester.requestFocus()

                                controller.onEvent(UserColumnEvent.Empty, presenter, opPresenter)
                            } // Добавляем кликабельность и перенаправление фокуса
                            .focusRequester(focusRequester)
                            .onKeyEvent { event -> controller.onKeyEvent(event, presenter, opPresenter) }

                    ) {
                        showMainContent(
                            onEvent = { event ->
                                val updateFocus = event is UserColumnEvent.UpdateTextFieldPath || event is UserColumnEvent.OnFocusTextFieldPath || event is UserColumnEvent.SetCurrentBufferedText
                                if(!updateFocus) focusRequester.requestFocus()
                                totalController.setPresenters(presenter, opPresenter)
                                upButtonController.setPresenters(presenter, opPresenter)
                                controller.onEvent(event, presenter, opPresenter)
                            },
                            columnPresenter = presenter
                        )
                    }
                }
            }
        }
        //background
        Image(
            modifier = Modifier.alpha(0.1f),
            painter = painterResource(Utils.PainterResources.BACKGROUND),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}