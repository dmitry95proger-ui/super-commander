package domain.model

enum class ListMode {
    COLUMN,
    GRID,
    BLOCK;

    fun getString() = when(this){
        COLUMN -> "column"
        GRID -> "grid"
        BLOCK -> "block"
    }

    companion object {
        fun getListMode(value: String): ListMode = when(value){
            "column" -> COLUMN
            "grid" -> GRID
            "block" -> BLOCK
            else -> TODO("incorrect param to ListMode companion")
        }
    }
}
