package com.example.choresapp.Utilities

sealed interface SignUpResult {

    data class Success(val username :String) : SignUpResult

    data object Cancelled : SignUpResult

    data object Failure : SignUpResult

    data class UsernameAlreadyExists(val username :String) : SignUpResult


}