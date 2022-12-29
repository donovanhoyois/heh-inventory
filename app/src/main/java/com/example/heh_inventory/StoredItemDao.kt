package com.example.heh_inventory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

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