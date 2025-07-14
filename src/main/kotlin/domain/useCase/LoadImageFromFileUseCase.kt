package domain.useCase

import domain.repository.FileSourceRepository

class LoadImageFromFileUseCase(val repository: FileSourceRepository) {
    fun invoke(path: String) = repository.loadImageFromFile(path)
}