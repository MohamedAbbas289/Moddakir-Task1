package com.example.domain.model

data class GoogleLoginData(
    val providerUserId: String? = null,
    val providerType: String? = null,
    val accessToken: String? = null,
    val missingFields: List<String>? = null,
    val socialMediaAccountPending: Boolean? = null
)