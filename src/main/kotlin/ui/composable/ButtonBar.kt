package ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import ui.Utils
import ui.model.TextButtonUI
import ui.model.UserUpButtonEvent

@Composable
fun showButtonBar(
    listUpButtonUI: List<TextButtonUI>,
    onEvent: (UserUpButtonEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp),
    ){
        repeat(listUpButtonUI.size) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 1.dp, end = 1.dp)
                    .background(Utils.ColorResources.getColor007)
                    .clickable { onEvent(listUpButtonUI[it].event) }
            ){
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = listUpButtonUI[it].text,
                    color = Utils.ColorResources.getWhite,
                    fontFamily = FontFamily.Cursive
                )
            }
        }
    }
}