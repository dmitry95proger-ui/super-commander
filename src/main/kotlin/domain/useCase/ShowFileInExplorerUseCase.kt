package domain.useCase

import domain.repository.FileSourceRepository

class ShowFileInExplorerUseCase(val repository: FileSourceRepository) {
    fun invoke(path: String) {
        repository.showFileInExplorer(path)
    }
}