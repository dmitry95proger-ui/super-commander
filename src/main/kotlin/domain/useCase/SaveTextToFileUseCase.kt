package domain.useCase

import domain.repository.FileSourceRepository

class SaveTextToFileUseCase(val repository: FileSourceRepository) {
    fun invoke(filePath: String, text: String): Boolean = repository.saveTextToFile(filePath, text)
}