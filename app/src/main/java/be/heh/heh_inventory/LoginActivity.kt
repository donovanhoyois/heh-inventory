package be.heh.heh_inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.widget.Button
import android.widget.Toast
import androidx.room.Room
import be.heh.heh_inventory.data.ErrorCode
import be.heh.heh_inventory.database.AppDatabase
import be.heh.heh_inventory.database.entity.StoredItem
import be.heh.heh_inventory.database.entity.User
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // UI Elements
        val loginButton = findViewById<Button>(R.id.login_button)
        val emailInput = findViewById<TextInputEditText>(R.id.input_email)
        val passwordInput = findViewById<TextInputEditText>(R.id.input_password)

        // Clicks listeners
        loginButton.setOnClickListener{
            when(checkInputs(emailInput, passwordInput)){
                ErrorCode.EMAIL_FORMAT_INVALID -> Toast.makeText(this,
                    R.string.error_mail_format, Toast.LENGTH_SHORT).show()
                ErrorCode.PASSWORD_FORMAT_INVALID -> Toast.makeText(this,
                    R.string.error_password_format, Toast.LENGTH_SHORT).show()
                ErrorCode.OK -> when(tryToLogin(emailInput, passwordInput)){
                    ErrorCode.USER_NOT_FOUND -> Toast.makeText(this,
                        R.string.error_user_not_found, Toast.LENGTH_SHORT).show()
                    ErrorCode.OK -> Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
                }
            }
        }
        startDb()
    }
    private fun checkInputs(emailInput : TextInputEditText, passwordInput : TextInputEditText) : Enum<ErrorCode>{
        // Retrieving text
        val emailText = emailInput.text.toString()
        val passwordText = passwordInput.text.toString()
        // Checking
        if (isEmpty(emailText) || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) return ErrorCode.EMAIL_FORMAT_INVALID
        if (isEmpty(passwordText) || passwordText.length < 5) return ErrorCode.PASSWORD_FORMAT_INVALID
        return ErrorCode.OK
    }
    private fun tryToLogin(emailInput : TextInputEditText, passwordInput : TextInputEditText) : Enum<ErrorCode>{
        return ErrorCode.USER_NOT_FOUND
    }
    private fun startDb(){
        CoroutineScope(Dispatchers.IO).launch {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "heh-inventory-db"
            ).build()
            val itemDao = db.storedItemDao()
            val userDao = db.userDao()
            userDao.insert(User(0, "donovan.hoyois@std.heh.be", "test"))
            itemDao.insert(StoredItem(0, "Samsung", "Galaxy S9", "https://samsung.com/"))

            val items : List<StoredItem> = itemDao.getAll()
            val users : List<User> = userDao.getAll()
            print(users)
            print(items)
        }
    }
}