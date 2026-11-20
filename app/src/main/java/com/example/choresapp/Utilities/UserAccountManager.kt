package com.example.choresapp.Utilities

import android.app.Activity
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CreateCredentialResponse
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException

class UserAccountManager(private val activity: Activity) {
    private val credentialManager = CredentialManager.create(activity)

    suspend fun signUp(username: String, password: String) : SignUpResult {
        return try {
            val response: CreateCredentialResponse = credentialManager.createCredential(
                activity,
                CreatePasswordRequest(
                    username, password)
            )
            SignUpResult.Success(username)
        } catch (e: CreateCredentialCancellationException) {
            e.printStackTrace()
            SignUpResult.Cancelled
        } catch (e: CreateCredentialException) {
            e.printStackTrace()
            SignUpResult.Failure
        }
    }

    suspend fun signIn() : SignInResult {
        return try {
            val credentialResponse = credentialManager.getCredential(
                context = activity,
                request = GetCredentialRequest(
                    credentialOptions = listOf(GetPasswordOption())
                )
            )
            val credential = credentialResponse.credential as? PasswordCredential
                ?: return SignInResult.Failure

            //Make login API call here with credential.id and credential.password
            SignInResult.Success(credential.id)
        } catch (e: GetCredentialCancellationException) {
            e.printStackTrace()
            SignInResult.Cancelled
        } catch (e : NoCredentialException) {
            e.printStackTrace()
            SignInResult.NoCredentials
        } catch ( e : GetCredentialException) {
            e.printStackTrace()
            SignInResult.Failure
        }
    }
}