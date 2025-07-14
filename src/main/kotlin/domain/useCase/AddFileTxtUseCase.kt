package domain.useCase

import domain.repository.FileSourceRepository

class AddFileTxtUseCase(val repository: FileSourceRepository) {
    fun invoke(path: String, name: String): Boolean = repository.addFileTxt(path, name)
}