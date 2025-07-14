package ui.composable.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ui.Utils
import ui.model.DialogWindow
import ui.model.UserColumnEvent

@Composable
fun showEmptyListScreen(onEvent: (UserColumnEvent) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Row(modifier = Modifier
            .align(Alignment.Center)
            .height(170.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(modifier = Modifier.padding(10.dp)) {
                val event = UserColumnEvent.ShowDialog(DialogWindow.ADD_FILE)
                Card(
                    modifier = Modifier
                        .clip(RoundedCornerShape(45.dp))
                        .clickable { onEvent(event) },
                    backgroundColor = Utils.ColorResources.getBlack.copy(alpha = 0.0f),
                ){
                    Image(
                        modifier = Modifier
                            .padding(15.dp)
                            .align(Alignment.Center),
                        painter = painterResource(Utils.PainterResources.ADD_FILE_GRAY),
                        contentDescription = null
                    )
                }
            }
            Box(modifier = Modifier.padding(10.dp)) {
                val event = UserColumnEvent.ShowDialog(DialogWindow.ADD_FOLDER)
                Card(
                    modifier = Modifier
                        .clip(RoundedCornerShape(45.dp))
                        .clickable { onEvent(event) },
                    backgroundColor = Utils.ColorResources.getBlack.copy(alpha = 0.0f),
                ){
                    Image(
                        modifier = Modifier
                            .padding(15.dp)
                            .align(Alignment.Center),
                        painter = painterResource(Utils.PainterResources.ADD_FOLDER_GRAY),
                        contentDescription = null
                    )
                }
            }
        }
    }
}