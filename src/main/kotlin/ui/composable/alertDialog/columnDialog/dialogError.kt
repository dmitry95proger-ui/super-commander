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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.Utils
import ui.model.SettingsDialog
import ui.model.UserColumnEvent


@Composable
fun showDialogError(
    onEvent: (UserColumnEvent) -> Unit,
    textError: String,
    settings: SettingsDialog,
    isVisible: MutableState<Boolean>
) {

    val focusRequesterOK = FocusRequester()

    LaunchedEffect(Unit) {
        focusRequesterOK.requestFocus()
    }

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
                        .width(320.dp)
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(alignment = Alignment.Center)

                    ) {

                        Spacer(Modifier.height(20.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(Utils.PainterResources.ALERT),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(text = textError, fontSize = 18.sp, color = Color.White)
                        }

                        Spacer(Modifier.height(20.dp))
                        Row(modifier = Modifier.padding(10.dp)) {
                            //OK
                            TextButton(
                                modifier = Modifier
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .focusRequester(focusRequesterOK)
                                    .onKeyEvent {
                                        if (it.type == KeyEventType.KeyDown && it.key == Key.Enter) {
                                            isVisible.value = false
                                            onEvent(UserColumnEvent.CloseDialog)
                                        }
                                        false
                                    },
                                onClick = {
                                    isVisible.value = false
                                    onEvent(UserColumnEvent.CloseDialog)
                                }
                            ){
                                Text(
                                    color = Color.Black,
                                    text = Utils.TextResources.OK
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
