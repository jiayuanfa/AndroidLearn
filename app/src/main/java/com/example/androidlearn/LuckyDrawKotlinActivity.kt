package com.example.androidlearn

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

/**
 * 抽奖系统Kotlin版本
 */
class LuckyDrawKotlinActivity: AppCompatActivity() {

    private val strings = arrayOf("优弧","船长","托尼","春哥","若川巨","南方佬")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lucky_draw)

        findViewById<Button>(R.id.mLuckyDrawBtn).setOnClickListener {
            findViewById<TextView>(R.id.mResultTv).text = strings[Random.nextInt(strings.size)]
        }
    }
}