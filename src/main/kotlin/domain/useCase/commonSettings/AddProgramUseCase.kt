package domain.useCase.commonSettings

import domain.repository.DatabaseRepository

class AddProgramUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(program: String) = databaseRepository.addPrograms(program)
}
