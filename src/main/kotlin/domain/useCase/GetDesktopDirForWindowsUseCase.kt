package domain.useCase

import domain.repository.FileSourceRepository

class GetDesktopDirForWindowsUseCase(val repository: FileSourceRepository) {
    fun invoke(): String = repository.getDesktopDirForWindows()
}