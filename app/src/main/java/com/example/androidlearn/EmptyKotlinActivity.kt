package com.example.androidlearn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EmptyKotlinActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_empty)
    }
}