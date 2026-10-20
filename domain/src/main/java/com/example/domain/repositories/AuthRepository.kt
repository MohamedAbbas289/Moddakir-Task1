package com.example.domain.repositories

import com.example.domain.common.ResultWrapper
import com.example.domain.model.GoogleLoginRequest
import com.example.domain.model.GoogleLoginResponse
import com.example.domain.model.user.UserResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<ResultWrapper<UserResponse>>

    suspend fun googleLogin(
        request: GoogleLoginRequest
    ): Flow<ResultWrapper<GoogleLoginResponse>>
}
