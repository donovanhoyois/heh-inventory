package be.heh.heh_inventory.ui.deviceAdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DeviceAddViewModel : ViewModel() {

    private val _families = MutableLiveData<List<String>>().apply{
        value = mutableListOf("Téléphone", "Tablette")
    }
    val families: LiveData<List<String>> = _families
}