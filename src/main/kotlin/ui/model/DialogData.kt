package ui.model

sealed class DialogColumnData {
    data object NoneColumnData : DialogColumnData()
    data object DeleteListColumnData : DialogColumnData()

    class CopyColumnData(val toPath: String): DialogColumnData()
    class CopyListColumnData(val listFromFile: List<String>, val toPath: String): DialogColumnData()
    class CutColumnData(val toPath: String): DialogColumnData()
    class CutListColumnData(val listFromFile: List<String>, val toPath: String): DialogColumnData()

    class AddProgramColumnData(val path: String, val name: String): DialogColumnData()
    class ErrorColumnData(val message: String): DialogColumnData()
}

sealed class DialogTotalData {
    data object NoneTotalData : DialogTotalData()
    class SettingTotalData(val settings: SettingsApplication): DialogTotalData()
    class DeleteTotalData(val programUI: ProgramUI): DialogTotalData()
    class ErrorTotalData(val message: String): DialogTotalData()
}