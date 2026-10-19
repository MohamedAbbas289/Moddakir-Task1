package com.example.domain.validation

object InputValidator {
    private val emailPattern =
        Regex("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", RegexOption.IGNORE_CASE)
    private val phonePattern = Regex("^\\+?[0-9]{7,15}$")

    fun isNotBlank(value: String?): Boolean = !value.isNullOrBlank()

    fun isValidEmail(value: String?): Boolean =
        value?.trim()?.matches(emailPattern) == true

    fun isValidPhone(value: String?): Boolean =
        value?.filterNot(Char::isWhitespace)?.matches(phonePattern) == true

    fun isValidPassword(value: String?): Boolean =
        value != null && value.length >= MIN_PASSWORD_LENGTH

    private const val MIN_PASSWORD_LENGTH = 6
}
