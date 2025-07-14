package domain.repository

import domain.model.DriveDomain

interface DriveRepository {
    fun getDrives() : List<DriveDomain>
}