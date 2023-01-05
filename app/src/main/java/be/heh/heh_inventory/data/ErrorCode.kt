package be.heh.heh_inventory.data

import be.heh.heh_inventory.R

enum class ErrorCode() {
    EMAIL_FORMAT_INVALID(),
    PASSWORD_FORMAT_INVALID(),
    USER_NOT_FOUND(),
    USER_ALREADY_EXISTS(),
    OK(),
}