package com.rbx.cleaner

import android.content.Context
import java.io.File
import java.io.RandomAccessFile
import kotlin.random.Random

class Wiper(private val ctx: Context) {

    private val log = StringBuilder()

    fun run(): String {
        log.clear()
        step("== PANIC WIPE iniciado ==")

        // 1. desinstala pacotes do Delta se ainda instalados
        WipeTargets.packages.forEach { pkg ->
            val r = ShizukuRunner.exec("pm uninstall --user 0 $pkg")
            step("uninstall $pkg -> $r")
        }

        // 2. shred arquivos conhecidos
        WipeTargets.paths.forEach { shredPath(it) }

        // 3. varre Downloads/Documents por keyword
        listOf("/sdcard/Download", "/sdcard/Documents").forEach { base ->
            File(base).walkTopDown().forEach { f ->
                if (f.isFile && WipeTargets.keywords.any {
                        f.name.lowercase().contains(it) }) {
                    shred(f)
                }
            }
        }

        // 4. limpa cache do Roblox (NÃO desinstala, só o cache onde o Delta injeta)
        ShizukuRunner.exec("pm clear com.roblox.client")
        step("roblox cache clear OK")

        // 5. logcat
        ShizukuRunner.exec("logcat -c")
        step("logcat zerado")

        // 6. recent tasks
        ShizukuRunner.exec("am stack list; input keyevent KEYCODE_APP_SWITCH; " +
                "sleep 1; am kill-all")
        step("recents limpas")

        // 7. MediaStore — força rescan pra sumir referências
        ShizukuRunner.exec(
            "content delete --uri content://media/external/file " +
            "--where \"_data LIKE '%delta%' OR _data LIKE '%exploit%'\""
        )
        step("MediaStore purgado")

        // 8. clipboard
        ShizukuRunner.exec("service call clipboard 2")
        step("clipboard limpo")

        // 9. DNS cache
        ShizukuRunner.exec("ndc resolver clearnetdns wlan0")

        step("== WIPE concluído ==")
        return log.toString()
    }

    private fun shredPath(path: String) {
        val f = File(path)
        if (!f.exists()) { step("skip $path"); return }
        if (f.isDirectory) f.walkBottomUp().forEach { if (it.isFile) shred(it) }
        else shred(f)
        ShizukuRunner.exec("rm -rf \"$path\"")
        step("shred $path")
    }

    private fun shred(file: File) {
        try {
            val len = file.length()
            if (len > 0) {
                RandomAccessFile(file, "rw").use { raf ->
                    val buf = ByteArray(4096)
                    var written = 0L
                    // 3 passadas: random, zeros, random
                    repeat(3) { pass ->
                        raf.seek(0)
                        written = 0
                        while (written < len) {
                            if (pass == 1) buf.fill(0) else Random.nextBytes(buf)
                            val toWrite = minOf(buf.size.toLong(), len - written).toInt()
                            raf.write(buf, 0, toWrite)
                            written += toWrite
                        }
                        raf.fd.sync()
                    }
                }
            }
            file.delete()
        } catch (e: Exception) { step("shred fail ${file.name}: ${e.message}") }
    }

    private fun step(s: String) { log.appendLine(s) }
}
