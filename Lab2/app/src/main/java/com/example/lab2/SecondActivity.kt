package com.example.lab2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val nextButton : Button = findViewById(R.id.button)
        nextButton.setOnClickListener {
            val intent = Intent(
                this,
                ThirdActivity::class.java
            )
            startActivity(intent)
        }
    }
}