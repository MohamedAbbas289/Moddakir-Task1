package com.example.data.data_source_contract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.LoginRequest
import com.example.domain.model.user.UserResponse
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    suspend fun login(loginRequest: LoginRequest): Flow<ResultWrapper<UserResponse>>
}