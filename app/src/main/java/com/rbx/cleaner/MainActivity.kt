package com.rbx.cleaner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rbx.cleaner.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rikka.shizuku.Shizuku

class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        Shizuku.addRequestPermissionResultListener { _, result ->
            b.status.text = if (result == 0) "Shizuku: OK" else "Shizuku: negado"
        }

        b.btnPermission.setOnClickListener {
            if (!Shizuku.pingBinder()) {
                b.status.text = "Shizuku não ativo. Abra o app Shizuku e inicie via ADB/wireless."
            } else if (Shizuku.checkSelfPermission() != 0) {
                Shizuku.requestPermission(1001)
            } else b.status.text = "Shizuku: OK"
        }

        b.btnWipe.setOnClickListener {
            if (!ShizukuRunner.isReady()) {
                b.status.text = "Autorize Shizuku primeiro."
                return@setOnClickListener
            }
            b.btnWipe.isEnabled = false
            b.status.text = "Limpando..."
            lifecycleScope.launch {
                val result = withContext(Dispatchers.IO) { Wiper(this@MainActivity).run() }
                b.logView.text = result
                b.status.text = "Pronto. Pode compartilhar tela."
                b.btnWipe.isEnabled = true
            }
        }
    }
}
