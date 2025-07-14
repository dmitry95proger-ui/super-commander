package domain.useCase.columnSettings

import domain.model.Order
import domain.repository.DatabaseRepository

class SetOrderUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(column: String, order: Order) = databaseRepository.setOrder(column, order)
}
