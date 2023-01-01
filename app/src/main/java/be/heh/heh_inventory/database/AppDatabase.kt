package be.heh.heh_inventory.database

import androidx.room.Database
import androidx.room.RoomDatabase
import be.heh.heh_inventory.database.dao.StoredItemDao
import be.heh.heh_inventory.database.dao.UserDao
import be.heh.heh_inventory.database.entity.StoredItem
import be.heh.heh_inventory.database.entity.User

@Database(entities = [StoredItem::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun storedItemDao(): StoredItemDao
    abstract fun userDao(): UserDao
}