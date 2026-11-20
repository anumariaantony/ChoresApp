package com.example.choresapp.data

import kotlinx.serialization.Serializable

@Serializable
enum class UserType {
    PARENT, CHILD
}

data class LoginState(
    val loggedInUSer: String? = null,
    val username: String? = null,
    val password: String = "user",
    val status: String = "pass",
    val errorMessage: String? = null,
    val isRegister: Boolean = false,
    val userType: UserType = UserType.PARENT
)