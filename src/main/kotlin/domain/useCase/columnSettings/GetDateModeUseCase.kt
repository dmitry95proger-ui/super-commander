package domain.useCase.columnSettings

import domain.model.DateMode
import domain.repository.DatabaseRepository

class GetDateModeUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(column: String): DateMode  = databaseRepository.getDateMode(column)
}
