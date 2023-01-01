package be.heh.heh_inventory.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import be.heh.heh_inventory.database.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Query("SELECT * FROM User WHERE uid = (:id)")
    fun getById(id: Int): User

    @Insert
    fun insert(vararg item: User)

    @Update
    fun update(vararg item: User)
}