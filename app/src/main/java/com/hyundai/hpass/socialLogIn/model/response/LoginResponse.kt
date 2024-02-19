package com.hyundai.hpass.socialLogIn.model.response

/**
 *
 * @author 최현서
 *
 */
data class LoginResponse (
    val isMember: Boolean,
    val memberName: String,
    val isSubscribed: Boolean,
    val accessToken: String,
    val refreshToken: String
)