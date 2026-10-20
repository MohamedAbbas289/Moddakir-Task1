package com.example.domain.model

data class GoogleLoginResponse(
    val status: String? = null,
    val result: String? = null,
    val error: String? = null,
    val data: GoogleLoginData? = null
)