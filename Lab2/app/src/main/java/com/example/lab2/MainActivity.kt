package com.example.lab2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nextButton : Button = findViewById(R.id.button)
        nextButton.setOnClickListener {
            val intent = Intent(
                this,
                SecondActivity::class.java
            )
            startActivity(intent)
        }
    }
}