package domain.useCase.commonSettings

import domain.repository.DatabaseRepository

class DeleteFavoriteUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(star: String) = databaseRepository.deleteFavorite(star)
}
