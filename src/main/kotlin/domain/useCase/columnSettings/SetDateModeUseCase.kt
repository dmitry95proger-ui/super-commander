package domain.useCase.columnSettings

import domain.model.DateMode
import domain.repository.DatabaseRepository

class SetDateModeUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(column: String, dateMode: DateMode) = databaseRepository.setDateMode(column, dateMode)
}
