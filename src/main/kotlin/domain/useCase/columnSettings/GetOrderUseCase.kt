package domain.useCase.columnSettings

import domain.model.Order
import domain.repository.DatabaseRepository

class GetOrderUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(column: String): Order = databaseRepository.getOrder(column)
}
