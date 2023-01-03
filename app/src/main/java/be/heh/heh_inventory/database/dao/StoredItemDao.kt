package be.heh.heh_inventory.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import be.heh.heh_inventory.database.entity.Device

@Dao
interface StoredItemDao {
    @Query("SELECT * FROM Device")
    fun getAll(): MutableList<Device>

    @Query("SELECT * FROM Device WHERE device_reference = (:ref)")
    fun getById(ref : String): Device

    @Insert
    fun insert(vararg item: Device)

    @Update
    fun update(vararg item: Device)
}