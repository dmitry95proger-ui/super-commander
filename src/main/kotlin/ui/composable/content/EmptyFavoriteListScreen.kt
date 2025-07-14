package ui.composable.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.ViewMode
import ui.Utils
import ui.model.UserColumnEvent

@Composable
fun showEmptyFavoriteListScreen(onEvent: (UserColumnEvent) -> Unit) {
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
                Row {
                    IconButton(
                        onClick = { onEvent(UserColumnEvent.SetViewMode(ViewMode.LIST_ITEMS)) },
                        modifier = Modifier.size(35.dp).padding(bottom = 9.dp)
                    ){
                        Image(
                            painter = painterResource(Utils.PainterResources.ARROW_BACK),
                            contentDescription = null,
                        )
                    }
                    Spacer(Modifier.width(5.dp))
                    Text(text = "Список избранных пуст", fontSize = 15.sp, fontFamily = FontFamily.Cursive)
                }
            }
        }
    }
}