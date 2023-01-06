package be.heh.heh_inventory.database.device

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DeviceDao {
    @Query("SELECT * FROM Device ORDER BY next_action ASC, device_reference ASC")
    fun getAll(): MutableList<Device>?

    @Query("SELECT * FROM Device WHERE device_reference = (:ref)")
    fun getByRef(ref : String): Device?

    @Insert
    fun insert(vararg item: Device)

    @Update
    fun update(vararg item: Device)

    @Delete
    fun delete(vararg item: Device)
}