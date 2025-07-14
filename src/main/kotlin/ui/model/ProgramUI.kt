package ui.model

data class ProgramUI(val name: String, val path: String, val painter: String, val isDefault: Boolean = false) {

    companion object {
        fun toProgramUI(valueString: String): ProgramUI? {

            if(valueString.isEmpty()) return null
            if(valueString.split('*').size != 4) return null
            val programUIString = valueString.split('*')
            val isDefault = programUIString[3] == "default"
            return ProgramUI(programUIString[0], programUIString[1], programUIString[2], isDefault)
        }
    }

    override fun toString(): String {
        return "$name*$path*$painter*$isDefault"
    }
}