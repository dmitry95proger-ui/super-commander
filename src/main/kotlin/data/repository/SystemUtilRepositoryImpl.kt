package data.repository

import domain.repository.SystemUtilRepository
import java.io.IOException

class SystemUtilRepositoryImpl: SystemUtilRepository {

    override fun openExplorer() {
        Runtime.getRuntime().exec("explorer.exe")
    }

    override fun openManagerTask(): Boolean {

        try {
            // Получаем имя операционной системы
            val osName = System.getProperty("os.name").lowercase()

            // Используем оператор when для определения текущей операционной системы
            when {
                osName.contains("win") -> {
                    println("Opening Task Manager on Windows...")
                    Runtime.getRuntime().exec("taskmgr")
                }

                osName.contains("mac") || osName.contains("darwin") -> {
                    println("Opening Activity Monitor on macOS...")
                    Runtime.getRuntime().exec("/usr/bin/open -a Activity\\\\ Monitor")
                }

                osName.contains("nix") || osName.contains("nux") || osName.contains("linux") -> {
                    println("Opening GNOME System Monitor on Linux...")
                    Runtime.getRuntime().exec("gnome-system-monitor")
                }

                else -> {
                    println("Unsupported operating system: $osName")
                    return false
                }
            }
            return true
        }
        catch (e: Exception){
            println("Требуются права администратора")
            return false
        }

    }

    override fun openCalculator() {

        val osName = System.getProperty("os.name").lowercase()

        when {
            osName.contains("win") -> {
                println("Opening Calculator on Windows...")
                Runtime.getRuntime().exec("calc")
            }
            osName.contains("mac") -> {
                println("Opening Calculator on macOS...")
                Runtime.getRuntime().exec("open /Applications/Calculator.app/")
            }
            osName.contains("nix") || osName.contains("nux") || osName.contains("linux") -> {
                println("Opening Calculator on Linux...")
                Runtime.getRuntime().exec("gnome-calculator")
            }
            else -> {
                println("Unsupported operating system: $osName")
            }
        }
    }

    override fun openSettings() {
        try {
            // Выполняем команду для открытия настроек Windows
            Runtime.getRuntime().exec("explorer.exe ms-settings:")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
