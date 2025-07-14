package domain.repository

import domain.model.*

interface DatabaseRepository {

    //column settings
    fun getOrder(column: String): Order
    fun setOrder(column: String, order: Order)

    fun getPath(column: String): String
    fun setPath(column: String, path: String)

    fun getListMode(column: String): ListMode
    fun setListMode(column: String, listMode: ListMode)

    fun getDateMode(column: String): DateMode
    fun setDateMode(column: String, dateMode: DateMode)

    //common settings
    fun getTheme(): Theme
    fun setTheme(theme: Theme)

    fun getFavorite(): List<String>
    fun addFavorite(favorite: String)
    fun deleteFavorite(favorite: String)

    fun getPrograms(): List<String>
    fun addPrograms(program: String)
    fun deleteProgram(program: String)

    fun getWindowAlignment(): WindowAlignment
    fun setWindowAlignment(windowAlignment: WindowAlignment)
}