package com.example.moddakirtask1.ui.login

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow

class LoginContract {
    interface ViewModel {
        val states: StateFlow<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class State {
        data object Loading : State()
        data object Success : State()
        class Error(val message: String) : State()
        data object Nothing : State()
    }

    sealed class Action {
        data object Login : Action()
    }

    sealed class Event {
        data object NavigateToHome : Event()
    }
}