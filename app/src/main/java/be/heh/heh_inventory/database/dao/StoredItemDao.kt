package be.heh.heh_inventory.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import be.heh.heh_inventory.database.entity.StoredItem

@Dao
interface StoredItemDao {
    @Query("SELECT * FROM StoredItem")
    fun getAll(): List<StoredItem>

    @Query("SELECT * FROM StoredItem WHERE uid = (:id)")
    fun getById(id: Int): StoredItem

    @Insert
    fun insert(vararg item: StoredItem)

    @Update
    fun update(vararg item: StoredItem)
}