package domain.useCase

import domain.repository.FileSourceRepository

class AddFolderUseCase(val repository: FileSourceRepository) {
    fun invoke(path: String, name: String) {
        repository.addFolder(path, name)
    }
}