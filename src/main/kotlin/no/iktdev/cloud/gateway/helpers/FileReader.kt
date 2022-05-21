package no.iktdev.cloud.gateway.helpers

import org.slf4j.LoggerFactory
import java.io.File

class FileReader {
    fun getText(file: File): String? {
        try {
            val instr = file.inputStream()
            return instr.bufferedReader().use { it.readText() }
        }
        catch (e: Exception) {
            LoggerFactory.getLogger(javaClass.simpleName).error("Failed to read preference file: ${file.absolutePath}.. Will use default configuration")
        }
        return null
    }
}