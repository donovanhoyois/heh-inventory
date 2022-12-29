package com.example.heh_inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "heh-inventory-db"
        ).build()

        val itemDao = db.storedItemDao()
        val items : List<StoredItem> = itemDao.getAll()
        */
    }
}