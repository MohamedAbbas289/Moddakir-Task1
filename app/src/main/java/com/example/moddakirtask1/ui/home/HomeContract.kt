package com.example.moddakirtask1.ui.home

import androidx.lifecycle.LiveData
import com.example.domain.model.user.UserResponse
import kotlinx.coroutines.flow.StateFlow

class HomeContract {
    interface ViewModel {
        val states: StateFlow<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        class Error(val message: String) : State()
        data object Nothing : State()
    }

    sealed class Action {
        class InitialData(val user: UserResponse?) : Action()
        data object Logout : Action()
    }

    sealed class Event {
        data object NavigateToLogin : Event()
    }
}