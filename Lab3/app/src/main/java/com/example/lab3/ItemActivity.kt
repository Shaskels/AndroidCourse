package com.example.lab3

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ItemActivity : AppCompatActivity() {

    companion object{
        const val UP_TEXT_KEY = "upText"
        const val DOWN_TEXT_KEY = "downText"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        val text1 : TextView = findViewById(R.id.text1)
        val text2 : TextView = findViewById(R.id.text2)

        text1.text = intent.getStringExtra(UP_TEXT_KEY)
        text2.text = intent.getStringExtra(DOWN_TEXT_KEY)
    }
}