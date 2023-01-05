package be.heh.heh_inventory.database.entity

import androidx.room.*
import be.heh.heh_inventory.data.DatabasePermission

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "user_mail_address") val email: String? = null,
    @ColumnInfo(name = "user_password") val password: String? = null,
    @ColumnInfo(name = "user_permission") val permission: DatabasePermission = DatabasePermission.READ,
    @ColumnInfo(name = "last_login") val lastLogin: String? = null){
}