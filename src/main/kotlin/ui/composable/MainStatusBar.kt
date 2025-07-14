package ui.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ui.TooltipBar
import ui.Utils
import ui.model.IconButtonUI

@Composable
fun showMainStatusBar(listIconButtonUI: List<IconButtonUI>) {

    Box(
        modifier = Modifier
            .height(30.dp)
            .fillMaxWidth()
            .background(color = Utils.ColorResources.getColor007),
    ){
        Box(modifier = Modifier.align(Alignment.TopStart)) {
            Image(
                painter = painterResource(Utils.PainterResources.APPLICATION_ICON),
                contentDescription = null,
                modifier = Modifier.padding(start = 5.dp, top = 5.dp).size(20.dp).align(alignment = Alignment.Center)
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = Utils.TextResources.APP_NAME,
            color = Utils.ColorResources.getWhite,
        )
        Box(Modifier.padding(4.dp).align(Alignment.TopEnd)){
            val listTips = listOf(Utils.TextResources.MINIMIZED, Utils.TextResources.SETTINGS, Utils.TextResources.CLOSE)
            Row {
                repeat(listIconButtonUI.size) {
                    TooltipBar(listTips[it]){
                        showIconButton(iconButtonUI = listIconButtonUI[it])
                    }
                    Spacer(Modifier.width(2.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun showIconButton(iconButtonUI: IconButtonUI){

    var isHovered by remember { mutableStateOf(false) }
    val paddingPlus  by animateDpAsState(targetValue = if (isHovered) 0.dp else 2.dp)

    IconButton(
        onClick = { iconButtonUI.action() },
        modifier = Modifier.size(20.dp).padding(paddingPlus)
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