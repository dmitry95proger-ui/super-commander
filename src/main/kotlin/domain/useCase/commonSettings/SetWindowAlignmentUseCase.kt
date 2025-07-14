package domain.useCase.commonSettings

import domain.model.WindowAlignment
import domain.repository.DatabaseRepository

class SetWindowAlignmentUseCase(val databaseRepository: DatabaseRepository) {
    fun invoke(windowAlignment: WindowAlignment) = databaseRepository.setWindowAlignment(windowAlignment)
}
