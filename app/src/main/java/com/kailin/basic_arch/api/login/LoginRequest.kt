package com.kailin.basic_arch.api.login

data class LoginRequest(
    val username: String,
    val password: String,
) {
    override fun toString(): String {
        return "LoginReqData(username='$username', password='$password')"
    }
}
