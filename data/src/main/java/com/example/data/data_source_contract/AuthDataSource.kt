package com.example.data.data_source_contract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.GoogleLoginRequest
import com.example.domain.model.GoogleLoginResponse
import com.example.domain.model.user.UserResponse
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    suspend fun login(email: String, password: String): Flow<ResultWrapper<UserResponse>>

    suspend fun googleLogin(request: GoogleLoginRequest): Flow<ResultWrapper<GoogleLoginResponse>>
}