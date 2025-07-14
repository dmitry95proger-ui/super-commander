package domain.useCase.columnSettings

import domain.model.ListMode
import domain.repository.DatabaseRepository

class SetListModeUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(column: String, listMode: ListMode) = databaseRepository.setListMode(column, listMode)
}
