package domain.useCase

import domain.repository.FileSourceRepository

class OpenFileUseCase(val repository: FileSourceRepository) {
    fun invoke(path: String): Boolean = repository.openFile(path)
}