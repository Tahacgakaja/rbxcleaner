package com.rbx.cleaner

object WipeTargets {
    // Caminhos conhecidos que Delta e variantes deixam
    val paths = listOf(
        "/sdcard/Delta",
        "/sdcard/Download/Delta.apk",
        "/sdcard/Download/delta.apk",
        "/sdcard/Android/data/com.roblox.client/files/exploits",
        "/sdcard/Android/data/com.delta.executor",
        "/sdcard/Android/data/com.deltaexploits.deltax",
        "/sdcard/Android/media/com.roblox.client/.delta",
        "/data/local/tmp/delta",
        "/sdcard/DCIM/.thumbnails",
        "/sdcard/Pictures/.thumbnails",
        "/data/data/com.android.chrome/app_chrome/Default/History",
        "/data/data/com.android.chrome/app_chrome/Default/Visited Links",
        "/data/data/com.android.chrome/cache/",
        "/data/data/com.google.android.googlequicksearchbox/cache/"
    )

    val packages = listOf(
        "com.delta.executor",
        "com.deltaexploits.deltax",
        "com.delta.android"
    )

    val keywords = listOf("delta", "exploit", "executor", "dex", "lua", "rbxlx")
}
