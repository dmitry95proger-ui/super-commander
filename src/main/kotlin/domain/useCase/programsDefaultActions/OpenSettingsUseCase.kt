package domain.useCase.programsDefaultActions

import domain.repository.SystemUtilRepository

class OpenSettingsUseCase(val repository: SystemUtilRepository) {
    fun invoke() = repository.openSettings()
}