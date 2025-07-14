package domain.useCase

import domain.repository.FileSourceRepository

class RenameFileUseCase(val repository: FileSourceRepository) {
    fun invoke(fromPath: String, toPath: String) = repository.renameFile(fromPath, toPath)
}