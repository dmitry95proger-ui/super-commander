package domain.useCase.programsDefaultActions

import domain.repository.SystemUtilRepository

class OpenCalculatorUseCase(val repository: SystemUtilRepository) {
    fun invoke() = repository.openCalculator()
}