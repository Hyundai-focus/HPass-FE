package com.hyundai.hpass.socialLogIn.model.response

/**
 *
 * @author 최현서
 *
 */
data class LoginResponse (
    val accessToken: String,
    val refreshToken: String
)