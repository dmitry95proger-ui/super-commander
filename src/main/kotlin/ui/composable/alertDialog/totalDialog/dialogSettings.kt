package ui.composable.alertDialog.totalDialog

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.unit.sp
import domain.model.Theme
import domain.model.WindowAlignment
import ui.Utils
import ui.model.*

@Composable
fun showDialogSettings(
    dialogTotalData: DialogTotalData.SettingTotalData,
    onEvent: (UserTotalEvent) -> Unit,
    settings: SettingsDialog,
    isVisible: MutableState<Boolean>
){
    var currentSwitchValue = dialogTotalData.settings.theme
    var currentWindowAlignment by remember { mutableStateOf(dialogTotalData.settings.windowAlignment) }

    val animationDuration = 300 // Длительность анимации в миллисекундах
    val widthAnimation by animateFloatAsState(
        targetValue = if (isVisible.value) 1f else 0f,
        animationSpec = tween(animationDuration)
    )

    AlertDialog(
        modifier = Modifier.fillMaxWidth().border(BorderStroke(1.dp, Color.White.copy(alpha = widthAnimation)), RoundedCornerShape(20.dp)).alpha(widthAnimation),
        onDismissRequest = {
            isVisible.value = false
            onEvent(UserTotalEvent.CloseDialog)
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
                                modifier = Modifier.size(25.dp),
                                painter = painterResource(Utils.PainterResources.SETTINGS),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(text = "Настройки", fontSize = 20.sp, color = Color.White, fontFamily = FontFamily.Cursive)
                        }
                        Spacer(Modifier.height(30.dp))
                        Text(text = "Положение окна", fontSize = 15.sp, color = Color.White, fontFamily = FontFamily.Cursive)
                        Spacer(Modifier.height(10.dp))
                        showAlignment(currentWindowAlignment){ alignment ->
                            currentWindowAlignment = alignment
                        }
                        Spacer(Modifier.height(30.dp))
                        Text(text = "Тема приложения", fontSize = 15.sp, color = Color.White, fontFamily = FontFamily.Cursive)
                        Spacer(Modifier.height(10.dp))
                        showSwitch(dialogTotalData.settings.theme) { theme ->
                            currentSwitchValue = theme
                        }

                        Spacer(Modifier.height(20.dp))

                        Row(modifier = Modifier.padding(10.dp)) {
                            //APPLY
                            TextButton(
                                modifier = Modifier
                                    .background(
                                        color = Color.Transparent,
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                onClick = {
                                    isVisible.value = false
                                    onEvent(UserTotalEvent.CloseDialog)
                                    onEvent(UserTotalEvent.ApplySettings(
                                        settings = SettingsApplication(
                                            theme = currentSwitchValue,
                                            windowAlignment = currentWindowAlignment)))
                                }
                            ){
                                Text(
                                    color = Color.White,
                                    text = Utils.TextResources.APPLY
                                )
                            }
                            //CANCEL
                            TextButton(
                                modifier = Modifier.background(
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(4.dp)
                                ),
                                onClick = {
                                    isVisible.value = false
                                    onEvent(UserTotalEvent.CloseDialog)
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

@Composable
fun showAlignment(currentAlignment: WindowAlignment, onClick: (WindowAlignment) -> Unit) {

    Box(
        modifier = Modifier.size(200.dp)
    ){
        Icon(
            tint = Color.Yellow,
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(Utils.PainterResources.MONITOR),
            contentDescription = null
        )
        Column(modifier = Modifier.align(Alignment.TopCenter).padding(top = 32.dp)) {
            Row {
                showCell(currentAlignment == WindowAlignment.TOP_START) { onClick(WindowAlignment.TOP_START) }
                showCell(currentAlignment == WindowAlignment.TOP_CENTER) { onClick(WindowAlignment.TOP_CENTER) }
                showCell(currentAlignment == WindowAlignment.TOP_END) { onClick(WindowAlignment.TOP_END) }
            }
            Row {
                showCell(currentAlignment == WindowAlignment.MIDDLE_START) { onClick(WindowAlignment.MIDDLE_START) }
                showCell(currentAlignment == WindowAlignment.MIDDLE_CENTER) { onClick(WindowAlignment.MIDDLE_CENTER) }
                showCell(currentAlignment == WindowAlignment.MIDDLE_END) { onClick(WindowAlignment.MIDDLE_END) }
            }
            Row {
                showCell(currentAlignment == WindowAlignment.BOTTOM_START) { onClick(WindowAlignment.BOTTOM_START) }
                showCell(currentAlignment == WindowAlignment.BOTTOM_CENTER) { onClick(WindowAlignment.BOTTOM_CENTER) }
                showCell(currentAlignment == WindowAlignment.BOTTOM_END) { onClick(WindowAlignment.BOTTOM_END) }
            }
        }
    }
}

@Composable
private fun showCell(isSelected: Boolean, onClick: () -> Unit) {

    val colorBackground = if(isSelected) Color.Yellow.copy(alpha = .2f) else Color.Yellow.copy(alpha = .05f)

    Box(
        modifier = Modifier
            .width(57.dp)
            .height(32.dp)
            .padding(1.dp)
            .background(colorBackground, RoundedCornerShape(5.dp))
            .clickable { onClick() }
    )
}

@Composable
fun showSwitch(
    currentTheme: Theme,
    onSwitch: (Theme) -> Unit
) {

    var checked by remember { mutableStateOf(currentTheme == Theme.WHITE) }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 2.dp,end = 10.dp),
    ){
        val customColors = SwitchDefaults.colors(
            checkedTrackColor = Color.Blue,
            uncheckedTrackColor = Color.White,
            checkedThumbColor = Color.White,
            uncheckedThumbColor = Color.Blue
        )

        Image(painter = painterResource(Utils.PainterResources.MOON), contentDescription = null, modifier = Modifier.size(20.dp).padding(2.dp))

        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                onSwitch(if(it) Theme.WHITE else Theme.BLACK)
            },
            colors = customColors
        )
        Image(painter = painterResource(Utils.PainterResources.SUN), contentDescription = null, modifier = Modifier.size(20.dp))
    }
}
