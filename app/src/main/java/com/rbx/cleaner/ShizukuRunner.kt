package com.rbx.cleaner

import android.content.pm.PackageManager
import rikka.shizuku.Shizuku
import java.io.BufferedReader
import java.io.InputStreamReader

object ShizukuRunner {
    fun exec(cmd: String): String {
        return try {
            val method = Shizuku::class.java.getDeclaredMethod(
                "newProcess", 
                Array<String>::class.java, 
                Array<String>::class.java, 
                String::class.java
            )
            method.isAccessible = true
            val process = method.invoke(null, arrayOf("sh", "-c", cmd), null, null) as Process
            val out = BufferedReader(InputStreamReader(process.inputStream)).readText()
            process.waitFor()
            out
        } catch (e: Exception) {
            "ERR: ${e.message}"
        }
    }

    fun isReady(): Boolean =
        Shizuku.pingBinder() && Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
}
