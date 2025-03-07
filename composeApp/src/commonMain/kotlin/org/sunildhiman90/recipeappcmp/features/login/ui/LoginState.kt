package org.sunildhiman90.recipeappcmp.features.login.ui

sealed class LoginState {

    object Idle: LoginState()
    object Loading: LoginState()
    object Success: LoginState()
    data class Error(val message: String): LoginState()

}