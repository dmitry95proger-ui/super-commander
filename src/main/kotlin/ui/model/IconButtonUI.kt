package ui.model

data class IconButtonUI(
    val painterResource: String,
    val action: () -> Unit = {},
)