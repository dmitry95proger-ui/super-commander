package ui.composable.content

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import domain.model.Theme
import domain.model.ViewMode
import ui.TooltipBar
import ui.Utils
import ui.model.Content
import ui.model.DialogWindow
import ui.model.IconButtonUI
import ui.model.UserColumnEvent

//add file, add folder, available space, progressBar, count folder and files
@Composable
fun showMiddleBottomBar(
    onEvent: (UserColumnEvent) -> Unit,
    content: Content
) {

    val countFolderAndFilesText = content.countDirectoryAndFilesText
    val viewMode = content.viewMode

    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(color = Utils.ColorResources.getColor007)
            .padding(3.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TooltipBar(text = Utils.TextResources.FAVORITE){
            showIconButton(
                iconButtonUI = IconButtonUI(
                    action = { onEvent(UserColumnEvent.SetViewMode(if (viewMode == ViewMode.FAVORITE) ViewMode.LIST_ITEMS else ViewMode.FAVORITE)) },
                    painterResource = if(viewMode == ViewMode.FAVORITE) Utils.PainterResources.FAVORITE_ON else Utils.PainterResources.FAVORITE_OFF
                ),
                size = 30.dp,
                isSelected = true
            )
        }

        if(viewMode == ViewMode.LIST_ITEMS) {
            val painterFile = if(Utils.currentTheme == Theme.BLACK) Utils.PainterResources.ADD_FILE_NIGHT else Utils.PainterResources.ADD_FILE_DAY
            val painterFolder = if(Utils.currentTheme == Theme.BLACK) Utils.PainterResources.ADD_FOLDER_NIGHT else Utils.PainterResources.ADD_FOLDER_DAY
            TooltipBar(text = Utils.TextResources.ADD_FILE){
                showIconButton(
                    iconButtonUI = IconButtonUI(
                        action = { onEvent(UserColumnEvent.ShowDialog(DialogWindow.ADD_FILE)) },
                        painterResource = painterFile
                    ),
                    size = 40.dp,
                    isSelected = true
                )
            }
            TooltipBar(text = Utils.TextResources.ADD_FOLDER){
                showIconButton(
                    iconButtonUI = IconButtonUI(
                        action = { onEvent(UserColumnEvent.ShowDialog(DialogWindow.ADD_FOLDER)) },
                        painterResource = painterFolder
                    ),
                    size = 40.dp,
                    isSelected = true
                )
            }
            Text(
                modifier = Modifier.padding(start = 10.dp).weight(1.5f),
                text = content.currentDrive?.availableSpace ?: "",
                color = Utils.ColorResources.getGray,
                fontFamily = FontFamily.Cursive,
                fontSize = 12.sp
            )
            Spacer(Modifier.weight(0.05f))

            val progressSpace = content.currentDrive?.progressSpace ?: 1f
            val animatedProgress: Float by animateFloatAsState(targetValue = progressSpace)
            val animatedProgressColor: Color by animateColorAsState(targetValue = Color(progressSpace, 1f - progressSpace, 0f))

            Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .width(200.dp)
                        .height(17.dp)
                        .clip(shape = RoundedCornerShape(10.dp)),
                    progress = animatedProgress,
                    color = animatedProgressColor,
                    backgroundColor = animatedProgressColor.copy(alpha = 0.2f)
                )
            }
            Spacer(Modifier.weight(0.05f))
            Text(
                modifier = Modifier.padding(start = 10.dp).weight(1.0f),
                text = countFolderAndFilesText,
                color = Utils.ColorResources.getGray,
                fontFamily = FontFamily.Cursive
            )

        }
        else {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "Избранное",
                color = Color.Yellow,
                fontFamily = FontFamily.Cursive
            )
            Spacer(Modifier.width(30.dp))
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = countFolderAndFilesText,
                color = Color.Yellow,
                fontFamily = FontFamily.Cursive
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun showIconButton(
    iconButtonUI: IconButtonUI,
    size: Dp = 25.dp,
    isSelected: Boolean
){
    var isHovered by remember { mutableStateOf(false) }
    val paddingPlus  by animateDpAsState(targetValue = if (isHovered) 0.dp else 3.dp)

    IconButton(
        onClick = { iconButtonUI.action() },
        modifier = Modifier.size(size).alpha(if(isSelected) 1f else 0.2f).padding(paddingPlus)

    ){
        Image(
            painter = painterResource(iconButtonUI.painterResource),
            contentDescription = null,
            modifier = Modifier
                .onPointerEvent(PointerEventType.Enter){ isHovered = true }
                .onPointerEvent(PointerEventType.Exit){ isHovered = false }
        )
    }
}