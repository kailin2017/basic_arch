package com.kailin.basic_arch.data

data class RepoErrorData(
    val code: Int,
    val error: String
) {
    override fun toString(): String {
        return "RespData(code=$code, error='$error')"
    }
}