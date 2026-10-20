package com.example.moddakirtask1.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.domain.model.user.UserResponse
import com.example.moddakirtask1.R
import com.example.moddakirtask1.databinding.ActivityHomeScreenBinding
import com.example.moddakirtask1.ui.login.LoginActivity
import com.example.moddakirtask1.utils.showErrorMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initViews()
        initObservers()
        extractUserData()
    }

    private fun extractUserData() {
        val userExtra = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("USER_EXTRA", UserResponse::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("USER_EXTRA")
        }
        viewModel.invokeAction(HomeContract.Action.InitialData(userExtra))
    }

    private fun initViews() {
        binding = DataBindingUtil.setContentView<ActivityHomeScreenBinding>(
            this,
            R.layout.activity_home_screen
        )
        binding.vm = viewModel
        binding.lifecycleOwner = this

        binding.logoutBtn.setOnClickListener {
            viewModel.invokeAction(HomeContract.Action.Logout)
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.states.collect { renderViewStates(it) }
            }
        }
        viewModel.events.observe(this, ::handleEvents)

        viewModel.user.observe(this) { user ->
            val avatarUrl = user?.avatarUrl
            if (!avatarUrl.isNullOrBlank()) {
                Glide.with(this)
                    .load(avatarUrl)
                    .placeholder(R.drawable.google_ic)
                    .error(R.drawable.google_ic)
                    .into(binding.profileImage)
            } else {
                binding.profileImage.setImageResource(R.drawable.google_ic)
            }
        }
    }

    private fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.NavigateToLogin -> navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun renderViewStates(state: HomeContract.State) {
        when (state) {
            is HomeContract.State.Error -> showError(state.message)
            is HomeContract.State.Nothing -> return
        }
    }

    private fun showError(message: String) {
        showErrorMessage(message, longDuration = true)
    }
}