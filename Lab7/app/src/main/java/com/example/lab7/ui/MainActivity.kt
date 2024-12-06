package com.example.lab7.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.lab7.ContactsApplication
import com.example.lab7.R


class MainActivity : AppCompatActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as ContactsApplication).appComponent.inject(this)
        setContentView(R.layout.activity_main)
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (!it) {
                    Toast.makeText(this, "Need permissions", Toast.LENGTH_SHORT).show()
                    return@registerForActivityResult
                }
            }
        permissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_container, ContactsListFragment()).commit()
    }
}