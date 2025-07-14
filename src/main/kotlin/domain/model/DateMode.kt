package domain.model

enum class DateMode {
    NUMBER,
    STRING;

    fun getString() = when(this){
        NUMBER -> "number"
        STRING -> "string"
    }

    companion object {
        fun getDateMode(value: String): DateMode = when(value){
            "number" -> NUMBER
            "string" -> STRING
            else -> TODO("incorrect param to DateMode companion")
        }
    }
}