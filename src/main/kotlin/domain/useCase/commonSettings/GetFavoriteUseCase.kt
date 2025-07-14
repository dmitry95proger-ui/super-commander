package domain.useCase.commonSettings

import domain.repository.DatabaseRepository

class GetFavoriteUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(): List<String> = databaseRepository.getFavorite()
}
