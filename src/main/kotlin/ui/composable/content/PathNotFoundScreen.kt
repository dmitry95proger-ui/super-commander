package ui.composable.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.Theme
import ui.Utils
import ui.model.UserColumnEvent

@Composable
fun showPathNotFoundScreen(onEvent: (UserColumnEvent) -> Unit, currentTheme: Theme) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            modifier = Modifier.size(550.dp).align(Alignment.Center).padding(end = 50.dp),
            painter = painterResource(Utils.PainterResources.PATH_NOT_FOUND),
            contentDescription = null
        )
        Row(modifier = Modifier
            .align(Alignment.Center)
            .height(170.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = { onEvent(UserColumnEvent.BackFromPathNotFoundScreen) },
                modifier = Modifier.size(40.dp)
            ){
                Image(
                   painter = painterResource(Utils.PainterResources.arrowBack(currentTheme)),
                   contentDescription = null
                )
            }
            Spacer(Modifier.width(15.dp))
            Text(
                text = Utils.TextResources.PATH_NOT_FOUND,
                fontSize = 25.sp,
                color = Utils.ColorResources.getGray
            )
        }
    }
}