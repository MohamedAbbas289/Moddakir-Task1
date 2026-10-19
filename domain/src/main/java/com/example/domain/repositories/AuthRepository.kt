package com.example.domain.repositories

import com.example.domain.common.ResultWrapper
import com.example.domain.model.LoginRequest
import com.example.domain.model.user.UserResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): Flow<ResultWrapper<UserResponse>>
}