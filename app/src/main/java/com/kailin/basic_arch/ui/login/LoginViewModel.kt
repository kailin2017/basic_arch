package com.kailin.basic_arch.ui.login

import androidx.lifecycle.*
import com.kailin.basic_arch.BuildConfig
import com.kailin.basic_arch.R
import com.kailin.basic_arch.api.login.LoginService
import com.kailin.basic_arch.app.DataStateViewModel
import com.kailin.basic_arch.data.RepoResult
import com.kailin.basic_arch.data.login.*
import com.kailin.basic_arch.model.user.UserInfo
import com.kailin.basic_arch.utils.SingleLiveEvent
import com.kailin.basic_arch.utils.connect.ConnectHelper
import com.kailin.basic_arch.utils.isEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : DataStateViewModel() {

    private val loginResult = loginRepository.observerLogin()
    private val _usernameError = MutableLiveData(R.string.inputText_blank)
    private val _passwordError = MutableLiveData(R.string.inputText_blank)
    private val _displayedPassword = MutableLiveData(false)
    private val _isShowDevLogin = MutableLiveData(BuildConfig.DEV_IS_SHOW_DEVLOGIN)

    val username = SingleLiveEvent<String>()
    val usernameError: LiveData<Int> = _usernameError
    val password = SingleLiveEvent<String>()
    val passwordError: LiveData<Int> = _passwordError
    val displayedPassword: LiveData<Boolean> = _displayedPassword
    val loginFinish: LiveData<UserInfo> =
        loginResult.distinctUntilChanged().switchMap { filterState(it) }
    val isShowDevLogin: LiveData<Boolean> = _isShowDevLogin

    init {
        _isLoading.addSource(loginResult.map { it is RepoResult.Loading }) {
            _isLoading.value = it
        }
    }

    fun switchPasswordDisplayed() {
        _displayedPassword.value = !_displayedPassword.value!!
    }

    fun login() {
        val username = username.value
        val password = password.value

        if (!loginInputValid(username, password)) {
            return
        }

        viewModelScope.launch {
            loginRepository.login(username!!, password!!)
        }
    }

    fun devLogin() {
        username.value = "test2@qq.com"
        password.value = "test1234qq"
        login()
    }

    private fun loginInputValid(username: String?, password: String?): Boolean {
        var usernameIsValid = false
        _usernameError.value = when {
            username.isNullOrEmpty() -> {
                R.string.login_username_error_empty
            }
            !username.isEmail() -> {
                R.string.login_username_error_invalid
            }
            else -> {
                usernameIsValid = true
                R.string.inputText_blank
            }
        }

        var passwordIsValid = false
        _passwordError.value = when {
            password.isNullOrEmpty() -> {
                R.string.login_password_error_empty
            }
            password.length < 6 -> {
                R.string.login_password_error_length
            }
            else -> {
                passwordIsValid = true
                R.string.inputText_blank
            }
        }

        return usernameIsValid && passwordIsValid
    }

    private fun filterState(result: RepoResult<UserInfo>): LiveData<UserInfo> {
        val liveData = MutableLiveData<UserInfo>()
        when (result) {
            is RepoResult.Success -> {
                liveData.value = result.data
            }
            is RepoResult.Error -> {
                _message.value = if (result.data != null) {
                    result.data.error
                } else {
                    result.exception.message
                }
            }
            else -> {

            }
        }
        return liveData
    }
}