package ui.model

import domain.model.DriveDomain
import ui.Utils

data class DriveUI(
    val name: String,
    val totalSpace: Long,
    val freeSpace: Long,
    val isUSB: Boolean
){
    val progressSpace: Float
        get() = 1f - (freeSpace.toFloat() / totalSpace.toFloat())

    val availableSpace: String
        get() = "Доступно места на диске ${name.first()} : ${Utils.formatSizeToString(freeSpace)} Всего ${Utils.formatSizeToString(totalSpace)}"

    companion object {
        fun toDriverUI(drive: DriveDomain): DriveUI {
            return DriveUI(
                name = drive.name,
                totalSpace = drive.totalSpace,
                freeSpace = drive.freeSpace,
                isUSB = drive.isUSB
            )
        }
    }
}