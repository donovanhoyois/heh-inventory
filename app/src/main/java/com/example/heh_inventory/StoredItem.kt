package com.example.heh_inventory

import androidx.room.*

@Entity
data class StoredItem(
    @PrimaryKey val uid: Int?,
    @ColumnInfo(name = "device_family") val family: String? = null,
    @ColumnInfo(name = "device_name") val name: String? = null,
    @ColumnInfo(name = "website") val website: String? = null){
}