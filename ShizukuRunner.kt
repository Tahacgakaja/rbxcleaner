package com.rbx.cleaner

import rikka.shizuku.Shizuku
import java.io.BufferedReader
import java.io.InputStreamReader

object ShizukuRunner {
    fun exec(cmd: String): String {
        return try {
            val process = Shizuku.newProcess(arrayOf("sh", "-c", cmd), null, null)
            val out = BufferedReader(InputStreamReader(process.inputStream)).readText()
            process.waitFor()
            out
        } catch (e: Exception) { "ERR: ${e.message}" }
    }

    fun isReady(): Boolean =
        Shizuku.pingBinder() && Shizuku.checkSelfPermission() == 0
}
