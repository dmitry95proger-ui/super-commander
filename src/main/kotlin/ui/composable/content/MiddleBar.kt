package ui.composable.content

import ui.model.ColumnPresenter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import domain.model.OrderField
import domain.model.OrderType
import ui.model.IconButtonUI
import androidx.compose.ui.Modifier
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import domain.model.ListMode
import ui.TooltipBar
import ui.Utils
import ui.model.DialogWindow
import ui.model.UserColumnEvent

//settings and tabs
@Composable
fun showMiddleBar(
    onEvent: (UserColumnEvent) -> Unit,
    columnPresenter: ColumnPresenter,
) {
    val order = columnPresenter.content.order
    val isHidden = columnPresenter.content.isHidden
    val listMode = columnPresenter.content.listMode

    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(color = Utils.ColorResources.getColor007)
            .padding(3.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val fields = listOf(
            OrderField.NAME,
            OrderField.EXTENSION,
            OrderField.SIZE,
            OrderField.DATE
        )

        val tabs = listOf("ИМЯ", "ТИП", "РАЗМЕР", "ДАТА")
        val selectedTabIndex = order.field.getInt()

        val sizePlus = 6.dp
        Row(
            modifier = Modifier.weight(0.8f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TooltipBar(Utils.TextResources.DESKTOP){
                showIconButton(
                    iconButtonUI = IconButtonUI(
                        action = { onEvent(UserColumnEvent.ToDesktop)},
                        painterResource = Utils.PainterResources.HOME
                    ),
                    size = 25.dp + sizePlus,
                    isSelected = true,
                )
            }
            TooltipBar(Utils.TextResources.VIEW) {
                showIconButton(
                    iconButtonUI = IconButtonUI(
                        action = { onEvent(UserColumnEvent.Visibility) },
                        painterResource = Utils.PainterResources.VIEW
                    ),
                    size = 25.dp + sizePlus,
                    isSelected = isHidden
                )
            }
            TooltipBar(Utils.TextResources.COPY_SELECTED) {
                showIconButton(
                    iconButtonUI = IconButtonUI(
                        action = { onEvent(UserColumnEvent.ShowDialog(DialogWindow.COPY_LIST)) },
                        painterResource = Utils.PainterResources.COPY
                    ),
                    size = 25.dp + sizePlus,
                    isSelected = columnPresenter.content.isActiveCopyListButton,
                    enabled = columnPresenter.content.isActiveCopyListButton
                )
            }
            TooltipBar(Utils.TextResources.CUT_SELECTED) {
                showIconButton(
                    iconButtonUI = IconButtonUI(
                        action = { onEvent(UserColumnEvent.ShowDialog(DialogWindow.CUT_LIST)) },
                        painterResource = Utils.PainterResources.CUT
                    ),
                    size = 25.dp + sizePlus,
                    isSelected = columnPresenter.content.isActiveCutListButton,
                    enabled = columnPresenter.content.isActiveCutListButton
                )
            }
            TooltipBar(Utils.TextResources.DELETE_SELECTED) {
                showIconButton(
                    iconButtonUI = IconButtonUI(
                        action = { onEvent(UserColumnEvent.ShowDialog(DialogWindow.DELETE_LIST)) },
                        painterResource = Utils.PainterResources.DELETE
                    ),
                    size = 25.dp + sizePlus,
                    isSelected = columnPresenter.content.isActiveDeleteListButton,
                    enabled = columnPresenter.content.isActiveDeleteListButton
                )
            }
            TooltipBar(Utils.TextResources.EQUAL) {
                showIconButton(
                    iconButtonUI = IconButtonUI(
                        action = { onEvent(UserColumnEvent.OnEqualsWindow) },
                        painterResource = Utils.PainterResources.EQUAL
                    ),
                    size = 25.dp + sizePlus,
                    isSelected = true
                )
            }
            Spacer(Modifier.width(30.dp))
            TooltipBar(Utils.TextResources.COLUMN_LIST) {
                showIconButton(
                    iconButtonUI = IconButtonUI(
                        action = { onEvent(UserColumnEvent.SetListMode(ListMode.COLUMN)) },
                        painterResource = Utils.PainterResources.COLUMN
                    ),
                    size = 25.dp + sizePlus,
                    isSelected = listMode == ListMode.COLUMN
                )
            }
            Spacer(Modifier.width(5.dp))
            TooltipBar(Utils.TextResources.GRID_LIST) {
                showIconButton(
                    iconButtonUI = IconButtonUI(
                        action = { onEvent(UserColumnEvent.SetListMode(ListMode.GRID)) },
                        painterResource = Utils.PainterResources.GRID
                    ),
                    size = 25.dp + sizePlus,
                    isSelected = listMode == ListMode.GRID
                )
            }
            Spacer(Modifier.width(5.dp))
            TooltipBar(Utils.TextResources.BLOCK_LIST) {
                showIconButton(
                    iconButtonUI = IconButtonUI(
                        action = { onEvent(UserColumnEvent.SetListMode(ListMode.BLOCK)) },
                        painterResource = Utils.PainterResources.BLOCK
                    ),
                    size = 25.dp + sizePlus,
                    isSelected = listMode == ListMode.BLOCK
                )
            }
            Spacer(Modifier.width(5.dp))
        }
        Box(Modifier.weight(1.0f)) {
            Row{
                TabRow(
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1.2f),
                    selectedTabIndex = selectedTabIndex,
                ) {
                    repeat(fields.size) { index ->
                        Tab(
                            modifier = Modifier.background(Utils.ColorResources.getBlack),
                            text = {
                                Text(
                                    text = tabs[index],
                                    color = Utils.ColorResources.getWhite,
                                    fontFamily = FontFamily.Cursive,
                                    fontSize = 12.sp
                                )
                            },
                            selected = selectedTabIndex == index,
                            onClick = { onEvent(UserColumnEvent.SetOrder(order.copy(field = fields[index]))) }
                        )
                    }
                }
                Box(Modifier.weight(.12f)){
                    rotatingImage(
                        isSelected = order.type == OrderType.ASС,
                        onClick = { onEvent(UserColumnEvent.SetOrder(order.copy(type = it))) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun showIconButton(
    iconButtonUI: IconButtonUI,
    size: Dp = 25.dp,
    isSelected: Boolean,
    enabled: Boolean = true
){
    var isHovered by remember { mutableStateOf(false) }
    val paddingPlus  by animateDpAsState(targetValue = if (isHovered) 0.dp else 3.dp)
    val animateAlpha by animateFloatAsState(targetValue = if (isSelected) 1f else 0.2f)

    IconButton(
        enabled = enabled,
        onClick = { iconButtonUI.action() },
        modifier = Modifier.size(size).alpha(animateAlpha).padding(paddingPlus)

    ){
        Image(
            painter = painterResource(iconButtonUI.painterResource),
            contentDescription = null,
            modifier = Modifier.onPointerEvent(PointerEventType.Enter){
                isHovered = true
            }.onPointerEvent(PointerEventType.Exit){
                isHovered = false
            }

        )
    }
}

@Composable
fun rotatingImage(
    isSelected: Boolean,
    onClick: (OrderType) -> Unit
) {
    BoxWithConstraints(modifier = Modifier.size(50.dp)) {
        val rotationAngle = remember { Animatable(0f) }

        LaunchedEffect(key1 = isSelected) {
            rotationAngle.animateTo(
                targetValue = if (isSelected) 0f else 180f,
                animationSpec = tween(durationMillis = 500)
            )
        }
        TooltipBar(Utils.TextResources.SORT) {
            IconButton(
                onClick = { onClick( if(isSelected) OrderType.DESС else OrderType.ASС) },
                modifier = Modifier.size(40.dp)

            ){
                Image(
                    painter = painterResource(Utils.PainterResources.ARROW_UP),
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(rotationAngle.value)
                )
            }
        }
    }
}