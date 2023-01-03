package be.heh.heh_inventory.ui.deviceAdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DeviceAddViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is deviceAdd Fragment"
    }
    val text: LiveData<String> = _text
}