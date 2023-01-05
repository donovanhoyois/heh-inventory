package be.heh.heh_inventory.database

import androidx.room.Database
import androidx.room.RoomDatabase
import be.heh.heh_inventory.database.dao.DeviceDao
import be.heh.heh_inventory.database.dao.UserDao
import be.heh.heh_inventory.database.entity.Device
import be.heh.heh_inventory.database.entity.User

@Database(entities = [Device::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun deviceDao(): DeviceDao
    abstract fun userDao(): UserDao
}