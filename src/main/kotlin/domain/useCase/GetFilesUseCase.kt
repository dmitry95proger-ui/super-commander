package domain.useCase

import domain.model.FileDomain
import domain.model.Settings
import domain.repository.FileSourceRepository

class GetFilesUseCase(val OSrepository: FileSourceRepository) {
    fun invoke(settings: Settings): List<FileDomain> = OSrepository.getFilesByPath(settings.path)
}
