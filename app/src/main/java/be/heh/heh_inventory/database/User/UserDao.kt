package be.heh.heh_inventory.database.User

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import be.heh.heh_inventory.data.DatabasePermission
import be.heh.heh_inventory.database.User.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User ORDER BY user_permission DESC")
    fun getAll(): List<User>

    @Query("SELECT * FROM User WHERE uid = (:id)")
    fun getById(id: Int): User

    @Query("SELECT * FROM User WHERE user_mail_address = (:email) LIMIT 1")
    fun getByMail(email: String) : User

    @Query("SELECT user_permission FROM User WHERE user_mail_address = (:email) LIMIT 1")
    fun getUserPermissionByMail(email : String) : DatabasePermission

    @Insert
    fun insert(vararg item: User)

    @Update
    fun update(vararg item: User)
}
