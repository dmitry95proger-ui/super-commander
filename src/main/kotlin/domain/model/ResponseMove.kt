package domain.model

sealed class ResponseMove {
    class Failure : ResponseMove()
    class Success(val index: Int) : ResponseMove()
}