package com.example.data.api

import com.example.data.model.AuthResponse
import com.example.data.model.GoogleLoginResponseDto
import com.example.domain.model.GoogleLoginRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface WebServices {

    @POST("/api/auth/public/login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): AuthResponse

    @POST("/api/auth/public/social-login")
    suspend fun socialLogin(
        @Body googleLoginRequest: GoogleLoginRequest
    ): GoogleLoginResponseDto
}