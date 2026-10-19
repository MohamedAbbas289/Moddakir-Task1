package com.example.moddakirtask1.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.moddakirtask1.R
import com.example.moddakirtask1.databinding.ActivityLoginBinding
import com.example.moddakirtask1.ui.home.HomeScreen
import com.example.moddakirtask1.utils.showErrorMessage
import com.example.moddakirtask1.utils.showSuccessMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initViews()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.states.collect { renderViewStates(it) }
            }
        }
        viewModel.events.observe(this, ::handleEvents)
    }

    private fun handleEvents(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.NavigateToHome -> navigateToHome()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
        finish()
    }

    private fun renderViewStates(state: LoginContract.State) {
        when (state) {
            is LoginContract.State.Error -> showError(state.message)
            is LoginContract.State.Loading -> showLoading()
            is LoginContract.State.Success -> loginSuccess()
            is LoginContract.State.Nothing -> binding.loadingIndicator.isVisible = false
        }
    }

    private fun loginSuccess() {
        binding.loadingIndicator.isVisible = false
        showSuccessMessage("Login successful")
    }

    private fun showLoading() {
        binding.loadingIndicator.isVisible = true
    }

    private fun showError(message: String) {
        binding.loadingIndicator.isVisible = false
        showErrorMessage(message, longDuration = true)
    }

    private fun initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.loginBtn.setOnClickListener {
            viewModel.invokeAction(LoginContract.Action.Login)
        }
    }
}