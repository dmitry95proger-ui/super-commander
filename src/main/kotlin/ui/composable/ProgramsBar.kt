package ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ui.TooltipBar
import ui.Utils
import ui.model.DialogTotalWindow
import ui.model.ProgramUI
import ui.model.UserTotalEvent
import ui.model.DialogTotalData

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun showProgramsBar(onEvent:(UserTotalEvent) -> Unit, listProgram: List<ProgramUI>) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(Utils.ColorResources.getColor007.copy(alpha = 0.3f))
    ) {
        listProgram.forEachIndexed { index, item ->
            TooltipBar(text = item.name) {
                var isVisibleCross by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier
                        .onPointerEvent(PointerEventType.Enter){ isVisibleCross = true }
                        .onPointerEvent(PointerEventType.Exit){ isVisibleCross = false }
                ) {
                    Image(
                        painter = painterResource(item.painter),
                        contentDescription = item.name,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(40.dp)
                            .padding(2.dp)
                            .clickable {
                                when(index) {
                                    //default programs
                                    0 -> onEvent(UserTotalEvent.OpenExplorer)
                                    1 -> onEvent(UserTotalEvent.OpenManagerTask)
                                    2 -> onEvent(UserTotalEvent.OpenCalculator)
                                    3 -> onEvent(UserTotalEvent.OpenSettings)
                                    else -> onEvent(UserTotalEvent.OpenProgram(item))
                                }
                            }
                    )
                    if(!item.isDefault) {
                        Row (horizontalArrangement = Arrangement.End, modifier = Modifier.align(Alignment.TopEnd)){
                            AnimatedVisibility(visible = isVisibleCross) {
                                Image(
                                    painter = painterResource(Utils.PainterResources.PROGRAM_CROSS),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(12.dp)
                                        .clickable { onEvent(UserTotalEvent.ShowDialog(
                                            dialogTotalWindow = DialogTotalWindow.DELETE_PROGRAM,
                                            dialogTotalData = DialogTotalData.DeleteTotalData(item) ))
                                        }
                                )
                            }
                        }
                    }
                }
            }
            if(index == 3) {
                Spacer(Modifier.width(6.dp))
                Card(modifier = Modifier.width(2.dp).fillMaxHeight().padding(top = 3.dp, bottom = 3.dp)){ Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray)){} }
                Spacer(Modifier.width(6.dp))
            }
        }
    }
}