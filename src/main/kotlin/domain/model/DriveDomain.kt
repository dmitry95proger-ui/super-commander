package domain.model

class DriveDomain(
    val name: String,
    val totalSpace: Long,
    val freeSpace: Long,
    var isUSB: Boolean
)