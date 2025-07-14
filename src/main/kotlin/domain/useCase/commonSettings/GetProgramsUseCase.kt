package domain.useCase.commonSettings

import domain.repository.DatabaseRepository

class GetProgramsUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(): List<String> = databaseRepository.getPrograms()
}
