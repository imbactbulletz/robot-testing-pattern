package com.imbactbulletz.robotpattern.login

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imbactbulletz.robotpattern.R

class LoginViewModel : ViewModel() {

    private val _loginScreenState: MutableLiveData<LoginScreenState> = MutableLiveData()
    val loginScreenState: LiveData<LoginScreenState> = _loginScreenState

    private var username = ""
    private var password = ""

    fun onUsernameEntered(value: String) {
        username = value
    }

    fun onPasswordEntered(value: String) {
        password = value
    }

    fun onLoginClicked() {
        _loginScreenState.value = when {
            username.isBlank() && password.isBlank() -> LoginScreenState.FailedLogin(R.string.empty_credentials)
            username.isBlank() -> LoginScreenState.FailedLogin(R.string.empty_username)
            password.isBlank() -> LoginScreenState.FailedLogin(R.string.empty_password)
            else -> {
                val areCredentialsValid = username == VALID_USERNAME && password == VALID_PASSWORD

                if (areCredentialsValid) {
                    LoginScreenState.SuccessfulLogin
                } else {
                    LoginScreenState.FailedLogin(
                        errorMessageResource = R.string.invalid_credentials
                    )
                }
            }
        }
    }

    companion object {
        private const val VALID_USERNAME = "demo"
        private const val VALID_PASSWORD = "demo"
    }
}

sealed class LoginScreenState {
    data class FailedLogin(
        @StringRes val errorMessageResource: Int
    ) : LoginScreenState()

    object SuccessfulLogin : LoginScreenState()
}