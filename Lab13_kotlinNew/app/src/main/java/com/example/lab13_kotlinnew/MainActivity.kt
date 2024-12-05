package com.example.lab13_kotlinnew

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var messageViewModel: MessageViewModel
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
        val tvMsg = findViewById<TextView>(R.id.tvMsg)

        // 觀察 ViewModel 中的消息變化
        messageViewModel.message.observe(this, { msg ->
            tvMsg.text = msg
        })

        // 註冊廣播接收器
        broadcastReceiver = createReceiver()
        val intentFilter = IntentFilter().apply {
            addAction("music")
            addAction("new")
            addAction("sport")
        }
        registerReceiver(broadcastReceiver, intentFilter)

        // 設置按鈕點擊事件
        findViewById<Button>(R.id.btnMusic).setOnClickListener {
            registerService("music")
        }

        findViewById<Button>(R.id.btnNew).setOnClickListener {
            registerService("new")
        }

        findViewById<Button>(R.id.btnSport).setOnClickListener {
            registerService("sport")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver) // 註銷廣播接收器
    }

    // 創建並返回一個廣播接收器
    private fun createReceiver(): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val msg = intent.getStringExtra("msg") ?: "無法接收到消息"
                messageViewModel.updateMessage(msg) // 更新 ViewModel
            }
        }
    }

    // 註冊服務並啟動
    private fun registerService(channel: String) {
        val serviceIntent = Intent(this, MyService::class.java).apply {
            putExtra("channel", channel)
        }
        startService(serviceIntent) // 啟動服務
    }
}