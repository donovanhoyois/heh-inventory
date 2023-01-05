package be.heh.heh_inventory.database.entity

import androidx.room.*
import java.util.*

@Entity
data class Device(
    @ColumnInfo(name = "device_reference") @PrimaryKey val ref: String,
    @ColumnInfo(name = "device_family") val family: String? = null,
    @ColumnInfo(name = "device_brand") val brand: String? = null,
    @ColumnInfo(name = "device_name") val name: String? = null,
    @ColumnInfo(name = "website") val website: String? = null,
    @ColumnInfo(name = "last_used_date") val lastUsedDate: String? = null,
    @ColumnInfo(name = "last_retrieved_date") val lastRetrievedDate: String? = null){
}