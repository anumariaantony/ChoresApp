package com.example.choresapp.presentation.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.choresapp.Utilities.LoginAction
import com.example.choresapp.Utilities.UserAccountManager
import com.example.choresapp.data.LoginState
import com.example.choresapp.data.UserType
import com.example.choresapp.presentation.ui.theme.ColorSupport
import com.example.choresapp.presentation.ui.theme.Purple40
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(state: LoginState,
                onAction: (LoginAction) -> Unit,
                onLoggedIn: (String, UserType) -> Unit) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val userAccountManager = remember {
        UserAccountManager(context as ComponentActivity)
    }

    LaunchedEffect(key1 = state.loggedInUSer) {
        if (state.loggedInUSer != null) {
            onLoggedIn(state.loggedInUSer, state.userType)
        }
    }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val interactionSource = remember { MutableInteractionSource() }

    // Trigger sign-in pop-up when clicking the username field, but only in Login mode
    LaunchedEffect(interactionSource, state.isRegister) {
        if (!state.isRegister) {
            interactionSource.interactions.collectLatest { interaction ->
                if (interaction is PressInteraction.Release) {
                    val result = userAccountManager.signIn()
                    onAction(LoginAction.OnSignIn(result))
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(ColorSupport.gradient))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onAction(LoginAction.OnUserTypeChange(UserType.PARENT)) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (state.userType == UserType.PARENT) Purple40 else Color.White.copy(alpha = 0.5f),
                        contentColor = if (state.userType == UserType.PARENT) Color.White else Purple40
                    )
                ) {
                    Text("Parent")
                }
                Button(
                    onClick = { onAction(LoginAction.OnUserTypeChange(UserType.CHILD)) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (state.userType == UserType.CHILD) Purple40 else Color.White.copy(alpha = 0.5f),
                        contentColor = if (state.userType == UserType.CHILD) Color.White else Purple40
                    )
                ) {
                    Text("Child")
                }
            }

            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    usernameError = null
                },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                isError = usernameError != null,
                supportingText = { usernameError?.let { Text(it) } },
                interactionSource = interactionSource
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = null
                },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = passwordError != null,
                supportingText = { passwordError?.let { Text(it) } }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Login")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = state.isRegister,
                    onCheckedChange = {
                        onAction(LoginAction.OnToggleIsRegister)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Register")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        if (state.isRegister) {
                            val result = userAccountManager.signUp(username, password)
                            onAction(LoginAction.OnSignUp(result))
                        } else {
                            val result = userAccountManager.signIn()
                            onAction(LoginAction.OnSignIn(result))
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Purple40)
            ) {
                Text(if(state.isRegister) "Register as ${state.userType}" else "Login as ${state.userType}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            if(state.errorMessage != null) {
                Text(state.errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
