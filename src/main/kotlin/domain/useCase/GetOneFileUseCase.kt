package domain.useCase

import domain.model.FileDomain
import domain.repository.DatabaseRepository
import domain.repository.FileSourceRepository

class GetOneFileUseCase(val OSrepository: FileSourceRepository, val databaseRepository: DatabaseRepository) {
    fun invoke(path: String): FileDomain? = OSrepository.getFileByPath(path)
}
