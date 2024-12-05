package com.example.lab13_kotlinnew

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.IBinder

class MyService : Service() {

    private lateinit var handler: Handler

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channel = intent?.getStringExtra("channel") ?: ""
        handler = Handler(Looper.getMainLooper()) // 使用主線程處理廣播發送

        // 發送初始廣播
        sendMessage(channel, "歡迎來到${getChannelName(channel)}頻道")

        // 模擬延遲3秒後發送另一條訊息
        handler.postDelayed({
            sendMessage(channel, "即將播放本月TOP10音樂")
        }, 3000)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    // 發送廣播訊息
    private fun sendMessage(channel: String, msg: String) {
        sendBroadcast(Intent(channel).putExtra("msg", msg))
    }

    // 根據頻道返回名稱
    private fun getChannelName(channel: String): String {
        return when (channel) {
            "music" -> "音樂"
            "new" -> "新聞"
            "sport" -> "體育"
            else -> "未知"
        }
    }
}