package com.example.heh_inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

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
                ErrorCode.EMAIL_FORMAT_INVALID -> Toast.makeText(this, R.string.error_mail_format, Toast.LENGTH_SHORT).show()
                ErrorCode.PASSWORD_FORMAT_INVALID -> Toast.makeText(this, R.string.error_password_format, Toast.LENGTH_SHORT).show()
                ErrorCode.USER_NOT_FOUND -> Toast.makeText(this, R.string.error_user_not_found, Toast.LENGTH_SHORT).show()
                ErrorCode.OK -> Toast.makeText(this, R.string.error_ok, Toast.LENGTH_SHORT).show()
            }
        }




        /*
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "heh-inventory-db"
        ).build()

        val itemDao = db.storedItemDao()
        val items : List<StoredItem> = itemDao.getAll()
        */
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
    private fun userExists() : Boolean{
        return false
    }
}