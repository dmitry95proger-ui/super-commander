package domain

import domain.repository.DatabaseRepository
import domain.repository.FileSourceRepository
import domain.repository.SystemUtilRepository
import domain.useCase.DeleteFileUseCase
import domain.useCase.programsDefaultActions.OpenCalculatorUseCase
import domain.useCase.OpenFileUseCase
import domain.useCase.commonSettings.*
import domain.useCase.programsDefaultActions.OpenManagerTaskUseCase
import domain.useCase.programsDefaultActions.OpenExplorerUseCase
import domain.useCase.programsDefaultActions.OpenSettingsUseCase

class SettingsUseCases(
    databaseRepository: DatabaseRepository,
    fileRepository: FileSourceRepository,
    systemUtilRepository: SystemUtilRepository
)
{
    val getThemeUseCase = GetThemeUseCase(databaseRepository)
    val setThemeUseCase = SetThemeUseCase(databaseRepository)
    val getWindowAlignmentUseCase = GetWindowAlignmentUseCase(databaseRepository)
    val setWindowAlignmentUseCase = SetWindowAlignmentUseCase(databaseRepository)

    val getProgramsUseCase = GetProgramsUseCase(databaseRepository)
    val addProgramUseCase = AddProgramUseCase(databaseRepository)
    val deleteProgramUseCase = DeleteProgramUseCase(databaseRepository)
    val deleteFileUseCase = DeleteFileUseCase(fileRepository)
    val openFileUseCase = OpenFileUseCase(fileRepository)

    val openExplorerUseCase = OpenExplorerUseCase(systemUtilRepository)
    val openManagerTaskUseCase = OpenManagerTaskUseCase(systemUtilRepository)
    val openCalculatorUseCase = OpenCalculatorUseCase(systemUtilRepository)
    val openSettingsUseCase = OpenSettingsUseCase(systemUtilRepository)
}

