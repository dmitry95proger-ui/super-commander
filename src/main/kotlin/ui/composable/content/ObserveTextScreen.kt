package ui.composable.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.Utils
import ui.model.Content
import ui.model.UserColumnEvent

@Composable
fun showObserveTextScreen(onEvent: (UserColumnEvent) -> Unit, content: Content) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){

        Text(
            modifier = Modifier.align(Alignment.TopCenter).padding(5.dp),
            text = content.currentObserveFile?.nameWithExtension ?: "Unknown file",
            color = Utils.ColorResources.getLightGray,
            fontSize = 16.sp
        )
        Box(
            modifier = Modifier.align(Alignment.Center).height(480.dp).fillMaxWidth().padding(2.dp).padding(top = 10.dp)
        ){
            if(content.currentBufferedText != null) {

                TextField(
                    value = content.currentBufferedText,
                    onValueChange = { onEvent(UserColumnEvent.SetCurrentBufferedText(it)) },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Utils.ColorResources.getLightGray, // Цвет текста внутри TextField
                        backgroundColor = Utils.ColorResources.getBlack, // Цвет фона TextField
                        focusedIndicatorColor = Utils.ColorResources.getLightGray, // Цвет индикатора при фокусе
                        unfocusedIndicatorColor = Utils.ColorResources.getGray // Цвет индикатора без фокуса
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                )
            }
            Row(modifier = Modifier.align(Alignment.TopEnd).padding(10.dp)) {
                IconButton(
                    onClick = {
                        onEvent(UserColumnEvent.SaveTextToFile(filePath = content.currentObserveFile?.path ?: "", text = content.currentBufferedText ?: ""))
                    },
                    modifier = Modifier.size(18.dp)
                ){
                    Image(
                        painter = painterResource(Utils.PainterResources.SAVE_FILE),
                        contentDescription = null,
                        alpha = if(content.isActiveSaveTextButton) 1f else 0.3f
                    )
                }
                Spacer(Modifier.width(5.dp))
                IconButton(
                    onClick = { onEvent(UserColumnEvent.BackFromObserveScreen) },
                    modifier = Modifier.size(18.dp)
                ){
                    Image(
                        painter = painterResource(Utils.PainterResources.CLOSE_FILE),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}