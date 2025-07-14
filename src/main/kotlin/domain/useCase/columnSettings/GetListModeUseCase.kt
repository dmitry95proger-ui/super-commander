package domain.useCase.columnSettings

import domain.model.ListMode
import domain.repository.DatabaseRepository

class GetListModeUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(column: String): ListMode  = databaseRepository.getListMode(column)
}
