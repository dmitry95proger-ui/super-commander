package data.repository

import domain.model.DriveDomain
import domain.repository.DriveRepository
import java.io.File

class DriveRepositoryImpl: DriveRepository {
    override fun getDrives(): List<DriveDomain> {
        val drives = File.listRoots().map { DriveDomain(name = it.path.toString(), totalSpace = it.totalSpace, freeSpace = it.freeSpace, isUSB = isUsbDrive(it.totalSpace)) }
        return drives.filter { File(it.name).canRead() }
    }
}

private fun isUsbDrive(totalSpace: Long): Boolean {

    val totalGb = totalSpace / 1024 / 1024 / 1024
    return when {
        totalGb <= 8L ||          // Менее 8 ГБ
                totalGb in 14L..16L ||    // От 14 ГБ до 16 ГБ
                totalGb in 28L..32L ||    // От 28 ГБ до 32 ГБ
                totalGb in 57L..64L -> true  // От 57 ГБ до 64 ГБ
        else -> false
    }
}


