package com.example.choresapp.Utilities

import com.example.choresapp.data.UserType

sealed interface LoginAction {
    data class OnSignIn(val result : SignInResult) : LoginAction
    data class OnSignUp(val result : SignUpResult) : LoginAction
    data class OnUsernameChange(val username : String) : LoginAction
    data class OnPasswordChange(val password : String) : LoginAction
    data object OnToggleIsRegister: LoginAction
    data class OnUserTypeChange(val userType: UserType) : LoginAction
}