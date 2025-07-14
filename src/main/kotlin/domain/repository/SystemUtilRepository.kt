package domain.repository

interface SystemUtilRepository {
    fun openExplorer()
    fun openManagerTask(): Boolean
    fun openCalculator()
    fun openSettings()
}