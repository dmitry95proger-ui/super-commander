package domain.useCase

import domain.model.DriveDomain
import domain.repository.DriveRepository

class GetDriveUseCase(val repository: DriveRepository) {
    fun invoke(): List<DriveDomain> {
        return repository.getDrives()
    }
}