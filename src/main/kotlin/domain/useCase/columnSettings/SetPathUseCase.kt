package domain.useCase.columnSettings

import domain.repository.DatabaseRepository

class SetPathUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(column: String, path: String) = databaseRepository.setPath(column, path)
}
