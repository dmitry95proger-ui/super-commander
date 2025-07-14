package ui.composable.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ui.Utils
import ui.model.Content
import ui.model.UserColumnEvent

@Composable
fun showObserveImageScreen(onEvent: (UserColumnEvent) -> Unit, content: Content) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        IconButton(
            onClick = { onEvent(UserColumnEvent.BackFromObserveScreen) },
            modifier = Modifier.size(40.dp).align(Alignment.TopStart)
        ){
            Image(
                painter = painterResource(Utils.PainterResources.ARROW_BACK),
                contentDescription = null
            )
        }

        if (content.currentBufferedImage != null) {

            val imageHeight = content.currentBufferedImage.height
            val imageWidth = content.currentBufferedImage.width

            val maxWidth = 700

            var cardHeight = 450
            var cardWidth = ((cardHeight * imageWidth).dp / imageHeight.dp).toInt()

            if (cardWidth > maxWidth) {
                cardHeight = (cardHeight.toDouble() / (cardWidth.toDouble() / maxWidth.toDouble())).toInt()
                cardWidth = maxWidth
            }

            Card(

                backgroundColor = Color(0, 0, 255).copy(alpha = 0.1f),
                modifier = Modifier
                    .height(cardHeight.dp)
                    .width(cardWidth.dp)
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(25.dp))
            ) {
                Image(
                    painter = content.currentBufferedImage.toPainter(),
                    contentDescription = "Loaded Image",
                    modifier = Modifier.fillMaxSize().align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}