package com.example.lab3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.songsList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CustomAdapter(this)
        adapter.setItems(fillList())
        recyclerView.adapter = adapter

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            adapter.setItems(fillListWithBanner())
        }, 5000)
    }

    private fun fillList(): List<ListItem> {
        val items = mutableListOf<ListItem>()
        (0..14).forEach{ items.add(Song("No Hay Ley", "Kali Uchis")) }
        return items
    }

    private fun fillListWithBanner(): List<ListItem> {
        val items = mutableListOf<ListItem>()
        (0..16).forEach{ i ->
            if (i == 2 || i == 16)
                items.add(Advertisement("Technology", "Twitter has a new boss",
                    "Big changes are coming"))
            else
                items.add(Song("No Hay Ley", "Kali Uchis"))
        }
        return items
    }
}