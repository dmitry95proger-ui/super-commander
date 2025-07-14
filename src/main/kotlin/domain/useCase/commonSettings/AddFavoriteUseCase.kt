package domain.useCase.commonSettings

import domain.repository.DatabaseRepository

class AddFavoriteUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(star: String) = databaseRepository.addFavorite(star)
}
