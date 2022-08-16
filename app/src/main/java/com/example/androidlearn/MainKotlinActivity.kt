package com.example.androidlearn

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

/**
 * 倒计时Kotlin版本
 */
class MainKotlinActivity: AppCompatActivity() {

    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            val timeTv = findViewById<TextView>(R.id.mCounterTv)
            timeTv.text = msg.obj.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        createThread()
    }

    private fun createThread() {
        thread {
            var time = 30
            while (time >= 0) {
                Thread.sleep(1000)
                val message = Message()
                message.obj = time.toString()
                mHandler.sendMessage(message)
                time--
            }
        }
    }
}