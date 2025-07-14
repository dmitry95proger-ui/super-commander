package ui.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import domain.model.DateMode
import domain.model.FileDomain
import kotlinx.coroutines.*
import net.coobird.thumbnailator.Thumbnails
import ui.Component
import ui.Utils
import ui.Utils.PainterResources.ADD_PROGRAM
import ui.Utils.PainterResources.COPY
import ui.Utils.PainterResources.CUT
import ui.Utils.PainterResources.DELETE
import ui.Utils.PainterResources.FAVORITE_OFF
import ui.Utils.PainterResources.FAVORITE_ON
import ui.Utils.PainterResources.OBSERVE
import ui.Utils.PainterResources.EDIT
import ui.Utils.PainterResources.SHOW_IN_EXPLORER
import java.awt.image.BufferedImage
import java.io.File
import java.time.Duration
import java.time.Instant
import kotlin.String

data class FileUI(
    val name: String = "",
    val nameWithExtension: String = "",
    val fileType: String = "",
    val isDirectory: Boolean = true,
    val isHidden: Boolean = true,
    val path: String,
    val dateLong: Long = 0L,
    val sizeLong: Long = 0L,
    val isRooted: Boolean = false,
    val absolutePath: String,
    var isBlue: Boolean = false,
    var isSelected: Boolean = false,
    var isFavorite: Boolean = false
) {

    fun dateString(dateMode: DateMode) : String = if (dateMode == DateMode.NUMBER) Utils.getLastModifiedDate(dateLong) else Utils.timeAgo(dateLong)
    fun dateColor(): Color {

        val currentTime = System.currentTimeMillis()

        // Преобразуем lastModified из миллисекунд в Instant
        val lastModifiedInstant = Instant.ofEpochMilli(dateLong)

        // Преобразуем currentTime из миллисекунд в Instant
        val currentInstant = Instant.ofEpochMilli(currentTime)

        // Вычисляем разницу во времени
        val duration = Duration.between(lastModifiedInstant, currentInstant)

        if(duration.toHours() > 48) return Color.White
        val redPercent = 5 * duration.toHours().toInt()
        return Color(255, redPercent, redPercent)

    }

    var sizeFolderLong by mutableStateOf(-1L)

    fun initSizeFolder() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            sizeFolderLong = Component.buildComponent(path).getSizeComponent()
        }
    }

    val sizeFolderString: String
        get() {
            if(sizeFolderLong == -1L) return "?"
            return Utils.formatSizeToString(sizeFolderLong)
        }

    val favoriteIcon: String
        get() = if(isFavorite) FAVORITE_ON else FAVORITE_OFF

    val getListIconsForListColumn: List<String>
        get() =
            if(isAbleShow)
                listOf(COPY, CUT, DELETE, SHOW_IN_EXPLORER, EDIT, ADD_PROGRAM,  OBSERVE)
            else
                listOf(COPY, CUT, DELETE, SHOW_IN_EXPLORER, EDIT, ADD_PROGRAM)

    val getListTipsForListColumn: List<String>
        get() =
            if(isAbleShow)
                listOf(
                    Utils.TextResources.COPY,
                    Utils.TextResources.CUT,
                    Utils.TextResources.DELETE,
                    Utils.TextResources.SHOW_IN_EXPLORER,
                    Utils.TextResources.EDIT,
                    Utils.TextResources.ADD_PROGRAM,
                    Utils.TextResources.OBSERVE,
                )
            else
                listOf(
                    Utils.TextResources.COPY,
                    Utils.TextResources.CUT,
                    Utils.TextResources.DELETE,
                    Utils.TextResources.SHOW_IN_EXPLORER,
                    Utils.TextResources.EDIT,
                    Utils.TextResources.ADD_PROGRAM
                )


    val isAbleShow: Boolean
        get() = isImage || isText

    val isImage: Boolean
        get() = imageFormats.contains(fileType.lowercase())

    val imageFormats: List<String>
        get() = listOf(
            "jpg",
            "jpeg",
            "png",
            "tif",
//            "psd",
//            "webp",
//            "ico",
//            "svg"
        )

    val isText: Boolean
        get() = fileType.lowercase() == "txt"

    val painterResourceForListColumn: String
        get() = Utils.PainterResources.getPainterForFile(extension = fileType)

    val painterResourceForListGrid: String
        get() = if(isDirectory) Utils.PainterResources.FOLDER_128 else  Utils.PainterResources.imageGrid(extension = fileType.lowercase())


    fun loadImageFromFile(width: Int, height: Int) {

        CoroutineScope(Dispatchers.Main).launch {
            val image = withContext(Dispatchers.IO) {
                val file = File(path)
                if (file.exists()) {
                    Thumbnails.of(file).size(width, height).asBufferedImage()
                } else {
                    null
                }
            }
            currentBufferedImage = image
        }
    }

    var currentBufferedImage by mutableStateOf<BufferedImage?>(null)

    val iconAlpha: Float
        get() = if(isHidden) 0.25f else 1f

    val nameTextUI: String
        get() {
            val text = if(isDirectory) nameWithExtension else name
            return if(text.length < 33) text else text.take(33) + "..."
        }

    val info: String
        get() = if(isDirectory) if(isBlue) sizeFolderString else "" else sizeString

    val sizeString: String
        get() = Utils.formatSizeToString(sizeLong)

    companion object {
        fun toFileUI(file: FileDomain): FileUI {
            return FileUI(
                name = file.name,
                nameWithExtension = file.nameWithExtension,
                fileType = file.type,
                isDirectory =  file.isDirectory,
                isHidden = file.isHidden,
                path = file.path,
                dateLong = file.dateLong,
                sizeLong = file.sizeLong,
                isRooted = file.isRooted,
                absolutePath = file.absolutePath
            )
        }
    }
}