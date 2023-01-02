package be.heh.heh_inventory

import android.content.Context
import androidx.room.Room
import be.heh.heh_inventory.database.AppDatabase
import be.heh.heh_inventory.database.entity.Device
import be.heh.heh_inventory.database.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

class DatabaseHelper {
    companion object{
        lateinit var db : AppDatabase private set get
        fun startDatabase(applicationContext : Context){
            CoroutineScope(Dispatchers.IO).launch {
                db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "heh-inventory-db"
                ).allowMainThreadQueries().build()
            }
        }
        fun initializeDatabase(){
            val passwordHash = BCrypt.hashpw("Mebeya1007", BCrypt.gensalt())
            db.userDao().insert(User(0, "donovan.hoyois@std.heh.be", passwordHash))
            db.storedItemDao().insert(Device(0,"phone", "Samsung", "Galaxy S8", "https://samsung.com/"))
            db.storedItemDao().insert(Device(0,"phone", "Samsung", "Galaxy S9", "https://samsung.com/"))
        }
    }
}