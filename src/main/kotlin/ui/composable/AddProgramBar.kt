package ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ui.TooltipBar
import ui.Utils
import ui.model.DialogTotalData
import ui.model.DialogTotalWindow
import ui.model.UserTotalEvent

@Composable
fun showAddProgramBar(onEvent:(UserTotalEvent) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(20.dp)
            .fillMaxWidth()
    ){
        TooltipBar(Utils.TextResources.ADD_PROGRAM_2){
            Image(
                painter = painterResource(Utils.PainterResources.ADD_PROGRAM),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .padding(3.dp)
                    .clickable { onEvent(UserTotalEvent.ShowDialog(dialogTotalWindow = DialogTotalWindow.ADD_PROGRAM, dialogTotalData = DialogTotalData.NoneTotalData)) }

            )
        }
    }
}