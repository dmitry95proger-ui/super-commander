package ui.model

data class TextButtonUI(
    val text: String,
    val action: () -> Unit = {},
    val event: UserUpButtonEvent
)
