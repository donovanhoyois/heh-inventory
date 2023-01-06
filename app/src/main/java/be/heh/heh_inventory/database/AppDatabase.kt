package be.heh.heh_inventory.database

import androidx.room.Database
import androidx.room.RoomDatabase
import be.heh.heh_inventory.database.device.DeviceDao
import be.heh.heh_inventory.database.user.UserDao
import be.heh.heh_inventory.database.device.Device
import be.heh.heh_inventory.database.user.User

@Database(entities = [Device::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun deviceDao(): DeviceDao
    abstract fun userDao(): UserDao
}