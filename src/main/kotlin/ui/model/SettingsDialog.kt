package ui.model

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

data class SettingsDialog(val shape: Shape, val backgroundColor: Color, val borderStroke: BorderStroke){
    companion object {
        fun getInstance(): SettingsDialog =
            SettingsDialog(
                shape = RoundedCornerShape(20.dp),
                backgroundColor = Color.Black.copy(alpha = 0.8f),
                borderStroke = BorderStroke(1.dp, Color.Blue.copy(alpha = 0.3f))
            )
    }
}
