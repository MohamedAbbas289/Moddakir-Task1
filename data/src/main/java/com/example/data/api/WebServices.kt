package com.example.data.api

import com.example.data.model.AuthResponse
import com.example.domain.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.GET

interface WebServices {

    @GET("/api/auth/public/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse
}