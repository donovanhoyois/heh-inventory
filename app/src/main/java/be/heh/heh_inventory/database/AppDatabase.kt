package be.heh.heh_inventory.database

import androidx.room.Database
import androidx.room.RoomDatabase
import be.heh.heh_inventory.database.Device.DeviceDao
import be.heh.heh_inventory.database.User.UserDao
import be.heh.heh_inventory.database.Device.Device
import be.heh.heh_inventory.database.User.User

@Database(entities = [Device::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun deviceDao(): DeviceDao
    abstract fun userDao(): UserDao
}