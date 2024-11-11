package com.example.lab4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

class MainActivity : AppCompatActivity() {

    companion object{
        const val PASSWORD = "1234"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.editTextTextPassword)
        val springAnimation = SpringAnimation(editText, DynamicAnimation.TRANSLATION_X, 0f)
        springAnimation.spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        springAnimation.spring.stiffness = SpringForce.STIFFNESS_LOW

        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            if (editText.text.toString() == PASSWORD){
                Toast.makeText(this, "All Right!", Toast.LENGTH_SHORT).show()
            }
            else {
                springAnimation.setStartValue(100f)
                springAnimation.start()
            }
        }

    }
}