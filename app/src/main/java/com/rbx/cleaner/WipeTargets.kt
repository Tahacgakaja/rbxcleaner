package com.rbx.cleaner

object WipeTargets {
    // caminhos conhecidos que Delta e variantes deixam
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
        "/sdcard/Pictures/.thumbnails"
    )

    val packages = listOf(
        "com.delta.executor",
        "com.deltaexploits.deltax",
        "com.delta.android"
    )

    // palavras-chave pra varrer Downloads/Documents
    val keywords = listOf("delta", "exploit", "executor", "dex", "lua", "rbxlx")
}
