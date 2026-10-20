package com.example.moddakirtask1.ui.login

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.domain.model.GoogleLoginRequest
import com.example.domain.model.user.UserResponse
import com.example.moddakirtask1.R
import com.example.moddakirtask1.databinding.ActivityLoginBinding
import com.example.moddakirtask1.ui.home.HomeScreen
import com.example.moddakirtask1.utils.showErrorMessage
import com.example.moddakirtask1.utils.showSuccessMessage
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID

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
            is LoginContract.Event.NavigateToHome -> navigateToHome(event.user)
            is LoginContract.Event.LaunchGoogleSignIn -> launchGoogleSignIn()
        }
    }

    private fun launchGoogleSignIn() {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(getString(R.string.default_web_client_id))
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credentialManager = CredentialManager.create(this)

        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = this@LoginActivity
                )
                handleGoogleSignInResult(result.credential)
            } catch (e: GetCredentialException) {
                showError(e.message ?: "Google Sign-In cancelled or failed")
            } catch (e: Exception) {
                showError(e.message ?: "Google Sign-In failed")
            }
        }
    }

    private fun handleGoogleSignInResult(credential: Credential) {
        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

            val deviceUUID = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                ?: UUID.randomUUID().toString()

            val firebaseAuth = FirebaseAuth.getInstance()
            val authCredential =
                GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)

            showLoading()
            firebaseAuth.signInWithCredential(authCredential)
                .addOnSuccessListener { authResult ->
                    val firebaseUser = authResult.user
                    val googleLoginRequest = GoogleLoginRequest(
                        email = firebaseUser?.email ?: googleIdTokenCredential.id,
                        providerUserId = firebaseUser?.uid ?: googleIdTokenCredential.id,
                        fullName = firebaseUser?.displayName ?: googleIdTokenCredential.displayName
                        ?: "User",
                        avatarUrl = firebaseUser?.photoUrl?.toString()
                            ?: googleIdTokenCredential.profilePictureUri?.toString(),
                        deviceUUID = deviceUUID,
                        phone = "+20001222345",
                        gender = "male",
                        providerType = "Google"
                    )
                    viewModel.invokeAction(LoginContract.Action.GoogleLogin(googleLoginRequest))
                }
                .addOnFailureListener {
                    val googleLoginRequest = GoogleLoginRequest(
                        email = googleIdTokenCredential.id,
                        providerUserId = googleIdTokenCredential.id,
                        fullName = googleIdTokenCredential.displayName ?: "User",
                        avatarUrl = googleIdTokenCredential.profilePictureUri?.toString(),
                        deviceUUID = deviceUUID,
                        phone = "+20001222345",
                        gender = "male",
                        providerType = "Google"
                    )
                    viewModel.invokeAction(LoginContract.Action.GoogleLogin(googleLoginRequest))
                }
        } else {
            showError("Unrecognized credential type")
        }
    }

    private fun navigateToHome(user: UserResponse? = null) {
        val intent = Intent(this, HomeScreen::class.java).apply {
            putExtra("USER_EXTRA", user)
        }
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
        binding.btnGoogleSignIn.setOnClickListener {
            viewModel.invokeAction(LoginContract.Action.GoogleSignInClicked)
        }
    }
}