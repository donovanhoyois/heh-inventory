package be.heh.heh_inventory

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.widget.Button
import android.widget.Toast
import be.heh.heh_inventory.data.DatabasePermission
import be.heh.heh_inventory.data.ErrorCode
import be.heh.heh_inventory.database.entity.User
import com.google.android.material.textfield.TextInputEditText
import org.mindrot.jbcrypt.BCrypt

class LoginActivity : AppCompatActivity(){
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
                    ErrorCode.OK ->
                        // Start the new activity and retrieve the permissions
                        startActivity(Intent(this, DevicesListActivity::class.java)
                            .putExtra("permission", DatabaseHelper.db.userDao().getUserPermissionByMail(emailInput.text.toString()))
                            .apply{})
                }
            }
        }

        // Startup the db (and initialize if first startup)
        DatabaseHelper.startDatabase(applicationContext)
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
        // Try to retrieve the user
        val user : User = DatabaseHelper.db.userDao().getByMail(emailInput.text.toString())
        if (user != null){
            if (!BCrypt.checkpw(passwordInput.text.toString(), user.password)) return ErrorCode.USER_NOT_FOUND
            return ErrorCode.OK
        }
        return ErrorCode.USER_NOT_FOUND
    }
}