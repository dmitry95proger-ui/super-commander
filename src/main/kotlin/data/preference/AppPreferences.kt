package data.preference

import domain.model.*
import java.util.prefs.Preferences

//column
private const val COLUMN_FIRST = "_first"
private const val COLUMN_SECOND = "_second"

//order
private const val ORDER_TYPE = "order_type"
private const val ORDER_FIELD = "order_field"
private const val DEFAULT_TYPE = "asc"
private const val DEFAULT_FIELD = "name"

//path
private const val PATH = "path"
private const val DEFAULT_PATH = "C:\\"

//listMode
private const val LIST_MODE = "list_mode"
private const val DEFAULT_LIST_MODE = "column"

//dateMode
private const val DATE_MODE = "date_mode"
private const val DEFAULT_DATE_MODE = "number"



//theme
private const val THEME = "theme"
private const val DEFAULT_THEME = "black"

//star
private const val FAVORITE = "favorite"
private const val DEFAULT_FAVORITE = ""

//program
private const val PROGRAM = "program"
private const val DEFAULT_EMPTY_PROGRAM = ""

//windowAlignment
private const val WINDOW_ALIGNMENT = "window_alignment"
private const val DEFAULT_WINDOW_ALIGNMENT = "5"

class AppPreferences {

    private val prefs = Preferences.userNodeForPackage(AppPreferences::class.java)

    private fun getColumnConst(column: String) = if(column == "first") COLUMN_FIRST else COLUMN_SECOND

    fun getOrder(column: String): Order {
        val typeString = prefs.get("$ORDER_TYPE${getColumnConst(column)}", DEFAULT_TYPE)
        val fieldString = prefs.get("$ORDER_FIELD${getColumnConst(column)}", DEFAULT_FIELD)

        return Order(
            type = OrderType.getType(typeString),
            field = OrderField.getField(fieldString)
        )
    }

    fun setOrder(column: String, order: Order) {

        val typeString = order.type.getString()
        val fieldString = order.field.getString()

        prefs.put("$ORDER_TYPE${getColumnConst(column)}", typeString)
        prefs.put("$ORDER_FIELD${getColumnConst(column)}", fieldString)
    }

    fun getPath(column: String): String = prefs.get("$PATH${getColumnConst(column)}", DEFAULT_PATH)

    fun setPath(column: String, path: String) {
        if(path.isEmpty()) return
        val resultPath = (if(path[path.length - 1] == '\\' && path.length > 3) path.take(path.length - 1) else path).replaceFirstChar{ it.uppercase() }
        prefs.put("$PATH${getColumnConst(column)}", resultPath)
    }

    fun getListMode(column: String): ListMode {
        val listModeString = prefs.get("$LIST_MODE${getColumnConst(column)}", DEFAULT_LIST_MODE)
        return ListMode.getListMode(listModeString)
    }

    fun setListMode(column: String, listMode: ListMode) {
        val listModeString = listMode.getString()
        prefs.put("$LIST_MODE${getColumnConst(column)}", listModeString)
    }

    fun getDateMode(column: String): DateMode {
        val dateModeString = prefs.get("$DATE_MODE${getColumnConst(column)}", DEFAULT_DATE_MODE)
        return DateMode.getDateMode(dateModeString)
    }

    fun setDateMode(column: String, dateMode: DateMode) {
        val dateModeString = dateMode.getString()
        prefs.put("$DATE_MODE${getColumnConst(column)}", dateModeString)
    }

    //COMMON SETTINGS

    //theme
    fun getTheme(): Theme {
        val themeString = prefs.get(THEME, DEFAULT_THEME)
        return Theme.getTheme(themeString)
    }

    fun setTheme(theme: Theme) {
        val themeString = if(theme == Theme.BLACK) "black" else "white"

        prefs.put(THEME, themeString)
    }

    //favorite
    fun getFavoriteList(): List<String> {
        val stringFavorite = prefs.get(FAVORITE, DEFAULT_FAVORITE)
        if(stringFavorite.isEmpty()) return emptyList()
        return stringFavorite.split('|').filter{ it.isNotEmpty() }
    }

    fun addFavorite(favorite: String) {
        val listFavorite = getFavoriteList().toMutableList()
        listFavorite.add("$favorite|")
        var stringFavorite = ""
        listFavorite.forEach { stringFavorite += "$it|" }
        prefs.put(FAVORITE, stringFavorite)
    }

    fun deleteFavorite(favorite: String) {
        val listFavorite = getFavoriteList().toMutableList()
        var stringFavorite = ""
        listFavorite.forEach { if(it != favorite )stringFavorite += "$it|" }
        prefs.put(FAVORITE, stringFavorite)
    }

    //program
    fun getProgramList(): List<String> {
        val stringProgram = prefs.get(PROGRAM, DEFAULT_EMPTY_PROGRAM)
        return stringProgram.split('|').filter{ it.isNotEmpty() }
    }

    fun addProgram(program: String) {
        val listProgram = getProgramList().toMutableList()
        listProgram.add("$program|")
        var stringProgram = ""
        listProgram.forEach { stringProgram += "$it|" }
        prefs.put(PROGRAM, stringProgram)
    }

    fun deleteProgram(program: String) {
        val listProgram = getProgramList().toMutableList()
        var stringProgram = ""
        listProgram.forEach { if(it != program )stringProgram += "$it|" }
        prefs.put(PROGRAM, stringProgram)
    }

    //windowAlignment
    fun getWindowAlignment(): WindowAlignment {
        val windowAlignmentString = prefs.get(WINDOW_ALIGNMENT, DEFAULT_WINDOW_ALIGNMENT)
        return WindowAlignment.getWindowAlignment(windowAlignmentString)
    }

    fun setWindowAlignment(windowAlignment: WindowAlignment) {
        val windowAlignmentString = windowAlignment.getString()
        prefs.put(WINDOW_ALIGNMENT, windowAlignmentString)
    }














    fun saveBoolean(key: String, value: Boolean) {
        prefs.putBoolean(key, value)

    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    fun saveInt(key: String, value: Int) {
        prefs.putInt(key, value)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return prefs.getInt(key, defaultValue)
    }

    fun saveString(key: String, value: String) {
        prefs.put(key, value)
    }

    fun getString(key: String, defaultValue: String): String {
        return prefs.get(key, defaultValue)
    }
}

// Пример использования
fun test() {
    val preferences = AppPreferences()

    // Сохранение данных
    preferences.saveBoolean("isDarkMode", true)
    preferences.saveInt("userId", 123)
    preferences.saveString("username", "john_doe")

    // Перезапуск приложения (эмуляция)
    val newPreferences = AppPreferences()

    // Получение данных после перезапуска
    val isDarkMode = newPreferences.getBoolean("isDarkMode", false)
    val userId = newPreferences.getInt("userId", 0)
    val username = newPreferences.getString("username", "")

    println("Is Dark Mode: $isDarkMode")
    println("User ID: $userId")
    println("Username: $username")
}