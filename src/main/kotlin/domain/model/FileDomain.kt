package domain.model
import java.io.File

data class FileDomain(
    val name: String,
    val nameWithExtension: String,
    val type: String,
    val dateLong: Long,
    val isDirectory: Boolean,
    val isHidden: Boolean,
    val path: String,
    val sizeLong: Long,
    val isRooted: Boolean,
    val absolutePath: String

){
    companion object {
        fun toFileDomain(file: File): FileDomain? {

            val fileDomain = try {
                FileDomain(
                    name = file.name.take(file.name.length - (file.extension.length + if(file.isDirectory) 0 else 1)),
                    nameWithExtension = file.name,
                    type = if (file.isDirectory) "" else file.extension,
                    dateLong = file.lastModified(),
                    isDirectory = file.isDirectory,
                    isHidden = file.isHidden,
                    path = file.path,
                    sizeLong = file.length(),
                    isRooted = file.isRooted,
                    absolutePath = file.absolutePath
                )
            }
            catch (e: Exception) {
                null
            }
            return fileDomain
        }
    }
}


