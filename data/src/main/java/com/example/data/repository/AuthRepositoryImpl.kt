package com.example.data.repository

import com.example.data.data_source_contract.AuthDataSource
import com.example.domain.common.ResultWrapper
import com.example.domain.model.GoogleLoginRequest
import com.example.domain.model.GoogleLoginResponse
import com.example.domain.model.user.UserResponse
import com.example.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource
) : AuthRepository {
    override suspend fun login(email: String, password: String): Flow<ResultWrapper<UserResponse>> {
        return dataSource.login(email, password)
    }

    override suspend fun googleLogin(request: GoogleLoginRequest): Flow<ResultWrapper<GoogleLoginResponse>> {
        return dataSource.googleLogin(request)
    }
}