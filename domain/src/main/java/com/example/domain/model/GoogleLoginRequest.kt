package com.example.domain.model

data class GoogleLoginRequest(
    val email: String,
    val providerUserId: String,
    val providerType: String,
    val fullName: String,
    val avatarUrl: String?,
    val deviceUUID: String,
    val phone: String?,
    val gender: String?
)