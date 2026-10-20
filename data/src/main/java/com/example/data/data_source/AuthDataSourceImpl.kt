package com.example.data.data_source

import com.example.data.api.WebServices
import com.example.data.data_source_contract.AuthDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.GoogleLoginRequest
import com.example.domain.model.GoogleLoginResponse
import com.example.domain.model.user.UserResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : AuthDataSource {
    override suspend fun login(email: String, password: String): Flow<ResultWrapper<UserResponse>> {
        val response = safeApiCall {
            webServices.login(email, password).toUser()
        }
        return response
    }

    override suspend fun googleLogin(request: GoogleLoginRequest): Flow<ResultWrapper<GoogleLoginResponse>> {
        val response = safeApiCall {
            webServices.socialLogin(request).toGoogleLoginResponse()
        }
        return response
    }
}