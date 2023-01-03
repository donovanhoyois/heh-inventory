package be.heh.heh_inventory.database.entity

import androidx.room.*

@Entity
data class Device(
    @ColumnInfo(name = "device_reference") @PrimaryKey val ref: String,
    @ColumnInfo(name = "device_family") val family: String? = null,
    @ColumnInfo(name = "device_brand") val brand: String? = null,
    @ColumnInfo(name = "device_name") val name: String? = null,
    @ColumnInfo(name = "website") val website: String? = null){
}