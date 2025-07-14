package ui.composable.alertDialog.columnDialog

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.Utils
import ui.model.DialogColumnData
import ui.model.ProgramUI
import ui.model.SettingsDialog
import ui.model.UserColumnEvent

@Composable
fun showDialogColumnAddProgram(
    onEvent: (UserColumnEvent) -> Unit,
    dialogColumnData: DialogColumnData.AddProgramColumnData,
    settings: SettingsDialog,
    isVisible: MutableState<Boolean>
) {

    val animationDuration = 300 // Длительность анимации в миллисекундах
    val widthAnimation by animateFloatAsState(
        targetValue = if (isVisible.value) 1f else 0f,
        animationSpec = tween(animationDuration)
    )

    val textState1 = remember { mutableStateOf(dialogColumnData.name) }
    val textState2 = remember { mutableStateOf(dialogColumnData.path) }

    var selectedIndex by remember { mutableStateOf(0) }

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
                                painter = painterResource(Utils.PainterResources.FILE_64),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(text = Utils.TextResources.CREATE_PROGRAM, fontSize = 20.sp, color = Color.White)
                        }
                        Spacer(Modifier.height(15.dp))

                        TextField(
                            maxLines = 1,
                            value = textState1.value,
                            onValueChange = { textState1.value = it },
                            label = { Text(Utils.TextResources.PROGRAM_NAME, color = Color.LightGray) }, // Цвет лейбла
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.White, // Цвет текста внутри TextField
                                //backgroundColor = Utils.ColorResources.getLightGray, // Цвет фона TextField
                                focusedIndicatorColor = Utils.ColorResources.getBlack, // Цвет индикатора при фокусе
                                unfocusedIndicatorColor = Utils.ColorResources.getGray // Цвет индикатора без фокуса
                            ),
                            shape = RoundedCornerShape(3.dp),
                            modifier = Modifier.border(border = settings.borderStroke, shape = RoundedCornerShape(5.dp))
                        )
                        Spacer(Modifier.height(15.dp))
                        TextField(
                            enabled = false,
                            maxLines = 1,
                            value = textState2.value,
                            onValueChange = { textState2.value = it },
                            label = { Text(Utils.TextResources.PROGRAM_PATH, color = Color.LightGray) }, // Цвет лейбла
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Gray, // Цвет текста внутри TextField
                                //backgroundColor = Utils.ColorResources.getLightGray, // Цвет фона TextField
                                focusedIndicatorColor = Utils.ColorResources.getBlack, // Цвет индикатора при фокусе
                                unfocusedIndicatorColor = Utils.ColorResources.getGray // Цвет индикатора без фокуса
                            ),
                            shape = RoundedCornerShape(3.dp),
                            modifier = Modifier.border(border = BorderStroke(1.dp, Color.Red.copy(alpha = 0.1f)), shape = RoundedCornerShape(5.dp))
                        )
                        Spacer(Modifier.height(20.dp))
                        Row (modifier = Modifier.height(160.dp)){
                            Box(modifier = Modifier.fillMaxSize().weight(1.3f)) {
                                val lazyGridState = rememberLazyGridState()

                                val scrollbarStyle = ScrollbarStyle(
                                    minimalHeight = 50.dp, // Устанавливаем минимальную высоту thumb
                                    thickness = 4.dp,     // Толщина ползунка
                                    shape = RoundedCornerShape(6.dp), // Форма thumb
                                    hoverDurationMillis = 200,       // Время задержки появления thumb при наведении
                                    unhoverColor = Color.White.copy(alpha = 0.3f), // Цвет thumb, когда не наводится мышь
                                    hoverColor = Color.White.copy(alpha = 0.8f),          // Цвет thumb при наведении мыши
                                )

                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    state = lazyGridState,
                                    modifier = Modifier.padding(end = 6.dp)
                                ) {
                                    Utils.PainterResources.listProgramPainterSmall.forEachIndexed { index, item ->
                                        item {
                                            Image(
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .padding(2.dp)
                                                    .clip(shape = RoundedCornerShape(5.dp))
                                                    .background(if(index == selectedIndex) Color.Green.copy(alpha = 0.05f) else Color.Gray.copy(alpha = 0.05f))
                                                    .clickable { selectedIndex = index },
                                                painter = painterResource(item),
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }
                                VerticalScrollbar(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .fillMaxHeight()
                                        .padding(end = 1.dp),
                                    adapter = rememberScrollbarAdapter(scrollState = lazyGridState),
                                    style = scrollbarStyle
                                )
                            }

                            Image(
                                modifier = Modifier
                                    .weight(2f)
                                    .size(160.dp)
                                    .padding(2.dp)
                                    .clip(shape = RoundedCornerShape(10.dp))
                                    .background(Color(0.5f, 0f, 1f).copy(alpha = 0.1f)),
                                painter = painterResource(Utils.PainterResources.listProgramPainterBig[selectedIndex]),
                                contentDescription = null
                            )
                        }
                        // buttons OK and CANCEL
                        Row {
                            TextButton(
                                onClick = {
                                    onEvent(UserColumnEvent.CloseDialog)
                                    isVisible.value = false
                                    onEvent(
                                        UserColumnEvent.AddProgramFromColumn(
                                            programUI = ProgramUI(
                                                name = textState1.value,
                                                path = textState2.value,
                                                painter = Utils.PainterResources.listProgramPainterSmall[selectedIndex],
                                                isDefault = false
                                            )
                                        )
                                    )
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



