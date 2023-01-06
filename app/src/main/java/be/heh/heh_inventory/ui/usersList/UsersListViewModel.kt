package be.heh.heh_inventory.ui.usersList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.heh.heh_inventory.DatabaseHelper
import be.heh.heh_inventory.database.entity.Device
import be.heh.heh_inventory.database.entity.User

class UsersListViewModel : ViewModel() {
    private val _users = MutableLiveData<List<User>>().apply{
        value = DatabaseHelper.db.userDao().getAll()
    }
    val users: MutableLiveData<List<User>> = _users
}