package domain.useCase.programsDefaultActions

import domain.repository.SystemUtilRepository

class OpenExplorerUseCase(val repository: SystemUtilRepository) {
    fun invoke() = repository.openExplorer()
}