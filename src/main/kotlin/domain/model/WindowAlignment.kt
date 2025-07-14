package domain.model

import androidx.compose.ui.Alignment

enum class WindowAlignment {
    TOP_START,
    TOP_CENTER,
    TOP_END,
    MIDDLE_START,
    MIDDLE_CENTER,
    MIDDLE_END,
    BOTTOM_START,
    BOTTOM_CENTER,
    BOTTOM_END;

    fun getString() = when(this){
        TOP_START -> "0"
        TOP_CENTER -> "1"
        TOP_END -> "2"
        MIDDLE_START -> "3"
        MIDDLE_CENTER -> "4"
        MIDDLE_END -> "5"
        BOTTOM_START -> "6"
        BOTTOM_CENTER -> "7"
        BOTTOM_END -> "8"
    }

    companion object {
        fun getWindowAlignment(value: String): WindowAlignment = when(value) {
            "0" -> TOP_START
            "1" -> TOP_CENTER
            "2" -> TOP_END
            "3" -> MIDDLE_START
            "4" -> MIDDLE_CENTER
            "5" -> MIDDLE_END
            "6" -> BOTTOM_START
            "7" -> BOTTOM_CENTER
            "8" -> BOTTOM_END
            else -> TODO("incorrect param to WindowAlignment companion")
        }

        fun toAlignment(windowAlignment: WindowAlignment): Alignment = when(windowAlignment){
            TOP_START -> Alignment.TopStart
            TOP_CENTER -> Alignment.TopCenter
            TOP_END -> Alignment.TopEnd
            MIDDLE_START -> Alignment.CenterStart
            MIDDLE_CENTER -> Alignment.Center
            MIDDLE_END -> Alignment.CenterEnd
            BOTTOM_START -> Alignment.BottomStart
            BOTTOM_CENTER -> Alignment.BottomCenter
            BOTTOM_END -> Alignment.BottomEnd
        }
    }
}