package com.example.domain.usecase

import com.example.domain.repositories.AuthRepository
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        authRepository.login(email, password)

}