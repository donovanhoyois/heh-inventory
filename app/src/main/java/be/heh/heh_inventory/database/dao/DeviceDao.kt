package be.heh.heh_inventory.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import be.heh.heh_inventory.database.entity.Device

@Dao
interface DeviceDao {
    @Query("SELECT * FROM Device ORDER BY next_action ASC")
    fun getAll(): MutableList<Device>

    @Query("SELECT * FROM Device WHERE device_reference = (:ref)")
    fun getByRef(ref : String): Device

    @Insert
    fun insert(vararg item: Device)

    @Update
    fun update(vararg item: Device)
}