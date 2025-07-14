package domain.useCase.commonSettings

import domain.repository.DatabaseRepository

class DeleteProgramUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(program: String) = databaseRepository.deleteProgram(program)
}