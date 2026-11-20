package com.example.choresapp.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.choresapp.Utilities.LoginAction
import com.example.choresapp.Utilities.SignInResult
import com.example.choresapp.Utilities.SignUpResult
import com.example.choresapp.data.LoginState

class LoginViewModel : ViewModel() {

    var state by mutableStateOf(LoginState())

        private set

    fun onAction(action: LoginAction) {
        when(action) {
            is LoginAction.OnSignUp -> {
                when(action.result) {
                    SignUpResult.Failure -> {
                        state = state.copy(errorMessage = "Sign up failed")

                    }

                    SignUpResult.Cancelled -> {
                        state = state.copy(errorMessage = null)
                    }

                    is SignUpResult.Success -> {
                        state = state.copy(loggedInUSer = action.result.username)
                    }

                    else -> {
                        state = state.copy(errorMessage = "Sign up not allowed")
                    }
                }
            }
            is LoginAction.OnPasswordChange -> {
                state = state.copy(password = action.password)
            }
            is LoginAction.OnUsernameChange -> {
                state = state.copy(username = action.username)
            }
            is LoginAction.OnToggleIsRegister -> {
                state = state.copy(isRegister = !state.isRegister)
            }
            is LoginAction.OnUserTypeChange -> {
                state = state.copy(userType = action.userType)
            }
            is LoginAction.OnSignIn -> {
                when(action.result) {
                    SignInResult.Failure -> {
                        state = state.copy(errorMessage = "Sign in failed")

                    }

                    SignInResult.Cancelled -> {
                        state = state.copy(errorMessage = null)
                    }

                    is SignInResult.Success -> {
                        state = state.copy(loggedInUSer = action.result.username)
                    }

                    is SignInResult.NoCredentials -> {
                        state = state.copy(errorMessage = "No credentials were set up")
                    }

                    else -> {
                        state = state.copy(errorMessage = "Sign up not allowed")
                    }
                }

            }
        }
    }
}