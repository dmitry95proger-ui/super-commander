package ui.composable.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import ui.Utils
import ui.model.FileUI
import ui.model.UserColumnEvent

data class SettingsCard(
    val height: Int,
    val shapeCard: Shape,
    val shapeIcon: Shape,
    val heightImage: Int,
    val withImage: Int,
    val fontSize: Int,
    val borderCard: BorderStroke,
    val paddingIcon: Int = 0
)

@Composable
fun showItemGrid(
    onEvent: (UserColumnEvent) -> Unit,
    file: FileUI,
    settingsCard: SettingsCard
) {
    var lastClickTime by remember { mutableStateOf(0L) }

    val backgroundColor =
        if(file.isBlue) Utils.ColorResources.getSelectedItemColor
        else {
            if(file.isSelected) Utils.ColorResources.getSelectedGridBackgroundItem
            else Color.Black
        }

    Box(
        modifier = Modifier
            .height(settingsCard.height.dp)
            .padding(start = 1.dp, end = 1.dp, top = 2.dp)
            .fillMaxWidth()
            .border(
                border = settingsCard.borderCard,
                shape = settingsCard.shapeCard
            )
            .background(
                color = if(file.isSelected) Utils.ColorResources.getSelectedGridBackgroundItem else Utils.ColorResources.getUnSelectedGridBackgroundItem,
                shape = settingsCard.shapeCard
            )
            .clip(settingsCard.shapeCard)
            .clickable {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime < 500)
                    onEvent(UserColumnEvent.OnDoubleClickFile(file))
                else
                    onEvent(UserColumnEvent.OnClickFile(file))

                lastClickTime = currentTime
            }
    ) {
        Column {
            if(file.isImage && file.currentBufferedImage?.height != settingsCard.heightImage) file.loadImageFromFile(settingsCard.withImage, settingsCard.heightImage)
            Row(modifier = Modifier.weight(1f)) {
                Box(modifier = Modifier.fillMaxSize().alpha(file.iconAlpha)){
                    Image(
                        modifier = Modifier.align(Alignment.Center),
                        painter = file.currentBufferedImage?.toPainter() ?: painterResource(file.painterResourceForListGrid),
                        contentDescription = null
                    )
                    Icon(
                        tint = Color.Yellow,
                        painter = painterResource(file.favoriteIcon),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(settingsCard.paddingIcon.dp)
                            .size(30.dp)
                            .background(color = Utils.ColorResources.getBackGroundIcon, shape = settingsCard.shapeIcon)
                            .padding(5.dp)
                            .clickable { onEvent(UserColumnEvent.SetItemToListFavorite(file)) }
                    )
                    if(file.isAbleShow){
                        Icon(
                            tint = Color.Yellow,
                            painter = painterResource(Utils.PainterResources.OBSERVE),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(settingsCard.paddingIcon.dp)
                                .size(30.dp)
                                .background(color = Utils.ColorResources.getBackGroundIcon, shape = settingsCard.shapeIcon)
                                .padding(5.dp)
                                .clickable { onEvent(UserColumnEvent.ObserveFile(file)) }
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxWidth()
                    .background(
                        color = backgroundColor,
                    )
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    color = Utils.ColorResources.getTextAndBorderColorItemGrid,
                    text = file.nameTextUI,
                    maxLines = 1,
                    fontSize = settingsCard.fontSize.sp
                )
            }
        }
    }
}