package ui.composable.alertDialog.columnDialog

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import ui.Utils
import ui.model.*

@Composable
fun showDialogListDelete(
    listFiles: List<FileUI>,
    onEvent: (UserColumnEvent) -> Unit,
    settings: SettingsDialog,
    isVisible: MutableState<Boolean>,
    onTotalEvent: (UserTotalEvent) -> Unit
){

    if(listFiles.isEmpty()) return

    val focusRequesterOK = FocusRequester()
    val focusRequesterCANCEL = FocusRequester()

    LaunchedEffect(Unit) {
        focusRequesterOK.requestFocus()
    }
    var currentFocusButton by remember { mutableStateOf(Utils.TextResources.OK) }

    val animationDuration = 300 // Длительность анимации в миллисекундах
    val widthAnimation by animateFloatAsState(
        targetValue = if (isVisible.value) 1f else 0f,
        animationSpec = tween(animationDuration)
    )

    AlertDialog(
        modifier = Modifier.fillMaxWidth().border(BorderStroke(1.dp, Color.White.copy(alpha = widthAnimation)), RoundedCornerShape(20.dp)).alpha(widthAnimation),
        onDismissRequest = {
            isVisible.value = false
            onEvent(UserColumnEvent.CloseDialog)
        },
        confirmButton = {},
        dismissButton = {
            Box(modifier = Modifier.fillMaxSize()){
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .width(400.dp)
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(alignment = Alignment.Center)

                    ) {

                        Spacer(Modifier.height(20.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(Utils.PainterResources.DELETE),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Вы действительно хотите \n удалить файлы (${listFiles.size} шт.)? ",
                                    fontFamily = FontFamily.Cursive,
                                    color = Color.White
                                )
                                Spacer(Modifier.height(5.dp))
                                listFiles.forEach {
                                    Text(text = it.nameWithExtension, fontFamily = FontFamily.Cursive, color = Color.Red)
                                }
                            }
                        }

                        Spacer(Modifier.height(20.dp))
                        Row(modifier = Modifier.padding(10.dp)) {
                            //OK
                            TextButton(
                                modifier = Modifier
                                    .background(
                                        color = if(currentFocusButton == Utils.TextResources.OK) Color.White else Color.Transparent,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .focusRequester(focusRequesterOK)
                                    .onKeyEvent {
                                        if (it.type == KeyEventType.KeyDown && it.key == Key.Enter) {
                                            if(currentFocusButton == Utils.TextResources.OK) onTotalEvent(UserTotalEvent.Delete(listFiles))
                                            isVisible.value = false
                                            onEvent(UserColumnEvent.CloseDialog)
                                        }
                                        if (it.type == KeyEventType.KeyDown && (it.key == Key.DirectionRight || it.key == Key.DirectionLeft)) {
                                            focusRequesterCANCEL.requestFocus()
                                            currentFocusButton = Utils.TextResources.CANCEL
                                        }
                                        false
                                    }
                                    .onFocusChanged { focusState ->
                                        currentFocusButton = if(focusState.isFocused) Utils.TextResources.OK
                                        else Utils.TextResources.CANCEL
                                    },
                                onClick = {
                                    isVisible.value = false
                                    onEvent(UserColumnEvent.CloseDialog)
                                    onTotalEvent(UserTotalEvent.Delete(listFiles))
                                }
                            ){
                                Text(
                                    color = if(currentFocusButton == Utils.TextResources.OK) Color.Black else Color.White,
                                    text = Utils.TextResources.OK
                                )
                            }
                            //CANCEL
                            TextButton(
                                modifier = Modifier
                                    .background(
                                        color = if(currentFocusButton == Utils.TextResources.CANCEL) Color.White else Color.Transparent,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .focusRequester(focusRequesterCANCEL)
                                    .onKeyEvent {
                                        if (it.type == KeyEventType.KeyDown && it.key == Key.Enter) {
                                            if(currentFocusButton == Utils.TextResources.OK) onTotalEvent(UserTotalEvent.Delete(listFiles))
                                            isVisible.value = false
                                            onEvent(UserColumnEvent.CloseDialog)
                                        }
                                        if (it.type == KeyEventType.KeyDown && (it.key == Key.DirectionRight || it.key == Key.DirectionLeft)) {
                                            focusRequesterOK.requestFocus()
                                            currentFocusButton = Utils.TextResources.OK
                                        }
                                        false
                                    }
                                    .onFocusChanged { focusState ->
                                        currentFocusButton = if(focusState.isFocused) Utils.TextResources.CANCEL
                                        else Utils.TextResources.OK
                                    },
                                onClick = {
                                    isVisible.value = false
                                    onEvent(UserColumnEvent.CloseDialog)
                                }
                            ){
                                Text(
                                    color = if(currentFocusButton == Utils.TextResources.CANCEL) Color.Black else Color.White,
                                    text = Utils.TextResources.CANCEL
                                )
                            }
                        }
                    }
                }
            }
        },
        shape = settings.shape,
        backgroundColor = settings.backgroundColor
    )
}
