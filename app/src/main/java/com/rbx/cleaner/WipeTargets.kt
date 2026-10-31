package com.rbx.cleaner

object WipeTargets {
    // Caminhos conhecidos que Delta, variantes e rastros de navegador deixam
    val paths = listOf(
        "/sdcard/Delta",
        "/sdcard/Download/Delta.apk",
        "/sdcard/Download/delta.apk",
        "/sdcard/Android/data/com.roblox.client/files/exploits",
        "/sdcard/Android/data/com.delta.executor",
        "/sdcard/Android/data/com.deltaexploits.deltax",
        "/sdcard/Android/media/com.roblox.client/.delta",
        // Alvos de histórico e cache do Chrome e Google
        "/data/data/com.android.chrome/app_chrome/Default/History",
        "/data/data/com.android.chrome/app_chrome/Default/Visited Links",
        "/data/data/com.android.chrome/cache/",
        "/data/data/com.google.android.googlequicksearchbox/cache/"
    )
}
