package be.heh.heh_inventory

import android.content.Context
import androidx.room.Room
import be.heh.heh_inventory.data.DatabasePermission
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
            val startJob = CoroutineScope(Dispatchers.IO).launch {
                db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "heh-inventory-db"
                ).allowMainThreadQueries().build()
                initializeDatabase()
            }
        }
        private fun initializeDatabase(){
            if (db.userDao().getAll().isEmpty() && db.storedItemDao().getAll().isEmpty()){
                db.userDao().insert(User(0, "donovan.hoyois@std.heh.be", BCrypt.hashpw("Mebeya1007", BCrypt.gensalt()), DatabasePermission.READ_WRITE))
                db.userDao().insert(User(0, "donovan.hoyois2@std.heh.be", BCrypt.hashpw("Mebeya1007", BCrypt.gensalt())))
                db.storedItemDao().insert(Device("146009/A","phone", "LG", "Nexus 5", "https://www.lg.com/"))
                db.storedItemDao().insert(Device("146009/B","phone", "LG", "Nexus 5", "https://www.lg.com/"))
                db.storedItemDao().insert(Device("146009/C","phone", "LG", "Nexus 5", "https://www.lg.com/"))
                db.storedItemDao().insert(Device("146009/D","phone", "LG", "Nexus 5", "https://www.lg.com/"))
                db.storedItemDao().insert(Device("146009/E","phone", "LG", "Nexus 5", "https://www.lg.com/"))
                db.storedItemDao().insert(Device("146010/A","tablet", "Samsung", "Nexus 10", "https://samsung.com/be_fr/"))
                db.storedItemDao().insert(Device("146010/B","tablet", "Samsung", "Nexus 10", "https://samsung.com/be_fr/"))
                db.storedItemDao().insert(Device("146010/C","tablet", "Samsung", "Nexus 10", "https://samsung.com/be_fr/"))
                db.storedItemDao().insert(Device("146010/D","tablet", "Samsung", "Nexus 10", "https://samsung.com/be_fr/"))
                db.storedItemDao().insert(Device("146010/E","tablet", "Samsung", "Nexus 10", "https://samsung.com/be_fr/"))
            }
        }
    }
}