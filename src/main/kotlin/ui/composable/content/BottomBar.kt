package ui.composable.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ui.Utils
import ui.model.UserColumnEvent

//arrow back textField current path
@Composable
fun showBottomBar(
    isSelectedBackAction: Boolean,
    onEvent: (UserColumnEvent) -> Unit,
    textFieldValue: String
) {
    Row(
        modifier = Modifier
            .height(73.dp)
            .fillMaxWidth()
            .background(color = Utils.ColorResources.getColor007)
            .padding(3.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            onClick = { onEvent(UserColumnEvent.Back(isSelectedBackAction)) },
            modifier = Modifier.size(40.dp)
        ){
            Image(
                painter = painterResource(Utils.PainterResources.ARROW_BACK),
                contentDescription = null,
                alpha = if(isSelectedBackAction) 1f else 0.3f
            )
        }
        showTextField(
            textFieldValue = textFieldValue,
            onEvent = { onEvent(it) }
        )
    }
}


@Composable
fun showTextField(
    textFieldValue: String,
    onEvent: (UserColumnEvent) -> Unit,
) {

    var onFocusState by remember { mutableStateOf(false) }

    TextField(
        maxLines = 2,
        value = textFieldValue,
        onValueChange = { onEvent(UserColumnEvent.UpdateTextFieldPath(it)) },
        label = { Text(Utils.TextResources.TEXT_FIELD_TIP, color = Utils.ColorResources.getGray) }, // Цвет лейбла
        colors = TextFieldDefaults.textFieldColors(
            textColor = Utils.ColorResources.getLightGray, // Цвет текста внутри TextField
            backgroundColor = Utils.ColorResources.getBlack, // Цвет фона TextField
            focusedIndicatorColor = Utils.ColorResources.getLightGray, // Цвет индикатора при фокусе
            unfocusedIndicatorColor = Utils.ColorResources.getGray // Цвет индикатора без фокуса
        ),
        shape = RoundedCornerShape(3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(53.dp)
            .padding(start = 10.dp, end = 10.dp)
            .onFocusChanged { state ->
                onFocusState = state.isFocused
                onEvent(UserColumnEvent.OnFocusTextFieldPath(state.isFocused))
            }
            .border(Utils.OtherComponent.getBorderColumn(isFocusedColumn = onFocusState))
            .onKeyEvent { event ->
                if (event.type != KeyEventType.KeyDown) false
                if (event.key == Key.Enter)
                    onEvent(UserColumnEvent.NavigateToByTextField(textFieldValue))
                false
            }
    )
}