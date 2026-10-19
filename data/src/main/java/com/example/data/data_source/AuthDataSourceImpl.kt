package com.example.data.data_source

import com.example.data.api.WebServices
import com.example.data.data_source_contract.AuthDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.LoginRequest
import com.example.domain.model.user.UserResponse
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class AuthDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : AuthDataSource {
    override suspend fun login(loginRequest: LoginRequest): Flow<ResultWrapper<UserResponse>> {
        val response = safeApiCall {
            webServices.login(loginRequest).toUser()
        }
        return response
    }
}