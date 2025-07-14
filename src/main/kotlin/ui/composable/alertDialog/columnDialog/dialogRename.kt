package ui.composable.alertDialog.columnDialog

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.Utils
import ui.model.FileUI
import ui.model.SettingsDialog
import ui.model.UserColumnEvent

@Composable
fun showDialogRename(
    onEvent: (UserColumnEvent) -> Unit,
    file: FileUI?,
    settings: SettingsDialog,
    isVisible: MutableState<Boolean>
) {

    if(file == null) return
    var fileName = file.name
    val fileNameWithExtension = file.nameWithExtension
    var isInvokeFirstLaunched = false

    val focusRequester = FocusRequester()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
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
                        .width(270.dp)
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(alignment = Alignment.Center)

                    ) {

                        Spacer(Modifier.height(20.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(Utils.PainterResources.EDIT),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(text = Utils.TextResources.RENAME, fontSize = 20.sp, color = Color.White)
                        }
                        Spacer(Modifier.height(20.dp))
                        var textFieldValue by remember { mutableStateOf(TextFieldValue(fileNameWithExtension)) }
                        val interactionSource = remember { MutableInteractionSource() }
                        val isFocused by interactionSource.collectIsFocusedAsState()
                        LaunchedEffect(isFocused) {
                            val endRange = if (isFocused) fileName.length else 0
                            if(isInvokeFirstLaunched) fileName = ""
                            isInvokeFirstLaunched = true
                            textFieldValue = textFieldValue.copy(
                                selection = TextRange(
                                    start = 0,
                                    end = endRange
                                )
                            )
                        }
                        TextField(
                            interactionSource = interactionSource,
                            value = textFieldValue,
                            onValueChange = { newText ->
                                textFieldValue = newText
                                if(newText.text.contains('\n')){
                                    onEvent(UserColumnEvent.Rename(file, textFieldValue.text.filter { it != '\n' }))
                                    isVisible.value = false
                                    onEvent(UserColumnEvent.CloseDialog)
                                }
                            },
                            label = { Text(Utils.TextResources.TEXT_FIELD_DIALOG_TIP, color = Color.LightGray) }, // Цвет лейбла
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.White, // Цвет текста внутри TextField
                                //backgroundColor = Utils.ColorResources.getLightGray, // Цвет фона TextField
                                focusedIndicatorColor = Utils.ColorResources.getBlack, // Цвет индикатора при фокусе
                                unfocusedIndicatorColor = Utils.ColorResources.getGray // Цвет индикатора без фокуса
                            ),
                            shape = RoundedCornerShape(3.dp),
                            modifier = Modifier.focusRequester(focusRequester).border(border = settings.borderStroke, shape = RoundedCornerShape(5.dp))
                        )
                        Row {
                            TextButton(
                                onClick = {
                                    onEvent(UserColumnEvent.Rename(file, textFieldValue.text.filter { it != '\n' }))
                                    isVisible.value = false
                                    onEvent(UserColumnEvent.CloseDialog)

                                }
                            ){
                                Text(
                                    color = Color.White,
                                    text = Utils.TextResources.OK
                                )
                            }
                            TextButton(
                                onClick = {
                                    isVisible.value = false
                                    onEvent(UserColumnEvent.CloseDialog)
                                }
                            ){
                                Text(
                                    color = Color.White,
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
