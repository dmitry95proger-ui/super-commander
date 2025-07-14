package ui.composable.content

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ui.Utils
import ui.model.DriveUI
import ui.model.UserColumnEvent

//drives
@Composable
fun showMiddleTopBar(
    drives: List<DriveUI>,
    currentDrive: DriveUI?,
    onEvent: (UserColumnEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Utils.ColorResources.getColor007, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),

        horizontalArrangement = Arrangement.Center,
    ){
        drives.forEach { drive ->

            val isSelected = drive.name == currentDrive?.name
            val painter = if(drive.isUSB) Utils.PainterResources.USB else Utils.PainterResources.HARD
            val animateAlpha by animateFloatAsState(targetValue = if (isSelected) 1f else 0.2f)

            Box {
                IconButton(
                    onClick = {},
                    modifier = Modifier.size(60.dp).padding(5.dp)
                ){

                    Image(
                        modifier = Modifier.padding(
                            animateDpAsState(
                                if (isSelected) 0.dp else 3.dp
                            ).value
                        ),
                        painter = painterResource(painter),
                        contentDescription = null,
                        alpha = animateAlpha
                    )
                }
                IconButton(
                    onClick = { onEvent (UserColumnEvent.NavigateToByDrive(drive)) },
                    modifier = Modifier.size(60.dp).padding(5.dp)
                ){
                    val isSelected = drive.name == currentDrive?.name
                    Image(
                        modifier = Modifier
                            .padding(
                                animateDpAsState(
                                    if (isSelected) 0.dp else 3.dp
                                ).value
                            ),
                        painter = painterResource(Utils.PainterResources.getLetterHard(drive.name.first().toString())),
                        contentDescription = null,
                        alpha = if(isSelected) 1f else 0.3f
                    )
                }
            }
        }
    }
}