package domain.useCase

import domain.repository.FileSourceRepository

class DeleteFileUseCase(val repository: FileSourceRepository) {
    fun invoke(path: String) {
        repository.deleteFile(path)
    }
}