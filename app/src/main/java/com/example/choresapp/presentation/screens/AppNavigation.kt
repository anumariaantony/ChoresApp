package com.example.choresapp.presentation.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.choresapp.data.UserType
import com.example.choresapp.presentation.viewmodel.ChoresViewModel
import com.example.choresapp.presentation.viewmodel.LoginViewModel
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

@Serializable
data class LoggedInRoute(val username: String, val userType: UserType)

@Composable
fun AppNavigation() {

    val choresViewModel = viewModel<ChoresViewModel>()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = LoginRoute) {
        composable<LoginRoute> {
            val viewModel = viewModel<LoginViewModel>()
            LoginScreen(
                state = viewModel.state,
                onAction = viewModel::onAction,
                onLoggedIn = { username, userType ->
                    navController.navigate(LoggedInRoute(username, userType)) {
                        popUpTo(LoginRoute) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<LoggedInRoute> { backStackEntry ->
            val route: LoggedInRoute = backStackEntry.toRoute()
            ChoresScreen(
                choresViewModel = choresViewModel,
                username = route.username,
                userType = route.userType,
                onLogout = {
                    navController.navigate(LoginRoute) {
                        popUpTo(LoggedInRoute::class) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
