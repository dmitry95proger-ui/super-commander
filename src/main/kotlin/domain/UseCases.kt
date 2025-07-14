package domain

import data.repository.DatabaseRepositoryImpl
import data.repository.DriveRepositoryImpl
import data.repository.FileSourceRepositoryImpl
import domain.repository.DatabaseRepository
import domain.repository.DriveRepository
import domain.repository.FileSourceRepository
import domain.useCase.AddFileTxtUseCase
import domain.useCase.AddFolderUseCase
import domain.useCase.commonSettings.DeleteFavoriteUseCase
import domain.useCase.GetDesktopDirForWindowsUseCase
import domain.useCase.GetDriveUseCase
import domain.useCase.GetFilesUseCase
import domain.useCase.columnSettings.GetListModeUseCase
import domain.useCase.GetOneFileUseCase
import domain.useCase.columnSettings.GetOrderUseCase
import domain.useCase.columnSettings.GetPathUseCase
import domain.useCase.commonSettings.GetFavoriteUseCase
import domain.useCase.IsExistDirectoryUseCase
import domain.useCase.LoadImageFromFileUseCase
import domain.useCase.LoadTextFromFileUseCase
import domain.useCase.MoveUseCase
import domain.useCase.OpenFileUseCase
import domain.useCase.RenameFileUseCase
import domain.useCase.columnSettings.SetListModeUseCase
import domain.useCase.columnSettings.SetOrderUseCase
import domain.useCase.columnSettings.SetPathUseCase
import domain.useCase.ShowFileInExplorerUseCase
import domain.useCase.SaveTextToFileUseCase
import domain.useCase.commonSettings.AddFavoriteUseCase
import domain.useCase.columnSettings.GetDateModeUseCase
import domain.useCase.columnSettings.SetDateModeUseCase
import domain.useCase.commonSettings.AddProgramUseCase

class UseCases(
    fileRepository: FileSourceRepository = FileSourceRepositoryImpl(),
    driveRepository: DriveRepository = DriveRepositoryImpl(),
    databaseRepository: DatabaseRepository = DatabaseRepositoryImpl(),
) {
    val getFileUseCase = GetFilesUseCase(fileRepository)
    val openFileUseCase = OpenFileUseCase(fileRepository)

    val showFileInExplorerUseCase = ShowFileInExplorerUseCase(fileRepository)
    val getDriveUseCase = GetDriveUseCase(driveRepository)
    val addFolderUseCase = AddFolderUseCase(fileRepository)
    val addFileTxtUseCase = AddFileTxtUseCase(fileRepository)
    val getDesktopDirForWindowsUseCase = GetDesktopDirForWindowsUseCase(fileRepository)
    val getOrderUseCase = GetOrderUseCase(databaseRepository)
    val setOrderUseCase = SetOrderUseCase(databaseRepository)
    val getPathUseCase = GetPathUseCase(databaseRepository)
    val setPathUseCase = SetPathUseCase(databaseRepository)
    val isExistDirectoryUseCase = IsExistDirectoryUseCase(fileRepository, databaseRepository)
    val renameFileUseCase = RenameFileUseCase(fileRepository)
    val getListModeUseCase = GetListModeUseCase(databaseRepository)
    val setListModeUseCase = SetListModeUseCase(databaseRepository)
    val getDateModeUseCase = GetDateModeUseCase(databaseRepository)
    val setDateModeUseCase = SetDateModeUseCase(databaseRepository)

    val loadImageFromFileUseCase = LoadImageFromFileUseCase(fileRepository)
    val loadTextFromFileUseCase = LoadTextFromFileUseCase(fileRepository)
    val saveTextToFileUseCase = SaveTextToFileUseCase(fileRepository)

    val getFavoriteUseCase = GetFavoriteUseCase(databaseRepository)
    val addFavoriteUseCase = AddFavoriteUseCase(databaseRepository)
    val deleteFavoriteUseCase = DeleteFavoriteUseCase(databaseRepository)

    val getOneFileUseCase = GetOneFileUseCase(fileRepository, databaseRepository)
    val addProgramUseCase = AddProgramUseCase(databaseRepository)

    val moveUseCase = MoveUseCase()
}

