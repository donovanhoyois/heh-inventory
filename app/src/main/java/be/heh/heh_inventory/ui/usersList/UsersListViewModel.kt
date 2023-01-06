package be.heh.heh_inventory.ui.usersList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.heh.heh_inventory.database.DatabaseHelper
import be.heh.heh_inventory.database.user.User

class UsersListViewModel : ViewModel() {
    private val _users = MutableLiveData<List<User>>().apply{
        value = DatabaseHelper.db.userDao().getAll()
    }
    val users: MutableLiveData<List<User>> = _users
}