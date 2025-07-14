package domain.useCase

import domain.repository.FileSourceRepository

class LoadTextFromFileUseCase(val repository: FileSourceRepository) {
    fun invoke(path: String) = repository.loadTextFromFile(path)
}