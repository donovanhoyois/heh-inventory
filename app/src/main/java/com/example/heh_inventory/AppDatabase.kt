package com.example.heh_inventory

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StoredItem::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun storedItemDao(): StoredItemDao
}