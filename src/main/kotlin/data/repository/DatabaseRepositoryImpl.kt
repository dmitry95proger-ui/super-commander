package data.repository

import data.preference.AppPreferences
import domain.model.*
import domain.repository.DatabaseRepository

class DatabaseRepositoryImpl: DatabaseRepository {

    val preferences = AppPreferences()

    override fun getOrder(column: String): Order = preferences.getOrder(column)
    override fun setOrder(column: String, order: Order) = preferences.setOrder(column, order)

    override fun getPath(column: String): String = preferences.getPath(column)
    override fun setPath(column: String, path: String) = preferences.setPath(column, path)

    override fun getTheme(): Theme  = preferences.getTheme()
    override fun setTheme(theme: Theme) = preferences.setTheme(theme)

    override fun getListMode(column: String): ListMode = preferences.getListMode(column)
    override fun setListMode(column: String, listMode: ListMode) = preferences.setListMode(column, listMode)

    override fun getDateMode(column: String): DateMode = preferences.getDateMode(column)
    override fun setDateMode(column: String, dateMode: DateMode) = preferences.setDateMode(column, dateMode)

    override fun getFavorite(): List<String> = preferences.getFavoriteList()
    override fun addFavorite(favorite: String) = preferences.addFavorite(favorite)
    override fun deleteFavorite(favorite: String) = preferences.deleteFavorite(favorite)

    override fun getPrograms(): List<String> = preferences.getProgramList()
    override fun addPrograms(program: String) = preferences.addProgram(program)
    override fun deleteProgram(program: String) = preferences.deleteProgram(program)

    override fun getWindowAlignment() = preferences.getWindowAlignment()
    override fun setWindowAlignment(windowAlignment: WindowAlignment) = preferences.setWindowAlignment(windowAlignment)
}








