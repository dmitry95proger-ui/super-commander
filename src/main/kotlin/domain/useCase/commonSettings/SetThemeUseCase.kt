package domain.useCase.commonSettings

import domain.model.Theme
import domain.repository.DatabaseRepository

class SetThemeUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(theme: Theme) = databaseRepository.setTheme(theme)
}
