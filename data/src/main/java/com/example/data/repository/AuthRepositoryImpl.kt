package com.example.data.repository

import com.example.data.data_source_contract.AuthDataSource
import com.example.domain.common.ResultWrapper
import com.example.domain.model.LoginRequest
import com.example.domain.model.user.UserResponse
import com.example.domain.repositories.AuthRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource
) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): Flow<ResultWrapper<UserResponse>> {
        return dataSource.login(loginRequest)
    }
}