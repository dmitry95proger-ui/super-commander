package domain.useCase.programsDefaultActions

import domain.repository.SystemUtilRepository

class OpenManagerTaskUseCase(val repository: SystemUtilRepository) {
    fun invoke(): Boolean = repository.openManagerTask()
}