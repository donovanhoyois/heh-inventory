package be.heh.heh_inventory.ui.devicesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.heh.heh_inventory.database.DatabaseHelper
import be.heh.heh_inventory.database.entity.Device

class DevicesListViewModel : ViewModel() {

    private val _devices = MutableLiveData<List<Device>>().apply{
        value = DatabaseHelper.db.deviceDao().getAll()
    }
    val devices: LiveData<List<Device>> = _devices
}