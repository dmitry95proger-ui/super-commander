package domain.useCase.columnSettings

import domain.repository.DatabaseRepository

class GetPathUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(column: String): String  = databaseRepository.getPath(column)
}
