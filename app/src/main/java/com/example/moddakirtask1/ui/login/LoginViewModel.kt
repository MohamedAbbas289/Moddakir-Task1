package com.example.moddakirtask1.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.GoogleLoginRequest
import com.example.domain.model.user.UserResponse
import com.example.domain.usecase.GoogleLoginUseCase
import com.example.domain.usecase.LoginUseCase
import com.example.domain.validation.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val googleLoginUseCase: GoogleLoginUseCase
) : ViewModel(), LoginContract.ViewModel {

    private val _states = MutableStateFlow<LoginContract.State>(
        LoginContract.State.Nothing
    )
    override val states: StateFlow<LoginContract.State> = _states.asStateFlow()

    private val _events = MutableLiveData<LoginContract.Event>()
    override val events: LiveData<LoginContract.Event> = _events

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()

    override fun invokeAction(action: LoginContract.Action) {
        when (action) {
            is LoginContract.Action.Login -> login()
            is LoginContract.Action.GoogleSignInClicked -> _events.postValue(LoginContract.Event.LaunchGoogleSignIn)
            is LoginContract.Action.GoogleLogin -> googleLogin(action.request)
        }
    }

    private fun googleLogin(request: GoogleLoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            _states.emit(LoginContract.State.Loading)
            googleLoginUseCase.invoke(request).collect { response ->
                when (response) {
                    is ResultWrapper.Error -> {
                        _states.emit(
                            LoginContract.State.Error(
                                response.error?.message ?: "Google login failed"
                            )
                        )
                    }

                    is ResultWrapper.Loading -> {
                        _states.emit(LoginContract.State.Loading)
                    }

                    is ResultWrapper.ServerError -> {
                        _states.emit(
                            LoginContract.State.Error(
                                response.serverError.serverMessage
                            )
                        )
                    }

                    is ResultWrapper.Success -> {
                        val apiResponse = response.data
                        val errorMessage = apiResponse.error
                        if (!errorMessage.isNullOrBlank()) {
                            _states.emit(LoginContract.State.Error(errorMessage))
                        } else {
                            _states.emit(LoginContract.State.Success)
                            val user = UserResponse(
                                fullName = request.fullName,
                                email = request.email,
                                avatarUrl = request.avatarUrl
                            )
                            _events.postValue(LoginContract.Event.NavigateToHome(user))
                        }
                    }
                }
            }
        }
    }

    private fun login() {
        if (!validForm()) return

        viewModelScope.launch(Dispatchers.IO) {
            _states.emit(LoginContract.State.Loading)
            loginUseCase.invoke(email.value!!, password.value!!)
                .collect { response ->
                    when (response) {
                        is ResultWrapper.Error -> {
                            _states.emit(
                                LoginContract.State.Error(
                                    response.error?.message ?: "An unknown error occurred"
                                )
                            )
                        }

                        is ResultWrapper.Loading -> {
                            _states.emit(LoginContract.State.Loading)
                        }

                        is ResultWrapper.ServerError -> {
                            _states.emit(
                                LoginContract.State.Error(
                                    response.serverError.serverMessage
                                )
                            )
                        }

                        is ResultWrapper.Success -> {
                            _states.emit(LoginContract.State.Success)
                            _events.postValue(LoginContract.Event.NavigateToHome(response.data))
                        }
                    }
                }
        }
    }

    private fun validForm(): Boolean {
        var isValid = true

        if (!InputValidator.isValidEmail(email.value)) {
            isValid = false
            emailError.value = "Please enter a valid email address"
        } else {
            emailError.value = null
        }
        if (!InputValidator.isValidPassword(password.value)) {
            isValid = false
            passwordError.value = "Password must contain at least 6 characters"
        } else {
            passwordError.value = null
        }
        return isValid
    }
}