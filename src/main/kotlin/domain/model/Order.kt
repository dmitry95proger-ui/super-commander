package domain.model

enum class OrderType{
    ASС,  //возр
    DESС; //убыв

    fun getString() = when(this){
        ASС -> "asc"
        DESС -> "desc"
    }

    companion object {
        fun getType(value: String): OrderType = when(value){
            "asc" -> ASС
            "desc" -> DESС
            else -> TODO("incorrect param to OrderType companion")
        }
    }
}

enum class OrderField{
    NAME,
    EXTENSION,
    SIZE,
    DATE;

    fun getString() = when(this){
        NAME -> "name"
        EXTENSION -> "extension"
        SIZE -> "size"
        DATE -> "date"
    }

    fun getInt() = when(this){
        NAME -> 0
        EXTENSION -> 1
        SIZE -> 2
        DATE -> 3
    }

    companion object{
        fun getField(value: String): OrderField = when(value){
            "name" -> NAME
            "extension" -> EXTENSION
            "size" -> SIZE
            "date" -> DATE
            else -> TODO("incorrect param to OrderField companion")
        }
    }
}

data class Order(
    val field: OrderField,
    val type: OrderType
)