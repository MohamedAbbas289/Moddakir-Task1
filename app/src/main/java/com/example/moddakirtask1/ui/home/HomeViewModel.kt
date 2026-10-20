package com.example.moddakirtask1.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.user.UserResponse
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel(), HomeContract.ViewModel {

    private val _states = MutableStateFlow<HomeContract.State>(
        HomeContract.State.Nothing
    )
    override val states: StateFlow<HomeContract.State> = _states.asStateFlow()

    private val _events = MutableLiveData<HomeContract.Event>()
    override val events: LiveData<HomeContract.Event> = _events

    val user = MutableLiveData<UserResponse?>()

    override fun invokeAction(action: HomeContract.Action) {
        when (action) {
            is HomeContract.Action.InitialData -> setInitialData(action.user)
            is HomeContract.Action.Logout -> logout()
        }
    }

    private fun setInitialData(userData: UserResponse?) {
        user.value = userData
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        _events.value = HomeContract.Event.NavigateToLogin
    }
}