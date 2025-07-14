package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import ui.model.IconButtonUI
import ui.model.TextButtonUI
import domain.model.Theme
import ui.model.UserUpButtonEvent
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.util.Date

object Utils {

    var currentTheme: Theme = Theme.BLACK

    object PainterResources {

        //application_icon
        const val APPLICATION_ICON = "icons/icon_application_32x32.png"

        //column extensions
        const val EXTENSION_ZIP_RAR_NIGHT = "icons/extensions/zip_32x32.png"
        const val EXTENSION_ZIP_RAR_DAY = "icons/extensions/zip_day_32x32.png"

        //grid extensions
        const val IMAGE_PNG_NIGHT = "icons/extensions/png_256x256.png"
        const val IMAGE_PNG_DAY = "icons/extensions/png_day_256x256.png"
        const val IMAGE_JPEG_NIGHT = "icons/extensions/jpeg_256x256.png"
        const val IMAGE_JPEG_DAY = "icons/extensions/jpeg_day_256x256.png"
        const val IMAGE_JPG_NIGHT = "icons/extensions/jpg_256x256.png"
        const val IMAGE_JPG_DAY = "icons/extensions/jpg_day_256x256.png"
        const val IMAGE_TIF_NIGHT = "icons/extensions/tif_256x256.png"
        const val IMAGE_TIF_DAY = "icons/extensions/tif_day_256x256.png"
        const val IMAGE_PSD_NIGHT = "icons/extensions/psd_256x256.png"
        const val IMAGE_PSD_DAY = "icons/extensions/psd_day_256x256.png"
        const val IMAGE_ICO_NIGHT = "icons/extensions/ico_256x256.png"
        const val IMAGE_ICO_DAY = "icons/extensions/ico_day_256x256.png"
        const val IMAGE_SVG_NIGHT = "icons/extensions/svg_256x256.png"
        const val IMAGE_SVG_DAY = "icons/extensions/svg_day_256x256.png"
        const val IMAGE_GIF_NIGHT = "icons/extensions/gif_256x256.png"
        const val IMAGE_GIF_DAY = "icons/extensions/gif_day_256x256.png"

        const val IMAGE_TXT_NIGHT = "icons/extensions/txt_256x256.png"
        const val IMAGE_TXT_DAY = "icons/extensions/txt_day_256x256.png"
        const val IMAGE_DOC_NIGHT = "icons/extensions/doc_256x256.png"
        const val IMAGE_DOC_DAY = "icons/extensions/doc_day_256x256.png"
        const val IMAGE_DOCX_NIGHT = "icons/extensions/docx_256x256.png"
        const val IMAGE_DOCX_DAY = "icons/extensions/docx_day_256x256.png"
        const val IMAGE_XML_NIGHT = "icons/extensions/xml_256x256.png"
        const val IMAGE_XML_DAY = "icons/extensions/xml_day_256x256.png"

        const val IMAGE_EXE_NIGHT = "icons/extensions/exe_256x256.png"
        const val IMAGE_EXE_DAY = "icons/extensions/exe_day_256x256.png"
        const val IMAGE_DLL_NIGHT = "icons/extensions/dll_256x256.png"
        const val IMAGE_DLL_DAY = "icons/extensions/dll_day_256x256.png"
        const val IMAGE_JAR_NIGHT = "icons/extensions/jar_256x256.png"
        const val IMAGE_JAR_DAY = "icons/extensions/jar_day_256x256.png"

        const val IMAGE_PDF_NIGHT = "icons/extensions/pdf_256x256.png"
        const val IMAGE_PDF_DAY = "icons/extensions/pdf_day_256x256.png"

        const val IMAGE_MP3_NIGHT = "icons/extensions/mp3_256x256.png"
        const val IMAGE_MP3_DAY = "icons/extensions/mp3_day_256x256.png"
        const val IMAGE_WAV_NIGHT = "icons/extensions/wav_256x256.png"
        const val IMAGE_WAV_DAY = "icons/extensions/wav_day_256x256.png"
        const val IMAGE_WMA_NIGHT = "icons/extensions/wma_256x256.png"
        const val IMAGE_WMA_DAY = "icons/extensions/wma_day_256x256.png"

        const val IMAGE_MP4_NIGHT = "icons/extensions/mp4_256x256.png"
        const val IMAGE_MP4_DAY = "icons/extensions/mp4_day_256x256.png"
        const val IMAGE_AVI_NIGHT = "icons/extensions/avi_256x256.png"
        const val IMAGE_AVI_DAY = "icons/extensions/avi_day_256x256.png"
        const val IMAGE_MKV_NIGHT = "icons/extensions/mkv_256x256.png"
        const val IMAGE_MKV_DAY = "icons/extensions/mkv_day_256x256.png"
        const val IMAGE_MTS_NIGHT = "icons/extensions/mts_256x256.png"
        const val IMAGE_MTS_DAY = "icons/extensions/mts_day_256x256.png"
        const val IMAGE_MOV_NIGHT = "icons/extensions/mov_256x256.png"
        const val IMAGE_MOV_DAY = "icons/extensions/mov_day_256x256.png"
        const val IMAGE_WMV_NIGHT = "icons/extensions/wmv_256x256.png"
        const val IMAGE_WMV_DAY = "icons/extensions/wmv_day_256x256.png"

        const val IMAGE_ZIP_NIGHT = "icons/extensions/zip_256x256.png"
        const val IMAGE_ZIP_DAY = "icons/extensions/zip_day_256x256.png"
        const val IMAGE_RAR_NIGHT = "icons/extensions/rar_256x256.png"
        const val IMAGE_RAR_DAY = "icons/extensions/rar_day_256x256.png"
        const val IMAGE_ISO_NIGHT = "icons/extensions/iso_256x256.png"
        const val IMAGE_ISO_DAY = "icons/extensions/iso_day_256x256.png"

        const val IMAGE_HTML_NIGHT = "icons/extensions/html_256x256.png"
        const val IMAGE_HTML_DAY = "icons/extensions/html_day_256x256.png"
        const val IMAGE_MHTML_NIGHT = "icons/extensions/mhtml_256x256.png"
        const val IMAGE_MHTML_DAY = "icons/extensions/mhtml_day_256x256.png"
        const val IMAGE_WEBP_NIGHT = "icons/extensions/webp_256x256.png"
        const val IMAGE_WEBP_DAY = "icons/extensions/webp_day_256x256.png"

        const val IMAGE_UNKNOWN_NIGHT = "icons/extensions/unknown_256x256.png"
        const val IMAGE_UNKNOWN_DAY = "icons/extensions/unknown_day_256x256.png"


        fun imageGrid(extension: String) : String = when(extension) {
            "png" -> if(currentTheme == Theme.BLACK) IMAGE_PNG_NIGHT else IMAGE_PNG_DAY
            "jpeg" -> if(currentTheme == Theme.BLACK) IMAGE_JPEG_NIGHT else IMAGE_JPEG_DAY
            "jpg" -> if(currentTheme == Theme.BLACK) IMAGE_JPG_NIGHT else IMAGE_JPG_DAY
            "tif" -> if(currentTheme == Theme.BLACK) IMAGE_TIF_NIGHT else IMAGE_TIF_DAY
            "psd" -> if(currentTheme == Theme.BLACK) IMAGE_PSD_NIGHT else IMAGE_PSD_DAY
            "ico" -> if(currentTheme == Theme.BLACK) IMAGE_ICO_NIGHT else IMAGE_ICO_DAY
            "svg" -> if(currentTheme == Theme.BLACK) IMAGE_SVG_NIGHT else IMAGE_SVG_DAY
            "gif" -> if(currentTheme == Theme.BLACK) IMAGE_GIF_NIGHT else IMAGE_GIF_DAY

            "txt" -> if(currentTheme == Theme.BLACK) IMAGE_TXT_NIGHT else IMAGE_TXT_DAY
            "doc" -> if(currentTheme == Theme.BLACK) IMAGE_DOC_NIGHT else IMAGE_DOC_DAY
            "docx" -> if(currentTheme == Theme.BLACK) IMAGE_DOCX_NIGHT else IMAGE_DOCX_DAY
            "xml" -> if(currentTheme == Theme.BLACK) IMAGE_XML_NIGHT else IMAGE_XML_DAY

            "exe" -> if(currentTheme == Theme.BLACK) IMAGE_EXE_NIGHT else IMAGE_EXE_DAY
            "dll" -> if(currentTheme == Theme.BLACK) IMAGE_DLL_NIGHT else IMAGE_DLL_DAY
            "jar" -> if(currentTheme == Theme.BLACK) IMAGE_JAR_NIGHT else IMAGE_JAR_DAY

            "pdf" -> if(currentTheme == Theme.BLACK) IMAGE_PDF_NIGHT else IMAGE_PDF_DAY

            "mp3" -> if(currentTheme == Theme.BLACK) IMAGE_MP3_NIGHT else IMAGE_MP3_DAY
            "wav" -> if(currentTheme == Theme.BLACK) IMAGE_WAV_NIGHT else IMAGE_WAV_DAY
            "wma" -> if(currentTheme == Theme.BLACK) IMAGE_WMA_NIGHT else IMAGE_WMA_DAY

            "mp4" -> if(currentTheme == Theme.BLACK) IMAGE_MP4_NIGHT else IMAGE_MP4_DAY
            "avi" -> if(currentTheme == Theme.BLACK) IMAGE_AVI_NIGHT else IMAGE_AVI_DAY
            "mkv" -> if(currentTheme == Theme.BLACK) IMAGE_MKV_NIGHT else IMAGE_MKV_DAY
            "mts" -> if(currentTheme == Theme.BLACK) IMAGE_MTS_NIGHT else IMAGE_MTS_DAY
            "mov" -> if(currentTheme == Theme.BLACK) IMAGE_MOV_NIGHT else IMAGE_MOV_DAY
            "wmv" -> if(currentTheme == Theme.BLACK) IMAGE_WMV_NIGHT else IMAGE_WMV_DAY

            "zip" -> if(currentTheme == Theme.BLACK) IMAGE_ZIP_NIGHT else IMAGE_ZIP_DAY
            "rar" -> if(currentTheme == Theme.BLACK) IMAGE_RAR_NIGHT else IMAGE_RAR_DAY
            "iso" -> if(currentTheme == Theme.BLACK) IMAGE_ISO_NIGHT else IMAGE_ISO_DAY

            "html" -> if(currentTheme == Theme.BLACK) IMAGE_HTML_NIGHT else IMAGE_HTML_DAY
            "mhtml" -> if(currentTheme == Theme.BLACK) IMAGE_MHTML_NIGHT else IMAGE_MHTML_DAY
            "webp" -> if(currentTheme == Theme.BLACK) IMAGE_WEBP_NIGHT else IMAGE_WEBP_DAY

            else -> if(currentTheme == Theme.BLACK) IMAGE_UNKNOWN_NIGHT else IMAGE_UNKNOWN_DAY
        }

        //others
        const val ARROW_BACK_NIGHT = "icons/arrow_back_64x64.png"
        const val ARROW_BACK_DAY = "icons/arrow_back_day_64x64.png"
        const val LOADING_NIGHT = "icons/load/loading_128x128.png"
        const val LOADING_DAY = "icons/load/loading_day_128x128.png"
        const val BACKGROUND_NIGHT = "background.jpg"
        const val BACKGROUND_DAY = "background.jpg"
        const val ADD_FOLDER_GRAY_NIGHT = "icons/add_folder_gray_512x512.png"
        const val ADD_FOLDER_GRAY_DAY = "icons/add_folder_gray_512x512.png"
        const val ADD_FILE_GRAY_NIGHT = "icons/add_file_gray_512x512.png"
        const val ADD_FILE_GRAY_DAY = "icons/add_file_gray_512x512.png"
        const val PATH_NOT_FOUND_NIGHT = "icons/path_not_found_512x512.png"
        const val PATH_NOT_FOUND_DAY = "icons/path_not_found_day_512x512.png"
        const val ALERT = "icons/alert_64x64.png"
        const val MONITOR = "icons/programs/default_icons/200x200/icon_5_200x200.png"

        const val PROGRAM_CROSS = "icons/programs/cross_program_20x20.png"
        const val PROGRAM_CALCULATOR = "icons/programs/calculator_64x64.png"
        const val PROGRAM_SETTINGS = "icons/programs/settings_64x64.png"

        fun arrowBack(theme: Theme) = if(theme == Theme.BLACK) ARROW_BACK_NIGHT else ARROW_BACK_DAY

        val ARROW_BACK : String
            get() = if(currentTheme == Theme.BLACK) ARROW_BACK_NIGHT else ARROW_BACK_DAY
        val LOADING : String
            get() = if(currentTheme == Theme.BLACK) LOADING_NIGHT else LOADING_DAY
        val BACKGROUND : String
            get() = if(currentTheme == Theme.BLACK) BACKGROUND_NIGHT else BACKGROUND_DAY
        val ADD_FOLDER_GRAY : String
            get() = if(currentTheme == Theme.BLACK) ADD_FOLDER_GRAY_NIGHT else ADD_FOLDER_GRAY_DAY
        val ADD_FILE_GRAY : String
            get() = if(currentTheme == Theme.BLACK) ADD_FILE_GRAY_NIGHT else ADD_FILE_GRAY_DAY

        val PATH_NOT_FOUND : String
            get() = if(currentTheme == Theme.BLACK) PATH_NOT_FOUND_NIGHT else PATH_NOT_FOUND_DAY


        //hard
        private const val HARD_NIGHT = "icons/hard/new/disk_96x96.png"
        private const val HARD_DAY = "icons/hard/new/disk_day_96x96.png"

        private const val USB_NIGHT = "icons/hard/new/usb_96x96.png"
        private const val USB_DAY = "icons/hard/new/usb_day_96x96.png"

        val HARD: String
            get() = if(currentTheme == Theme.BLACK) HARD_NIGHT else HARD_DAY

        val USB: String
            get() = if(currentTheme == Theme.BLACK) USB_NIGHT else USB_DAY

        fun getLetterHard(letter: String) : String {
            return if(letter.lowercase() in "a".."z") {
                "icons/hard/new/${letter.lowercase()}${if(currentTheme == Theme.BLACK) "_" else "_day_"}96x96.png"
            }
            else {
                "icons/hard/new/question${if(currentTheme == Theme.BLACK) "_" else "_day_"}96x96.png"
            }
        }

        const val FILE_64_NIGHT = "icons/file_64x64.png"
        const val FILE_64_DAY = "icons/file_day_64x64.png"
        const val FOLDER_256_NIGHT = "icons/folder_256x256.png"
        const val FOLDER_256_DAY = "icons/folder_day_256x256.png"

        const val FOLDER_32_NIGHT = "icons/file/folder_32x32.png"
        const val FOLDER_32_DAY = "icons/file/folder_day_32x32.png"

        val FOLDER_32 : String
            get() = if(currentTheme == Theme.BLACK) FOLDER_32_NIGHT else FOLDER_32_DAY
        val FILE_64 : String
            get() = if(currentTheme == Theme.BLACK) FILE_64_NIGHT else FILE_64_DAY
        val FOLDER_128 : String
            get() = if(currentTheme == Theme.BLACK) FOLDER_256_NIGHT else FOLDER_256_DAY

        fun getPainterForFile(extension: String): String {
            return when(extension.lowercase()){
                "" -> FOLDER_32
                "zip", "rar", "iso" -> if(currentTheme == Theme.BLACK) EXTENSION_ZIP_RAR_NIGHT else EXTENSION_ZIP_RAR_DAY
                else -> FILE_64
            }
        }

        //main status bar
        const val LINE_NIGHT = "icons/main_status_bar/line_64x64.png"
        const val LINE_DAY = "icons/main_status_bar/line_day_64x64.png"
        const val SETTINGS_NIGHT = "icons/main_status_bar/settings_64x64.png"
        const val SETTINGS_DAY = "icons/main_status_bar/settings_day_64x64.png"
        const val CROSS_NIGHT = "icons/main_status_bar/close_application_64x64.png"
        const val CROSS_DAY = "icons/main_status_bar/close_application_day_64x64.png"

        val LINE : String
            get() = if(currentTheme == Theme.BLACK) LINE_NIGHT else LINE_DAY
        val SETTINGS : String
            get() = if(currentTheme == Theme.BLACK) SETTINGS_NIGHT else SETTINGS_DAY
        val CROSS : String
            get() = if(currentTheme == Theme.BLACK) CROSS_NIGHT else CROSS_DAY

        //switch
        const val MOON_NIGHT = "icons/switch/moon_64x64.png"
        const val MOON_DAY = "icons/switch/moon_day_64x64.png"
        const val SUN_NIGHT = "icons/switch/sun_64x64.png"
        const val SUN_DAY = "icons/switch/sun_day_64x64.png"

        val MOON : String
            get() = if(currentTheme == Theme.BLACK) MOON_NIGHT else MOON_DAY
        val SUN : String
            get() = if(currentTheme == Theme.BLACK) SUN_NIGHT else SUN_DAY

        //file txt
        const val SAVE_FILE_NIGHT = "icons/file_txt/save_file_64x64.png"
        const val SAVE_FILE_DAY = "icons/file_txt/save_file_64x64.png"
        const val CLOSE_FILE_NIGHT = "icons/file_txt/close_file_64x64.png"
        const val CLOSE_FILE_DAY = "icons/file_txt/close_file_64x64.png"

        val SAVE_FILE : String
            get() = if(currentTheme == Theme.BLACK) SAVE_FILE_NIGHT else SAVE_FILE_DAY
        val CLOSE_FILE : String
            get() = if(currentTheme == Theme.BLACK) CLOSE_FILE_NIGHT else CLOSE_FILE_DAY

        //middle bar
        const val HOME_NIGHT = "icons/middle_bar/home_64x64.png"
        const val HOME_DAY = "icons/middle_bar/home_day_64x64.png"
        const val VIEW_NIGHT = "icons/middle_bar/view_64x64.png"
        const val VIEW_DAY = "icons/middle_bar/view_day_64x64.png"
        const val EQUAL_NIGHT = "icons/middle_bar/equal_64x64.png"
        const val EQUAL_DAY = "icons/middle_bar/equal_day_64x64.png"
        const val COLUMN_NIGHT = "icons/middle_bar/column_64x64.png"
        const val COLUMN_DAY = "icons/middle_bar/column_day_64x64.png"
        const val GRID_NIGHT = "icons/middle_bar/grid_64x64.png"
        const val GRID_DAY = "icons/middle_bar/grid_day_64x64.png"
        const val BLOCK_NIGHT = "icons/middle_bar/block_64x64.png"
        const val BLOCK_DAY = "icons/middle_bar/block_day_64x64.png"
        const val ARROW_UP_NIGHT = "icons/middle_bar/arrow_up_64x64.png"
        const val ARROW_UP_DAY = "icons/middle_bar/arrow_up_day_64x64.png"

        val HOME : String
            get() = if(currentTheme == Theme.BLACK) HOME_NIGHT else HOME_DAY
        val VIEW : String
            get() = if(currentTheme == Theme.BLACK) VIEW_NIGHT else VIEW_DAY
        val EQUAL : String
            get() = if(currentTheme == Theme.BLACK) EQUAL_NIGHT else EQUAL_DAY
        val COLUMN : String
            get() = if(currentTheme == Theme.BLACK) COLUMN_NIGHT else COLUMN_DAY
        val GRID : String
            get() = if(currentTheme == Theme.BLACK) GRID_NIGHT else GRID_DAY
        val BLOCK : String
            get() = if(currentTheme == Theme.BLACK) BLOCK_NIGHT else BLOCK_DAY
        val ARROW_UP : String
            get() = if(currentTheme == Theme.BLACK) ARROW_UP_NIGHT else ARROW_UP_DAY

        //file item
        const val COPY_NIGHT = "icons/file/copy_64x64.png"
        const val COPY_DAY = "icons/file/copy_day_64x64.png"
        const val CUT_NIGHT = "icons/file/cut_64x64.png"
        const val CUT_DAY = "icons/file/cut_day_64x64.png"
        const val RENAME_NIGHT = "icons/file/edit_64x64.png"
        const val RENAME_DAY = "icons/file/edit_day_64x64.png"
        const val SHOW_IN_EXPLORER_NIGHT = "icons/file/folder_64x64.png"
        const val SHOW_IN_EXPLORER_DAY = "icons/file/folder_day_64x64.png"
        const val OBSERVE_NIGHT = "icons/file/observe_64x64.png"
        const val OBSERVE_DAY = "icons/file/observe_day_64x64.png"
        const val DELETE_NIGHT = "icons/file/delete_64x64.png"
        const val DELETE_DAY = "icons/file/delete_day_64x64.png"
        const val FAVORITE_ON_NIGHT = "icons/file/favorite_on_32x32.png"
        const val FAVORITE_ON_DAY = "icons/file/favorite_on_day_40x40.png"
        const val FAVORITE_OFF_NIGHT = "icons/file/favorite_off_40x40.png"
        const val FAVORITE_OFF_DAY = "icons/file/favorite_off_day_40x40.png"
        const val ADD_PROGRAM_OFF_NIGHT = "icons/file/add_program_64x64.png"
        const val ADD_PROGRAM_OFF_DAY = "icons/file/add_program_day_64x64.png"

        val COPY : String
            get() = if(currentTheme == Theme.BLACK) COPY_NIGHT else COPY_DAY
        val CUT : String
            get() = if(currentTheme == Theme.BLACK) CUT_NIGHT else CUT_DAY
        val EDIT : String
            get() = if(currentTheme == Theme.BLACK) RENAME_NIGHT else RENAME_DAY
        val SHOW_IN_EXPLORER : String
            get() = if(currentTheme == Theme.BLACK) SHOW_IN_EXPLORER_NIGHT else SHOW_IN_EXPLORER_DAY
        val OBSERVE : String
            get() = if(currentTheme == Theme.BLACK) OBSERVE_NIGHT else OBSERVE_DAY
        val DELETE : String
            get() = if(currentTheme == Theme.BLACK) DELETE_NIGHT else DELETE_DAY
        val FAVORITE_ON : String
            get() = if(currentTheme == Theme.BLACK) FAVORITE_ON_NIGHT else FAVORITE_ON_DAY
        val FAVORITE_OFF : String
            get() = if(currentTheme == Theme.BLACK) FAVORITE_OFF_NIGHT else FAVORITE_OFF_DAY
        val ADD_PROGRAM : String
            get() = if(currentTheme == Theme.BLACK) ADD_PROGRAM_OFF_NIGHT else ADD_PROGRAM_OFF_DAY


        const val ADD_FILE_NIGHT = "icons/file_64x64.png"
        const val ADD_FILE_DAY = "icons/file_day_64x64.png"
        const val ADD_FOLDER_NIGHT = "icons/folder_64x64.png"
        const val ADD_FOLDER_DAY = "icons/folder_day_64x64.png"

        val listProgramPainterSmall: List<String>
            get() {
                val list = mutableListOf<String>()
                for(i in 1..20) {
                    list += "icons/programs/default_icons/64x64/icon_${i}_64x64.png"
                }
                return list
            }

        val listProgramPainterBig: List<String>
            get() {
                val list = mutableListOf<String>()
                for(i in 1..20) {
                    list += "icons/programs/default_icons/200x200/icon_${i}_200x200.png"
                }
                return list
            }
    }

    object ColorResources {

        val colorBlue02f = Color.Blue.copy(alpha = 0.2f)
        val colorTransparent = Color.Transparent

        private val LIGHT_GRAY = Color.LightGray
        private val LIGHT_WHITE = Color(0, 40, 40)

        //progress par, items background
        private val BLACK = Color.Black
        private val WHITE = Color(187, 196, 255)

        private val GRAY = Color.Gray

        //цвет шрифта
        private val BLUE = Color.Blue
        private val DARK_GRAY = Color.DarkGray

        //обводка выделенной панели и элементов
        private val CYAN = Color.Cyan
        private val YELLOW = Color(66, 92, 255)

        private val GREEN = Color.Green

        //цвет верхних кнопок и общий фон контента
        private val COLOR_007 = Color(0.07f, 0.07f, 0.07f)
        private val COLOR_07 = Color(192, 200, 255)

        //background
        private val COLOR_015 = Color(0.15f, 0.15f, 0.15f)
        private val COLOR_075 = Color(214, 220, 255)

        private val COLOR_01 = Color(0.1f, 0.1f, 0.1f)
        private val COLOR_09 = Color(0.9f, 0.9f, 0.9f)

        private val COLOR_BLACK_05 = Color.Black.copy(alpha = 0.5f)
        private val COLOR_WHITE_05 = Color(124, 135, 255)


        val  getLightGray: Color
            get() = if(currentTheme == Theme.BLACK) LIGHT_GRAY else LIGHT_WHITE

        val getBlack: Color
            get() = if(currentTheme == Theme.BLACK) BLACK else WHITE

        val getWhite: Color
            get() = if(currentTheme == Theme.BLACK) WHITE else BLACK

        val getGray: Color
            get() = if(currentTheme == Theme.BLACK) GRAY else BLUE


        val getSelectedItemColor: Color
            get() = if(currentTheme == Theme.BLACK) GREEN.copy(alpha = 0.1f) else BLUE.copy(alpha = 0.3f)

        val getColor007: Color
            get() = if(currentTheme == Theme.BLACK) COLOR_007 else COLOR_07

        val getColor01: Color
            get() = if(currentTheme == Theme.BLACK) COLOR_01 else COLOR_09

        val getColor015: Color
            get() = if(currentTheme == Theme.BLACK) COLOR_015 else COLOR_075

        val getSelectedGridBackgroundItem: Color
            get() = if(currentTheme == Theme.BLACK) DARK_GRAY else COLOR_WHITE_05

        val getUnSelectedGridBackgroundItem: Color
            get() = if(currentTheme == Theme.BLACK) COLOR_BLACK_05 else COLOR_07


        val DarkRed = Color(128, 0, 0)
        val DarkPink = Color(97, 27, 132)


        val getTextAndBorderColorItemGrid: Color
            get() = if(currentTheme == Theme.BLACK) DarkRed else DarkPink

        val RedBackGroundIcon = Color.Red.copy(alpha = 0.2f)
        val BlueBackGroundIcon = Color.Blue.copy(alpha = 0.2f)

        val getBackGroundIcon: Color
            get() = if(currentTheme == Theme.BLACK) RedBackGroundIcon else BlueBackGroundIcon

        fun colorScrollBarEnter(): Color {
            return if(currentTheme == Theme.BLACK) Color.White.copy(alpha = 0.8f) else Color.Black.copy(alpha = 0.8f)
        }

        fun colorScrollBarExit(): Color {
            return if(currentTheme == Theme.BLACK) Color.White.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.3f)
        }
    }

    object TextResources {
        const val APP_NAME = "Super Commander 1.0"
        const val TEXT_FIELD_TIP = "Текущий каталог"
        const val TEXT_FIELD_DIALOG_TIP = "Название"
        const val PATH_NOT_FOUND = "Путь не найден"
        const val CREATE_FOLDER = "Создание каталога"
        const val CREATE_FILE = "Создание текстового файла"
        const val OK = "ОК"
        const val CANCEL = "ОТМЕНА"
        const val APPLY = "ПРИМЕНИТЬ"
        const val RENAME = "Переименовать"
        const val ERROR_ACCESS = "Ошибка прав доступа!"
        const val ERROR_FOUND_FILE = "Файл не найден!"
        const val ERROR_SELECTED_FILE = "Нет выбранных файлов"
        const val ERROR_SELECTED_DIRECTORY = "Нет выбранной директории"

        //TIPS
        const val CREATE_PROGRAM = "Добавление новой программы"
        const val PROGRAM_NAME = "Название"
        const val PROGRAM_PATH = "Путь запуска"

        const val DESKTOP = "Рабочий стол"
        const val VIEW = "Скрытые файлы"
        const val COPY_SELECTED = "Копировать выделенные"
        const val CUT_SELECTED = "Вырезать выделенные"
        const val DELETE_SELECTED = "Удалить выделенные"
        const val EQUAL = "Приравнять соседнюю панель"
        const val COLUMN_LIST = "Список"
        const val GRID_LIST = "Сетка"
        const val BLOCK_LIST = "Блоки"
        const val SORT = "Порядок сортировки"

        const val FAVORITE = "Показать/скрыть избранные"
        const val ADD_FILE = "Добавить текстовый документ"
        const val ADD_FOLDER = "Добавить папку"

        const val COPY = "Копировать"
        const val CUT = "Переместить"
        const val EDIT = "Переименовать"
        const val SHOW_IN_EXPLORER = "Показать в проводнике"
        const val OBSERVE = "Просмотр"
        const val DELETE = "Удалить"
        const val ADD_FAVORITE = "Добавить/Убрать (Избранные)"
        const val ADD_PROGRAM = "Добавить в панель быстрого доступа"

        const val ADD_PROGRAM_2 = "Добавить программу в панель быстрого доступа"

        const val MINIMIZED = "Свернуть"
        const val SETTINGS = "Настройки"
        const val CLOSE = "Закрыть"

    }

    object OtherComponent {

        val shapeItem = RectangleShape

        private val borderSelectedFile : BorderStroke = BorderStroke(width = 1.dp, color = Color.Blue)
        private val borderUnselectedFile : BorderStroke = BorderStroke(width = 1.dp, color = Color.Transparent)

        private val borderSelectedFile02 : BorderStroke = BorderStroke(width = 1.dp, color = Color.Blue.copy(alpha = 0.2f))

        fun getBorderItem(isSelectedFile: Boolean, isFocusColumn: Boolean): BorderStroke {
            return if(isSelectedFile)
                if(isFocusColumn) borderSelectedFile else borderSelectedFile02
            else
                borderUnselectedFile
        }

        private val borderSelectedColumn : BorderStroke = BorderStroke(width = 1.dp, color = Color.Blue)
        private val borderUnselectedColumn : BorderStroke = BorderStroke(width = 1.dp, color = Color.Blue.copy(alpha = 0.2f))

        fun getBorderColumn(isFocusedColumn: Boolean): BorderStroke {
            return if(isFocusedColumn)
                 borderSelectedColumn
            else
                borderUnselectedColumn
        }


        val roundedCornerShape0 = RoundedCornerShape(0.dp)
        val roundedCornerShape8 = RoundedCornerShape(8.dp)
        val roundedCornerShape20 = RoundedCornerShape(20.dp)

        val redBorderCard = BorderStroke(1.dp, ColorResources.getTextAndBorderColorItemGrid.copy(alpha = 0.3f))
    }

    fun getListUpTextButtonUI(): List<TextButtonUI> {
        return listOf(
            TextButtonUI(text = "F2 Переименовать", event = UserUpButtonEvent.F2),
            TextButtonUI(text = "F3 Переместить", event = UserUpButtonEvent.F3),
            TextButtonUI(text = "F4 Копировать", event = UserUpButtonEvent.F4),
            TextButtonUI(text = "F5 Удалить", event = UserUpButtonEvent.F5),
            TextButtonUI(text = "F6 Текстовый файл", event = UserUpButtonEvent.F6),
            TextButtonUI(text = "F7 Каталог", event = UserUpButtonEvent.F7)
        )
    }
    fun getMainListIconButtonUI(list: List<() -> Unit>) : List<IconButtonUI>{
        return listOf(
            IconButtonUI(
                painterResource = PainterResources.LINE,
                action = list[0]
            ),
            IconButtonUI(
                painterResource = PainterResources.SETTINGS,
                action = list[1]
            ),
            IconButtonUI(
                painterResource = PainterResources.CROSS,
                action = list[2]
            )
        )
    }

    fun formatSizeToString(size: Long): String {

        val decimalFormat = DecimalFormat("#,##0.0")

        // Константы для различных единиц измерения
        val KB = 1024L
        val MB = KB * 1024L
        val GB = MB * 1024L

        return when {
            size >= GB -> "${decimalFormat.format(size / GB.toDouble())} GB"
            size >= MB -> "${decimalFormat.format(size / MB.toDouble())} MB"
            size >= KB -> "${decimalFormat.format(size / KB.toDouble())} KB"
            else -> "${size} байт"
        }
    }

    fun timeAgo(lastModified: Long): String {
        // Получаем текущее время в миллисекундах с помощью System.currentTimeMillis()
        val currentTime = System.currentTimeMillis()

        // Преобразуем lastModified из миллисекунд в Instant
        val lastModifiedInstant = Instant.ofEpochMilli(lastModified)

        // Преобразуем currentTime из миллисекунд в Instant
        val currentInstant = Instant.ofEpochMilli(currentTime)

        // Вычисляем разницу во времени
        val duration = Duration.between(lastModifiedInstant, currentInstant)

        // Формируем строку в зависимости от величины разницы
        return when {
            duration.toMinutes() < 1 -> "Только что"
            duration.toHours() < 1 -> "${duration.toMinutes()} мин. назад"
            duration.toDays() < 1 -> "${duration.toHours()} ч. назад"
            duration.toDays() in 1..30 -> "${duration.toDays()} дн. назад"
            duration.toDays() in 1..365 -> "${duration.toDays() / 30L} мес. назад"
            else -> "${duration.toDays() / 365} г. назад"
        }
    }

    fun getLastModifiedDate(lastModified: Long): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
        // Преобразуем время в объект Date
        val date = Date(lastModified)
        // Форматируем дату в строку
        return dateFormat.format(date)
    }
}