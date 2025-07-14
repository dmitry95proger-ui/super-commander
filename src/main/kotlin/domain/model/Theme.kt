package domain.model

enum class Theme {
    WHITE,
    BLACK;

    companion object {
        fun getTheme(value: String): Theme = when(value){
            "white" -> WHITE
            "black" -> BLACK
            else -> TODO("incorrect param to Theme companion")
        }
    }
}