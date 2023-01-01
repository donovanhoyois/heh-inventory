package be.heh.heh_inventory.data

import be.heh.heh_inventory.R

enum class ErrorCode(val errorText : String) {
    EMAIL_FORMAT_INVALID(R.string.error_mail_format.toString()),
    PASSWORD_FORMAT_INVALID(R.string.error_password_format.toString()),
    USER_NOT_FOUND(R.string.error_user_not_found.toString()),
    OK(R.string.error_ok.toString())
}