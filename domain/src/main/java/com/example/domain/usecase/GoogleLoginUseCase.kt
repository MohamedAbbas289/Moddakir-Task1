package com.example.domain.usecase

import com.example.domain.model.GoogleLoginRequest
import com.example.domain.repositories.AuthRepository
import javax.inject.Inject

class GoogleLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(request: GoogleLoginRequest) =
        authRepository.googleLogin(request)
}
