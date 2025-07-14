package ui.composable.content

import ui.model.ColumnPresenter
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import domain.model.ViewMode

import ui.Utils
import ui.model.UserColumnEvent

@Composable
fun showMainContent(
    columnPresenter: ColumnPresenter,
    onEvent: (UserColumnEvent) -> Unit,
) {
    val content = columnPresenter.content
    val mode = content.viewMode

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        //drives
        showMiddleTopBar(
            drives = content.drives,
            currentDrive = content.currentDrive,
            onEvent = { onEvent(it) }
        )
        //sort
        showMiddleBar(
            onEvent = { onEvent(it) },
            columnPresenter = columnPresenter
        )
        //list or empty list
        Box(
            modifier = Modifier
                .height(521.dp)
                .border(Utils.OtherComponent.getBorderColumn(isFocusedColumn = columnPresenter.isFocused))
                //.border(BorderStroke(1.dp, Utils.ColorResources.getCyan.copy(alpha = if (columnPresenter.isFocused) 1f else 0.2f)))
                .padding(2.dp)
                .fillMaxWidth()
        ) {
            when(mode) {
                ViewMode.LIST_ITEMS -> {
                    if (content.listFiles.isEmpty()) {
                        showEmptyListScreen{ onEvent(it) }
                    } else {
                        showListItems(
                            columnPresenter = columnPresenter,
                            onEvent = { onEvent(it) }
                        )
                    }
                }
                ViewMode.FAVORITE -> {
                    if (content.listFiles.isEmpty()) {
                        showEmptyFavoriteListScreen { onEvent(it) }
                    } else {
                        showListItems(
                            columnPresenter = columnPresenter,
                            onEvent = { onEvent(it) }
                        )
                    }
                }
                ViewMode.PATH_NOT_FOUND -> {
                    showPathNotFoundScreen( onEvent = { onEvent(it) }, currentTheme = Utils.currentTheme )
                }
                ViewMode.PREVIEW_IMAGE -> {
                    showObserveImageScreen(
                        content = content,
                        onEvent = { onEvent(it) }
                    )
                }
                ViewMode.PREVIEW_TEXT -> {
                    showObserveTextScreen(
                        content = content,
                        onEvent = { onEvent(it) }
                    )
                }
            }
            Row(modifier = Modifier.align(Alignment.Center), horizontalArrangement = Arrangement.Center){
                loadingIndicator(columnPresenter.isLoadingIcon)
            }
        }
        //add file and add directory
        showMiddleBottomBar(
            onEvent = { onEvent(it) },
            content = columnPresenter.content
        )
        //back button and current path
        showBottomBar(
            isSelectedBackAction = content.isSelectedBackAction,
            textFieldValue = content.textFieldPath,
            onEvent = { onEvent(it) }
        )
    }
}