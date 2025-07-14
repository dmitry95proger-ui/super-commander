package domain.useCase.commonSettings

import domain.model.Theme
import domain.repository.DatabaseRepository

class GetThemeUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(): Theme  = databaseRepository.getTheme()
}
