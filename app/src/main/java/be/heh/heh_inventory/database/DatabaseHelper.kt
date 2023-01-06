package be.heh.heh_inventory.database

import android.content.Context
import androidx.room.Room
import be.heh.heh_inventory.data.DatabasePermission
import be.heh.heh_inventory.data.DeviceFamily
import be.heh.heh_inventory.database.Device.Device
import be.heh.heh_inventory.database.User.User
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
            // Called only at the first startup (or after data cleanup)
            if (db.userDao().getAll().isEmpty() && db.deviceDao().getAll().isEmpty()){
                db.userDao().insert(User(0, "fabrice.scopel@heh.be", BCrypt.hashpw("admin", BCrypt.gensalt()), DatabasePermission.READ_WRITE))
                db.userDao().insert(User(0, "standard.user@std.heh.be", BCrypt.hashpw("test", BCrypt.gensalt())))
                db.deviceDao().insert(Device("146009/A",DeviceFamily.PHONE, "LG", "Nexus 5", "https://www.lg.com/"))
                db.deviceDao().insert(Device("146009/B",DeviceFamily.PHONE, "LG", "Nexus 5", "https://www.lg.com/"))
                db.deviceDao().insert(Device("146009/C",DeviceFamily.PHONE, "LG", "Nexus 5", "https://www.lg.com/"))
                db.deviceDao().insert(Device("146009/D",DeviceFamily.PHONE, "LG", "Nexus 5", "https://www.lg.com/"))
                db.deviceDao().insert(Device("146009/E",DeviceFamily.PHONE, "LG", "Nexus 5", "https://www.lg.com/"))
                db.deviceDao().insert(Device("146010/A",DeviceFamily.TABLET, "Samsung", "Nexus 10", "https://samsung.com/be_fr/"))
                db.deviceDao().insert(Device("146010/B",DeviceFamily.TABLET, "Samsung", "Nexus 10", "https://samsung.com/be_fr/"))
                db.deviceDao().insert(Device("146010/C",DeviceFamily.TABLET, "Samsung", "Nexus 10", "https://samsung.com/be_fr/"))
                db.deviceDao().insert(Device("146010/D",DeviceFamily.TABLET, "Samsung", "Nexus 10", "https://samsung.com/be_fr/"))
                db.deviceDao().insert(Device("146010/E",DeviceFamily.TABLET, "Samsung", "Nexus 10", "https://samsung.com/be_fr/"))
            }
        }
    }
}