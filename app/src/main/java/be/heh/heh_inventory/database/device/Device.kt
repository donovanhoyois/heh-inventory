package be.heh.heh_inventory.database.device

import androidx.room.*
import be.heh.heh_inventory.data.DeviceAction
import be.heh.heh_inventory.data.DeviceFamily

@Entity
data class Device(
    @ColumnInfo(name = "device_reference") @PrimaryKey val ref: String,
    @ColumnInfo(name = "device_family") val family: DeviceFamily = DeviceFamily.PHONE,
    @ColumnInfo(name = "device_brand") val brand: String? = null,
    @ColumnInfo(name = "device_name") val name: String? = null,
    @ColumnInfo(name = "website") val website: String? = null,
    @ColumnInfo(name = "next_action") var nextAction: DeviceAction = DeviceAction.GIVE){
}