package ui.composable.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.DateMode
import ui.TooltipBar
import ui.Utils
import ui.model.DialogWindow
import ui.model.FileUI
import ui.model.UserColumnEvent

@Composable
fun showItemColumn(
    onEvent: (UserColumnEvent) -> Unit,
    file: FileUI,
    isFocus: Boolean,
    dateMode: DateMode
) {

    var lastClickTime by remember { mutableStateOf(0L) }

    val backgroundItem =
        if(file.isBlue)
            Utils.ColorResources.getSelectedItemColor
        else
            Utils.ColorResources.getBlack.copy(alpha = 0.85f)

    Box(
        modifier = Modifier
            .height(32.dp)
            .clickable {

                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime < 500)
                    onEvent(UserColumnEvent.OnDoubleClickFile(file))
                else
                    onEvent(UserColumnEvent.OnClickFile(file))

                lastClickTime = currentTime
            }
            .padding(start = 2.dp, top = 1.dp, end = 2.dp)
            .background(backgroundItem)
            .fillMaxWidth()
            .border(
                shape = Utils.OtherComponent.shapeItem,
                border = Utils.OtherComponent.getBorderItem(isSelectedFile = file.isSelected, isFocusColumn = isFocus)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(30.dp).alpha(file.iconAlpha).padding(start = 2.dp),
                painter = painterResource(file.painterResourceForListColumn),
                contentDescription = null
            )
            Spacer(Modifier.width(2.dp))
            Text(
                color = Utils.ColorResources.getLightGray,
                modifier = Modifier.weight(0.47f),
                text = file.nameTextUI,
                maxLines = 1
            )
            Box(Modifier.weight(0.15f)){
                val backgroundColor = if(!file.isDirectory) Utils.ColorResources.colorBlue02f else Utils.ColorResources.colorTransparent
                Text(
                    color = Utils.ColorResources.getLightGray,
                    modifier = Modifier.align(Alignment.Center).background(backgroundColor, Utils.OtherComponent.roundedCornerShape8).padding(start = 10.dp, end = 10.dp),
                    text = file.fileType
                )
            }
            if(file.isBlue && file.isDirectory && file.sizeFolderLong == -1L) file.initSizeFolder()
            Text(
                color = Utils.ColorResources.getLightGray,
                modifier = Modifier.weight(0.15f),
                text = file.info
            )
            Box(Modifier.weight(0.2f)) {
                Text(
                    color = file.dateColor(),
                    text = file.dateString(dateMode),
                    fontFamily = FontFamily.Cursive,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable { onEvent(UserColumnEvent.SetDateMode(if(dateMode == DateMode.NUMBER) DateMode.STRING else DateMode.NUMBER)) },
                )
            }

            Row(
                modifier = Modifier.weight(0.33f),
                horizontalArrangement = Arrangement.End
            ) {
                if(file.isSelected && isFocus) iconsRow(
                    onEvent = { onEvent(it) },
                    //isSelected = file.isSelected,
                    file = file
                )
                TooltipBar(Utils.TextResources.ADD_FAVORITE){
                    Image(
                        painter = painterResource(file.favoriteIcon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 5.dp, end = 7.dp)
                            .size(20.dp)
                            .clickable { onEvent(UserColumnEvent.SetItemToListFavorite(file)) }
                    )
                }
            }
        }
    }
}

@Composable
fun iconsRow(
    onEvent: (UserColumnEvent) -> Unit,
    file: FileUI
) {
    val icons = file.getListIconsForListColumn
    val listTips = file.getListTipsForListColumn

    val events = mutableListOf(
        UserColumnEvent.ShowDialog(dialog = DialogWindow.COPY),
        UserColumnEvent.ShowDialog(dialog = DialogWindow.CUT),
        UserColumnEvent.ShowDialog(dialog = DialogWindow.DELETE),
        UserColumnEvent.ShowInExplorer(file),
        UserColumnEvent.ShowDialog(dialog = DialogWindow.EDIT),
        UserColumnEvent.ShowDialog(dialog = DialogWindow.ADD_PROGRAM)

    )

    if(file.isAbleShow) events += UserColumnEvent.ObserveFile(file)

    repeat(icons.size) {
        TooltipBar(listTips[it]){
            Image(
                painter = painterResource(icons[it]),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onEvent(events[it]) }
                    .background(if(it == 6) Color.Green.copy(alpha = 0.2f) else Color.Transparent, shape = RoundedCornerShape(7.dp))
            )
        }
    }
}