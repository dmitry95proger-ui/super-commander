package domain.useCase

import domain.repository.DatabaseRepository
import domain.repository.FileSourceRepository

class IsExistDirectoryUseCase(val OSrepository: FileSourceRepository, val databaseRepository: DatabaseRepository) {
    fun invoke(path: String): Boolean {
        if(path.length < 3) return false
        return OSrepository.isExistDirectory(path)
    }
}
