package data

import data.repository.DatabaseRepositoryImpl
import data.repository.DriveRepositoryImpl
import data.repository.FileSourceRepositoryImpl
import data.repository.SystemUtilRepositoryImpl
import domain.repository.DatabaseRepository
import domain.repository.DriveRepository
import domain.repository.FileSourceRepository
import domain.repository.SystemUtilRepository

class Repositories {
    val fileRepository: FileSourceRepository = FileSourceRepositoryImpl()
    val driveRepository: DriveRepository = DriveRepositoryImpl()
    val databaseRepository: DatabaseRepository = DatabaseRepositoryImpl()
    val systemUtilRepository: SystemUtilRepository = SystemUtilRepositoryImpl()
}

