package com.rbx.cleaner

import android.content.pm.PackageManager
import rikka.shizuku.Shizuku
import java.io.BufferedReader
import java.io.InputStreamReader

object ShizukuRunner {
    fun exec(cmd: String): String {
        return try {
            // Usa o Binder do Shizuku para invocar o processo via remote service / shizuku API pública
            val shizukuUserServiceArgs = Shizuku.UserServiceArgs(
                android.content.ComponentName("com.rbx.cleaner", "com.rbx.cleaner.ShizukuUserService")
            )
            
            // Alternativa direta utilizando o método que o Shizuku expõe para injetar comandos:
            val process = Shizuku.newProcess(arrayOf("sh", "-c", cmd), null, null)
            val out = BufferedReader(InputStreamReader(process.inputStream)).readText()
            process.waitFor()
            out
        } catch (e: Exception) {
            // Se falhar a chamada direta devido à visibilidade, podes recorrer a reflection se necessário, 
            // mas vamos atualizar a forma de chamada:
            try {
                val method = Shizuku::class.java.getDeclaredMethod("newProcess", Array<String>::class.java, Array<String>::class.java, String::class.java)
                method.isAccessible = true
                val process = method.invoke(null, arrayOf("sh", "-c", cmd), null, null) as Process
                val out = BufferedReader(InputStreamReader(process.inputStream)).readText()
                process.waitFor()
                out
            } catch (ex: Exception) {
                "ERR: ${ex.message}"
            }
        }
    }

    fun isReady(): Boolean =
        Shizuku.pingBinder() && Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
}
