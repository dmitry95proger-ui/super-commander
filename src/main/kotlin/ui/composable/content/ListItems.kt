package ui.composable.content

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.ListMode
import ui.Utils
import ui.model.ColumnPresenter
import ui.model.UserColumnEvent

@Composable
fun showListItems(
    onEvent: (UserColumnEvent) -> Unit,
    columnPresenter: ColumnPresenter
) {
    val listFiles = columnPresenter.content.listFiles

    val scrollbarStyle = ScrollbarStyle(
        minimalHeight = 50.dp, // Устанавливаем минимальную высоту thumb
        thickness = 6.dp,     // Толщина ползунка
        shape = RoundedCornerShape(6.dp), // Форма thumb
        hoverDurationMillis = 200,       // Время задержки появления thumb при наведении
        unhoverColor = Utils.ColorResources.colorScrollBarExit(), // Цвет thumb, когда не наводится мышь
        hoverColor = Utils.ColorResources.colorScrollBarEnter(),          // Цвет thumb при наведении мыши
    )

    when(columnPresenter.content.listMode) {
        ListMode.COLUMN -> {
            Box(modifier = Modifier.fillMaxSize()) {
                val lazyListState = rememberLazyListState()
                LazyColumn(
                    state = lazyListState, // Передаем состояние прокрутки
                    modifier = Modifier
                        .padding(end = 7.dp)
                ) {
                    items(listFiles){
                        showItemColumn(
                            file = it,
                            isFocus = columnPresenter.isFocused,
                            onEvent = { item -> onEvent(item) },
                            dateMode = columnPresenter.content.dateMode
                        )
                    }
                }
                VerticalScrollbar(
                    modifier = Modifier

                        .align(Alignment.CenterEnd)
                        .fillMaxHeight()
                        .padding(end = 1.dp),
                    adapter = rememberScrollbarAdapter(scrollState = lazyListState),
                    style = scrollbarStyle
                )
            }
        }
        ListMode.GRID -> {
            Box(modifier = Modifier.fillMaxSize()) {
                val lazyGridState = rememberLazyGridState()

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    state = lazyGridState,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    listFiles.forEach { item ->
                        item  {
                            showItemGrid(
                                file = item,
                                onEvent = { onEvent(it) },
                                settingsCard = SettingsCard(
                                    height = 150,
                                    shapeCard = Utils.OtherComponent.roundedCornerShape0,
                                    shapeIcon = Utils.OtherComponent.roundedCornerShape20,
                                    heightImage = 150,
                                    withImage = 245,
                                    fontSize = 12,
                                    borderCard = Utils.OtherComponent.redBorderCard,
                                    paddingIcon = 5
                                )
                            )
                        }
                    }
                }
                VerticalScrollbar(
                    modifier = Modifier

                        .align(Alignment.CenterEnd)
                        .fillMaxHeight()
                        .padding(end = 1.dp),
                    adapter = rememberScrollbarAdapter(scrollState = lazyGridState),
                    style = scrollbarStyle
                )
            }
        }
        ListMode.BLOCK -> {

            Box(modifier = Modifier.fillMaxSize()) {
                val lazyGridState = rememberLazyGridState()

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = lazyGridState,
                    modifier = Modifier.padding(end = 7.dp)
                ) {
                    listFiles.forEach { item ->
                        item {
                            showItemGrid(
                                file = item,
                                onEvent = { onEvent(it) },
                                settingsCard = SettingsCard(
                                    height = 280,
                                    shapeCard = Utils.OtherComponent.roundedCornerShape0,
                                    shapeIcon = Utils.OtherComponent.roundedCornerShape20,
                                    heightImage = 260,
                                    withImage = 390,
                                    fontSize = 16,
                                    borderCard = Utils.OtherComponent.redBorderCard,
                                    paddingIcon = 5

                                )
                            )
                        }
                    }
                }
                VerticalScrollbar(
                    modifier = Modifier

                        .align(Alignment.CenterEnd)
                        .fillMaxHeight()
                        .padding(end = 1.dp),
                    adapter = rememberScrollbarAdapter(scrollState = lazyGridState),
                    style = scrollbarStyle
                )
            }
        }
    }
}