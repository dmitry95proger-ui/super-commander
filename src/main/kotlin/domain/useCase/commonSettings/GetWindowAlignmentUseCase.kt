package domain.useCase.commonSettings

import domain.model.WindowAlignment
import domain.repository.DatabaseRepository

class GetWindowAlignmentUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(): WindowAlignment  = databaseRepository.getWindowAlignment()
}
